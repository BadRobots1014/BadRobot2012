/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

/**
 *
 * @author Jon Buckley
 */
public class GatherBalls extends CommandBase 
{    
    private boolean wasBlocked = false;
    private int iterations = 0;
    public GatherBalls() 
    {
        requires(ballGatherer);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    /*
     * Gathers Balls -- if the garage door sensor is blocked, then a ball is in the
     * gatherer, and the conveyor system runs until the sensor is no longer obsscured.
     * Then, the top roller activates, pulling the ball out of the conveyor and onto 
     * the shooter platform. The bottomRoller constantly runs at a low speed (.2) until 
     * a ball is sensed in the gatherer.
     */
    protected void execute() 
    {
        ballGatherer.runBottomRoller(.2);
        
        if (ballGatherer.channelBlocked())      
        {
            ballGatherer.runConveyor(.5);
            wasBlocked = true;
        }
        
        else if (!ballGatherer.channelBlocked() && wasBlocked == true && iterations < 7)
        {
            iterations++;
            ballGatherer.runTopRoller(.5);
            
            if (iterations == 7)
            {
                wasBlocked = false;
                iterations = 0;
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
