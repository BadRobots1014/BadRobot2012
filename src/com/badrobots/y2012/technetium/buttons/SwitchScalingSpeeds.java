/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 * @author Jon Buckley
 */
public class SwitchScalingSpeeds extends Button
{
    private boolean low = false;
    
    public boolean get()
    {
        if (OI.controller.getRawButton(3) || OI.leftJoystick.getRawButton(3))
        {
            if (!low)
            {
                OI.setScalingFactor(.5);
                low = true;
            }
            
            else
            {
                OI.setScalingFactor(1);
                low = false;
            }
            
            return true;
        }
        
        return false;
    }
    
}
