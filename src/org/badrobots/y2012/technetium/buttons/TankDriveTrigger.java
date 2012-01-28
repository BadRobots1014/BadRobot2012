/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.badrobots.y2012.technetium.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import org.badrobots.y2012.technetium.OI;
import org.badrobots.y2012.technetium.commands.ManualAim;
import org.badrobots.y2012.technetium.commands.TankDrive;

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
       if (OI.leftJoystick.getTrigger())
           return true;

       return false;
    }

}
