/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands.autonomousCommands;

import com.badrobots.y2012.technetium.OI;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author 1014
 */
public class AutoShootHighKey extends CommandGroup {

    public AutoShootHighKey()//NEEDS TESTING
    {
        //addSequential(new AutoTarget()); TODO
        addSequential(new AutoShootAndGather(OI.getAnalogIn(2), true, 6));
        addSequential(new AutoDriveBack());
        addSequential(new AutoBridgeDown());
    }
}