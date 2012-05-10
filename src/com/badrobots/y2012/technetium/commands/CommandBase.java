package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.ImageProcessing;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.*;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author 1014 Programming Team
 */
public abstract class CommandBase extends Command
{
    /**
     * The stored instance of the driveTrain
     */
    public static Hermes driveTrain;
    /**
     * The stored instance of the shooter
     */
    public static Artemis shooter;
    /**
     * The stored instance of the bridging tool
     */
    public static Xerxes bridgeTool;
    /**
     * The stored instance of the sensor array
     */
    public static Helios sensors;
    /**
     * The stored instance of the ball gathering system
     */
    public static Demeter ballGatherer;
    /**
     * The image processing thread
     */
    public static ImageProcessing imageProcessor;

    /**
     * Created instances of all the subsystems. Must be called before any other actions are performed on the robot.
     * @param processor The image processing thread
     */
    public static void init( ImageProcessing processor)
    {
        OI.init();
        driveTrain = Hermes.getInstance();
        sensors = Helios.getInstance();
        bridgeTool = Xerxes.getInstance();
        shooter = Artemis.getInstance();
        ballGatherer = Demeter.getInstance();
        imageProcessor = processor;
        SmartDashboard.putData(driveTrain);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
