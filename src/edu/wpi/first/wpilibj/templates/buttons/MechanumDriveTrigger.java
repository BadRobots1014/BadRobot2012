/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.commands.MechanumDrive;

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
