/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 * @author Jon Buckley
 * 
 * PID = Proportional Integral Derivative
 * Curve Smoothing
 */
public class SoftPID implements PIDOutput
{

    double output = 0;

    public SoftPID()
    {
    }

    public double getValue()
    {
        return this.output;
    }

    public void pidWrite(double output)
    {
        this.output = output;
    }
}