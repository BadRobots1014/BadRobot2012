package com.badrobots.y2012.technetium;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

/*
 * @author 1014 Programming Team
 */
public class RobotMap
{

    public static final int cRIOsidecar = 1; // port from the cRIO to the digital
    // sidecar
    public static final int lFront = 4;// 3
    public static final int rFront = 2;// 2
    public static final int lBack = 3;// 5
    public static final int rBack = 1;//1
    public static final int leftJoystick = 1;
    public static final int rightJoystick = 2;
    public static final int controller = 3;
    public static final int controller2 = 4;
    public static final int shooterJoystick = 4;
    public static final int ultrasonicOut = 0; // TODO
    public static final int ultrasonicIn = 0; // TODO
    public static final int topSensor = 5;
    public static final int bottomSensor = 7;
    public static final int camera = 4;
    public static final int horizontalGyro = 10;
    public static final int verticalGyro = 1;
    public static final int conveyor = 2; //TODO (find all these ports)
    public static final int bottomRoller = 1;
    //public static final int topRoller = 7;
    public static final int leftShooter = 6;
    public static final int rightShooter = 7;
    public static final int bridgingTool = 9;//TODO Find port
}
