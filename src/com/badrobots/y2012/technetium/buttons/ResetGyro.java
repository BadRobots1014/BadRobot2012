/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Hermes;
import edu.wpi.first.wpilibj.buttons.Button;

/*
 * @author 1014 Programming Team
 */
public class ResetGyro extends Button
{

    /*
     * THIS FUNCTIONALITY NEEDS TO BE MOVED TO OI
     * DELTE WHEN FINISHED
     */


    public boolean get() 
    {
        if (OI.leftJoystick.getRawButton(4) && OI.rightJoystick.getRawButton(4))
        {
            Hermes.getInstance().resetGyro();

            return true;
        }
        
        return false;
    }
    
}
