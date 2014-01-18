
/*
 *
 * This is the bridging tool
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.commands.ManualBridge;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;

/*
 * @author 1014 Programming Team
 */
public class BridgingArm extends Subsystem 
{
    private static BridgingArm instance;
    
    private static Gyro verticalGyro;

    public static BridgingArm getInstance()
    {
        if (instance == null)
        {
            instance = new BridgingArm();
        }
        return instance;
    }

    private BridgingArm()
    {
        super();
        System.out.println("Xerxes: initialized");
       /// verticalGyro = new Gyro(RobotMap.verticalGyro);
    }

    public void setMotor(double speed)
    {
        
    }

    public void initDefaultCommand()
    {
        System.out.println("setting default command of Xerxes");
        super.setDefaultCommand(new ManualBridge());
    }
}