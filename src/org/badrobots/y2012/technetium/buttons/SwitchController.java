/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.badrobots.y2012.technetium.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import org.badrobots.y2012.technetium.OI;
import org.badrobots.y2012.technetium.commands.MechanumDrive;
import org.badrobots.y2012.technetium.subsystems.DriveTrain;

/**
 *
 * @author Lucas Beaufore
 */
public class SwitchController extends Button
{
    boolean xbox;
    boolean toChange;
    public SwitchController()
    {
        xbox = false;
    }

    public boolean get()
    {
        if (OI.leftJoystick.getRawButton(3))
        {
          toChange = true;
          return true;
        }
        else if(toChange)
        {
            toChange = false;
            DriveTrain.getInstance().changeController();
        }

        return false;
    }


}
