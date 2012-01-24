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
<<<<<<< HEAD
  
=======
>>>>>>> f242af576b25aa402d429afdbfac2cd6fc371a8c
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
        drive.setSafetyEnabled(false);
    }

    /*
     * Takes in four values from the joysticks, and converts it into tank drive
     * Status:Untested
     */
    public void mecanumDrive()
    {
<<<<<<< HEAD
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
=======
       System.out.println("Left stick: " + OI.leftJoystick.getX());
       drive.mecanumDrive_Cartesian(deadzone(-rJoystick.getX()), deadzone(-lJoystick.getX()), deadzone(rJoystick.getY()), 0);
>>>>>>> f242af576b25aa402d429afdbfac2cd6fc371a8c
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
<<<<<<< HEAD
     * Arcade drives using left j joystick controls
     * Status:Untested
     */
    public void arcadeDrive(Joystick j)
    {
        drive.arcadeDrive(j);//Note: This will not have deadzone
=======
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
>>>>>>> f242af576b25aa402d429afdbfac2cd6fc371a8c
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new MecanumDrive());
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