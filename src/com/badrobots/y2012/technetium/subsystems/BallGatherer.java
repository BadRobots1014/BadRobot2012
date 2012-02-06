/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import com.badrobots.y2012.technetium.RobotMap;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Jon Buckley
 */
public class BallGatherer extends Subsystem 
{
    BallGatherer instance;
    AnalogChannel garageSensor;
    Victor conveyor, bottomRoller, topRoller;
    double threshold = 200;
    
    public BallGatherer getInstance()
    {
        if (instance == null)
        {
           instance = new BallGatherer(); 
        }
        
        return instance;
    }
        
    public BallGatherer()
    {
        super();
        garageSensor = new AnalogChannel(RobotMap.garage);
        conveyor = new Victor(RobotMap.conveyor);
        bottomRoller = new Victor(RobotMap.bottomRoller);
        topRoller = new Victor(RobotMap.topRoller);
    }
    
    public void runConveyor(double speed)
    {
        conveyor.set(-speed);
        bottomRoller.set(speed);
    }
    
    public void runTopRoller(double speed)
    {
        topRoller.set(speed);
    }
    
    public void runBottomRoller(double speed)
    {
        bottomRoller.set(speed);
    }
    
    /*
     * @return whether the garage door sensor is obscured. 
     * Method uses analog threshold to detect this
     */
    public boolean channelBlocked()
    {
        if (garageSensor.getAverageVoltage() > threshold)
            return false;
        
        return true;
    }
    
    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
