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
        if(OI.lightRingStatus())
        {
            CameraAndLights.getInstance().turnOnLight(true);
        }
        else
        {
            CameraAndLights.getInstance().turnOnLight(false);
        }
        
        if (OI.takePicture())
        {
            CameraAndLights.getInstance().saveImageToDesktop();
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
