package com.badrobots.y2012.technetium.subsystems;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import com.badrobots.y2012.technetium.commands.MechanumDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.PacketListener;
import com.badrobots.y2012.technetium.RobotMap;
import edu.wpi.first.wpilibj.*;

/*
 * @author 1014 Programming Team
 */
public class Hermes extends Subsystem
{
    private static Hermes instance;
    private static RobotDrive drive;
    public Victor lFront, lBack, rFront, rBack;
    private Gyro horizontalGyro;
    private Accelerometer accel;

    /**
     * Singleton Design getter method -- ensures that only one instance of DriveTrain
     * is every used. If one has not been made, this method also invokes the constructor
     * @return the single instance of DriveTrain per program
     */
    public static Hermes getInstance()
    {
        if (instance == null)
        {
            instance = new Hermes();
        }
        return instance;
    }

    /*
     * Initailizes four Victors, feeds them into a RobotDrive instance,
     * and sets the motors in RobotDrive to the correct running direction.
     */
    private Hermes()
    {
        super();

        lFront = new Victor(RobotMap.lFront);   //initializes all victors
        lBack = new Victor(RobotMap.lBack);
        rFront = new Victor(RobotMap.rFront);
        rBack = new Victor(RobotMap.rBack);

        drive = new RobotDrive(lFront, lBack, rFront, rBack);   // feeds victors to RobotDrive
       // drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true); //
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
       // drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true); //
        //horizontalGyro = new Gyro(RobotMap.horizontalGyro); //gyro
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
        double scaledRightStrafe = OI.getUsedRightX() * 1.25 * OI.getSensitivity();
        double scaledLeftStrafe = OI.getUsedLeftX() * 1.25 * OI.getSensitivity();
        
        if (scaledRightStrafe > 1)
            scaledRightStrafe = 1;
        
        if (scaledLeftStrafe > 1)
            scaledLeftStrafe = 1;

        
        //correct for strafing code
        double scaledLeftTurn = (OI.getUsedLeftX() + (.19 * -scaledRightStrafe)) * OI.getSensitivity();  // forces slight turn
        double scaledRightTurn = (OI.getUsedRightX() + (.19 * -scaledLeftStrafe)) * OI.getSensitivity();
        
         if (OI.rightStrafe())          
             drive.mecanumDrive_Cartesian(-scaledRightStrafe, (OI.getUsedRightY()*OI.getSensitivity()), scaledLeftTurn, 0); //if right hand stick is being used for strafing left, right, up and down
         else                       // if left hand stick is being used for strafing
            drive.mecanumDrive_Cartesian(-scaledLeftStrafe, (OI.getUsedLeftY()*OI.getSensitivity()), scaledRightTurn, 0);
    }

    public void autoAimMechanum(PacketListener kinecter)
    {
        double scaledRightStrafe = OI.getUsedRightX() * 1.25 * OI.getSensitivity();
        double scaledLeftStrafe = OI.getUsedLeftX() * 1.25 * OI.getSensitivity();

        if (scaledRightStrafe > 1)
            scaledRightStrafe = 1;

        if (scaledLeftStrafe > 1)
            scaledLeftStrafe = 1;


        double scaledLeftTurn = 0;
        double scaledRightTurn = 0;
        //correct for strafing code
        if(kinecter.getOffAxis() > .1)
        {
            scaledLeftTurn = (kinecter.getOffAxis() * (.19 * -scaledRightStrafe)) * OI.getSensitivity();  // forces slight turn
            scaledRightTurn = (kinecter.getOffAxis() * (.19 * -scaledLeftStrafe)) * OI.getSensitivity();
        }
         if (OI.rightStrafe())
             drive.mecanumDrive_Cartesian(-scaledRightStrafe, (OI.getUsedRightY()*OI.getSensitivity()), scaledLeftTurn, 0); //if right hand stick is being used for strafing left, right, up and down
         else                       // if left hand stick is being used for strafing
            drive.mecanumDrive_Cartesian(-scaledLeftStrafe, (OI.getUsedLeftY()*OI.getSensitivity()), scaledRightTurn, 0);
    }

    /*
     * Used for cartesian control of a mechanum drive
     * Status: Untested
     */
    public void autoMechanumDrive(double x, double y, double rotation)
    {
        drive.mecanumDrive_Cartesian(x, y, rotation, 0);
        if(rotation > 0)
            horizontalGyro.reset();
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
        lFront.set(OI.getUsedLeftY()); //deadzone(OI.leftJoystick.getY()));
        lBack.set(-OI.getUsedLeftY()); //-deadzone(OI.leftJoystick.getY()));

        rFront.set(-OI.getUsedRightY()); //deadzone(OI.rightJoystick.getY()));
        rBack.set(OI.getUsedRightY()); //deadzone(OI.rightJoystick.getY()));
        
    }
    
    /*
     * Tank drives using two doubles, left side speed and right speed
     * Status: untested
     */
    public void tankDrive(double left, double right)
    {
        lFront.set(left); //deadzone(OI.leftJoystick.getY()));
        lBack.set(left); //-deadzone(OI.leftJoystick.getY()));

        rFront.set(right); //deadzone(OI.rightJoystick.getY()));
        rBack.set(right);   
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
        horizontalGyro.reset();
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new MechanumDrive());
    }
}