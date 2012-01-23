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
     * Initailizes four Victors, and makes a local instance of joysticks for
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
    }

    /*
     * Takes in four values from the joysticks, and converts it into tank drive
     * Status:Untested
     */
    public void tankDrive()
    {
        lFront.set(deadzone(lJoystick.getY()));
        lBack.set(deadzone(lJoystick.getY()));
        rFront.set(deadzone(rJoystick.getY()));
        rBack.set(deadzone(rJoystick.getY()));

    }

    /*
     * Takes in Joystick values and converts it into mechanum drive
     * Status:Untested
     */
    public void mechanumDrive()
    {
        System.out.println("Left stick: " + lJoystick.getX());
        drive.mecanumDrive_Cartesian(deadzone(lJoystick.getX()),deadzone(lJoystick.getY()),deadzone(rJoystick.getX()), 0.0);
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
     * Arcade drives using left j joystick controls
     * Status:Untested
     */
    public void arcadeDrive(Joystick j)
    {
        drive.arcadeDrive(j);//Note: This will not have deadzone
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new MoveWithJoysticks());
    }

    /*
     * Creates a deadzone for joysticks
     * Status:Tested, accurate for joysticks 1/21/12
     */
    private double deadzone(double d)
    {
        if (Math.abs(d) < 0.10)
            return 0;
        return d / Math.abs(d) * ((Math.abs(d) - .10) / .90);
    }
}