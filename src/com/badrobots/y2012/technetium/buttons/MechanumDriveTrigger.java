/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.badrobots.y2012.technetium.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.MechanumDrive;

/**
 * Pressed to activate Mechanum drive
 * @deprecated
 * @author 1014 Programming Team
 */
public class MechanumDriveTrigger extends Button
{
    public MechanumDriveTrigger()
    {
        super.whenPressed(new MechanumDrive());
    }

    /**
     * Checks to see if the button is pressed.
     * @return if either the rightJoystick's 11th button or the B button is pressed,
     * returns true. otherwise, returns false
     */
    public boolean get()
    {
        if (OI.rightJoystick.getRawButton(11) || OI.xboxController.getRawButton(2))//B on Xbox
            return true;

        return false;
    }

}
