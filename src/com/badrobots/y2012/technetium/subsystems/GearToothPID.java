/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GearTooth;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Jon Buckley
 */
public class GearToothPID extends Thread implements PIDSource
{
    protected GearTooth gearTooth;
    protected boolean running = true;
    protected double gearToothSpeed = -1;
    protected int sleepTime = 50;
    
    public GearToothPID(int port)
    {
        gearTooth = new GearTooth(port);
        running = true;
        SmartDashboard.putDouble("GearToothSpeed", gearToothSpeed);
    }
    
    public void run()
    {
        while(true)
        {
            //System.out.println("running outside run method loop");
            if (true)//if this is "running", the code breaks. Why?
            {   
               // System.out.println("running run method in GearToothPID");
                try
                {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                
                gearToothSpeed = 1/gearTooth.getPeriod();
                System.out.println("GearToothSpeed " +  gearToothSpeed);
                SmartDashboard.putDouble("GearToothSpeed", gearToothSpeed);
            }
        }
    }

    public double pidGet()
    {
        return gearToothSpeed;   
    }

    public void setRunning(boolean b)
    {
        running = b;
    }
    
    
}
