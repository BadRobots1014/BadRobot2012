/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.OI;

/**
 *
 * @author Jon Buckley, TheGaur
 */
public class MoveWithJoysticks extends CommandBase
{
    boolean tankDrive = false;
    public MoveWithJoysticks()
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        //tankDrive = OI.leftJoystick.getRawButton(10);

        if (tankDrive) driveTrain.tankDrive();
        else driveTrain.mechanumDrive();

        System.out.println("tank driving");

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