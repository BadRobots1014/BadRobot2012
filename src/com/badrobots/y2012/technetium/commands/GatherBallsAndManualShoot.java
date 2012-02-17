package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Helios;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Relay;

/*
 * @author 1014 Programming Team
 */
public class GatherBallsAndManualShoot extends CommandBase //We need to rename this. Just maybe
{    
    private static boolean bottomWasBlocked = false;  //was the top garage sensor blocked?
    private static boolean topWasBlocked = false;
    private static boolean done = true;
    
    public GatherBallsAndManualShoot() 
    {
        requires(ballGatherer);
        requires(shooter);
    }

    protected void initialize() 
    {
        //if (Helios.getInstance().topChannelBlocked())
            //topBlocked = true;
    }

    /*
     * Gathers Balls -- if the garage door sensor is blocked, then a ball is in the
     * gatherer, and the conveyor system runs until the sensor is no longer obsscured.
     * 
     * If the user wants to shoot, he/she first hits the secondary trigger,
     * revving the shooter. Then, while the shooter is revved, if the primary trigger is
     * pressed, the conveyors roll, decreasing the tracked ball count
     *
     * LUCAS WARNING: Uncommon circumstances could throw off counts!!
     * There needs to be greater self correcting added after the collector is built.
     */
    protected void execute() 
    {
        System.out.println("Executing...");
        //#1 at bottom
       /* if (ballGatherer.numBalls() >= 3)   //if 3 or more balls, reject incoming ones
            ballGatherer.runBottomRoller(false, true);*/
        
        
        ballGatherer.bottomRoller.set(Relay.Value.kForward); //constantly runs the bottomRoller
            
        if (bottomWasBlocked)   //if the garage door sensor was/is blocked
        {
            System.out.println("bottom was blocked");
            ballGatherer.runConveyor(true, false);
           // ballGatherer.runBottomRoller(false, false); //stop movement
            
            if (Helios.getInstance().topChannelBlocked())   //if the ball has made it to the
            {                                               //top sensor, set some booleans
                ballGatherer.addBall();
                bottomWasBlocked = false;
                topWasBlocked = true;
                System.out.println("Top Channel Blocked");
            }
        } 
        else if (OI.secondXboxLB())   // if the sensor is blocked, set boolean to true
        {
            System.out.println("left bumper depressed, my good sir");
            bottomWasBlocked = true;
        }
        
        if (topWasBlocked)  // if ball is at top of sensor
        {
            shooter.run(1); //spin up
            
            if (OI.secondXboxRB()) // wait for input to push ball into shooter
            {
                if (Helios.getInstance().topChannelBlocked())   //run conveyor
                    ballGatherer.runConveyor(true, false);
                
                else    // if the sensor isnt blocked anymore, we are all done, ball has been shot
                {
                    topWasBlocked = false;
                    ballGatherer.removeBall();
                    done = true;
                }
            }
            
        }

        
        /*if (OI.getSecondaryTrigger())   //warm up the shooter -- think gatling gun
        {                           

            shooter.run(1);
            
            if (OI.getPrimaryTrigger()) // push balls into shooter
            {
                //System.out.println("running conveyor");
                ballGatherer.runConveyor(true, false);
                
                //#2 at bottom
            }
            else
            {
                ballGatherer.runConveyor(OI.secondXboxX(), OI.secondXboxB());
                ballGatherer.runBottomRoller(OI.secondXboxA(), OI.secondXboxY());
            }
        }
        else
        {
            shooter.run(0);
            ballGatherer.runConveyor(OI.secondXboxX(), OI.secondXboxB());
            ballGatherer.runBottomRoller(OI.secondXboxA(), OI.secondXboxY());
        }*/

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        //ballGatherer.runBottomRoller(false, false);
        //ballGatherer.runConveyor(false, false);
        //shooter.run(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
//2

/*if (Helios.getInstance().topChannelBlocked())   // ball enters loading zone
                    topBlocked = true;

                if (topBlocked && !Helios.getInstance().topChannelBlocked()) //ball leaves loading zone
                {
                    ballGatherer.notifyBallShot();
                    topBlocked = false;
                }*/
