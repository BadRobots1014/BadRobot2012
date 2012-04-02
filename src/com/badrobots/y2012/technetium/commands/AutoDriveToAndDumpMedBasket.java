/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.commands.AutoShootAndGather;
import com.badrobots.y2012.technetium.commands.AutoDriveToWallGyroCorrection;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author 1014
 */
public class AutoDriveToAndDumpMedBasket extends CommandGroup {

    public AutoDriveToAndDumpMedBasket()
    {
        addSequential(new AutoDriveToWallGyroCorrection(1000));
        addSequential(new AutoShootAndGather(.3, true));//test shooting value
    }
}