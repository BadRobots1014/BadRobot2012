package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import com.badrobots.y2012.technetium.commands.MechanumDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.commands.TankDrive;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * @author 1014 Programming Team
 */
public class Hermes extends Subsystem
{//tinychange

    private static Hermes instance;
    private static RobotDrive drive;
    public Jaguar lFront, lBack, rFront,rBack;
    private Gyro horizontalGyro;
    private Relay LEDS;
    protected static double strafeCorrectionFactor = .165;
    protected static double oneForOneDepth = 5000; // millimeters
    protected static final double P = .01;
    protected static final double I = 0;
    protected static final double D = 0;
    private SoftPID rotationPID;
    private SendablePIDController pidController;
    private double requestedAngle = 0;
    private double orientation = 1;
    private boolean changeDirection = false;
    private boolean PIDControl = false;
    private boolean toggleButton = false;
    private boolean buttonTogglePIDOff = false;
    private double rotation;
    private double wantedAngle;

    /**
     * Singleton Design getter method -- ensures that only one instance of
     * DriveTrain is every used. If one has not been made, this method also
     * invokes the constructor
     *
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
     * Initailizes four Victors, feeds them into a RobotDrive instance, and sets
     * the motors in RobotDrive to the correct running direction.
     */
    private Hermes()
    {
        super();

        lFront = new Jaguar(RobotMap.lFront);   //initializes all victors
        lBack = new Jaguar(RobotMap.lBack);
        rFront = new Jaguar(RobotMap.rFront);
        rBack = new Jaguar(RobotMap.rBack);

        drive = new RobotDrive(lFront, lBack, rFront, rBack);   // feeds jaguars to RobotDrive
        LEDS = new Relay(RobotMap.LEDCircle);
        LEDS.setDirection(Relay.Direction.kForward);

        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        //drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true); 
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        //drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true); //
       
        if (OI.PIDOn)
        {
            horizontalGyro = Helios.getInstance().getGyro();
            
            if (horizontalGyro != null)
            {
                rotationPID = new SoftPID();
                //TODO: Set these to constants
                pidController = new SendablePIDController(P, I, D, horizontalGyro, rotationPID);
                pidController.setTolerance(10);
            }
            
            SmartDashboard.putData("HermesPIDController", pidController);
        }

        drive.setSafetyEnabled(false);
        
        //Rotation and angle requesting stuff for PID
        rotation = 0;
        
    }

    /*
     * Takes in 3 values from the joysticks, and sends voltages to
     * speedcontrollers accordingly Status:Tested
     */
    public void mechanumDrive()
    {
        double strafeX = 0;
        double strafeY = 0;
        double scaledTurn = 0;
        double leftX = OI.getUsedLeftX();
        double leftY = OI.getUsedLeftY();
        double rightX = OI.getUsedRightX();
        //TODO delete
        //System.out.println(rightX + " right X");
        double rightY = OI.getUsedRightY();
        double sensitivity = 1;//OI.getSensitivity();
        boolean useRightJoystickForStrafe = OI.rightStrafe();

        //correct for strafing code
        //if right hand stick is being used for strafing left, right, up and down
        if (useRightJoystickForStrafe)
        {
            strafeY = rightY * sensitivity;
            strafeX = rightX * 1.25 * sensitivity;
            strafeX = clampMotorValues(strafeX);
            scaledTurn = (leftX) * sensitivity; //+ (strafeCorrectionFactor * strafeX)) * sensitivity;
        }
        else
        {
            strafeY = leftY * sensitivity;
            strafeX = leftX * 1.25 * sensitivity;
            strafeX = clampMotorValues(strafeX);
            scaledTurn = (rightX) * sensitivity; //+ (strafeCorrectionFactor * strafeX)) * sensitivity;            
            //System.out.println("Scaled Turn:" + scaledTurn);
        }

        checkForOrientationChange();
//System.out.println("Turn: " + scaledTurn);
        //apply PID if it should
        scaledTurn = checkAndRunPIDOperations(scaledTurn);

        drive.mecanumDrive_Cartesian(-strafeX * orientation, strafeY * orientation, scaledTurn, 0);
    }

    private void checkForOrientationChange()
    {
        //reverse orientation of control
        if (OI.primaryXboxRBButton())
        {
            changeDirection = true;
        }
        else if (changeDirection)
        {
            orientation = orientation * -1;
            changeDirection = false;
        }
        if(orientation < 0)
            LEDS.set(Relay.Value.kOff);
        else
            LEDS.set(Relay.Value.kOn);
    }

