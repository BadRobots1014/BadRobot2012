/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands.autonomousCommands;

import com.badrobots.y2012.technetium.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;



/*
 * @author 1014
 */
public class AutoDriveToWallGyroCorrection extends CommandBase {

    Timer timer;
    boolean finished;
    int distance;
    double startingAngle;
    double begin;

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
        begin = timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        driveTrain.mechanumWithGyro(0, .16, sensors.getGyro().getAngle());
        /*double currentAngle = sensors.getGyroAngle();
        System.out.println("Ultra: " + sensors.getUltraBackRange() + " : " + currentAngle);



        if(Math.abs(currentAngle - startingAngle) < 2)
            driveTrain.autoMechanumDrive(0, .3, 0);
        else if(currentAngle - startingAngle > 0)
            driveTrain.autoMechanumDrive(0, .3, -.16);
         else if(currentAngle - startingAngle < 0)
            driveTrain.autoMechanumDrive(0, .3, .16);*/


    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return sensors.closerThan(distance) || timer.getFPGATimestamp() - begin > 7;
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