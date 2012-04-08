/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.badrobots.y2012.technetium;

import com.badrobots.y2012.technetium.buttons.*;
import com.badrobots.y2012.technetium.commands.AutoDriveToAndDumpMedBasket;
import com.badrobots.y2012.technetium.commands.AutoDriveToWallGyroCorrection;
import com.badrobots.y2012.technetium.commands.AutoGoToTeamBridge;
import com.badrobots.y2012.technetium.commands.AutoOrient;
import com.badrobots.y2012.technetium.commands.AutoShootHighKey;
import com.badrobots.y2012.technetium.commands.AutoShootHighKeyDriveBack;
import com.badrobots.y2012.technetium.commands.AutoShootHighKeyNoBridge;
import com.badrobots.y2012.technetium.commands.PolarMechanumDrive;
import com.badrobots.y2012.technetium.commands.TankDrive;
import com.badrobots.y2012.technetium.commands.MechanumDrive;
import com.badrobots.y2012.technetium.commands.CommandBase;
import com.badrobots.y2012.technetium.commands.DriveToWall;
import com.badrobots.y2012.technetium.commands.GatherBallsAndManualShoot;
import com.badrobots.y2012.technetium.commands.ManualBridge;
import com.badrobots.y2012.technetium.commands.Monitor;
import com.badrobots.y2012.technetium.subsystems.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    Button mecanumDriveTrigger, tankDriveTrigger, switchScaling, trackingButton;
    protected ImageProcessing thread;
    protected AxisCamera camera;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        // Initialize all subsystems
        
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        if(OI.cameraOn)
        {
            camera = AxisCamera.getInstance();

            thread = new ImageProcessing(camera);
            thread.start();
        }

        //This is where all subsystems are actually initialized
        CommandBase.init(thread);

    }

    public void autonomousInit()
    {
        //System.out.println("Init");
        if(OI.getDigitalIn(6))
            Scheduler.getInstance().add(new AutoShootHighKeyNoBridge());
        else if(OI.getDigitalIn(5))
            Scheduler.getInstance().add(new AutoDriveToAndDumpMedBasket());
        else if(OI.getDigitalIn(7))
            Scheduler.getInstance().add(new AutoShootHighKey());
        else if(OI.getDigitalIn(8))
            Scheduler.getInstance().add(new AutoShootHighKeyDriveBack());
        else
            Scheduler.getInstance().add(new AutoShootHighKeyNoBridge());
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        //Scheduler.getInstance().add(new DriveToWall());//We need to test to see if this is stopped after autonomous is over
        Scheduler.getInstance().run();
    }

    /*
     * Intializes all buttons that should be active during teleop period
     */
    public void teleopInit()
    {
        
        Scheduler.getInstance().add(new GatherBallsAndManualShoot());
        Scheduler.getInstance().add(new Monitor());
        Scheduler.getInstance().add(new MechanumDrive());
        Scheduler.getInstance().add(new ManualBridge());

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

        if(OI.cameraOn)
        {
            System.out.println("ThreadStarted");
            new TrackingButton(thread);
            thread.setRunning(false);
        }

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
        
        //System.out.println("Encoder : "  + Artemis.getInstance().encoderValue());
        //System.out.println("Range: " + Helios.getInstance().getUltraFrontRange());//  + " ranger: " + Artemis.getInstance().distanceToWall());
        
        if (trackingButton == null)
            trackingButton = new TrackingButton(thread);
    }

    public void disabledInit()
    {
        System.out.println("Default IterativeRobot.disabledInit() method... Overload you!");
        //Helios.getInstance().setNumBalls(0); //TODO
        if (thread != null)
        {
            thread.setRunning(false);
        }
        //else
            //kinecter.setRunning(false);
    }
}
