
/*
 *
 * This is the bridging tool
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Victor;
import com.badrobots.y2012.technetium.RobotMap;
import edu.wpi.first.wpilibj.Gyro;

/*
 * @author 1014 Programming Team
 */
public class Xerxes extends Subsystem 
{
    private static Xerxes instance;
    private static Victor motor;
    private static Gyro verticalGyro;
    private boolean stopCondition;

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
        verticalGyro = new Gyro(RobotMap.verticalGyro);
        stopCondition = false;//THIS NEEDS TO BE REPLACED WITH AN ENCODER OR LIMIT SWITCH
    }

    public void setMotor(double speed)
    {
        if((verticalGyro.getAngle() > 10 || verticalGyro.getAngle() < -10) && stopCondition)
        {
            motor.set(-1);
        }
        
        else
            motor.set(speed);
    }

    public void initDefaultCommand()
    {
    }
}