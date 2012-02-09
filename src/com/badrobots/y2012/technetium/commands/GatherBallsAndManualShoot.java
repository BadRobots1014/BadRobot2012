package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Helios;

/**
 *
 * @author Jon Buckley
 */
public class GatherBallsAndManualShoot extends CommandBase 
{    
    private static boolean topBlocked = false;  //was the top garage sensor blocked?
    
    public GatherBallsAndManualShoot() 
    {
        requires(ballGatherer);
        requires(shooter);
    }

    protected void initialize() 
    {
        if (Helios.getInstance().topChannelBlocked())
            topBlocked = true;
    }

    /*
     * Gathers Balls -- if the garage door sensor is blocked, then a ball is in the
     * gatherer, and the conveyor system runs until the sensor is no longer obsscured.
     * 
     * If the user wants to shoot, he/she first hits the secondary trigger,
     * revving the shooter. Then, while the shooter is revved, if the primary trigger is
     * pressed, the conveyors roll, decreasing the tracked ball count  
     */
    protected void execute() 
    {
        if (ballGatherer.getBalls() < 3) 
            ballGatherer.runBottomRoller(.2);   //"Ball pickup" mode
        
        //If the bottom garage sensor is blocked, and the top isn't blocked, pull the ball until
        //it is no longer blocking the bottom sensor
        if (Helios.getInstance().bottomChannelBlocked() && !topBlocked)
        {
            ballGatherer.runConveyor(.5);
            ballGatherer.addBall();
        }
        
        if (OI.getSecondaryTrigger())   //warm up the shooter -- think gatling gun
        {
            shooter.run(1);
            
            if (OI.getShooterTrigger()) // push balls into shooter
            {
                ballGatherer.runConveyor(.2);
                
                if (Helios.getInstance().topChannelBlocked())   // ball enters loading zone
                    topBlocked = true;
                
                if (topBlocked && !Helios.getInstance().topChannelBlocked()) //ball leaves loading zone
                {
                    ballGatherer.notifyBallShot();
                    topBlocked = false;
                }
            }
        }
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
