package com.badrobots.y2012.technetium;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Contains methods and code for interfacing with the driver controls
 * @author 1014 Programming Team
 */
public class OI
{

    /**
     * The left Logitech Joystick
     */
    /**
     * The right Logitech Joystick
     */
    public static Joystick leftJoystick, rightJoystick;
   /**
    * 
    */
    public static DriverStation ds;
    /**
     * The printout area of the DS
     */
    public static DriverStationLCD screen;
    /**
     * The primary Xbox controller
     */
    public static Joystick xboxController;
    /**
     * The secondary Xbox controller
     */
    public static Joystick xboxController2;

    //This is where we will put the on/off functionality booleans
    /**
     * If true, the driving PID is activated
     */
    public static final boolean PIDOn = true;
    /**
     * If true, the shooting PID is activated
     */
    public static final boolean shooterPIDOn = true;
    /**
     * If true, the turntable PID is activated
     */
    public static final boolean turnTablePIDOn = false;
    /**
     * If true, the camera is activated (will still appear on dashboard if set to false)
     */
    public static final boolean cameraOn = false;
    /**
     * If true, Smart dashboard should process images. May not be functional
     */
    public static final boolean smartdashboardImageProcessingOn = false;
    /**
     *  If true, BangBang system is activated for shooter
     */
    public static final boolean bangBangOn = false;
    
    /**
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

    /**
     * Prints a string to the DriverStation LCD Status
     * @param out The string to be sent to the screen
     */
    public static void printToDS(String out)
    {
        screen.free();
        screen.println(DriverStationLCD.Line.kMain6, 1, out);
        screen.updateLCD();
    }

    /**
     * Sets the digital output on the DriverStation to a boolean value
     * @param i The digital output number
     * @param logic The boolean value to be set
     */
    public static void setDigitalOutput(int i, boolean logic)
    {
        ds.setDigitalOut(i, logic);
    }

    /**
     * @return  Whether the Xbox should be used to control the driveTrain (boolean on DriverStation)
     */
    public static boolean xboxControl()
    {
        return ds.getDigitalIn(1);
    }

    /**
     * @return Whether the right joystick should be used for strafing commands
     */
    public static boolean rightStrafe()
    {
        return ds.getDigitalIn(2);
    }

    /**
     * Used with the Logitech controllers
     * @return The deadzone corrected left stick x value
     */
    public static double getLeftX()
    {
        return deadzone(leftJoystick.getX());
    }

    /**
     * Used with the Logitech controllers
     * @return The deadzone corrected left stick y value
     */
    public static double getLeftY()
    {
        return deadzone(leftJoystick.getY());
    }

    /**
     * Used with the Logitech controllers
     * @return The deadzone corrected right stick x value
     */
    public static double getRightX()
    {
        return deadzone(rightJoystick.getX());
    }

    /**
     * Used with the Logitech controllers
     * @return The deadzone corrected right stick y value
     */
    public static double getRightY()
    {
        return deadzone(rightJoystick.getY());
    }


    /**
     * Used with the primary xbox controller
     * @return The deadzone corrected left stick x value
     */
    public static double getPrimaryXboxLeftX()
    {
        return deadzone(-xboxController.getRawAxis(1));
    }

    /**
     * Used with the primary xbox controller
     * @return The deadzone corrected left stick y value
     */
    public static double getPrimaryXboxLeftY()
    {
        return deadzone(xboxController.getRawAxis(2));
    }

    /**
     * Used with the primary xbox controller
     * @return The deadzone corrected right stick x value
     */
    public static double getPrimaryXboxRightX()
    {
        return deadzone(-xboxController.getRawAxis(4));
    }

    /**
     * Used with the primary xbox controller
     * @return The deadzone corrected right stick y value
     */
    public static double getPrimaryXboxRightY()
    {
        return deadzone(xboxController.getRawAxis(5));
    }

    /**
     * @return The primary xbox X button value
     */
    public static boolean primaryXboxXButton()
    {
        return xboxController.getRawButton(3);
    }

    /**
     * @return The primary xbox Y button value
     */
    public static boolean primaryXboxYButton()
    {
        return xboxController.getRawButton(4);
    }

    /**
     * @return The primary xbox A button value
     */
    public static boolean primaryXboxAButton()
    {
        return xboxController.getRawButton(1);
    }

    /**
     * @return The primary xbox B button value
     */
    public static boolean primaryXboxBButton()
    {
        return xboxController.getRawButton(2);
    }

