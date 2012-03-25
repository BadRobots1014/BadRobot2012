/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Kinect.Point4;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.commands.AutoAim;
import com.badrobots.y2012.technetium.commands.Monitor;
import edu.wpi.first.wpilibj.*;

/*
 * @author 1014 Programming Team
 *
 * The sensor: Cameras, Ultra sonic and garge door sensors
 */
public class Helios extends Subsystem
{

    private static Helios sensors;
    private static AxisCamera camera;
    private static Ultrasonic lFront, lBack, back;
    public static AnalogChannel bottomSensor, topSensor, ultra;
    private static final double threshold = .7;
    private static final double spacing = 25;
    protected static int numBalls;
    protected static Gyro gyro;
    protected static int topCount, bottomCount;
    protected double lastRange;

    public static Helios getInstance()
    {
        if (sensors == null)
        {
            sensors = new Helios();
        }

        return sensors;
    }

    private Helios()
    {
        //camera = AxisCamera.getInstance();

        if (camera == null)
        {
            System.out.println("Unable to find camera");
        }
        numBalls = 0;
        topCount = 0;
        bottomCount = 0;

        //gyro = new Gyro(RobotMap.verticalGyro);
        bottomSensor = new AnalogChannel(RobotMap.bottomSensor);
        topSensor = new AnalogChannel(RobotMap.topSensor);
        //back = new Ultrasonic(new DigitalOutput(1,3), new DigitalInput(1,2));
        ultra = new AnalogChannel(3);
        //back.setAutomaticMode(true);
        //back.setEnabled(true);
        lastRange = 0;

        //TODO
    }

    /**
     * @return the difference in distances (inches) that the two side sensors
     * are reading, front minus left
     */
    public double getDifferenceInSensors()
    {
        return (lFront.getRangeMM() - lBack.getRangeMM());
    }

    public double getUltraFrontRange()
    {
        return ultra.getVoltage();
    }

    /**
     * @return the average distanced sensed from the left side of the robot
     */
    public double getDistanceFromWall()
    {
        double front = lFront.getRangeMM();
        double back = lBack.getRangeMM();

        if (Math.abs(back - front) > 15)//needs to be calibrated
        {
            return -1; // not accurate enough to use, return a nonpossible value
        }
        return (front + back) / 2;
    }

    /*
     * @return the angle at which the robot is oriented. 0 degrees is when the
     * robot is parallel with the arena walls.
     */
    public double getAngleOfOrientation()
    {
        double difference = getDifferenceInSensors();

        double theta = MathUtils.atan(difference / spacing);
        theta *= (180 / Math.PI);

        return theta;
    }

    public double getAngleToTarget()
    {
        return 3.14;//XXX:Really? Remeber to fix this
    }

    /*
     * @return whether the top garage door sensor is obscured. This method uses
     * analog threshold to detect this
     */
    public boolean topChannelBlocked()
    {
        //System.out.println(topSensor.getAverageVoltage());
        if (topSensor.getAverageVoltage() < threshold)
        {
            topCount++;
            if (topCount > 8)
            {
                return true;
            }
        }
        else
        {
            topCount = 0;
        }

        return false;
    }

    /*
     * @return whether the bottom garage door sensor is obscured. This method
     * uses analog threshold to detect this
     */
    public boolean bottomChannelBlocked()
    {
        if (bottomSensor.getAverageVoltage() < threshold)
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
    public double getVerticalGyro()
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
        //System.out.println("Mili" + millimeters);
        if (back.isRangeValid() && back.getRangeMM() < millimeters)
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
    public void resetVerticalGyro()
    {
        gyro.reset();
    }

    public void initDefaultCommand()
    {
        System.out.println("DefaultCommandHelios");
        //setDefaultCommand(new Monitor());
    }
}