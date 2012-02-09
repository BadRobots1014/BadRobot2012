/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;


/**
 *
 * @author Jon Buckley, Lucas Beaufore
 */
public class AutoOrient extends CommandBase
{
    double distance = 0;

    public AutoOrient()
    {
       // requires(sensors);
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        distance = sensors.getDifferenceInSensors();

        if (distance > 0)
            driveTrain.autoMechanumDrive(0, 0, .5); //We must test
        
        else if (distance < 0)
            driveTrain.autoMechanumDrive(0, 0, -.5); //Testtt testt testtttt
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
       // if (sensors.getDifferenceInSensorsFromWall(true) < 3) // difference is negligble
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