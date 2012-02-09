package com.badrobots.y2012.technetium.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.*;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * 
 */

/*
 * @author 1014 Programming Team
 */
public abstract class CommandBase extends Command
{
    // Create a single static instance of all of your subsystems
    public static Hermes driveTrain;
    public static Artemis shooter;
    public static Helios sensors;
    public static Demeter ballGatherer;
    public static Xerxes bridgeTool;

    public static void init()
    {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        OI.init();
        driveTrain = Hermes.getInstance();
       // shooter = Shooter.getInstance();
        
        
        //shooter = Shooter.getInstance();
        //sensors = ArenaSensors.getInstance();
        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(driveTrain);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
