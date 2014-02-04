/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import com.badrobots.y2012.technetium.RobotMap;
import com.sun.squawk.platform.windows.natives.Time;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Utility;
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
    private ColorImage image;
   
    private CameraAndLights() 
    {
        System.out.println("~~~~~~~~~~~~~~~~Constructing cameraandlights");
        ringLightRelay = new Relay(RobotMap.Sidecar.RELAY4);
        ringLightRelay.setDirection(Relay.Direction.kForward);
        
        try{
            axisCamera = AxisCamera.getInstance("10.10.14.11");
            axisCamera.writeResolution(AxisCamera.ResolutionT.k640x480);
        } catch (Exception ex)
        {
            System.out.println(ex);
        }
        
        System.out.println("~~~~~~~~~~~~~~~~CAMERA: "+axisCamera + "null? "+axisCamera == null);
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
    
    boolean tookenPicture = false;
    
    public void saveImageToDesktop()
    {
        try {
            image = axisCamera.getImage();
            
            System.out.println("Image Acquired");
            
            if (!tookenPicture)
            {
                System.out.println("Writing To File");
                image.write("C:\\Users\\Isaac\\Desktop\\picture.jpg");
                System.out.println("Successful Write");
                tookenPicture = true;
            }
            image.free();
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
    }
    
    protected void initDefaultCommand()
    {
    
    }
    
}
