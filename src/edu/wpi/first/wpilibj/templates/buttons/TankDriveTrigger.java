/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.commands.ManualAim;
import edu.wpi.first.wpilibj.templates.commands.TankDrive;

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
