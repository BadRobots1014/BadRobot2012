/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.badrobots.y2012.technetium.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.ManualAim;
import com.badrobots.y2012.technetium.commands.TankDrive;

/*
 * @author 1014 Programming Team
 */
public class TankDriveTrigger extends Button
{
    public TankDriveTrigger()
    {
       super.whenPressed(new TankDrive());
    }

    /**
     * Checks to see if the button is pressed.
     * @return if either the leftJoystick's 10th button or the A button is pressed,
     * returns true. otherwise, returns false
     */
    public boolean get()
    {
       if (OI.leftJoystick.getRawButton(10) || OI.xboxController.getRawButton(1))
           return true;

       return false;
    }


}
