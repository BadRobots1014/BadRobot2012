/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;
import com.badrobots.y2012.technetium.OI;


/*
 * @author 1014 Programming Team
 */
public class SwitchSpeeds extends CommandBase {

    private static boolean low = false;
    int i = 0;
    public SwitchSpeeds()
    {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        i++;
        System.out.println("Changed: " + low + " " + i);
        if (!low)
        {
            OI.setScalingFactor(.5);
            low = true;
        }
        else
        {
            OI.setScalingFactor(1);
            low = false;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return true;//may require debugging here
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}