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

/*
 * @author 1014 Programming Team
 */
public class Helios extends Subsystem
{
    private static Helios sensors;
    private static AxisCamera camera;
    private static Ultrasonic lFront, lBack;
    private static AnalogChannel bottomSensor, topSensor;
    private static final double threshold = 200;//200 VOLTS?!?!? This needs to be changed
    private static final double spacing = 25;
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
        camera = AxisCamera.getInstance();

        if(camera == null)
            System.out.println("Unable to find camera");
        bottomSensor = new AnalogChannel(RobotMap.bottomSensor);
        topSensor = new AnalogChannel(RobotMap.topSensor);

        //Note: If this doesn't work, use digital In and Outs as arguements
        lFront = new Ultrasonic(1, 2);
        lBack = new Ultrasonic(3, 4);
        lFront.setEnabled(true);
        lBack.setEnabled(true);
        //Stops interference between sensors
        lFront.setAutomaticMode(true);
        lBack.setAutomaticMode(true);
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
        return 3.14;//Really? Remeber to fix this
    }

    public ParticleAnalysisReport[] getRectangleParticles() throws AxisCameraException
    {
        ParticleAnalysisReport[] toReturn = new ParticleAnalysisReport[4];
        
        ColorImage img;
        int current = 0;
        
        try 
        {
            //gets and stores the current camera image
            img = camera.getImage();
            img = AxisCamera.getInstance().getImage();

            //Created a binary image where pixels meeting threshold
            BinaryImage binary =  img.thresholdHSL(0, 180, 0, 100, 0, 5);

            //Array of all detected rectangles, right?
            ParticleAnalysisReport[] particles = binary.getOrderedParticleAnalysisReports();

            //Makes checks to see if the rectangle meets size and ratio requirements
            for (int i = 0; i < particles.length; i++)
            {
                ParticleAnalysisReport test = particles[i];
                if (test.particleToImagePercent > .1 && test.particleToImagePercent < .4)
                {
                    double ratio = test.boundingRectWidth/test.boundingRectHeight;
                    if (ratio > ((4/3) - .2) && ratio < ((4/3) + .2))
                    {
                        toReturn[current] = test;
                        current++;
                    }
                }
            }

            //release memory allocated to the images
            img.free();
            binary.free();
        }
        
        catch (NIVisionException ex)
        {
            ex.printStackTrace();
        }

        //return the rectangles that meet the requirements
        return toReturn;
    }
    
    /*
     * @return whether the top garage door sensor is obscured. 
     * This method uses analog threshold to detect this
     */
    public boolean topChannelBlocked()
    {
        if (topSensor.getAverageVoltage() > threshold)
            return false;
        
        return true;
    }
    
    /*
     * @return whether the bottom garage door sensor is obscured. 
     * This method uses analog threshold to detect this
     */
    public boolean bottomChannelBlocked()
    {
        if (bottomSensor.getAverageVoltage() > threshold)
            return false;
        
        return true;
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new AutoAim());
    }
}