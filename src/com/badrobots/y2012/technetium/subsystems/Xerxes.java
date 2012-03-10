
/*
 *
 * This is the bridging tool
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Victor;
import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.commands.ManualBridge;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;

/*
 * @author 1014 Programming Team
 */
public class Xerxes extends Subsystem 
{
    private static Xerxes instance;
    private static Jaguar motor;
    private static Gyro verticalGyro;

    public static Xerxes getInstance()
    {
        if (instance == null)
        {
            instance = new Xerxes();
        }
        return instance;
    }

    private Xerxes()
    {
        super();
        motor = new Jaguar(RobotMap.bridgingTool);
        System.out.println("Xerxes: initialized");
       /// verticalGyro = new Gyro(RobotMap.verticalGyro);
    }

    public void setMotor(double speed)
    {
        motor.set(speed);
    }

    public void initDefaultCommand()
    {
        System.out.println("setting default command of Xerxes");
        super.setDefaultCommand(new ManualBridge());
    }
}