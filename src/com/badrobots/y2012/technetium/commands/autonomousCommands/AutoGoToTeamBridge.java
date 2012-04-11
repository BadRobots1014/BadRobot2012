/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands.autonomousCommands;

import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoOrient;
import com.badrobots.y2012.technetium.commands.autonomousCommands.AutoDriveToWallGyroCorrection;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author 1014
 */
public class AutoGoToTeamBridge extends CommandGroup {

    public AutoGoToTeamBridge() {
        System.out.println("Step 1");
        addSequential(new AutoOrient(90));
        System.out.println("Step 2");
        addSequential(new AutoDriveToWallGyroCorrection(1000));
        System.out.println("Step 3");
        addSequential(new AutoOrient(90));
        System.out.println("Step 4");
        
    }
}