/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Jon Buckley
 */
public class ImageProcessing extends Thread
{
    protected static final boolean LOGGING = false;
    protected AxisCamera camera;
    protected ParticleAnalysisReport[] toReturn;
    protected int sleepTimer = 100;
    protected boolean running;
    protected int[] coords;
    protected CriteriaCollection criteria;


    public ImageProcessing(AxisCamera c)
    {
        camera = c;
        this.setPriority(MIN_PRIORITY);
        running = true;
        criteria = new CriteriaCollection();
        criteria.addCriteria(NIVision.MeasurementType.IMAQ_MT_CENTER_OF_MASS_Y, 0, 50, true);//wrong

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
            
            //binary = img.thresholdHSL(98, 155, 45, 255, 137, 255);//Works great at low angles.
            binary = img.thresholdHSL(10, 155, 20, 255, 55, 255);
            
            //Convex Hull
            binary.convexHull(true);
            
            //Remove small objects (parameters are connectivity and number of erosions)
            noSmall = binary.removeSmallObjects(true, 1);
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
