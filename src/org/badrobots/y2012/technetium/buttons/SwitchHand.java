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
public class SwitchHand extends Button
{
    boolean rightStrafe;
    boolean toChange;
    public SwitchHand()
    {
        rightStrafe = false;
    }

    public boolean get()
    {
        if (OI.rightJoystick.getRawButton(3))
        {
          toChange = true;
          return true;
        }
        else if(toChange)
        {
            toChange = false;
            DriveTrain.getInstance().changeStrafe();
        }

        return false;
    }


}
