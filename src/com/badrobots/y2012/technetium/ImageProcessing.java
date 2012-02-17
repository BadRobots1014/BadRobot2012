/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Jon Buckley
 */
public class ImageProcessing extends Thread
{

    protected static final boolean LOGGING = true;
    protected AxisCamera camera;
    protected ParticleAnalysisReport[] toReturn;
    protected int sleepTimer = 2000;
    protected boolean running;

    public ImageProcessing(AxisCamera c)
    {
        camera = c;
        this.setPriority(MIN_PRIORITY);
        running = false;

        /* while (!camera.freshImage())
        {
        try {
        Thread.sleep(1000);
        } catch (InterruptedException ex) {
        ex.printStackTrace();
        }
        System.out.println("Image is unavailable");
        }  */
        camera.writeResolution(AxisCamera.ResolutionT.k160x120);

    }

    public void run()
    {
        while (true)
        {
            if (running)
            {
                ParticleAnalysisReport[] particleAnalysisReports = getRectangleParticles();
                setParticleAnalysisReport(particleAnalysisReports);
                println("running...");

                try
                {
                    Thread.sleep(sleepTimer);
                } catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }

            }

        }
    }

    protected void println(String string)
    {
        if (LOGGING)
        {
            System.out.println("ImageProcessing - " + string);
        }
    }
    int i = 0;

    public ParticleAnalysisReport[] getRectangleParticles()
    {
        toReturn = new ParticleAnalysisReport[1];

        ColorImage img;
        int current = 0;

        try
        {
            //gets and stores the current camera image

            img = camera.getImage();

            //Created a binary image where pixels meeting threshold
            BinaryImage binary = img.thresholdHSL(0, (int) (OI.getAnalogIn(1) * 100), 0, (int) (OI.getAnalogIn(2) * 100), (int) (OI.getAnalogIn(3) * 100), (int) (OI.getAnalogIn(4) * 100));
            println(" " + (int) (OI.getAnalogIn(1) * 100));
            //BinaryImage binary = img.thresholdHSL(0, 180, 40, 60, 60, 100);

            //binary = binary.convexHull(true);
            //Array of all detected rectangles, right?
            ParticleAnalysisReport[] particles = binary.getOrderedParticleAnalysisReports();

            ParticleAnalysisReport test;
            //Makes checks to see if the rectangle meets size and ratio requirements
            for (int i = 0; i < particles.length; i++)
            {
                test = particles[i];
                if (Math.abs(test.boundingRectWidth / test.boundingRectHeight - 1.33) < .2) //(test.particleToImagePercent > .1 && test.particleToImagePercent < .4)
                {

                    double ratio = test.boundingRectWidth / test.boundingRectHeight;
                    if (test.particleArea / (test.boundingRectHeight * test.boundingRectWidth) > .8) //(ratio > ((4/3) - .2) && ratio < ((4/3) + .2))
                    {
                        if (toReturn.length - 1 < current)
                        {
                            ParticleAnalysisReport[] temp = new ParticleAnalysisReport[current + 5];
                            for (int x = 0; x < toReturn.length; x++)
                            {
                                temp[x] = toReturn[x];
                            }

                            toReturn = temp;
                        }
                        toReturn[current] = test;
                        current++;
                    }
                }
            }

            println("got through the analysis hell " + "   Rects: " + (current) + " Raw: " + particles.length);
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, i + "  Rect: " + current + "area ");
            DriverStationLCD.getInstance().updateLCD();

            //release memory allocated to the images
            img.free();
            binary.free();
        } catch (NIVisionException ex)
        {
            ex.printStackTrace();
        } catch (AxisCameraException ace)
        {
            ace.printStackTrace();
        }

        //return the rectangles that meet the requirements
        return toReturn;
    }

    public ParticleAnalysisReport[] getParticleAnalysisReport()
    {
        synchronized (toReturn)
        {
            println("getting value");
            return toReturn;
        }
    }

    public void setParticleAnalysisReport(ParticleAnalysisReport[] particleAnalysisReports)
    {
        synchronized (toReturn)
        {
            toReturn = particleAnalysisReports;
        }
    }

    public boolean getRunning()
    {
        return running;
    }

    public void setRunning(boolean b)
    {
        running = b;
    }
}
