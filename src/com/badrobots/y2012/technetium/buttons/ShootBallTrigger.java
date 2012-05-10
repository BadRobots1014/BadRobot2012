/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author 1014 Programming Team
 * @deprecated 
 */
public class ShootBallTrigger extends Button
{

    /*
     * THIS FUNCTIIONALITY SHOULD NOT BE IN A BUTTON
     */

    public boolean get() 
    {
        if (OI.xboxController.getRawButton(3))
            return true;
        
        return false;
    }

}
