/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import com.badrobots.y2012.technetium.OI;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.badrobots.y2012.technetium.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*
 * @author 1014 Programming Team
 */
public class Shooter extends Subsystem
{
    private static Shooter instance;
    private static Victor victorRightShooterMotor, victorLeftShooterMotoer; //MIKE RIGHT MOTOR IS NEGATIZED, LEFT IS POSITIVE
    private static Servo servoTurnTable; 
    private static Ultrasonic ranger;
    
    private static OpticalSensorPID opticalSensorShooter;
    private static Encoder turnTableEncoder;
    
    protected SoftPID shooterPIDOutput;
    protected SoftPID turnTablePIDOutput;
    
    protected double speedOfShooter = 0; 
    
    protected PIDController shooterPIDController;
    protected PIDController turnTableController; 
   
    //public static final double MAX_SPEED = 3600;
    //public static final double TOP_SPEED_PERIOD = .018;

    public static final double SHOOTER_P = .1;
    public static final double SHOOTER_I = 0;
    public static final double SHOOTER_D = 0;
    
    public static final double SHOOTER_HIGH_PERIOD = .016;
    public static final double SHOOTER_LOW_PERIOD = .024;
    public static final double SHOOTER_DIFFERENCE_PERIOD = SHOOTER_LOW_PERIOD - SHOOTER_HIGH_PERIOD;
    
    public static final double TURNTABLE_P = .01;
    public static final double TURNTABLE_I = 0;
    public static final double TURNTABLE_D = 0;
    
    public static Shooter getInstance()
    {
        if (instance == null)
        {
            instance = new Shooter();
        }

        return instance;
    }

    private Shooter()
    {
        super();
        victorRightShooterMotor = new Victor (RobotMap.Sidecar.PWMOut6); // initialize the motor
        victorLeftShooterMotoer = new Victor (RobotMap.Sidecar.PWMOut5);

        opticalSensorShooter = new OpticalSensorPID();//GearToothPID(RobotMap.shooterGearTooth);        
        opticalSensorShooter.start();
        
        if (OI.shooterPIDOn)
        {
            //we will need to change this variable name, but it's fine
            
            shooterPIDOutput = new SoftPID();

            shooterPIDController = new PIDController(SHOOTER_P, SHOOTER_I, SHOOTER_D, opticalSensorShooter, shooterPIDOutput);  
            //shooterController.setInputRange(0, 1.1);
            shooterPIDController.setOutputRange(0, 1);
            
            shooterPIDController.enable();
            
            SmartDashboard.putData("ShooterPID", shooterPIDController);
        }

        
        turnTableEncoder = new Encoder(RobotMap.Sidecar.DIO1, RobotMap.Sidecar.DIO2);
        turnTableEncoder.start();
        turnTableEncoder.reset();  
        
        if (OI.turnTablePIDOn)
        {
            turnTablePIDOutput = new SoftPID();
            turnTableController = new PIDController(TURNTABLE_P, TURNTABLE_I, TURNTABLE_D, turnTableEncoder, turnTablePIDOutput);
            turnTableController.setPercentTolerance(5);
            
            SmartDashboard.putData("TurnTablePID", turnTableController);
        }
        
        servoTurnTable = new Servo(RobotMap.Sidecar.PWMOut9);
    }

    
    /*
     * Turns the turnTable/turreter
     * 
     * @param speed from -1 to 1, where -1 is full speed counterclockwise and 
     * 1 is full speed clockwise
     */
    public void turn(double speed)
    {    
        System.out.println(speed);
        speed = clampMotorValues(speed);
        servoTurnTable.set(-speed);
    }

    public void setShooterPIDRunning(boolean b)
    {
        if(opticalSensorShooter != null)
            opticalSensorShooter.setRunning(b);
    }

    public void shootMiddle() // read distance from kinect/ultrasonic
    {
        //determine shooter speed through regression equation determined through testing
        int speed = 0;
        run(speed, false);
    }

    public void shootHigh() // read distance from kinect/ultrasonic
    {
        //determine shooter speed through regression equation determined through testing
        double speed = .45;
        run(speed, false);
    }

    public void shootLow() // read distance from kinect/ultrasonic
    {
        //determine shooter speed through regression equation determined through testing
        int speed = 0;
        run(speed, false);
    }
    
