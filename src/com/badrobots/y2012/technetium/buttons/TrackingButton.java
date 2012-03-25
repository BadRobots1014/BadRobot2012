/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.Balance;
import com.badrobots.y2012.technetium.commands.GatherBallsAndAutoShoot;
import com.badrobots.y2012.technetium.commands.GatherBallsAndManualShoot;
import com.badrobots.y2012.technetium.subsystems.Demeter;
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
        if (Demeter.getInstance().manualOverride())
        {
            System.out.println("manual mode, no button");
            return false;
        }
        
        if (OI.secondXboxBButton())
        {
            System.out.println("button pressed - trackingbutton.java");
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
