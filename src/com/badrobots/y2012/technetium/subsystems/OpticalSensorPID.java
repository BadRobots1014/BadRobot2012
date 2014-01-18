/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import com.badrobots.y2012.technetium.RobotMap;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Jon Buckley
 */
public class OpticalSensorPID extends Thread implements PIDSource
{

    protected double rate = 0;
    protected int count = 0;
    protected boolean blocked = false;
    protected Counter opticalSensorCounterForShooter;
    protected boolean running = false;
    private long sleepTime = 50;

    public OpticalSensorPID()
    {
        opticalSensorCounterForShooter = new Counter(RobotMap.Sidecar.DIO6);
        running = true;
        opticalSensorCounterForShooter.setMaxPeriod(2);
        
        //SmartDashboard.putDouble("Period", rate);
    }
    
    public void run()
    {
        opticalSensorCounterForShooter.start();

        while (true)
        {
            if (true)
            {                
                //this is the variable we will use to PID. The smaller this value is,
                //the faster the wheel is turning
                rate = opticalSensorCounterForShooter.getPeriod();
                SmartDashboard.putDouble("Period", rate);
                //System.out.println("Rate: " + rate);
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

    public void setRunning(boolean b)
    {
        running = b;
    }

    public double pidGet()
    {
        /*if (!(rate < 1 && rate > 0))
            return 1;*/
        
        if(1/rate < 1)//1/rate for bb
        {
            System.out.println("rate1:" + 1);
            return 1;
        }

        //System.out.println("rate2:" + rate);
        //System.out.println("rate1:" + 1/rate);//1/rate for bb
        return 1/rate;//1/rate for bb
    }
}
