/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.badrobots.y2012.technetium.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.MechanumDrive;

/**
 *
 * @author Jon Buckley
 */
public class MechanumDriveTrigger extends Button
{
    public MechanumDriveTrigger()
    {
        super.whenPressed(new MechanumDrive());

    }

    public boolean get()
    {
        if (OI.rightJoystick.getTrigger())
            return true;

        return false;
    }

}
