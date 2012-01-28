package com.badrobots.y2012.technetium.subsystems;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import com.badrobots.y2012.technetium.commands.MechanumDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.RobotMap;

/**
 *
 * @author Jon Buckley, Lucas Beaufore, Gautam RiCantSpellYourLastName
 */
public class DriveTrain extends Subsystem
{

    private static DriveTrain instance;
    private static RobotDrive drive;
    public Joystick lJoystick, rJoystick;
    public Victor lFront, lBack, rFront, rBack;
    private boolean xbox, rightStickStrafe;


    public static DriveTrain getInstance()
    {
        if (instance == null)
        {
            instance = new DriveTrain();
        }
        return instance;
    }

    /*
     * Initailizes four Victors, and makes a local instance of joysticks for
     * convenience of access
     */
    private DriveTrain()
    {
        super();
        xbox = false;
        rightStickStrafe = false;
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
     * Takes in four values from the joysticks, and converts it into tank drive
     * Status:Working
     */
    public void mechanumDrive()
    {
        if(OI.xboxControl() && OI.rightStrafe())
            drive.mecanumDrive_Cartesian(OI.getXboxRightX(), OI.getXboxLeftX(), OI.getXboxRightY(), 0);
        else if(OI.xboxControl())
            drive.mecanumDrive_Cartesian(OI.getXboxLeftX(), OI.getXboxRightX(), OI.getXboxLeftY(), 0);
        else if(!OI.xboxControl() && OI.rightStrafe())
            drive.mecanumDrive_Cartesian(-rJoystick.getX(), -lJoystick.getX(), rJoystick.getY(), 0);
        else
            drive.mecanumDrive_Cartesian(-lJoystick.getX(), -rJoystick.getX(), lJoystick.getY(), 0);

    }

    /*
     * @param mag the speed desired to be moved,
     *        theta the angle that the robot will move towards,
     *        rate the speed at which the robot is turning
     *
     * Moves the robot using polar coordinates - takes in three components and moves
     * the robot accordingly
     * Status: Untested
     */
    public void polarMechanum(double mag, double theta, double rate)
    {
        drive.mecanumDrive_Polar(mag, theta, rate);
    }

    /*
     * Tank drives using joystick controls, sets left side to Y value of left joystick
     * and right side as Y value of right joystick
     * Status:Working
     */
    public void tankDrive()
    {
        lFront.set(OI.getLeftY()); //deadzone(OI.leftJoystick.getY()));
        lBack.set(OI.getLeftY()); //-deadzone(OI.leftJoystick.getY()));

        rFront.set(-OI.getRightY()); //deadzone(OI.rightJoystick.getY()));
        rBack.set(-OI.getRightY()); //deadzone(OI.rightJoystick.getY()));
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new MechanumDrive());
    }

    public void changeController()
    {
        xbox = !xbox;
    }

     public void changeStrafe()
    {
        rightStickStrafe = !rightStickStrafe;
    }


}