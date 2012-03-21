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
    protected boolean shooting = false;
    
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
        
        autoSpeed = true;
    }
    
    public void runShootingOperations()
    {
        //if lined up 
        if (aligned)
        {
            // if aligned and there is more than zero balls available
            if (Helios.getInstance().getNumBalls() > 0)
            {
                shooterSpeed = 1;//TODO change speed to scale with depth
                conveyorUp = true;
                
                //if a ball is ready to be shot
                if (Helios.getInstance().topChannelBlocked())
                    shooting = true;
                
                //ball is no longer blocking the topSensor, decrease ball count and sets shooting to false
                else if (shooting)
                {
                    conveyorUp = false;
                    shooting = false;
                }
            }        
        }
    }
    
    public void runTurretingOperations()
    {
        autoAlign();
    }
    
    public void autoAlign()
    {
        turretTurn = kinecter.getOffAxis()[0]*(kinecter.getDepth()[0]/3657); //TODO - callibrate
        
        if (turretTurn == 0) //aligned with basket
        {            
            aligned = true;
            System.out.println("lined up! -- Artemis execute()");
        }
        
        turretTurn/=50;
    }
    
    public void runBallGathererOperations()
    {
        autoControl();
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
