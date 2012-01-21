/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

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
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.AutoAim;

/**
 *
 * @author Jon Buckley
 */
public class ArenaSensors extends Subsystem
{
    private static ArenaSensors sensors;
    private static AxisCamera camera;
    private static Ultrasonic lFront, lBack, rFront, rBack;
    private static final double spacing = 25;

    public static ArenaSensors getInstance()
    {
        if (sensors == null)
        {
            sensors = new ArenaSensors();
        }

        return sensors;
    }

    private ArenaSensors()
    {
        camera = AxisCamera.getInstance();
        
        lFront = new Ultrasonic(1, 1);
        lBack = new Ultrasonic(2, 2);
        rFront = new Ultrasonic(3, 3);
        rBack = new Ultrasonic(4, 4);
    }

    public double getDifferenceInSensorsFromWall(boolean left)
    {
        if (left)            
            return (lFront.getRangeInches() - lBack.getRangeInches());

        else
             return (rFront.getRangeInches() - rBack.getRangeInches());
    }

    /*
     * @return the angle at which the robot is oriented. 0 degrees is when
     * the robot is parallel with the arena walls.  
     */
    public double getAngleOfOrientation()
    {
        double difference = lFront.getRangeInches() - lBack.getRangeInches();

        double theta = MathUtils.atan(difference/spacing);
        theta *= (180/3.14159);

        return theta;
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
        }
        
        catch (NIVisionException ex)
        {
            ex.printStackTrace();
        }

        return toReturn;
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new AutoAim());
    }
}