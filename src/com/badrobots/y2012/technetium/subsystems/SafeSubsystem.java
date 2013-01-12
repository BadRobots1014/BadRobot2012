/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Jon Buckley
 */
public abstract class SafeSubsystem extends Subsystem
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    protected static SafeSubsystem instance;
    
    
    abstract SafeSubsystem getInstance();
    
    
}
