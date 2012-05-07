/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.badrobots.y2012.technetium;

import com.badrobots.y2012.technetium.buttons.*;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoDriveToAndDumpMedBasket;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoDriveToWallGyroCorrection;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoGoToTeamBridge;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoOrient;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoShootHighKey;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoShootHighKeyDriveBack;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoShootHighKeyNoBridge;
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
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import java.io.IOException;

//import com.badrobots.y2012.technetium.buttons.ResetGyro;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

/**
 * Main class, contains the methods called by the crio during competition. It all starts here.
 * @author 1014 Programming Team
 */
public class Technetium extends IterativeRobot
{
    /**
     * The button to make the turret autotrack targets
     */
    protected Button trackingButton;
    /**
     * The thread used to process images
     */
    protected ImageProcessing imageProcessingThread;
    /**
     * The webcam
     */
    protected AxisCamera camera;

    /**
     * This function is run when the robot is first booted and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        if(OI.cameraOn)
        {
            camera = AxisCamera.getInstance();

            imageProcessingThread = new ImageProcessing(camera);
            imageProcessingThread.start();
        }

        //This is where all subsystems are actually initialized
        CommandBase.init(imageProcessingThread);
        System.out.println("initializrd robot");
    }

    /**
     * This method runs once at the beginning of the autonomous mode.
     */
    public void autonomousInit()
    {
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
        Scheduler.getInstance().run();
    }

    /**
     * Initializes all buttons and commands that should run when autonomous starts
     */
    public void teleopInit()
    {
        //Set all subsystems to the manual control methods.
        Scheduler.getInstance().add(new GatherBallsAndManualShoot());
        Scheduler.getInstance().add(new Monitor());
        Scheduler.getInstance().add(new MechanumDrive());
        Scheduler.getInstance().add(new ManualBridge());

        //Initialize all used buttons
        new InitiateRampButton();

        //If the master boolean for using the camera is set to true, image processing is started
        if(OI.cameraOn)
        {
            System.out.println("ThreadStarted");
            new TrackingButton(imageProcessingThread);
            imageProcessingThread.setRunning(false);
        }

         //If the master boolean for using shooter PID is true, PID is started
        if(OI.shooterPIDOn)
        {
            System.out.println("EnablingHERE");
            Artemis.getInstance().setShooterPIDRunning(true);
        }
        
        //puts all the commands into the SmartDashboard
        SmartDashboard.putData(Scheduler.getInstance());
       
        try
        {
            System.out.println("Dashboard boolean: " + SmartDashboard.getBoolean("TurnTablePIDOn"));
        }
        catch (NetworkTableKeyNotDefined ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        //Feed it or it dies. And then stops the robot. From the grave. Really it is a poor metaphor.
        //Yes quite a poor metaphor.
        //A poor methaphor indeed
        Watchdog.getInstance().feed();
        
        //Runs the correct commands with their subsytems
        Scheduler.getInstance().run();     
    }

    /**
     * Shuts down certain systems when called. Automatically called when robot is disabled.
     */
    public void disabledInit()
    {
        //Poetic message coutesy of Jon
        System.out.println("Default IterativeRobot.disabledInit() method... Overload yo momma!");

        //Stops the image processing thread
        if (imageProcessingThread != null)
        {
            imageProcessingThread.setRunning(false);
        }
        //Stops the shooter PID
        Artemis.getInstance().setShooterPIDRunning(false);
    }
}
