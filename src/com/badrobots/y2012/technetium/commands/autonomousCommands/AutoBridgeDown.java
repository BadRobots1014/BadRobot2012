/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands.autonomousCommands;

import com.badrobots.y2012.technetium.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;


/**
 *
 * @author 1014
 */
public class AutoBridgeDown extends CommandBase {
    Timer timer;
    boolean once;

    public AutoBridgeDown() {
        requires(bridgeTool);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        once = false;
        timer = new Timer();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        timer.delay(.3);
        bridgeTool.setMotor(1);
        timer.delay(1.2);
        bridgeTool.setMotor(0);

        once = true;

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}