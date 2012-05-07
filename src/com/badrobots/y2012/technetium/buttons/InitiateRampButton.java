/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.InitiateRamp;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Button to initiate ramping command
 * @author 1014 Programming team
 */
public class InitiateRampButton extends Button
{
    public InitiateRampButton()
    {
        super();
        
        this.whenPressed(new InitiateRamp());
    }

    /**
     * @return If the button is currently pressed
     */
    public boolean get()
    {
        if (OI.primaryXboxXButton())
            return true;
        
        return false;
    }
    
}
