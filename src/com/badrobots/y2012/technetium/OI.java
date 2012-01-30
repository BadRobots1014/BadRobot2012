
package com.badrobots.y2012.technetium;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DriverStation;

public class OI
{
    public static Joystick leftJoystick, rightJoystick, shooterJoystick;
    public static DriverStation ds;

    public static Joystick controller;
    /*
     * initializes all input methods (eg. joysticks)
     */
    public static void init()
    {
        try
        {
           leftJoystick = new Joystick(RobotMap.leftJoystick);
           rightJoystick = new Joystick(RobotMap.rightJoystick);
           controller = new Joystick(RobotMap.controller);
           ds = DriverStation.getInstance();//Drivers Station

           controller = new Joystick(3); //XBOX Controller
        }

        catch (Exception e) {System.out.println(e);}
        
    }

    public static double getLeftX()
    {
        return deadzone(leftJoystick.getX());
    }

    public static double getLeftY()
    {
        return deadzone(leftJoystick.getY());
    }

    public static double getRightX()
    {
        return deadzone(rightJoystick.getX());
    }

    public static double getRightY()
    {
        return deadzone(rightJoystick.getY());
    }

    public static double getShooterX()
    {
        return deadzone(shooterJoystick.getX());
    }

    public static double getShooterY()
    {
        return deadzone(shooterJoystick.getY());
    }

    public static double getXboxLeftX()
    {
        detectAxis();
        return deadzone(-controller.getRawAxis(1));
    }

    public static double getXboxLeftY()
    {
        return deadzone(controller.getRawAxis(2));
    }

    public static double getXboxRightX()
    {
        return deadzone(-controller.getRawAxis(4));
    }

    public static double getXboxRightY()
    {
        return deadzone(controller.getRawAxis(5));
    }

    public static boolean xboxControl()
    {
        return ds.getDigitalIn(1);
    }

    public static boolean rightStrafe()
    {
        return ds.getDigitalIn(2);
    }
    
    public static double getUsedLeftX()
    {
        if (xboxControl())
            return controller.getRawAxis(1);
        
        return leftJoystick.getX();
        
    }
    
    public static double getUsedLeftY()
    {
        if (xboxControl())
            return controller.getRawAxis(2);
        
        return leftJoystick.getY();
    }
    
    public static double getUsedRightX()
    {
        if (xboxControl())
            return controller.getRawAxis(4);
        
        return rightJoystick.getX();
    }
    
    public static double getUsedRightY()
    {
        if (xboxControl())
            return controller.getRawAxis(5);
        
        return rightJoystick.getY();
    }
    

    /*
     * Creates a deadzone for joysticks
     * Status:Tested, accurate for joysticks 1/21/12, inaccurate for xbox 1/29/12
     * 
     */
    private static double deadzone(double d)
    {
        double jsSensitivity = getJoystickSensitivty();
        double xboxSensitivity = getXboxSensitivity();
        
        if (jsSensitivity > .9)
            jsSensitivity = .1;
        
        if (xboxSensitivity > .9)
            xboxSensitivity = .5;
        
        if (Math.abs(d) < jsSensitivity && !xboxControl())
            return 0;
        
        else if (Math.abs(d) < xboxSensitivity && xboxControl())
            return 0;
        
        return d / Math.abs(d) * ((Math.abs(d) - .10) / .90);
    }

    public static void detectAxis()
    {
        for(int i=0; i<=12; i++)
        {
            if(Math.abs(controller.getRawAxis(i)) > .1)
                System.out.println(i + " : " + controller.getRawAxis(i));
        }
    }
    
    public static double getJoystickSensitivty()
    {
        return ds.getAnalogIn(1);
    }
    
    public static double getXboxSensitivity()
    {
        return ds.getAnalogIn(2);
    }
    
}

