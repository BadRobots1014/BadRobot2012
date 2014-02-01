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
    public static Sidecar Sidecar;
    
    public static final int DriverStation_ControllerPort1 = 1;
    public static final int DriverStation_ControllerPort2 = 2;
    
    public static final int NI9102_AnalogIn1 = 1;

    
    // TODO: Figure out what the 3 is for, add it to the RobotMap or 
    // Sidecar definition, refactor that uses this to point to the
    // new definition
    /**
     * The spike regulating power to the led indicator light
     */
    public static final int LEDCircle = Sidecar.RELAY3;
}
