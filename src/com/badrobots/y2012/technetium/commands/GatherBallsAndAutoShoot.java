/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Helios;

/**
 *
 * @author Team 1014 Programming Team
 */
public class GatherBallsAndAutoShoot extends GatherBallsAndManualShoot
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
        if (Helios.topSensor != null)
        {
            if (Helios.getInstance().topChannelBlocked())
                topBlocked = true;
        }      
    }

    /*
     * Picks up balls the same way as GatherBallsAndManualShoot. When it has three balls,
     * it will shoot off a ball each time it senses it is aligned with the hoop.
     */
    protected void execute()
    {
        double turn = kinecter.getOffAxis()[0]*(kinecter.getDepth()[0]/3657); //TODO - callibrate
        
        if (turn == 0) //aligned with basket
        {            
            aligned = true;
            System.out.println("lined up! -- Artemis execute()");
        }
        
        else 
        {
            shooter.turn(turn/50); //a simple linear function of turn, at 50 turn, it turns full speed
        }
        
    }
    
    public void autoControl()
    {
        conveyorUp = false;
        conveyorDown = false;
        rollerIn = false;
        rollerOut = false;

         //Ball Spacing
        if(Helios.getInstance().getNumBalls() < 3)
        {
            System.out.println("Spacing");
            conveyorUp = false;
            conveyorDown = false;
            rollerIn = false;
            rollerOut = false;

            rollerIn = true;
            conveyorUp = false;

            if(sensors.bottomChannelBlocked())
            {
                spaceUp = 35;//was 20
                conveyorUp = true;
            }
        }

        if(spaceUp > 0)//space the ball
        {
            spaceUp--;
            conveyorUp = true;
        }

        //change speed

        if(aligned)//run with the manual control
        {
            shooterSpeed = 1;
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
