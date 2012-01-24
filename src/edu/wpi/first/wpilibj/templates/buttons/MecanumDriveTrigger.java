/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.commands.MecanumDrive;

/**
 *
 * @author Jon Buckley
 */
public class MecanumDriveTrigger extends Button
{
    public MecanumDriveTrigger()
    {
        super.whenPressed(new MecanumDrive());
    }

    public boolean get()
    {
        if (OI.rightJoystick.getTrigger())
            return true;

        return false;
    }

}
