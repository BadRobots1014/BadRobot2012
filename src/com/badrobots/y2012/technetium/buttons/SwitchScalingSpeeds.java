/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import edu.wpi.first.wpilibj.buttons.Button;
import com.badrobots.y2012.technetium.commands.SwitchSpeeds;

/**
 *
 * @author Jon Buckley
 */
public class SwitchScalingSpeeds extends Button
{
    public SwitchScalingSpeeds()
    {
        super.whenPressed(new SwitchSpeeds());
    }

    private boolean low = false;
    
    public boolean get()
    {
        if (OI.controller.getRawButton(3) || OI.leftJoystick.getRawButton(3))            
            return true;
        
        return false;
    }
    
}
