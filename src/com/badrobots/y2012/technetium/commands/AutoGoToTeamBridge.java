/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.commands.AutoOrient;
import com.badrobots.y2012.technetium.commands.AutoDriveToWallGyroCorrection;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author 1014
 */
public class AutoGoToTeamBridge extends CommandGroup {

    public AutoGoToTeamBridge() {
        addSequential(new AutoOrient(90));
        addSequential(new AutoDriveToWallGyroCorrection(1000));
        addSequential(new AutoOrient(-90));
        
    }
}