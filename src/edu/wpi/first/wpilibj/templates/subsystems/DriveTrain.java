/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
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
    private static RobotDrive drive;
    public Joystick lJoystick, rJoystick;

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
        lJoystick = OI.leftJoystick;
        rJoystick = OI.rightJoystick;

        drive = new RobotDrive(RobotMap.lFront, RobotMap.lBack, RobotMap.rFront, RobotMap.rBack);
    }

    /*
     * Takes in four values from the joysticks, and converts it into tank drive (mecanum)
     * instructions.
     */
    public void tankDrive()
    {
       System.out.println("Left stick: " + lJoystick.getX());
       drive.mecanumDrive_Cartesian(lJoystick.getX(),lJoystick.getY(), rJoystick.getX(), 0.0);
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
     * Arcade drives using left j joystick controls
     */
    public void backWheelDrive(Joystick j)
    {
        drive.arcadeDrive(j);
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new MoveWithJoysticks());
    }
}