/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;


/*
 * @author 1014 Programming Team
 */
public class AutoOrient extends CommandBase
{
    double distance = 0;
    double wantedAngle, currentAngle;

    public AutoOrient(double a)
    {
        requires(sensors);
        requires(driveTrain);
        wantedAngle = a;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        currentAngle = sensors.getGyro();
        wantedAngle += currentAngle;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        currentAngle = sensors.getGyro();
        if (currentAngle > wantedAngle)
            driveTrain.autoMechanumDrive(0, 0, -.3);
        else if (currentAngle < wantedAngle)
            driveTrain.autoMechanumDrive(0, 0, .3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
       return Math.abs(currentAngle - wantedAngle) < 5;//TODO: THIS REALLY NEEDS TO BE CALIBRATED
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        driveTrain.autoMechanumDrive(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        driveTrain.autoMechanumDrive(0, 0, 0);
    }
}