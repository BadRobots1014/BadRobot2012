/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.commands.Monitor;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * @author 1014 Programming Team
 *
 * The sensor: Cameras, Ultra sonic and garge door sensors
 */
public class Sensors extends Subsystem
{

    private static Sensors sensors;
    private static Ultrasonic backUltrasonic;
    public static DigitalInput bottomOpticalSensor;  
    
    private static final double threshold = .7;
    private static final double spacing = 25;
    protected static int numBalls;
    protected static Gyro gyro;
    protected static int topCount, bottomCount;
    protected double lastRange;

    public static Sensors getInstance()
    {
        if (sensors == null)
        {
            sensors = new Sensors();
        }

        return sensors;
    }

    private Sensors()
    {
        numBalls = 0;
        topCount = 0;
        bottomCount = 0;

        gyro = new Gyro(RobotMap.NI9102_AnalogIn1);
        
        bottomOpticalSensor = new DigitalInput(RobotMap.Sidecar.DIO4);
        //backUltrasonic = new Ultrasonic(RobotMap.ultrasonicOut, RobotMap.ultrasonicIn);
        //backUltrasonic.setAutomaticMode(true);
        //backUltrasonic.setEnabled(true);
        lastRange = 0;
    }
    
    public Gyro getGyro()
    {
        return gyro;
    }

    public double getUltraBackRange()
    {
        return 0; //backUltrasonic.getRangeMM();
    }

    /*
     * @return whether the bottom garage door sensor is obscured. This method
     * uses analog threshold to detect this
     */
    public boolean bottomChannelBlocked()
    {
        //System.out.println(bottomSensor.get());
        SmartDashboard.putBoolean("TopChannelBlocked", bottomOpticalSensor.get());
        if (!bottomOpticalSensor.get())
        {
            bottomCount++;
            if (bottomCount > 8)
            {
                return true;
            }
        }
        else
        {
            bottomCount = 0;
        }

        return false;
    }

    /*
     * @return the angle of the incline/decline the robot is at
     */
    public double getGyroAngle()
    {
        return gyro.getAngle();
    }


    public int getNumBalls()
    {
        return numBalls;
    }

    public void setNumBalls(int n)
    {
        numBalls = n;
    }

    public boolean closerThan(int millimeters)
    {
        //System.out.println(lastRange + "  " + front.isRangeValid());
        //System.out.println("Mili" + millimeters);\
        //System.out.println("back " + back.getRangeMM());
        if (backUltrasonic.isRangeValid() && backUltrasonic.getRangeMM() < millimeters)
        {
            if (lastRange > 10) // 10 checks to stop
            {
                return true;
            }
            else
            {
                lastRange++;
            }
        }
        else
        {
            lastRange = 0;
        }
        return false;

    }

    /*
     * Resets the vertical gyro so that its current heading is 0
     */
    public void resetGyro()
    {
        gyro.reset();
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new Monitor());
    }
}