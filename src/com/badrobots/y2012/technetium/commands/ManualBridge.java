/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;

/**
 *
 * @author Jon Buckley
 */
public class ManualBridge extends CommandBase
{
    
    public ManualBridge()
    {
        requires(bridgeTool);
        System.out.println("constructed manbridge");
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        System.out.println("initializing ManualBridge");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        double speed = 0;
        
        if (OI.primaryXboxAButton())
            speed = 1;
        else if (OI.primaryXboxBButton())
            speed = -1;   
        bridgeTool.setMotor(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    }
}
