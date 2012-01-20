/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.commands.AutoAim;
import edu.wpi.first.wpilibj.templates.commands.ManualAim;

/**
 *
 * @author Jon Buckley
 */
public class FireTrigger extends Button
{
    public FireTrigger()
    {
        super.whileHeld(new ManualAim());
    }

    public boolean get()
    {
       if (OI.leftJoystick.getTrigger())
           return true;

       return false;
    }

}
