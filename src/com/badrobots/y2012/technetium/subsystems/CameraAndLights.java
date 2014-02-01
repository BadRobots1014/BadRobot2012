/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import com.badrobots.y2012.technetium.RobotMap;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 *
 * @author Steve
 */
public class CameraAndLights extends Subsystem
{

    private static CameraAndLights instance;
    
    public static CameraAndLights getInstance() 
    {
        if (instance == null)
        {
            instance = new CameraAndLights();
        }
        return instance;
    }
    
    private Relay ringLightRelay;
    private AxisCamera axisCamera;
   
    private CameraAndLights() 
    {
        ringLightRelay = new Relay(RobotMap.Sidecar.RELAY4);
        ringLightRelay.setDirection(Relay.Direction.kForward);
        
        /*axisCamera = c;
        
        ColorImage imageFromCamera = null;
        
        try {
            imageFromCamera = axisCamera.getImage();
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("STRING FROM CAMERA: " + imageFromCamera.toString());*/
    }
    
    public void turnOnLight(boolean on)
    {
        if(on && ringLightRelay.get() == Relay.Value.kOff)
        {
            ringLightRelay.set(Relay.Value.kOn);
        }
        else if(!on && ringLightRelay.get() == Relay.Value.kOn)
        {
            ringLightRelay.set(Relay.Value.kOff);
        }

    }
    protected void initDefaultCommand()
    {
    
    }
    
}
