/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

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
        camera.writeResolution(AxisCamera.ResolutionT.k160x120);
        this.setPriority(MIN_PRIORITY);
        running = false;
    }

    public void run()
    {
        while(true)
        {
            if (running)
            {
                ParticleAnalysisReport[] particleAnalysisReports = getRectangleParticles();
            setParticleAnalysisReport(particleAnalysisReports);
            
            try {
                Thread.sleep(sleepTimer);
            } catch (InterruptedException ex) {
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
    
     public ParticleAnalysisReport[] getRectangleParticles() 
     {        
        toReturn = new ParticleAnalysisReport[4];
        
        ColorImage img;
        int current = 0;
        
        try 
        {
            //gets and stores the current camera image
            img = camera.getImage();

            //Created a binary image where pixels meeting threshold
            BinaryImage binary =  img.thresholdHSL(170, 180, 90, 100, 0, 5);
            
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
        catch (AxisCameraException ace)
        {
            ace.printStackTrace();
        }

        //return the rectangles that meet the requirements
        return toReturn;
    }
     
     public ParticleAnalysisReport[] getParticleAnalysisReport()
     {
         synchronized(toReturn)
         {
             println("getting value");
            return toReturn;             
         }
     }
     
     public void setParticleAnalysisReport(ParticleAnalysisReport[] particleAnalysisReports)
     {
         synchronized(toReturn)
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
