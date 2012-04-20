/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.GearTooth;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Jon Buckley
 */
public class GearToothPID extends Thread implements PIDSource
{

    protected GearTooth gearTooth;
    protected boolean running = true;
    protected boolean stopped = false;
    protected double gearToothSpeed = -1;
    protected double gearToothPeriod = 0;
    protected int sleepTime = 50;
    protected boolean inChannel = false;
   // protected DigitalInput in;

    public GearToothPID(int port)
    {
        gearTooth = new GearTooth(port, false);
        //in = new DigitalInput(port);
        running = true;
        gearTooth.reset();
        gearTooth.setMaxPeriod(2);
        gearTooth.start();

        gearToothSpeed = gearTooth.get();
        stopped = gearTooth.getStopped();
        
        //inChannel = in.get();
        
        SmartDashboard.putBoolean("DigitalInput", inChannel);
       /// SmartDashboard.putBoolean("GearToothStopped", stopped);
    }

    public void run()
    {
        while (true)
        {
            //System.out.println("running outside run method loop");
            if (true)//if this is "running", the code breaks. Why?
            {
                // System.out.println("running run method in GearToothPID");

                //gearToothPeriod = gearTooth.getPeriod();
                gearToothSpeed = gearTooth.get();
                stopped = gearTooth.getStopped();
                System.out.println("GearToothCount " + gearToothSpeed);
                
               // inChannel = in.get();
                SmartDashboard.putBoolean("DigitalInput", inChannel);
                //SmartDashboard.putDouble("GearToothPeriod", gearToothPeriod);
            }

            try
            {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
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
