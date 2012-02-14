/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.commands.GatherBallsAndManualShoot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * CODE IS UNTESTED -- MUST HAVE SPECIFIC RobotMap CHANNELS FILLED IN, AND CLASS DEBUGGED
 * @author 1014 Programming Team
 */
public class Demeter extends Subsystem 
{
    protected static Demeter instance;
    AnalogChannel garageSensor;
    Relay conveyor, bottomRoller;
    double threshold = 2; // voltage readout from the analog channel
    private int balls = 0;

    
    /**
     * Singleton static getter method for the class -- only one instance of BallGatherer
     * in program
     * @return the instance of itself--if not already initialized, this method also
     * calls its constructor
     */
    public static Demeter getInstance()
    {
        if (instance == null)
        {
           instance = new Demeter(); 
        }
        
        return instance;
    }
        
    /*
     * Private constructor that is called only if instance has not been instantiated
     * Also, it initializes its two motors
     */
    private Demeter()
    {
        super();
        conveyor = new Relay(RobotMap.conveyor);
        bottomRoller = new Relay(RobotMap.bottomRoller);
        bottomRoller.setDirection(Relay.Direction.kBoth);
        conveyor.setDirection(Relay.Direction.kBoth);
    }
    
    /*
     * Runs the bottomRoller and conveyor motor: the bottom half of the gatherer
     */
    public void runConveyor(boolean backward, boolean forward)
    {
        if(backward)
            conveyor.set(Relay.Value.kForward);
        else if(forward)
            conveyor.set(Relay.Value.kReverse);
        else
            conveyor.set(Relay.Value.kOff);
    }
    
    /*
     * Runs just the bottomRoller (the motor that pulls the ball into the conveyor)
     */
    public void runBottomRoller(boolean off, boolean reverse)
    {
         if(off)
         {
             bottomRoller.set(Relay.Value.kOff);
         }
         else if(reverse)
         {
             bottomRoller.set(Relay.Value.kReverse);
         }
         else
         {
             bottomRoller.set(Relay.Value.kForward);
         }
    }
    
    
    /*
     * Tells the BallGatherer that a ball has been shot off
     */
    public void notifyBallShot()
    {
        balls--;
        if (balls < 0)
            System.out.println("Hmmm... we have a problem: negatives balls");
    }
    
    /** 
     * @return the number of balls currently held in the gatherer
     */
    public int numBalls()
    {
        return balls;
    }
    
    /**
     * Increments the ball count
     */
    public void addBall()
    {
        balls++;
    }
    
    public void initDefaultCommand()
    {
        setDefaultCommand(new GatherBallsAndManualShoot());
    }
}
