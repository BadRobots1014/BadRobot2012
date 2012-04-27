/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands.autonomousCommands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;


/**
 *
 * @author 1014
 */
public class AutoDriveBack extends CommandBase {

    Timer timer;
    boolean done = false;
    double startingAngle;
    double startTime;
    public AutoDriveBack() {
        timer = new Timer();
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        startTime = timer.getFPGATimestamp();
        startingAngle = sensors.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        double currentAngle = sensors.getGyroAngle();
        if(Math.abs(currentAngle - startingAngle) < 5)
            driveTrain.autoMechanumDrive(0, -.3, 0);
        else if(currentAngle - startingAngle > 0)
            driveTrain.autoMechanumDrive(0, -.3, -.16);
         else if(currentAngle - startingAngle < 0)
            driveTrain.autoMechanumDrive(0, -.3, .16);

        done = true;

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return  (timer.getFPGATimestamp() - startTime) > OI.getAnalogIn(3);
    }

    // Called once after isFinished returns true
    protected void end() {
        driveTrain.autoMechanumDrive(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        driveTrain.autoMechanumDrive(0, 0, 0);
    }
}