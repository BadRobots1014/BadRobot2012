/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.Balance;
import com.badrobots.y2012.technetium.commands.GatherBallsAndAutoShoot;
import com.badrobots.y2012.technetium.commands.GatherBallsAndManualShoot;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 * @author Jon Buckley
 */
public class TrackingButton extends Button
{
    boolean once = false;
//NEEDS TESTING
    public TrackingButton()
    {
        
        super.whenPressed(new GatherBallsAndAutoShoot());
    }

    public boolean get()
    {
        if (OI.secondXboxBButton())
        {
            System.out.println("B button hit-----");

            once = true;
            return true;
        }
        else if(once)
        {
            System.out.println("B button released-----");

            Scheduler.getInstance().add(new GatherBallsAndManualShoot());
            once = false;
        }
        return false;
    }


}
