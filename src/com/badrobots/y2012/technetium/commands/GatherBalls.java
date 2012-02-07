package com.badrobots.y2012.technetium.commands;

/**
 *
 * @author Jon Buckley
 */
public class GatherBalls extends CommandBase 
{    
    private boolean wasBlocked = false; //Was the Channel blocked a second ago?
    private int iterations = 0;         //counter variable
    private static int maxIterations = 7;     //number of loops to go through before
                                               //returning to "ball pickup"  mode
    
    public GatherBalls() 
    {
        requires(ballGatherer);
    }

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
        ballGatherer.runBottomRoller(.2);   //"Ball pickup" mode
        
        if (ballGatherer.channelBlocked())  //"Ball convey" mode
        {
            ballGatherer.runConveyor(.5);
            wasBlocked = true;
        }
        
        /*
         *Enters the folllowing if statement 7 times after the channel becomes unblocked
         *After the 7th time, we assume the ball has successfully been pulled to the top
         *and the conveyor system stops, and wasBlocked is returned to false
         *"Ball complete gather" mode
         */
        else if (!ballGatherer.channelBlocked() && wasBlocked == true && iterations < maxIterations)   
        {
            iterations++;
            ballGatherer.runTopRoller(.5);
            
            if (iterations == maxIterations)
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
