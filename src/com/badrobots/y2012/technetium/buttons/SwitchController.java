/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.badrobots.y2012.technetium.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.MechanumDrive;
import com.badrobots.y2012.technetium.subsystems.DriveTrain;

/**
 *
 * @author Lucas Beaufore
 */
public class SwitchController extends Button
{
    boolean xbox;
    boolean toChange;
    
    public SwitchController()
    {
        xbox = false;
    }

    public boolean get()
    {
        if (OI.leftJoystick.getRawButton(3)) 
        {
          toChange = true;       
          return true;
        }
        
        else if(toChange)
        {
            toChange = false;
            //OI.;
        }

        return false;
    }


}
