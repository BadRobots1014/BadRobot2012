/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.badrobots.y2012.technetium;


import com.badrobots.y2012.technetium.commands.PolarMechanumDrive;
import com.badrobots.y2012.technetium.commands.TankDrive;
import com.badrobots.y2012.technetium.commands.MechanumDrive;
import com.badrobots.y2012.technetium.commands.CommandBase;
import com.badrobots.y2012.technetium.subsystems.DriveTrain;
import com.badrobots.y2012.technetium.buttons.TankDriveTrigger;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.badrobots.y2012.technetium.buttons.MechanumDriveTrigger;
import com.badrobots.y2012.technetium.buttons.ResetGyro;
import com.badrobots.y2012.technetium.buttons.SwitchScalingSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStationLCD;

//import com.badrobots.y2012.technetium.buttons.ResetGyro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Technetium extends IterativeRobot 
{

    Command firstCommand;
    Button mecanumDriveTrigger, tankDriveTrigger, switchScaling;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        // Initialize all subsystems
        CommandBase.init();
    }

    public void autonomousInit()
    {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        
    }

    /*
     * Intializes all buttons that should be active during teleop period
     */
    public void teleopInit()
    {
        new TankDriveTrigger();
        new SwitchScalingSpeeds();
        new MechanumDriveTrigger();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Watchdog.getInstance().feed();
        //Runs the correct commands with their subsytems
        Scheduler.getInstance().run();
        
    }
}
