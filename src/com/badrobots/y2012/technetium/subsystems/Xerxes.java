/*
 *
 * This is the bridging tool
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Victor;
import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.commands.manualBridge;

/*
 * @author 1014 Programming Team
 */
public class Xerxes extends Subsystem {
    private static Xerxes instance;
    private static Victor motor;

    public static Xerxes getInstance()
    {
        if (instance == null)
        {
            instance = new Xerxes();
        }
        return instance;
    }

    public Xerxes()
    {
        super();
        motor = new Victor(RobotMap.bridgingTool);
    }

    public void setMotor(double speed)
    {
        motor.set(speed);
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new manualBridge());
    }
}