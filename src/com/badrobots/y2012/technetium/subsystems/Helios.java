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
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;

/*
 * @author 1014 Programming Team
 *
 * The sensor: Cameras, Ultra sonic and garge door sensors
 */
public class Helios extends Subsystem
{
    private static Helios sensors;
    private static AxisCamera camera;
    private static Ultrasonic lFront, lBack;
    public static AnalogChannel bottomSensor, topSensor;
    private static final double threshold = .5;//200 VOLTS?!?!? This needs to be changed
    private static final double spacing = 25;
    protected static Gyro gyro;
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

        if(camera == null)
            System.out.println("Unable to find camera");
        
        //gyro = new Gyro(RobotMap.verticalGyro);
        topSensor = new AnalogChannel(RobotMap.topSensor);
        /*topSensor = new AnalogChannel(RobotMap.topSensor);

        //Note: If this doesn't work, use digital In and Outs as arguements
        lFront = new Ultrasonic(1, 2);
        lBack = new Ultrasonic(3, 4);
        lFront.setEnabled(true);
        lBack.setEnabled(true);
        //Stops interference between sensors
        lFront.setAutomaticMode(true);
        lBack.setAutomaticMode(true);*/

        
        //TODO
    }

    /**
     * 
     * @return the difference in distances (inches) that the two side sensors are reading,
     * front minus left
     */
    public double getDifferenceInSensors()
    {
        return (lFront.getRangeMM() - lBack.getRangeMM());
    }
    
    /**
     * @return the average distanced sensed from the left side of the robot
     */
    public double getDistanceFromWall()
    {
        double front = lFront.getRangeMM();
        double back = lBack.getRangeMM();
        
        if (Math.abs(back-front) > 15)//needs to be calibrated
            return -1; // not accurate enough to use, return a nonpossible value
            
        return (front+back)/2;
    }

    /*
     * @return the angle at which the robot is oriented. 0 degrees is when
     * the robot is parallel with the arena walls.  
     */
    public double getAngleOfOrientation()
    {
        double difference = getDifferenceInSensors();

        double theta = MathUtils.atan(difference/spacing);
        theta *= (180/Math.PI);

        return theta;
    }
    
    public double getAngleToTarget()
    {
        return 3.14;//XXX:Really? Remeber to fix this
    }
    
    /*
     * @return whether the top garage door sensor is obscured. 
     * This method uses analog threshold to detect this
     */
    public boolean topChannelBlocked()
    {
        if (topSensor.getAverageVoltage() > threshold)
            return true;
        
        return false;
    }
    
    /*
     * @return whether the bottom garage door sensor is obscured. 
     * This method uses analog threshold to detect this
     */
    public boolean bottomChannelBlocked()
    {
        if (bottomSensor.getAverageVoltage() > threshold)
            return true;

        return false;
    }
    
    /*
     * @return the angle of the incline/decline the robot is at 
     */
    public double getVerticalGyro()
    {
        return gyro.getAngle();
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
    }
}