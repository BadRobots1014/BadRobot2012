/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.buttons.ShootBallTrigger;
import com.badrobots.y2012.technetium.commands.AutoAim;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/*
 * @author 1014 Programming Team
 */
public class Artemis extends Subsystem
{
    private static Artemis instance;
    private static Victor right, left;
    private static Jaguar turnTable; //TODO Change to correct speed controller
    private static Ultrasonic ranger;
    private static AxisCamera camera;

    public static Artemis getInstance()
    {
        if (instance == null)
        {
            instance = new Artemis();
        }

        return instance;
    }

    private Artemis()
    {
        super();
        right = new Victor (RobotMap.rightShooter); // initialize the motor
        left = new Victor (RobotMap.leftShooter);
        
        //turnTable = new Jaguar(RobotMap.turnTable);
                
       // ranger = new Ultrasonic (RobotMap.ultrasonicOut, RobotMap.ultrasonicIn); //init
        //ranger.setEnabled(true);
       // ranger.setAutomaticMode(true);

       // camera.getInstance(); //init

    }

    
    /*
     * Turns the turnTable/turreter
     * 
     * @param speed from -1 to 1, where -1 is full speed counterclockwise and 
     * 1 is full speed clockwise
     */
    public void turn(double speed)
    {
        clampMotorValues(speed);
        turnTable.set(speed);
    }

    public void shootMiddle() // read distance from kinect/ultrasonic
    {
        //determine shooter speed through regression equation determined through testing
        int speed = 0;
        right.set(speed);
        left.set(-speed);
    }

    public void shootHigh() // read distance from kinect/ultrasonic
    {
        //determine shooter speed through regression equation determined through testing
        int speed = 0;
        right.set(speed);
        left.set(-speed);
    }

    public void shootLow() // read distance from kinect/ultrasonic
    {
        //determine shooter speed through regression equation determined through testing
        int speed = 0;
        right.set(speed);
        left.set(-speed);
    }
    
    /*
     * Runs both motors at 'speed' speed
     */
    public void run(double speed)
    {
        clampMotorValues(speed);
        System.out.println("Shooting:"  + speed);
        right.set(speed);
        left.set(-speed);
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
    

    protected void initDefaultCommand()
    {
        System.out.println("DefaultCommandArtemis");
        //super.setDefaultCommand(new GatherBalls);
    }
}