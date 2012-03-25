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
    protected static final boolean LOGGING = true;
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
        while (true)
        {
            if (running)
            {
                //println("running");
                setHoopCoords();
                
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

    public void setHoopCoords()
    {
        int current = 0;
        try
        {
            //gets and stores the current camera image
            ColorImage img = camera.getImage();
            
            BinaryImage binary = img.thresholdHSL(130, 200, 60, 253, 40, 255);//141, 208, 50, 255, 0, 255);
            
            //Convex Hull
            binary.convexHull(true);
            
            //Remove small objects (parameters are connectivity and number of erosions)
            BinaryImage noSmall = binary.removeSmallObjects(true, 1);
            noSmall.particleFilter(criteria);
            ParticleAnalysisReport[] report = noSmall.getOrderedParticleAnalysisReports();
            
            // binary.free();

            int size = report.length;
            if (size > 0)
            {
                double biggest = report[0].particleArea;
                int biggestIndex = 0;

                for (int i = 0; i < report.length; i++)
                {
                    if (report[i].particleArea > biggest)
                    {
                        biggest = report[i].particleArea;
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
