package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    public static final int cRIOsidecar = 3;

    public static final int lFront = 0;
    public static final int rFront = 0;
    public static final int lBack = 2;
    public static final int rBack = 1;

    public static final int leftJoystick = 3;
    public static final int rightJoystick = 1;

    public static final int shooter = 7;

    public static final int shooterMotor = 8;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
}
