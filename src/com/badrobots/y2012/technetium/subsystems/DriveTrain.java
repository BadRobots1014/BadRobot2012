package com.badrobots.y2012.technetium.subsystems;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import com.badrobots.y2012.technetium.commands.MechanumDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.RobotMap;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Jon Buckley, Lucas Beaufore, Gautam Rangavajla (there you go)
 */
public class DriveTrain extends Subsystem
{

    private static DriveTrain instance;
    private static RobotDrive drive;
    public Joystick lJoystick, rJoystick;
    public Victor lFront, lBack, rFront, rBack;
    private boolean xbox, rightStickStrafe;
    private Gyro gyro;
    private Accelerometer accel;


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
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);        
        gyro = new Gyro(RobotMap.gyro);
        drive.setSafetyEnabled(false);
    }

    /*
     * Takes in four values from the joysticks, and converts it into tank drive
     * Status:Untested
     */
    boolean waitToChangeGyro = false;
    public void mechanumDrive(boolean NoSticky) // controls whether we want mech drive to not stick the gyro angle
    {
        if (!NoSticky)
        {
            if (OI.rightStrafe())
            drive.mecanumDrive_Cartesian(OI.getUsedRightX(), OI.getUsedRightY(), OI.getUsedLeftX(), gyro.getAngle());
            else
            drive.mecanumDrive_Cartesian(OI.getUsedLeftX(), OI.getUsedLeftY(), OI.getUsedRightX(), gyro.getAngle());
        
        }
        else 
        {
            if (OI.getUsedLeftX() != 0) waitToChangeGyro = true;
            if (OI.getUsedLeftX() < 0.03 && waitToChangeGyro)
            {
                waitToChangeGyro = false;
                gyro.reset();
            }
        }
        
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
    public void polarMechanum()
    {
        double mag, theta, rot;
        mag = OI.getUsedRightY() * OI.getUsedRightX();
        mag = Math.sqrt(mag);
        rot = OI.getUsedLeftX();
        theta = Math.wat;//fixme
        
        drive.mecanumDrive_Polar(mag, theta, rot);
    }

    /*
     * Tank drives using joystick controls, sets left side to Y value of left joystick
     * and right side as Y value of right joystick
     * Status:Xbox tank drive untested
     */
    public void tankDrive()
    {
        lFront.set(OI.getUsedLeftY()); //deadzone(OI.leftJoystick.getY()));
        lBack.set(OI.getUsedLeftY()); //-deadzone(OI.leftJoystick.getY()));

        rFront.set(-OI.getUsedRightY()); //deadzone(OI.rightJoystick.getY()));
        rBack.set(-OI.getUsedRightY()); //deadzone(OI.rightJoystick.getY()));
        
    }
    
    public double getMovement()
    {
        return accel.getAcceleration();
    }
    
    public void resetGyro()
    {
        gyro.reset();
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new MechanumDrive());
    }
}