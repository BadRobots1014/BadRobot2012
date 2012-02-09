/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.sun.squawk.util.MathUtils;
import com.badrobots.y2012.technetium.OI;

/*
 * @author 1014 Programming Team
 */
public class PolarMechanumDrive extends CommandBase {
    
    public PolarMechanumDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    /*
     * Status: untested, faulty
     */
    protected void execute() 
    {
        double mag = Math.sqrt((OI.getRightX()*OI.getRightX()) + (OI.getRightY()*OI.getRightY()));
        double angle = MathUtils.atan(OI.getRightY()/OI.getRightX()) * (180/3.14159);
        
        System.out.println("Mag = " + mag + "  Angle = " + angle + " OI Right y " + OI.getRightY());
        
        driveTrain.polarMechanum(mag, angle, .2);
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
