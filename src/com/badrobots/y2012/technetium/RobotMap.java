package com.badrobots.y2012.technetium;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * @author 1014 Programming Team
 */
public class RobotMap
{

    /**
     * port from the cRIO to the digital sidecar
     */
    public static final int cRIOsidecar = 1;
    /**
     * The left front wheel speed controller
     */
    public static final int lFront = 4;
    /**
     * The right front wheel speed controller
     */
    public static final int rFront = 2;
    /**
     * The left rear wheel speed controller
     */
    public static final int lBack = 3;
    /**
     * The right rear wheel speed controller
     */
    public static final int rBack = 1;
    /**
     * First Logiteck Joystick
     */
    public static final int leftJoystick = 1;
    /**
     * Second Logitech Joystick
     */
    public static final int rightJoystick = 2;
    /**
     * First Xbox controller
     */
    public static final int controller = 1;
    /**
     * Second Xbox controller
     */
    public static final int controller2 = 2;
    /**
     * Ultrasonic output channel
     */
    public static final int ultrasonicOut = 3; //2 scotty 3 mike
    /**
     * Ultrasonic input channel
     */
    public static final int ultrasonicIn = 2; //3
    /**
     * Bottom optical sensor
     */
    public static final int bottomSensor = 4; //7
    /**
     * Webcam
     */
    public static final int camera = 4;
    /**
     * Horizontally oriented gyro
     */
    public static final int horizontalGyro = 1;
    /**
     * Relay for the conveyor system
     */
    public static final int conveyor = 1;
    /**
     * Relay for the bottom roller
     */
    public static final int bottomRoller = 2;
    /**
     * The left shooter motor
     */
    public static final int leftShooter = 5;
    /**
     * The right shooter motor
     */
    public static final int rightShooter = 6;
    /**
     * The victor attached to the bridging arm
     */
    public static final int bridgingTool = 8;
    /**
     * The turntable motor for the shooter
     */
    public static final int turnTable = 9;
    /**
     * The first encoder channel for the encoder on the turntable
     */
    public static final int turnTableEncoderAChannel = 1;
    /**
     * The second channel for the encoder on the turntable
     */
    public static final int turnTableEncoderBChannel = 2;
    /**
     * The optical sensor to measure the turret wheel speed
     */
    public static final int opticalSensor = 6;
    /**
     * The spike regulating power to the led indicator light
     */
    public static final int LEDCircle = 3;
}
