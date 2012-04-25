/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Artemis;
import com.badrobots.y2012.technetium.subsystems.Helios;
import com.badrobots.y2012.technetium.subsystems.Demeter;
import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * @author 1014
 */
public class Monitor extends CommandBase {

    private boolean topWasBlocked;
    private boolean bottomWasBlocked;
    boolean toggle = false;
    boolean camera = false;

    public Monitor() {
        requires(sensors);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        topWasBlocked = false;
        bottomWasBlocked = false;
        OI.setDigitalOutput(8, camera);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {

        //System.out.println("Meters: " + sensors.getUtraFrontRange()/1000);

        /*if(sensors.bottomChannelBlocked())
        {            
            bottomWasBlocked = true;
        }
        else if(bottomWasBlocked)
        {
            sensors.setNumBalls(sensors.getNumBalls() + 1);
            bottomWasBlocked = false;
            System.out.println("BallAdded : " + sensors.getNumBalls());
        }

        if(sensors.topChannelBlocked())
        {
            topWasBlocked = true;
        }
        else if(topWasBlocked)
        {
            if(Demeter.getInstance().conveyorDown())
            {
                sensors.setNumBalls(sensors.getNumBalls() - 1);
                System.out.println("BallRemoved : " + sensors.getNumBalls());
            }
            topWasBlocked = false;
            
        }


        updateDriverstationBallCount();
         */
        //sensors.closerThan(1);
        if(OI.secondXboxXButton())
        {
            toggle = true;
        }
        else if(toggle)
        {
            toggle = false;
            camera = !camera;
            OI.setDigitalOutput(9, camera);
        }
        
    }
    
    public void updateDriverstationBallCount()
    {
        //update ballcount
        int balls = sensors.getNumBalls();
        if(balls >= 4)
        {
            DriverStation.getInstance().setDigitalOut(3, true);
            balls -= 4;
        }
        else
            DriverStation.getInstance().setDigitalOut(3, false);
        if(balls >= 2)
        {
            DriverStation.getInstance().setDigitalOut(2, true);
            balls -= 2;
        }
        else
            DriverStation.getInstance().setDigitalOut(2, false);
        if(balls >= 1)
            DriverStation.getInstance().setDigitalOut(1, true);
        else
            DriverStation.getInstance().setDigitalOut(1, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}