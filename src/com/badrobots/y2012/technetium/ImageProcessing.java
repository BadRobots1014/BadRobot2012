/*
 * This is the image processing thread. It takes images from the webcam and uses 
 * an analysis algorithm to separate out the backboard targets. It has a method to return
 * an array of coordinates for the processed targets. The thread runs at the minimum priority
 * as to reduce lag in the robot.
 * 
 * It was not used in competition, and it was still buggy near the end of testing. The problems
 * are more likely to be in the tuning of the ranges and not in the logic itself.
 *
 */
package com.badrobots.y2012.technetium;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author 1014 Team
 */
public class ImageProcessing extends Thread
{
    //whether or not it is printing debugging lines to the terminal
    protected static final boolean LOGGING = false; 
    //The webcam on top of the shooter
    protected AxisCamera camera;
    //The particle analysis report which is returned after processing an image
    protected ParticleAnalysisReport[] toReturn;
    //How long to wait in between images. Increasing this value decreaces processing power used
    protected int sleepTimer = 100;
    //Whether or not images are currently being processed
    protected boolean running;
    //The coordinates of the detected targets
    protected int[] coords;
    //Filter used for certain operations. Currently unused
    protected CriteriaCollection criteria;

    /*
     *
     */
    public ImageProcessing(AxisCamera c)
    {
        //The thread starts with the images being processed, and the thread at minimum priority
        running = true;
        this.setPriority(MIN_PRIORITY);


        camera = c;
        camera.writeResolution(AxisCamera.ResolutionT.k160x120);
        
        coords = new int[2];
        coords[0] = -1;
        coords[1] = -1;

    }

    public void run()
    {
        ColorImage img = null;
        BinaryImage binary = null;
        BinaryImage noSmall = null;
        while (true)
        {
            if (running)
            {
                //println("running");
                setHoopCoords(img, binary, noSmall);
                
                try
                {
                    if(img != null)
                        img.free();
                    if(binary != null)
                        binary.free();
                    if(noSmall != null)
                        noSmall.free();
                    Thread.sleep(sleepTimer);
                } catch (Exception ex)
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
            System.out.println("ImageProcessing - ".concat(string));
        }
    }
    int i = 0;

    public void setHoopCoords(ColorImage img, BinaryImage binary, BinaryImage noSmall)
    {
        int current = 0;
        try
        {
            //gets and stores the current camera image
            img = camera.getImage();

            binary = img.thresholdHSL(100, 156, 30, 255, 145, 255);
            //binary = img.thresholdHSL(98, 155, 45, 255, 137, 255);//Works great on scottie for some reason. Whatever, sure.
            //binary = img.thresholdHSL(10, 155, 20, 255, 55, 255);
            
            //Convex Hull
            binary.convexHull(true);
            
            //Remove small objects (parameters are connectivity and number of erosions)
            noSmall = binary.removeSmallObjects(true, 3);
            //noSmall.particleFilter(criteria);
            ParticleAnalysisReport[] report = noSmall.getOrderedParticleAnalysisReports();


            int size = report.length;
            System.out.println("Size: " + size);
            if (size > 0)
            {
                double lowest = report[0].center_mass_y;
                int biggestIndex = 0;

                for (int i = 0; i < report.length; i++)
                {
                    if (report[i].center_mass_y < lowest)
                    {
                        lowest = report[i].center_mass_y;
                        biggestIndex = i;
                    }
                }
            
                coords[0] = report[biggestIndex].center_mass_x;
                coords[1] = report[biggestIndex].center_mass_y;
                this.println("size of analysis: " + report.length + "center of mass x: " + report[0].center_mass_x);
            }
            
            else
            {
                this.println(" no particles detected");
                coords[0] = -1;
                coords[1] = -1;
            }

            
            img.free();
            binary.free();
            noSmall.free();
            
            System.out.println("images have been freed");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

   
    public int[] getCoords()
    {
        synchronized (coords)
        {
            return coords;
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
