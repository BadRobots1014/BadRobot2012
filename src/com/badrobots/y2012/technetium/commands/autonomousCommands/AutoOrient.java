/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands.autonomousCommands;

import com.badrobots.y2012.technetium.commands.CommandBase;


/*
 * @author 1014 Programming Team
 */
public class AutoOrient extends CommandBase
{
    double count = 0;
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
        currentAngle = sensors.getGyroAngle();
        wantedAngle += currentAngle;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        currentAngle = sensors.getGyroAngle();
        //System.out.println("Angle " + currentAngle);
        if(Math.abs(currentAngle - wantedAngle) < 3)
        {
            count++;
            driveTrain.autoMechanumDrive(0, 0, 0);
        }
        else if(currentAngle > wantedAngle)
        {
            driveTrain.autoMechanumDrive(0, 0, -.3);
            count = 0;
        }
        else if (currentAngle < wantedAngle)
        {
            driveTrain.autoMechanumDrive(0, 0, .3);
            count = 0;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
       return count > 50;//TODO: THIS REALLY NEEDS TO BE CALIBRATED
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