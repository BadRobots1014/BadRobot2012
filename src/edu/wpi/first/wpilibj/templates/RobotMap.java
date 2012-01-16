package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
    public static final int cRIOsidecar = 3; // port for the cRIO to the digital
                                             // sidecar

    public static final int lFront = 0;
    public static final int rFront = 0;
    public static final int lBack = 2;
    public static final int rBack = 1;

    public static final int leftJoystick = 3;
    public static final int rightJoystick = 1;

    public static final int shooterJoystick = 4;

    public static final int shooter = 7;

    public static final int shooterMotor = 8;
}