    /**
     * @return The primary xbox right bumper button value
     */
    public static boolean primaryXboxRBButton()
    {
        return xboxController.getRawButton(6);
    }

    /**
     * @return The primary xbox left bumper button value
     */
    public static boolean primaryXboxLBButton()
    {
        return xboxController.getRawButton(5);
    }

    /**
     * @return The primary xbox left joystick clicked in value
     */
    public static boolean primaryXboxLeftJoyClick()
    {
        return xboxController.getRawButton(9);
    }

    /**
     * @return The primary xbox right joystick clicked in value
     */
    public static boolean primaryXboxRightJoyClick()
    {
        return xboxController.getRawButton(10);
    }

    /**
     * Used with the secondary xbox controller
     * @return The deadzone corrected left stick x value
     */
    public static double secondXboxLeftX()
    {
        return deadzone(xboxController2.getRawAxis(1));
    }

    /**
     * Used with the secondary xbox controller
     * @return The deadzone corrected left stick y value
     */
    public static double secondXboxLeftY()
    {
        return deadzone(xboxController2.getRawAxis(2));
    }

    /**
     * @return The secondary xbox X button value
     */
    public static boolean secondXboxXButton()
    {
        return xboxController2.getRawButton(3);
    }

    /**
     * @return The secondary xbox Y button value
     */
    public static boolean secondXboxYButton()
    {
        return xboxController2.getRawButton(4);
    }

    /**
     * @return The secondary xbox A button value
     */
    public static boolean secondXboxAButton()
    {
        return xboxController2.getRawButton(1);
    }

    /**
     * @return The secondary xbox B button value
     */
    public static boolean secondXboxBButton()
    {
        return xboxController2.getRawButton(2);
    }

    /**
     * @return The secondary xbox right bumper value
     */
    public static boolean secondXboxRBButton()//Right Bumper
    {
        return xboxController2.getRawButton(6);
    }

    /**
     * @return The secondary xbox left bumper value
     */
    public static boolean secondXboxLBButton()
    {
        return xboxController2.getRawButton(5);
    }

    /**
     * @return The secondary xbox left joystick click in value
     */
    public static boolean secondXboxLeftJoyClick()
    {
        return xboxController2.getRawButton(9);
    }

    /**
     * @return The secondary xbox right joystick click in value
     */
    public static boolean secondXboxRightJoyClick()
    {
        return xboxController2.getRawButton(10);
    }

    /**
     * @return The secondary xbox select button value
     */
    public static boolean secondXboxSelectButton()
    {
        return xboxController2.getRawButton(7);
    }

    /**
     * @return The secondary xbox start button value
     */
    public static boolean secondXboxStartButton()
    {
        return xboxController2.getRawButton(8);
    }

    /**
     * @return Whether or not the left trigger is pressed in most of the way
     */
    public static boolean secondXboxLeftTrigger()
    {
        return xboxController2.getRawAxis(3) > .7;
    }
    
    

    /**
     * @return the primary controller left x value
     */
    public static double getUsedLeftX()
    {
        if (xboxControl())
        {
            return deadzone(xboxController.getRawAxis(1));
        }

        return deadzone(leftJoystick.getX());

    }

    /**
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

    /**
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

    /**
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
     * @return whether the shoot (primary) trigger is depressed on the second Xbox
     */
    public static boolean getPrimaryTrigger()
    {
        return secondXboxRBButton();
    }

    /**
     *
     * @return battery voltage on robot
     */
    public static double getBatteryVoltage()
    {
        return ds.getBatteryVoltage();
    }

    /**
     * Creates a deadzone for joysticks
     * @param d Number betwenn 0 and 1 to be deadzoned
     * @return The deadzone value
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

    /**
     * Used to help figure out which controller buttons do what. Prints out all pressed buttons and joysticks
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

    /**
     * @return the deadzone for the Joysticks controller, taken from the logitech throttle control
     */
    public static double getJoystickSensitivity()
    {
        return (-leftJoystick.getZ() + 1) / 2;
    }

    /**
     * @return the deadzone for the Xbox controller, taken from the logitech throttle control
     */
    public static double getXboxSensitivity()
    {
        return (-rightJoystick.getZ() + 1);
    }

    /**
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


    /**
     *
     * @param channel
     * @return The value of the Digital Input on that channel
     */
    public static boolean  getDigitalIn(int channel)
    {
        return ds.getDigitalIn(channel);
    }
}
