/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.commands.MechanumDrive;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;

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
