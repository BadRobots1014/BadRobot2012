package com.badrobots.y2012.technetium;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;

/*
 * @author 1014 Programming Team
 */
public class OI
{

    public static Joystick leftJoystick, rightJoystick, shooterJoystick;
    public static DriverStation ds;
    public static DriverStationLCD screen;
    public static Joystick xboxController;
    public static Joystick xboxController2;

    //This is where we will put the on/off functionality booleans
    public static final boolean kinnectOn = false;

    /*
     * initializes all input methods (eg. joysticks)
     */
    public static void init()
    {
        try
        {
            leftJoystick = new Joystick(RobotMap.leftJoystick);
            rightJoystick = new Joystick(RobotMap.rightJoystick);
            xboxController = new Joystick(RobotMap.controller);
            xboxController2 = new Joystick(RobotMap.controller2);
            ds = DriverStation.getInstance();//Drivers Station
            screen = DriverStationLCD.getInstance();//Output on DS


        } catch (Exception e)
        {
            System.out.println(e);
        }

    }

    /*
     * Printles a string to the driverstation LCD Status: Untested //TODO: Test!
     */
    public static void printToDS(String out)
    {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, out);
        DriverStationLCD.getInstance().updateLCD();
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
        return deadzone(-xboxController.getRawAxis(1));
    }

    public static double getXboxLeftY()
    {
        return deadzone(xboxController.getRawAxis(2));
    }

    public static double getXboxRightX()
    {
        return deadzone(-xboxController.getRawAxis(4));
    }

    public static double getXboxRightY()
    {
        return deadzone(xboxController.getRawAxis(5));
    }

    /**
     * @return whether the xbox should be used to control the driveTrain
     */
    public static boolean xboxControl()
    {
        return ds.getDigitalIn(1);
    }

    /**
     * @return whether the right joystick should be used for strafing commands
     */
    public static boolean rightStrafe()
    {
        return ds.getDigitalIn(2);
    }

    public static boolean primaryXboxX()
    {
        return xboxController.getRawButton(3);
    }

    public static boolean primaryXboxY()
    {
        return xboxController.getRawButton(4);
    }

    public static boolean primaryXboxA()
    {
        return xboxController.getRawButton(1);
    }

    public static boolean primaryXboxB()
    {
        return xboxController.getRawButton(2);
    }

    public static boolean primaryXboxRB()
    {
        return xboxController.getRawButton(6);
    }

    public static boolean primaryXboxLB()
    {
        return xboxController.getRawButton(5);
    }

    public static boolean primaryXboxLeftJoyClick()
    {
        return xboxController.getRawButton(9);
    }

    public static boolean primaryXboxRightJoyClick()
    {
        return xboxController.getRawButton(10);
    }

    public static boolean secondXboxX()
    {
        return xboxController2.getRawButton(3);//X
    }

    public static boolean secondXboxY()
    {
        return xboxController2.getRawButton(4);//Y
    }

    public static boolean secondXboxA()
    {
        return xboxController2.getRawButton(1);//A
    }

    public static boolean secondXboxB()
    {
        return xboxController2.getRawButton(2);//B
    }

    public static boolean secondXboxRB()//Right Bumper
    {
        return xboxController2.getRawButton(6);
    }

    public static boolean secondXboxLB()
    {
        return xboxController2.getRawButton(5);
    }

    public static boolean secondXboxLeftJoyClick()
    {
        return xboxController2.getRawButton(9);
    }

    public static boolean secondXboxRightJoyClick()
    {
        return xboxController2.getRawButton(10);
    }

    public static boolean secondXboxSelect()
    {
        return xboxController2.getRawButton(7);
    }

    public static boolean secondXboxStart()
    {
        return xboxController2.getRawButton(8);
    }

    /*
     * @return the currently used controller left x value status: all tested
     * 1/30/12
     */
    public static double getUsedLeftX()
    {
        if (xboxControl())
        {
            return deadzone(xboxController.getRawAxis(1));
        }

        return deadzone(leftJoystick.getX());

    }

    /*
     * @return the currently used controller left y value
     */
    public static double getUsedLeftY()
    {
        if (xboxControl())
        {
            return deadzone(xboxController.getRawAxis(2));
        }

        return deadzone(leftJoystick.getY());
    }

    /*
     * @return the currently used controller right x value
     */
    public static double getUsedRightX()
    {
        if (xboxControl())
        {
            return deadzone(xboxController.getRawAxis(4));
        }

        return deadzone(rightJoystick.getX());
    }

    /*
     * @return the currently used controller right y value
     */
    public static double getUsedRightY()
    {
        if (xboxControl())
        {
            return deadzone(xboxController.getRawAxis(5));
        }

        return deadzone(rightJoystick.getY());
    }

    /**
     * @return whether the secondary trigger is depressed
     */
    public static boolean getSecondaryTrigger()
    {
        return secondXboxLB();
    }

    /**
     * @return whether the shoot trigger is depressed
     */
    public static boolean getPrimaryTrigger()
    {
        return secondXboxRB();
    }

    public static boolean secondXboxLeftTrigger()
    {
        return xboxController2.getRawAxis(3) > .7;
    }

    /*
     * Creates a deadzone for joysticks Status:Untested, must test scaling
     *
     */
    private static double deadzone(double d)
    {
        if (Math.abs(d) < .2 && !xboxControl())
        {
            return 0;
        } else
        {
            if (Math.abs(d) < .2 && xboxControl())
            {
                return 0;
            }
        }

        return d / Math.abs(d) * ((Math.abs(d) - .10) / .90);
    }

    /*
     * helper method to determine which axis is which on the xbox controller
     */
    public static void detectAxis()
    {
        for (int i = 0; i <= 12; i++)
        {
            if (xboxController2.getRawAxis(i) > .1)
            {
                System.out.println("AXIS: " + i + " @ " + xboxController2.getRawAxis(i));
            }
        }

        for (int i = 0; i <= 12; i++)
        {
            if (xboxController2.getRawButton(i))
            {
                System.out.println("Button: " + i);
            }
        }
    }

    /*
     * @return the deadzone for the Joysticks controller
     */
    public static double getJoystickSensitivity()
    {
        return (-leftJoystick.getZ() + 1) / 2;
    }

    /*
     * @return the deadzone for the Xbox controller
     */
    public static double getXboxSensitivity()
    {
        return (-rightJoystick.getZ() + 1);
    }

    /*
     * @return the factor for which all values taken in by controllers should be
     * multiplied with. it is contorlled by the throttle controls on the
     * joysticks
     */
    public static double getSensitivity()
    {
        double xboxSensitivity = getXboxSensitivity();
        double jsSensitivity = getJoystickSensitivity();

        if (xboxSensitivity > 1 || xboxSensitivity <= 0)
        {
            xboxSensitivity = 1;
        }

        if (jsSensitivity > 1 || jsSensitivity <= 0)
        {
            jsSensitivity = 1;
        }

        if (xboxControl())
        {
            return getXboxSensitivity();
        } 
        
        else
        {
            return getJoystickSensitivity();
        }
    }

    /**
     * @param channel the channel the method should query for a value
     * @return the value of the analog input from the driverstaion of a specific
     * input channel.
     */
    public static double getAnalogIn(int channel)
    {

        return ds.getAnalogIn(channel);
    }
}
