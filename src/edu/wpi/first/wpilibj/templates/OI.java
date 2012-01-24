
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class OI
{
    public static Joystick leftJoystick, rightJoystick, shooterJoystick;
    /*
     * initializes all input methods (eg. joysticks)
     */
    public static void init()
    {//tiny change
        try
        {
           leftJoystick = new Joystick(RobotMap.leftJoystick);
           rightJoystick = new Joystick(RobotMap.rightJoystick);

           System.out.println("leftJoystick" + leftJoystick.getX());
           System.out.println("OI initting");
           //shooterJoystick = new Joystick(RobotMap.shooterJoystick);
        }

        catch (Exception e) {System.out.println(e.toString());}
        
        
        
    }

    public double getLeftX()
    {
        return leftJoystick.getX();
    }

    public double getLeftY()
    {
        return leftJoystick.getY();
    }

    public double getRightX()
    {
        return rightJoystick.getX();
    }

    public double getRightY()
    {
        return rightJoystick.getY();
    }


   
    
}

