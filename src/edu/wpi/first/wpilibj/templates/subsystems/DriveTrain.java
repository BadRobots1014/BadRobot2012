/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.templates.commands.MoveWithJoysticks;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Jon Buckley
 */
public class DriveTrain extends Subsystem
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static DriveTrain instance;
    public static Jaguar lFront, lBack, rFront, rBack;

    public static DriveTrain getInstance()
    {
        if (instance == null)
        {
            instance = new DriveTrain();
        }
        return instance;
    }
    
    private DriveTrain()
    {
        lFront = new Jaguar(RobotMap.lFront, RobotMap.cRIOsidecar);
        rFront = new Jaguar(RobotMap.rFront, RobotMap.cRIOsidecar);
        lBack = new Jaguar(RobotMap.lBack, RobotMap.cRIOsidecar);
        rBack = new Jaguar(RobotMap.rBack, RobotMap.cRIOsidecar);
    }

    /*
     *
     */
    public void tankDrive(double left, double right, double leftX, double rightX)
    {
        double frontSpeed = (leftX + rightX)/2;
        double backSpeed = (-1)*frontSpeed;

        double lFrontSpeed = frontSpeed + (left/2);
        double rFrontSpeed = frontSpeed + (left/2);
        double lBackSpeed = backSpeed + (right/2);
        double rBackSpeed = backSpeed + (right/2);

        lFront.set(lFrontSpeed);
        rFront.set(rFrontSpeed);

        lBack.set(lBackSpeed);
        rBack.set(rBackSpeed);
    }

    public void backWheelDrive(double left, double right)
    {
        lBack.set(left);
        rBack.set(right);
    }

    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new MoveWithJoysticks());
    }
}