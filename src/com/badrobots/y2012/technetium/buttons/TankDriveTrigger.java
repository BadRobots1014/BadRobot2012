/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.badrobots.y2012.technetium.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.ManualAim;
import com.badrobots.y2012.technetium.commands.TankDrive;

/**
 *
 * @author Jon Buckley
 */
public class TankDriveTrigger extends Button
{
    public TankDriveTrigger()
    {
       super.whenPressed(new TankDrive());
    }

    public boolean get()
    {
       if (OI.leftJoystick.getTrigger() || OI.controller.getRawButton(1))
           return true;

       return false;
    }

}