    public boolean checkForPIDButton()
    {
        if (OI.primaryXboxYButton())
        {
            toggleButton = true;
        }
        else if (toggleButton)
        {
            toggleButton = false;
            buttonTogglePIDOff = !buttonTogglePIDOff;
            
            if(buttonTogglePIDOff)
            {
                //System.out.println("PID Reinitialized");
                //Initialize PID
                horizontalGyro.reset();
                requestedAngle = 0;
                pidController.setSetpoint(requestedAngle);
            }
        }
        return buttonTogglePIDOff;
    }

    private double clampMotorValues(double scaledStrafe)
    {
        //double scaledLeftStrafe = OI.getUsedLeftX() * 1.25 * OI.getSensitivity();

        if (scaledStrafe > 1)
        {
            scaledStrafe = 1;
        }
        if (scaledStrafe < -1)
        {
            scaledStrafe = -1;
        }
        return scaledStrafe;
    }

    /*
     * Takes care of all PID code and if the PID operations cshould be used, it
     * applies the operations to the values given in the parameter.
     */
    public double checkAndRunPIDOperations(double rotation)
    {
        double finalRotation = rotation;
        //System.out.println("Gyro:" + horizontalGyro.getAngle());
        if (pidController == null)
            return rotation;

        if (!pidController.isEnable())//Enables PID
            pidController.enable();

        //When the robot is being told to turn, tell the pidController to focus on
        //the current angle the robot is facing
        if (rotation != 0)
        {
            pidController.setSetpoint(horizontalGyro.getAngle());
        }
        //Checks if the toggle button is pressed, and if it is, it toggles PID enabled
        //disables PID if the toggle is on
        if (!checkForPIDButton())   
            PIDControl = false;
        else
            PIDControl = true;

        //if pid is enabled and the robot is not being told to turn, activate the 
        //PID control
        if (PIDControl && rotation == 0)
        {
            finalRotation = rotationPID.getValue();
        }
        return finalRotation;
    }

    /*
     * Used for cartesian control of a mechanum drive Status: Untested
     */
    public void autoMechanumDrive(double x, double y, double rotation)
    {
        //System.out.println("I'm trying to drive: " + x + " " + y);
        drive.mecanumDrive_Cartesian(x, y, rotation, 0);
        /*
         * if (rotation > 0) { horizontalGyro.reset(); }
         */
    }

    /*
     * @param mag the speed desired to be moved, theta the angle that the robot
     * will move towards, rotation the speed at which the robot is turning
     *
     * Moves the robot using polar coordinates - takes in three components and
     * moves the robot accordingly Status: Untested
     */
    public void polarMechanum(double mag, double theta, double rotation)
    {
        drive.mecanumDrive_Polar(mag, theta, rotation);
    }

    /*
     * Tank drives using joystick controls, sets left side to Y value of left
     * joystick and right side as Y value of right joystick Status:Tested for
     * both xbox + joysticks
     */
    public void tankDrive()
    {
        lFront.set(-OI.getUsedLeftY()); //deadzone(OI.leftJoystick.getY()));
        lBack.set(-OI.getUsedLeftY()); //-deadzone(OI.leftJoystick.getY()));

        rFront.set(OI.getUsedRightY()); //deadzone(OI.rightJoystick.getY()));
        rBack.set(OI.getUsedRightY()); //deadzone(OI.rightJoystick.getY()));
        System.out.println("Tank: " + OI.getUsedLeftY());

    }

    /*
     * Tank drives using two doubles, left side speed and right speed Status:
     * untested
     */
    public void tankDrive(double left, double right)
    {
        lFront.set(left); //deadzone(OI.leftJoystick.getY()));
        lBack.set(left); //-deadzone(OI.leftJoystick.getY()));

        rFront.set(right); //deadzone(OI.rightJoystick.getY()));
        rBack.set(right);
    }

    public void mechanumWithGyro(double x, double y, double angle)
    {
        drive.mecanumDrive_Cartesian(x, y, 0, angle);
    }

    /*
     * Right now, this will not be called because we don't have a gyro hooked
     * up. However, it will be used in autonomous, so we might as well keep it
     * 2/6/12
     */
    public void resetGyro()
    {
        horizontalGyro.reset();
    }

    public Gyro getGyro()
    {
        return horizontalGyro;
    }

    public void resetRequestedAngle()
    {
        pidController.reset();
        requestedAngle = 0;
        rotationPID.output = 0;
        pidController.enable();
    }

    public void initDefaultCommand()
    {
        System.out.println("DefaultCommandHermes");
        setDefaultCommand(new MechanumDrive());
    }
}
