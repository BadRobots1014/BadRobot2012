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
public class MecanumDrive extends CommandBase
{
<<<<<<< HEAD:src/edu/wpi/first/wpilibj/templates/commands/MecanumDrive.java

    public MecanumDrive()
=======
    boolean tankDrive = false;
    public MoveWithJoysticks()
>>>>>>> 42e4f51cae6143567c44ee8bab5fe49d6c9ef511:src/edu/wpi/first/wpilibj/templates/commands/MoveWithJoysticks.java
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
<<<<<<< HEAD:src/edu/wpi/first/wpilibj/templates/commands/MecanumDrive.java
        driveTrain.mecanumDrive();
=======
        //tankDrive = OI.leftJoystick.getRawButton(10);

        if (tankDrive) driveTrain.tankDrive();
        else driveTrain.mechanumDrive();

        System.out.println("tank driving");

>>>>>>> 42e4f51cae6143567c44ee8bab5fe49d6c9ef511:src/edu/wpi/first/wpilibj/templates/commands/MoveWithJoysticks.java
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