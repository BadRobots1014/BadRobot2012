package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Helios;

/*
 * @author 1014 Programming Team
 */
public class GatherBallsAndManualShoot extends CommandBase //We need to rename this. Just maybe
{    
    private static boolean topBlocked = false;  //was the top garage sensor blocked?
    
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
        /*if (ballGatherer.numBalls() < 3)
            ballGatherer.runBottomRoller(.2);   //"Ball pickup" mode
        
        //If the bottom garage sensor is blocked, and the top isn't blocked, pull the ball until
        //it is no longer blocking the bottom sensor
        if (Helios.getInstance().bottomChannelBlocked() && !topBlocked)
        {
            ballGatherer.runConveyor(.5);//WARNING: This may cause more than 1 ball to be picked up
            ballGatherer.addBall();
        }*/
        
        if (OI.leftJoystick.getTrigger())
        {
            System.out.println("Trigger shoot");
            shooter.run(1);
        }
        
        System.out.println(OI.getSecondaryTrigger());
        if (OI.getSecondaryTrigger() || OI.leftJoystick.getRawButton(9))   //warm up the shooter -- think gatling gun
        {                           //Why wouldn't this run all the time? Power? 
                                    // @reply: yup. and noise. just impractical
            shooter.run(1);
            System.out.println("run shooter");
            
            if (OI.getPrimaryTrigger()) // push balls into shooter
            {
                System.out.println("running conveyor");
                //ballGatherer.runConveyor(true);
                
                /*if (Helios.getInstance().topChannelBlocked())   // ball enters loading zone
                    topBlocked = true;
                
                if (topBlocked && !Helios.getInstance().topChannelBlocked()) //ball leaves loading zone
                {
                    ballGatherer.notifyBallShot();
                    topBlocked = false;
                }*/
            }
        }
        ballGatherer.runConveyor(OI.secondXboxB(), OI.secondXboxY());
        ballGatherer.runBottomRoller(OI.secondXboxX(), OI.secondXboxRB());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
