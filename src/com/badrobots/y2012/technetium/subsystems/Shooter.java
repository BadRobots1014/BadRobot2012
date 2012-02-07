/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.buttons.ShootBallTrigger;
import com.badrobots.y2012.technetium.commands.AutoAim;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 *
 * @author Jon Buckley, GauR
 */
public class Shooter extends Subsystem
{
    private static Shooter instance;
    private static Jaguar right, left;
    private static Ultrasonic ranger;
    private static AxisCamera camera;

    public static Shooter getInstance()
    {
        if (instance == null)
        {
            instance = new Shooter();
        }

        return instance;
    }

    private Shooter()
    {
        super();
        right = new Jaguar (RobotMap.rightShooter); // initialize the motor
        left = new Jaguar (RobotMap.leftShooter);
                
        ranger = new Ultrasonic (RobotMap.ultrasonicOut, RobotMap.ultrasonicIn); //init
        ranger.setEnabled(true);
        ranger.setAutomaticMode(true);

        camera.getInstance(); //init

    }

    public void aim()
    {
        //TODO
        //track rectangle using kinect/camera

        
    }

    public void shootMiddle() // read distance from kinect/ultrasonic
    {
        //determine shooter speed through regression equation determined through testing
        int speed = 0;
        right.set(speed);
        left.set(-speed);
    }

    public void shootHigh() // read distance from kinect/ultrasonic
    {
        //determine shooter speed through regression equation determined through testing
        int speed = 0;
        right.set(speed);
        left.set(-speed);
    }

    public void shootLow() // read distance from kinect/ultrasonic
    {
        //determine shooter speed through regression equation determined through testing
        int speed = 0;
        right.set(speed);
        left.set(-speed);
    }


    public ParticleAnalysisReport[] getRectangleParticles() throws AxisCameraException
    {
        ParticleAnalysisReport[] toReturn = new ParticleAnalysisReport[4];

        ColorImage img;
        int current = 0;

        try
        {
            img = camera.getImage();

            BinaryImage binary =  img.thresholdHSI(0, 180, 20, 50, 50, 100);
            //int hueLow, int hueHigh, int saturationLow, int saturationHigh, int intansityLow, int intensityHigh

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

    protected void initDefaultCommand() {
    }
}