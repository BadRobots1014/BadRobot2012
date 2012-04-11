/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.GearTooth;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Jon Buckley
 */
public class GearToothPID extends Thread implements PIDSource
{
    protected GearTooth gearTooth;
    protected boolean running;
    protected double gearToothSpeed = -1;
    protected int sleepTime = 50;
    
    public GearToothPID(int port)
    {
        gearTooth = new GearTooth(port);
        this.setPriority(MIN_PRIORITY);
        running = true;
    }
    
    public void run()
    {
        while(true)
        {
            if (running)
            {
                gearTooth.reset();
                gearTooth.start();
                
                try
                {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                
                gearToothSpeed = gearTooth.get()/sleepTime;
            }
        }
    }

    public double pidGet()
    {
        return gearToothSpeed;   
    }
    
    
}
