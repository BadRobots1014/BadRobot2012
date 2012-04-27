package com.badrobots.y2012.technetium;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * @author 1014 Programming Team
 */
public class OI
{

    public static Joystick leftJoystick, rightJoystick;
    public static DriverStation ds;
    public static DriverStationLCD screen;
    public static Joystick xboxController;
    public static Joystick xboxController2;

    //This is where we will put the on/off functionality booleans
    public static final boolean PIDOn = true;
    public static final boolean shooterPIDOn = true;
    public static final boolean turnTablePIDOn = false;
    public static final boolean cameraOn = false;
    public static final boolean smartdashboardImageProcessingOn = false;
    public static final boolean bangBangOn = false;
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

        SmartDashboard.putBoolean("CameraOn", cameraOn);
        SmartDashboard.putBoolean("HermesPIDOn", PIDOn);
        SmartDashboard.putBoolean("TurnTablePIDOn", turnTablePIDOn);
        SmartDashboard.putBoolean("ShooterPIDOn", shooterPIDOn);
    }

    /*
     * Prints a string to the driverstation LCD Status: Untested //TODO: Test!
     */
    public static void printToDS(String out)
    {
        //System.out.println("ShouldBeOnScreen: " + out);
        screen.free();
        screen.println(DriverStationLCD.Line.kMain6, 1, out);
        screen.updateLCD();
    }

    public static void setDigitalOutput(int i, boolean logic)
    {
        ds.setDigitalOut(i, logic);
    }

    /**
     * @return whether the Xbox should be used to control the driveTrain
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



    /*
     * These are controls from the Logitech Joysticks
     */
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


    /*
     * The joystick values from the primary Xbox Controller
     */
    public static double getPrimaryXboxLeftX()
    {
        return deadzone(-xboxController.getRawAxis(1));
    }

    public static double getPrimaryXboxLeftY()
    {
        return deadzone(xboxController.getRawAxis(2));
    }

    public static double getPrimaryXboxRightX()
    {
        return deadzone(-xboxController.getRawAxis(4));
    }

    public static double getPrimaryXboxRightY()
    {
        return deadzone(xboxController.getRawAxis(5));
    }


    /*
     * The buttons from the Primary Xbox Controller
     */
    public static boolean primaryXboxXButton()
    {
        return xboxController.getRawButton(3);
    }

    public static boolean primaryXboxYButton()
    {
        return xboxController.getRawButton(4);
    }

    public static boolean primaryXboxAButton()
    {
        return xboxController.getRawButton(1);
    }

    public static boolean primaryXboxBButton()
    {
        return xboxController.getRawButton(2);
    }

    public static boolean primaryXboxRBButton()
    {
        return xboxController.getRawButton(6);
    }

    public static boolean primaryXboxLBButton()
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


    /*
     * Button values from the second Xbox controller
     */
    public static boolean secondXboxXButton()
    {
        return xboxController2.getRawButton(3);
    }

    public static boolean secondXboxYButton()
    {
        return xboxController2.getRawButton(4);
    }

    public static boolean secondXboxAButton()
    {
        return xboxController2.getRawButton(1);
    }

    public static boolean secondXboxBButton()
    {
        return xboxController2.getRawButton(2);
    }

    public static boolean secondXboxRBButton()//Right Bumper
    {
        return xboxController2.getRawButton(6);
    }

    public static boolean secondXboxLBButton()
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

    public static boolean secondXboxSelectButton()
    {
        return xboxController2.getRawButton(7);
    }

    public static boolean secondXboxStartButton()
    {
        return xboxController2.getRawButton(8);
    }

    public static boolean secondXboxLeftTrigger()
    {
        return xboxController2.getRawAxis(3) > .7;
    }
    
    public static double secondXboxLeftX()
    {
        return deadzone(xboxController2.getRawAxis(1)); 
    }
    
    public static double secondXboxLeftY()
    {
        return deadzone(xboxController2.getRawAxis(2));
    }

    /*
     * @return the primary controller left x value status: all tested
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
     * @return the primary controller left y value
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
     * @return the primary controller right x value
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
     * @return the primary controller right y value
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
     * @return whether the secondary trigger is depressed on the second Xbox
     */
    public static boolean getSecondaryTrigger()
    {
        return secondXboxLBButton();
    }

    /**
     * @return whether the shoot trigger is depressed on the second Xbox
     */
    public static boolean getPrimaryTrigger()
    {
        return secondXboxRBButton();
    }

    
    public static double getBatteryVoltage()
    {
        return ds.getBatteryVoltage();
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
     * @return the deadzone for the Joysticks controller, taken from the logitech throttle control
     */
    public static double getJoystickSensitivity()
    {
        return (-leftJoystick.getZ() + 1) / 2;
    }

    /*
     * @return the deadzone for the Xbox controller, taken from the logitech throttle control
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
     * @return the value of the analog input from the DriverStaion of a specific
     * input channel.
     */
    public static double getAnalogIn(int channel)
    {
        return ds.getAnalogIn(channel);
    }


    public static boolean  getDigitalIn(int channel)
    {
        return ds.getDigitalIn(channel);
    }
}
