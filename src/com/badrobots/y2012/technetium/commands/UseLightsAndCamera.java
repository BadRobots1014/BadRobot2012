/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.CameraAndLights;
import edu.wpi.first.wpilibj.camera.AxisCamera;

/**
 *
 * @author Steve
 */
public class UseLightsAndCamera extends CommandBase {

    
    protected void initialize() 
    {
    
    }

    protected void execute() 
    {
        System.out.println(OI.lightRingStatus());
        if(OI.lightRingStatus())
        {
            lightsAndCamera.turnOnLight(true);
        }
        else
        {
            lightsAndCamera.turnOnLight(false);
        }
    }

    protected boolean isFinished() 
    {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
