
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
           //shooterJoystick = new Joystick(RobotMap.shooterJoystick);
        }

        catch (Exception e) {System.out.println(e.toString());}
        
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

    /*
     * Creates a deadzone for joysticks
     * Status:Tested, accurate for joysticks 1/21/12
     */
    private static double deadzone(double d)
    {
        if (Math.abs(d) < 0.10)
            return 0;
        return d / Math.abs(d) * ((Math.abs(d) - .10) / .90);
    }
    
}

