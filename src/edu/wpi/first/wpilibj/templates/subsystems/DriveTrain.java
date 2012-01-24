/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.templates.commands.MecanumDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.TankDrive;

/**
 *
 * @author Jon Buckley
 */
public class DriveTrain extends Subsystem
{
    private static DriveTrain instance;
    private static RobotDrive drive;
    public Joystick lJoystick, rJoystick;
    public Victor lFront, lBack, rFront, rBack;

    public static DriveTrain getInstance()
    {
        if (instance == null)
        {
            instance = new DriveTrain();
        }
        return instance;
    }

    /*
     * initailizes four Victors, and makes a local instance of joysticks for
     * convenience of access
     */
    private DriveTrain()
    {
        super();
        lJoystick = OI.leftJoystick;
        rJoystick = OI.rightJoystick;

        lFront = new Victor(RobotMap.lFront);
        lBack = new Victor(RobotMap.lBack);
        rFront = new Victor(RobotMap.rFront);
        rBack = new Victor(RobotMap.rBack);

        drive = new RobotDrive(lFront, lBack, rFront, rBack);
        drive.setSafetyEnabled(false);
    }

    /*
     * Takes in four values from the joysticks, and converts it into tank drive (mecanum)
     * instructions.
     */
    public void mecanumDrive()
    {
       System.out.println("Left stick: " + OI.leftJoystick.getX());
       drive.mecanumDrive_Cartesian(deadzone(-rJoystick.getX()), deadzone(-lJoystick.getX()), deadzone(rJoystick.getY()), 0);
    }

    /*
     * @param mag the speed desired to be moved,
     *        theta the angle that the robot will move towards,
     *        rate the speed at which the robot is turning
     *
     * Moves the robot using polar coordinates - takes in three components and moves
     * the robot accordingly
     */
    public void tankDrive(double mag, double theta, double rate)
    {
        drive.mecanumDrive_Polar(mag, theta, rate);
    }

    /*
     * Tank drives using joystick controls
     */
    public void tankDrive()
    {
        rFront.set(-deadzone(OI.leftJoystick.getY()));
        rBack.set(-deadzone(OI.leftJoystick.getY()));

        lFront.set(deadzone(OI.rightJoystick.getY()));
        lBack.set(deadzone(OI.rightJoystick.getY()));
    }

    /*
     * @param d the number to be converted into a more accurate joystick value
     */
    public double deadzone(double d)
    {
        if (Math.abs(d) < 0.10)
            return 0;
        
        return d / Math.abs(d) * ((Math.abs(d) - .10) / .90);
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new MecanumDrive());
    }
}