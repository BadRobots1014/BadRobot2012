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
        addSequential(new AutoDriveToWallGyroCorrection(523));
        addSequential(new AutoShootAndGather(.16, true, 1000000000));
    }
    }