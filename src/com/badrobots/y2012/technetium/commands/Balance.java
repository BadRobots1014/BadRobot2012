/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.subsystems.Helios;

/**
 *
 * @author Jon Buckley
 */
public class Balance extends CommandBase
{
    int step = 0;
    int threshold = 7;
    double angled = 0;
    
    public Balance() 
    {
        requires(driveTrain);
    }

    /*
     * Resets the gyro so that it has a current heading. If the gyro reading is too great 
     * to begin with (greater than 15 degrees) it cancels the command.
     */
    protected void initialize() 
    {
        if (Math.abs(Helios.getInstance().getVerticalGyro()) > 15) 
        {
            end();
            return;
        }
        
        Helios.getInstance().resetVerticalGyro();
    }

    /*
     * For threshold number of steps, the robot moves forward at a slowish clip.
     * Then, at threshold step, the robot continues to move at a slower speed and
     * only if the angle is not starting to fall (the ramp starts to fall).
     */
    protected void execute() 
    {
        if (step < threshold)
        {
            step++;
            driveTrain.tankDrive(.2, .2);
            
            angled = Helios.getInstance().getVerticalGyro();
        }
        
        else 
        {
            step++;
            driveTrain.tankDrive(.15, .15);
        }
        
    }
    

    /*
     * If the ramp starts to fall down, stop moving!!
     */
    protected boolean isFinished() 
    {
        return (Math.abs(Helios.getInstance().getVerticalGyro()) - angled > 5);
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        driveTrain.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
