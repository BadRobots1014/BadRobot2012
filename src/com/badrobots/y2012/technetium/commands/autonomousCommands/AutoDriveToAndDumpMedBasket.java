/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands.autonomousCommands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoShootAndGather;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoDriveToWallGyroCorrection;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author 1014
 */
public class AutoDriveToAndDumpMedBasket extends CommandGroup {


    public AutoDriveToAndDumpMedBasket()
    {
        addSequential(new AutoDriveToWallGyroCorrection(535));
        addSequential(new AutoShootAndGather(OI.getAnalogIn(2), true, 1000000000));
    }
    }