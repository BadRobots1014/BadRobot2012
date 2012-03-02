/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.badrobots.y2012.technetium;

import com.badrobots.y2012.technetium.buttons.*;
import com.badrobots.y2012.technetium.commands.PolarMechanumDrive;
import com.badrobots.y2012.technetium.commands.TankDrive;
import com.badrobots.y2012.technetium.commands.MechanumDrive;
import com.badrobots.y2012.technetium.commands.CommandBase;
//import com.badrobots.y2012.technetium.commands.DriveToWall;
import com.badrobots.y2012.technetium.commands.DriveToWall;
import com.badrobots.y2012.technetium.subsystems.Hermes;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.badrobots.y2012.technetium.subsystems.Helios;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import java.io.IOException;

//import com.badrobots.y2012.technetium.buttons.ResetGyro;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

/*
 * @author 1014 Programming Team
 */
public class Technetium extends IterativeRobot
{

    Command firstCommand;
    Button mecanumDriveTrigger, tankDriveTrigger, switchScaling;
    protected ImageProcessing thread;
    protected PacketListener kinecter;
    protected AxisCamera camera;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        // Initialize all subsystems
        
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        
        if(OI.kinnectOn)
        {
            try
            {
                kinecter = new PacketListener();
                //camera = AxisCamera.getInstance();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        //This is where all subsystems are actually initialized
        CommandBase.init(kinecter);

    }

    public void autonomousInit()
    {
        //System.out.println("Init");
        //Scheduler.getInstance().add(new DriveToWall());
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().add(new DriveToWall());//We need to test to see if this is stopped after autonomous is over
        Scheduler.getInstance().run();
    }

    /*
     * Intializes all buttons that should be active during teleop period
     */
    public void teleopInit()
    {
        new TankDriveTrigger();
        new MechanumDriveTrigger();

        /*try
        {
            kinecter = new PacketListener();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        *
        */
        //kinecter.start();
        
        // thread = new ImageProcessing(camera);
        // thread.start();
        // thread.setRunning(true);

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        //Feed it or it dies. And then stops the robot. From the grave. Really it is a poor metaphor.
        Watchdog.getInstance().feed();

        //Runs the correct commands with their subsytems
        Scheduler.getInstance().run();
    }

    public void disabledInit()
    {
        System.out.println("Default IterativeRobot.disabledInit() method... Overload you!");
        //Helios.getInstance().setNumBalls(0); //TODO
        /*if (thread != null)
        {
            thread.setRunning(false);
        }
        else
            kinecter.setRunning(false);*/
    }
}
