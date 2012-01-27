/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.AutoAim;

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
     * adjusts the angle at which the shooter is aiming up and down
     */
    public void rotateAngle(double ang)
    {
        
    }

    /*
     * rotates the shooter left and right
     */
    public void rotateBase(double rate)
    {

    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new AutoAim());
    }
}