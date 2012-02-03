/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;

/**
 *
 * @author Jon Buckley, TheGaur, Lucas Beaufore
 */
public class MechanumDrive extends CommandBase
{

    public MechanumDrive()

    {
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    public static boolean stickyGyro = false;
    protected void execute() 
    {
        driveTrain.mechanumDrive(); // controls whether you want gyro to stick the robot in one orientation
        stickyGyro = false;
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