/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import edu.wpi.first.wpilibj.Timer;



/*
 * @author 1014
 */
public class AutoDriveToWallGyroCorrection extends CommandBase {

    Timer timer;
    boolean finished;
    int distance;
    double startingAngle;

    public AutoDriveToWallGyroCorrection(int distanceFromWallMM) {
        requires(driveTrain);
        requires(sensors);
        requires(shooter);
        requires(ballGatherer);
        Timer timer = new Timer();
        finished = false;
        distance = distanceFromWallMM;

    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        startingAngle = sensors.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        double currentAngle = sensors.getGyroAngle();
        System.out.println("Ultra: " + sensors.getUltraBackRange());

        if(Math.abs(currentAngle - startingAngle) < 5)
            driveTrain.autoMechanumDrive(0, .16, 0);
        else if(currentAngle - startingAngle > 0)//TODO: Check to see if this convention needs flipping
            driveTrain.autoMechanumDrive(0, .16, .1);
         else if(currentAngle - startingAngle < 0)//TODO: Check to see if this convention needs flipping
            driveTrain.autoMechanumDrive(0, .16, -.1);


    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return sensors.closerThan(distance);
    }

    // Called once after isFinished returns true
    protected void end()
    {
        driveTrain.autoMechanumDrive(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {}
}