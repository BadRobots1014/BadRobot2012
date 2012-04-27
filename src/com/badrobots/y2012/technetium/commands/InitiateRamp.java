/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author 1014 Team
 */
public class InitiateRamp extends CommandBase
{
    protected double startTime = 0;
    protected double currentTime = 0;
    public static final double ARM_TIME = 2;
    public static final double DRIVE_TIME = 4;
    public static final double MOUNT_TIME = 3;
    
    protected boolean armDown = false;
    protected boolean driveForward = false;
    protected boolean slowArmDown = false;
    protected boolean slowDriveForward = false;
    
    protected static final int DEPLOY_ARM_PHASE = 1;
    protected static final int DRIVE_FORWARD_PHASE = 2;
    protected static final int COMPLETED_PHASE = 3;
    protected static final int MOUNT_RAMP_PHASE = 3;
    
    protected int phase = DEPLOY_ARM_PHASE;

    
    public InitiateRamp()
    {
        requires(driveTrain);
        requires(bridgeTool);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        driveTrain.tankDrive(0, 0);
        bridgeTool.setMotor(0);
        
        startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        currentTime = Timer.getFPGATimestamp();
        double timeDifference = currentTime - startTime;
        
        switch (phase)
        {
            case DEPLOY_ARM_PHASE:
            //if it is still in the period of time the arm should be moving
            if (timeDifference < ARM_TIME)
                armDown = true;
            //if the arm moving period has expired
            else
            {
                startTime = currentTime;
                phase = MOUNT_RAMP_PHASE;
            }
            break;

            case MOUNT_RAMP_PHASE:
            if(timeDifference < MOUNT_TIME)
            {
                armDown = false;
                driveForward = false;
                slowArmDown = true;
                slowDriveForward = true;
            }
            else
            {
                startTime = currentTime;
                phase = MOUNT_RAMP_PHASE;
            }
            break;

            case DRIVE_FORWARD_PHASE:
            //if it is now in the time period that the robot should be driving
            if (timeDifference < DRIVE_TIME)
            {
                driveForward = true;
                armDown = false;
                slowArmDown = false;
                slowDriveForward = false;
            }
            
            //if the robot driving time has expired
            else
            {
                phase = COMPLETED_PHASE;
                startTime = currentTime;
            }
                
            break;   
        } 
        
        
        if(armDown)
            bridgeTool.setMotor(.4);
        else
            bridgeTool.setMotor(-.4);
        
        if(driveForward)
            driveTrain.tankDrive(.3, .3);
        else
            driveTrain.tankDrive(0, 0);

        if(slowDriveForward)
            driveTrain.tankDrive(.2, .2);
        else
            driveTrain.tankDrive(0, 0);

        if(slowArmDown)
            bridgeTool.setMotor(.4);
        else
            bridgeTool.setMotor(-.4);


        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return (phase == COMPLETED_PHASE);
    }

    // Called once after isFinished returns true
    protected void end()
    {
        driveTrain.tankDrive(0, 0);
        bridgeTool.setMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        driveTrain.tankDrive(0, 0);
        bridgeTool.setMotor(0);
    }
}
