/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import edu.wpi.first.wpilibj.Timer;



/**
 *
 * @author 1014
 */
public class DriveToWall extends CommandBase {

    Timer timer;
    boolean finished;

    public DriveToWall() {
        requires(driveTrain);
        requires(sensors);
        requires(shooter);
        requires(ballGatherer);
        Timer timer = new Timer();
        finished = false;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        System.out.println("Ultra: " + sensors.getUltraBackRange());
        if(sensors.getUltraBackRange() < 1000 && sensors.getUltraBackRange() > 750)
        {
            driveTrain.autoMechanumDrive(0, 0, 0);
        }
        else if(sensors.getUltraBackRange() <= 750)
        {
            driveTrain.autoMechanumDrive(0,-.16,0);
        }
        else
            driveTrain.autoMechanumDrive(0,.16,0);
        if(sensors.getUltraBackRange() < 1020 && sensors.getUltraBackRange() > 730)
        {
            driveTrain.autoMechanumDrive(0, 0, 0);
            shooter.turn(1);
            timer.delay(.75);
            shooter.turn(0);
            shooter.shootHigh();
            timer.delay(2);
            shooter.run(0);
            finished = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished; //sensors.closerThan(900);
    }

    // Called once after isFinished returns true
    protected void end() {

        driveTrain.autoMechanumDrive(0, -.1, 0);
        Timer.delay(.25);
        driveTrain.autoMechanumDrive(0, 0, 0);
        System.out.println("OH DEAR GOD STOOOOOOPPPPPPP!!!!!!!!!!");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}