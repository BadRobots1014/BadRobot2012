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
    public Victor lFront, lBack, rFront, rBack;
    private Gyro gyro;
    private Accelerometer accel;

    /**
     * Singleton Design getter method -- ensures that only one instance of DriveTrain
     * is every used. If one has not been made, this method also invokes the constructor
     * @return the single instance of DriveTrain per program
     */
    public static DriveTrain getInstance()
    {
        if (instance == null)
        {
            instance = new DriveTrain();
        }
        return instance;
    }

    /*
     * Initailizes four Victors, feeds them into a RobotDrive instance,
     * and sets the motors in RobotDrive to the correct running direction.
     */
    private DriveTrain()
    {
        super();

        lFront = new Victor(RobotMap.lFront);   //initializes all victors
        lBack = new Victor(RobotMap.lBack);
        rFront = new Victor(RobotMap.rFront);
        rBack = new Victor(RobotMap.rBack);

        drive = new RobotDrive(lFront, lBack, rFront, rBack);   // feeds victors to RobotDrive
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);        
        gyro = new Gyro(RobotMap.gyro); //gyro
        drive.setSafetyEnabled(false);  //because why not. Jon: because it will kill us all. 
                                        // Haven't you seen iRobot? They left their robots on
                                        // safety enable = false
    }

    /*
     * Takes in 3 values from the joysticks, and sends voltages to speedcontrollers accordingly
     * Status:Tested
     */
    public void mechanumDrive() 
    {
         if (OI.rightStrafe())      //if right hand stick is being used for strafing left, right, up and down
            drive.mecanumDrive_Cartesian(OI.getUsedRightX(), OI.getUsedRightY(), OI.getUsedLeftX(), 0);
         else                       // if left hand stick is being used for strafing
            drive.mecanumDrive_Cartesian(OI.getUsedLeftX(), OI.getUsedLeftY(), OI.getUsedRightX(), 0);
    }

    /*
     * Used for cartesian control of a mechanum drive
     * Status: Untested
     */
    public void autoMechanumDrive(double x, double y, double rotation)
    {
        drive.mecanumDrive_Cartesian(x, y, rotation, 0);
        if(rotation > 0)
            gyro.reset();
    }
    
    /*
     * @param mag the speed desired to be moved,
     *        theta the angle that the robot will move towards,
     *        rotation the speed at which the robot is turning
     *
     * Moves the robot using polar coordinates - takes in three components and moves
     * the robot accordingly
     * Status: Untested
     */
    public void polarMechanum(double mag, double theta, double rotation)
    {
        drive.mecanumDrive_Polar(mag, theta, rotation);
    }

    /*
     * Tank drives using joystick controls, sets left side to Y value of left joystick
     * and right side as Y value of right joystick
     * Status:Tested for both xbox + joysticks
     */
    public void tankDrive()
    {
        lFront.set(-OI.getUsedLeftY()); //deadzone(OI.leftJoystick.getY()));
        lBack.set(-OI.getUsedLeftY()); //-deadzone(OI.leftJoystick.getY()));

        rFront.set(OI.getUsedRightY()); //deadzone(OI.rightJoystick.getY()));
        rBack.set(OI.getUsedRightY()); //deadzone(OI.rightJoystick.getY()));
        
    }
    
    /*
     * This method may or may not be used depending on whether we use an accelerometer
     * @return the accelerometer's value
     */
    public double getMovement()
    {
        return accel.getAcceleration();
    }
    
    /*
     * Right now, this will not be called because we don't have a gyro hooked up. However,
     * it will be used in autonomous, so we might as well keep it
     * 2/6/12
     */
    public void resetGyro()
    {
        gyro.reset();
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new MechanumDrive());
    }
}