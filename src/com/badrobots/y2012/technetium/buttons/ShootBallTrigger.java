/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author Jon Buckley
 */
public class ShootBallTrigger extends Button
{

    public boolean get() 
    {
        if (OI.controller.getRawButton(3))
            return true;
        
        return false;
    }

}
