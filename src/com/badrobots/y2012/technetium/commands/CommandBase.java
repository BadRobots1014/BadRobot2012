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
    public static DriveTrain driveTrain;
    /**
     * The stored instance of the shooter
     */
    public static Shooter shooter;
    /**
     * The stored instance of the bridging tool
     */
    public static BridgingArm bridgeTool;
    /**
     * The stored instance of the sensor array
     */
    public static Sensors sensors;
    /**
     * The stored instance of the ball gathering system
     */
    public static BallGatherer ballGatherer;
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
        driveTrain = DriveTrain.getInstance();
        sensors = Sensors.getInstance();
        bridgeTool = BridgingArm.getInstance();
        shooter = Shooter.getInstance();
        ballGatherer = BallGatherer.getInstance();
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
