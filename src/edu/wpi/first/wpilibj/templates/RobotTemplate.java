/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.buttons.MechanumDriveTrigger;
import edu.wpi.first.wpilibj.templates.buttons.TankDriveTrigger;
import edu.wpi.first.wpilibj.templates.commands.*;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {

    Command firstCommand;
    Button mecanumDriveTrigger, tankDriveTrigger;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        // Initialize all subsystems
        CommandBase.init();
        // instantiate the command used for the autonomous period
        firstCommand = new PolarMechanumDrive();

        //Initializes triggers
        mecanumDriveTrigger = new MechanumDriveTrigger();
        tankDriveTrigger = new TankDriveTrigger();
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

    public void teleopInit()
    {
	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to 
	// continue until interrupted by another command, remove
	// this line or comment it out.
        //Scheduler.getInstance().add(firstCommand);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Watchdog.getInstance().feed();
        Scheduler.getInstance().run();

        //Polls the buttons to see if they are active, if they are, it adds the
        //command to the Scheduler.
        if (mecanumDriveTrigger.get())        
            Scheduler.getInstance().add(new MechanumDrive());

        else if (tankDriveTrigger.get())
            Scheduler.getInstance().add(new TankDrive());
        
        else if (OI.rightJoystick.getRawButton(2))
            Scheduler.getInstance().add(new PolarMechanumDrive());

        //Puts the current command being run by DriveTrain into the SmartDashboard
        SmartDashboard.putData(DriveTrain.getInstance().getCurrentCommand());
        
        SmartDashboard.putString(ERRORS_TO_DRIVERSTATION_PROP, "Test String");


    }
}
