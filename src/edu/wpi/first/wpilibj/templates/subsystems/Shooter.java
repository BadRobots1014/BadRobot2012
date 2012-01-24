/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Jon Buckley
 */
public class Shooter extends Subsystem
{
    private static Shooter instance;
    public static Jaguar shooterMotor;

    public static Shooter getInstance()
    {
        if (instance == null)
        {
            instance = new Shooter();
        }

        return instance;
    }

    /*
     * initializes shooterMotor Jaguar
     */
    private Shooter()
    {
        //shooterMotor = new Jaguar(RobotMap.shooterMotor, RobotMap.cRIOsidecar);
    }

    /*
     * blank code for shooting
     */
    public void shoot(double x)
    {

    }

    /*
     * blank code for setting angle
     */
    public void setAngle(double ang)
    {
        
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}