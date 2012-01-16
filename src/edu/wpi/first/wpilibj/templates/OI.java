
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;

public class OI
{
    public static Joystick leftJoystick, rightJoystick;

    /*
     * initializes all inputs and output channels
     */
    public static void init()
    {
        leftJoystick = new Joystick(RobotMap.leftJoystick);
        rightJoystick = new Joystick(RobotMap.rightJoystick);
    }
   
    
}

