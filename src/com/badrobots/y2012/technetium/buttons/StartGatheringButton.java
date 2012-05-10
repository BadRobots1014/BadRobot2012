/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.buttons;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.GatherBallsAndManualShoot;
import com.badrobots.y2012.technetium.subsystems.Demeter;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * @deprecated
 * @author 1014 Programming team
 */
public class StartGatheringButton extends Button
{
    
    public StartGatheringButton()
    {
        super.whenPressed(new GatherBallsAndManualShoot());
    }

    /*
     * If Demeter is already in use, cancel that command. If it isn't in use, then
     * return true
     */
    public boolean get() 
    {
        /*if (OI.secondXboxXButton() && Demeter.getInstance().getCurrentCommand().)
        {
            System.out.println("Command already found");
            //Demeter.getInstance().getCurrentCommand().cancel();
        }*/
        
        if (OI.secondXboxXButton())
        {
            System.out.println("Adding command");
            return true;
        }
        
        return false;
    }

    
}