    /*
     * Uses the PIDGearTooth to run the shooter more efficiently
     */
    public void PIDRun(double speed)
    { 
        //the setpoint is set to the max RPMs (which is 3600 RPM) times 6 (number of bolts in gearbox)
        if(speed == 0)
        {
            victorRightShooterMotor.set(0);
            victorLeftShooterMotoer.set(0);
            return;
        }
        double toSet = (1/SHOOTER_HIGH_PERIOD - 1/SHOOTER_LOW_PERIOD);
        toSet = toSet * speed;
        toSet = toSet + 1/SHOOTER_LOW_PERIOD;
        shooterPIDController.setSetpoint(toSet);
       // System.out.println("TestMath" + toSet);
    
        double speedToSet = shooterPIDController.get();
        System.out.println("Motor: " + speedToSet + "speed: " + speed);
        //speedToSet = Math.abs(speedToSet);
        speedToSet = clampMotorValues(speedToSet);

        
        victorRightShooterMotor.set(-speedToSet);
        victorLeftShooterMotoer.set(speedToSet);
    }

    public void run(double speed)
    {
        run(speed, false);
    }
    
    /*
     * Runs both motors at 'speed' speed
     */
    public void run(double speed, boolean maxPower)
    {
        clampMotorValues(speed);

        //System.out.println("Rate2: " + shooterGearTooth.pidGet());

        if(maxPower)
        {
            victorRightShooterMotor.set(-.8);
            victorLeftShooterMotoer.set(.8);
        }
        
        else if(OI.bangBangOn)
        {
            bangBangRun(speed);
            
        }
        
        else if (OI.shooterPIDOn)
        {
            PIDRun(speed);
        }
        
        else
        {
            victorRightShooterMotor.set(-speed);
            victorLeftShooterMotoer.set(speed);
            System.out.println("Period: " + opticalSensorShooter.pidGet());
        }

        SmartDashboard.putDouble("ShooterSpeed", speed);
        
    }
    
    public void bangBangRun(double speed)
    {
        if(speed < .1)
        {
            victorRightShooterMotor.set(0);
            victorLeftShooterMotoer.set(0);
            return;
        }
        
        double scale = (1/SHOOTER_HIGH_PERIOD - 1/SHOOTER_LOW_PERIOD) * speed;
        double wanted = scale + 1/SHOOTER_LOW_PERIOD;
        wanted = 1/wanted;
        opticalSensorShooter.pidGet();
        
        //System.out.println("wanted: " + wanted);
        
        if(1/opticalSensorShooter.pidGet() > wanted)
        {
            victorRightShooterMotor.set(-1);
            victorLeftShooterMotoer.set(1);
            //System.out.println("at: " + shooterSensor.pidGet());

        }
        else
        {
            victorRightShooterMotor.set(0);
            victorLeftShooterMotoer.set(0);
            //System.out.println("at: " + shooterSensor.pidGet());

        }
        
        
    }

    private double clampMotorValues(double scaledStrafe)
    {
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

    public void resetEncoder()
    {
        turnTableEncoder.reset();
    }

    public int encoderValue()
    {
        return turnTableEncoder.get();
    }

    /*
     * Turns the shooter to a specific encoder value
     */
    public boolean turnByEncoderTo(int value)
    {
        if (OI.turnTablePIDOn)
        {
            return turnWithPID(value);
        }
        
        int currentAngle = turnTableEncoder.get();

        if(Math.abs(currentAngle - value) <= 8)
        {
            turn(0);
            return true;
        }
        else if(currentAngle - value > 0)
            turn(.50);
        else
            turn(-.50);
        return false;
    }
    
    /*
     * Uses PID to turn the turntable to a setpoint specified in the @parameter value
     */
    public boolean turnWithPID(int value)
    {
        turnTableController.setSetpoint(value);
        turn(turnTableController.get());
        
        //if the turnTable is within the point you tell it to go to, return true
        if (turnTableController.get() < .05)
            return true;
        
        return false;
    }
    
    public double distanceToWall()
    {
        if (ranger != null)
            return ranger.getRangeMM();
        
        return -1;
    }
    
    public double getShooterSpeed()
    {
        return victorLeftShooterMotoer.get();
    }
    

    protected void initDefaultCommand()
    {
        System.out.println("DefaultCommandArtemis");
        //super.setDefaultCommand(new GatherBalls);
    }
}