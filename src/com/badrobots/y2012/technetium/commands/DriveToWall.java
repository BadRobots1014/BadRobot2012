/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import edu.wpi.first.wpilibj.Timer;



/**
 * Drives the robot to a specific distance from a wall. For fun only, use AutoDriveBack for autonomous.
 * @see AutoDriveBack
 * @author 1014 Programming team
 */
public class DriveToWall extends CommandBase {

    Timer timer;
    boolean finished;
    int distance;
    /**
     * Creates the command to drive to the wall
     * @param distanceFromWall The distance in mm to maintain from the wall
     */
    public DriveToWall(int distanceFromWall) {
        requires(driveTrain);
        requires(sensors);
        requires(shooter);
        requires(ballGatherer);
        Timer timer = new Timer();
        finished = false;
        distance = distanceFromWall;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        driveTrain.autoMechanumDrive(0, .16, 0);
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