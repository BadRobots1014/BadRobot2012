/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Helios;

/**
 * Automated ball gathering and shooting. Not currently used.
 * @author Team 1014 Programming Team
 * @deprecated
 */
public class GatherBallsAndAutoShoot extends GatherBallsAndManualShoot
{
    /**
     * Whether or not the turret is aligned with the target
     */
    private boolean aligned = false;
    /**
     * Whether or not the turret is firing
     */
    protected boolean shooting = false;
    /**
     * The beginning shooting speed
     */
    private double previousSpeed = .45;
    
    public GatherBallsAndAutoShoot() 
    {
        requires(ballGatherer);
        requires(shooter);
    }

    /**
     * Currently does nothing
     */
    protected void initialize() 
    {
    }

    /**
     * Automatically runs the shooter (not the turret)
     */
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

    /**
     * Runs the turret automatically
     */
    public void runTurretingOperations()
    {
        autoAlign();
    }
    

    /**
     * Records the number of iterations with a positive identification of the target
     */
    int count = 0;
    /**
     * Whether or not the turret is actively turning
     */
    boolean turning = false;
    /**
     * The distance in encoder clicks to the destination. Sign signifies direction
     */
    int destination = 0;
    /**
     * Automatically align the turret to the target using image tracking
     */
    public void autoAlign()
    {
        //If the camera isn't activated, don't turn
        if (!OI.cameraOn)
            return;

        //If there is no recorded target
        if (imageProcessor.getCoords() == null || imageProcessor.getCoords()[0] < 0)
        {
            aligned = false;
            turretTurn = 0;
            return;
        }

        //How far off the center axis the center of the target is
        double offAxis = 80 - imageProcessor.getCoords()[0];
        turretTurn = -offAxis; //TODO - callibrate

        //If the turret is on target
        if (Math.abs(turretTurn) < 8)
        {            
            count++;
            turretTurn = 0;
            aligned = false;
            //Wait for 3 positive results before declaring alignment
            if(count > 3)
                aligned = true;
        }
        else
        {
            count = 0;
            aligned = false;
        }

        //Update drivers station (now unused)
        OI.setDigitalOutput(1, aligned);

        if(aligned)
            return;
        
        if(turretTurn < 0)
            turretTurn = -1;
        else if(turretTurn > 0)
            turretTurn = 1;

        //turns to target, then refreshes target
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
                turning = false;
            }
        }
    }

    /**
     * This method is called to control the ball gatherer. It automatically collects balls at all times
     */
    public void runBallGathererOperations()
    {
        rollerIn = true;
        rollerOut = false;

        if(sensors.bottomChannelBlocked())
        {
            spaceUp = startingSpaceUp;
            conveyorUp = true;
        }
        if(spaceUp > 0)//space the ball
        {
            spaceUp--;
            conveyorUp = true;
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
        shooter.run(0);
    }
}
