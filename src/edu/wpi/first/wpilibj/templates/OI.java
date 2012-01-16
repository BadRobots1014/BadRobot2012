
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;

public class OI
{
    public static Joystick leftJoystick, rightJoystick, shooterJoystick;

    /*
     * initializes all input methods (eg. joysticks)
     */
    public static void init()
    {
        leftJoystick = new Joystick(RobotMap.leftJoystick);
        rightJoystick = new Joystick(RobotMap.rightJoystick);
        shooterJoystick = new Joystick(RobotMap.shooterJoystick);
    }
   
    
}

