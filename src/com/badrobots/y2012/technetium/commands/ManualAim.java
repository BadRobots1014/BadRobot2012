/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;
import com.badrobots.y2012.technetium.buttons.ShootBallTrigger;
import edu.wpi.first.wpilibj.buttons.Button;


/*
 * @author 1014 Programming Team
 */
public class ManualAim extends CommandBase
{
    private static Button shootBallTrigger;

    public ManualAim()
    {
       requires(shooter);
       shootBallTrigger = new ShootBallTrigger();
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        
    }

    /*
     * Repeatedly obtains the Shooter Joystick's values and adjusts the shooter;
     * if the trigger is pulled, it shoots the ball
     */
    protected void execute() 
    {
        if (shootBallTrigger.get())
        {
            if (ballGatherer.numBalls() > 0)
            {
                shooter.shootHigh();    
                ballGatherer.removeBall();
            }
        }
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
    protected void interrupted() 
    {
        
    }
}