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

/**
 *
 * @author Jon Buckley
 */
public class Helios extends Subsystem
{
    private static Helios sensors;
    private static AxisCamera camera;
    private static Ultrasonic lFront, lBack;
    private static AnalogChannel garageSensor;
    private static final double threshold = 200;
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
        
        garageSensor = new AnalogChannel(RobotMap.garage);
               
        lFront = new Ultrasonic(1, 1);
        lBack = new Ultrasonic(2, 2);
    }

    /**
     * 
     * @return the difference in distances (inches) that the two side sensors are reading,
     * front minus left
     */
    public double getDifferenceInSensors()
    {
        return (lFront.getRangeInches() - lBack.getRangeInches());
    }
    
    /**
     * @return the average distanced sensed from the left side of the robot
     */
    public double getDistanceFromWall()
    {
        double front = lFront.getRangeInches();
        double back = lBack.getRangeInches();
        
        if (Math.abs(back-front) > 15)
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
        return 3.14;
    }

    public ParticleAnalysisReport[] getRectangleParticles() throws AxisCameraException
    {
        ParticleAnalysisReport[] toReturn = new ParticleAnalysisReport[4];
        
        ColorImage img;
        int current = 0;
        
        try 
        {
            img = camera.getImage();
            img = AxisCamera.getInstance().getImage();
            BinaryImage binary =  img.thresholdHSL(0, 180, 0, 100, 0, 5);
            ParticleAnalysisReport[] particles = binary.getOrderedParticleAnalysisReports();

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
            
            img.free();
            binary.free();
        }
        
        catch (NIVisionException ex)
        {
            ex.printStackTrace();
        }

        return toReturn;
    }
    
    /*
     * @return whether the garage door sensor is obscured. 
     * This method uses analog threshold to detect this
     */
    public boolean channelBlocked()
    {
        if (garageSensor.getAverageVoltage() > threshold)
            return false;
        
        return true;
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new AutoAim());
    }
}