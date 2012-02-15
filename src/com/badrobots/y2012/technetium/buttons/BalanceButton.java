/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.Balance;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 * @author Jon Buckley
 */
public class BalanceButton extends Button
{
    
    public BalanceButton()
    {
        super.whenPressed(new Balance());
    }

    public boolean get()
    {
        if (OI.getPrimaryTrigger())
            return true;
        
        return false;
    }
    
    
}
