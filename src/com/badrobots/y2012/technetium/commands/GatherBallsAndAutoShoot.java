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
    private boolean aligned = false;
    protected boolean shooting = false;
    private double previousSpeed = .45;
    
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
    }
    
    public void runShootingOperations()
    {
        //if lined up 
        if (aligned)
        {
            System.out.println("Shooting");//TODO change speed to scale with depth
            //conveyorUp = true;
        }
        else
        {
            shooterSpeed = 0;
        }
        if (OI.getPrimaryTrigger()) // push balls into shooter
        {
            conveyorUp = true;
            shooterSpeed = previousSpeed;
        }
        else
            conveyorUp = false;

    }
    
    public void runTurretingOperations()
    {
        //System.out.println("turretting");
        autoAlign();
    }
    
   /* protected void execute()
    {
        System.out.println("Executing");
        runBallGathererOperations();
        runTurretingOperations();
        runShootingOperations();
    }*/
    int count = 0;
    boolean turning = false;
    int destination = 0;
    public void autoAlign()
    {
        if (!OI.cameraOn)
            return;
        
        if (imageProcessor.getCoords() == null || imageProcessor.getCoords()[0] < 0)
        {
            aligned = false;
            turretTurn = 0;
            return;
        }
        
        System.out.println("coords: " + imageProcessor.getCoords()[0]);
        
        double offAxis = 80 - imageProcessor.getCoords()[0];
        turretTurn = -offAxis; //TODO - callibrate
        
        if (Math.abs(turretTurn) < 8)
        {            
            count++;
            System.out.println("lined up! -- Artemis execute()");
            turretTurn = 0;
            aligned = false;
            if(count > 3)
                aligned = true;
        }
        else
        {
            count = 0;
            aligned = false;
        }

        if(aligned)
            return;
        
        if(turretTurn < 0)
            turretTurn = -1;
        else if(turretTurn > 0)
            turretTurn = 1;

        if(!turning)
        {
            destination = shooter.encoderValue() + (int)turretTurn * 15 ;
            shooter.turnByEncoderTo(destination);
            turning = true;
        }
        else
        {
            if(shooter.turnByEncoderTo(destination))
            {
                System.out.println("Arrived");
                turning = false;
            }
        }

        /*System.out.println("TurretTurn: " + turretTurn);
        if(turretTurn > .2)
            turretTurn = .2;
        else if(turretTurn < -.2)
            turretTurn = -.2;
        if(turretTurn < .05 && turretTurn > 0 && !aligned)
            turretTurn = .05;
        if(turretTurn > -.05 && turretTurn < 0 && !aligned)
            turretTurn = -.05;
         * */

    }
    
    public void runBallGathererOperations()
    {
        autoControl();
    }
    
    public void autoControl()
    {
        //conveyorUp = false;
        //conveyorDown = false;
        rollerIn = true;
        rollerOut = false;

         //Ball Spacing
        /*if(Helios.getInstance().getNumBalls() < 3)
        {
            //System.out.println("Spacing");
            conveyorUp = false;
            conveyorDown = false;
            rollerIn = false;
            rollerOut = false;

            rollerIn = true;
            conveyorUp = false;
            */
            if(sensors.bottomChannelBlocked())
            {
                spaceUp = 35;//was 20
                conveyorUp = true;
            }
        //}

        if(spaceUp > 0)//space the ball
        {
            spaceUp--;
            conveyorUp = true;
        }

        //change speed

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
