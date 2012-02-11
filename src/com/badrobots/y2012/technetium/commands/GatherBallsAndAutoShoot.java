/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.subsystems.Helios;

/**
 *
 * @author Team 1014 Programming Team
 */
public class GatherBallsAndAutoShoot extends CommandBase 
{
    private boolean topBlocked = false;
    private boolean aligned = false;
    
    public GatherBallsAndAutoShoot() 
    {
        requires(ballGatherer);
        requires(shooter);
    }

    /*
     * Checks to see if the top garage sensor is already blocked, sets topBlocked to 
     * true, if it is
     */
    protected void initialize() 
    {
        if (Helios.getInstance().topChannelBlocked())
            topBlocked = true;
    }

    /*
     * Picks up balls the same way as GatherBallsAndManualShoot. When it has three balls,
     * it will shoot off a ball each time it senses it is aligned with the hoop.
     */
    protected void execute()
    {
        /*if (ballGatherer.numBalls() >= 3)
        {
            ballGatherer.runBottomRoller(0);
            
            if (aligned) //replace with condition of when the turret is lined up for a shot
            {
                shooter.run(1);
                ballGatherer.runConveyor(.5);
            }
           
        }
        
        else if (ballGatherer.numBalls() < 3)
            ballGatherer.runBottomRoller(.2);   //"Ball pickup" mode
        
        //If the bottom garage sensor is blocked, and the top isn't blocked, pull the ball until
        //it is no longer blocking the bottom sensor
        if (Helios.getInstance().bottomChannelBlocked() && !topBlocked)
        {
            ballGatherer.runConveyor(.5);//WARNING: This may cause more than 1 ball to be picked up
            ballGatherer.addBall();
        }
        */
        
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
