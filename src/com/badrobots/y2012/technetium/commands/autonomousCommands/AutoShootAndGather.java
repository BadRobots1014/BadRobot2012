/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands.autonomousCommands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;


/**
 *
 * @author 1014
 */
public class AutoShootAndGather extends CommandBase {

    double speed;
    boolean gather;
    Timer timer = new Timer();//Timer
    double timeLength;
    double start;
    public AutoShootAndGather(double s, boolean gathering, double time)
    {
        requires(shooter);
        requires(driveTrain);
        requires(ballGatherer);
        requires(sensors);
        speed = s;
        gather = gathering;
        timeLength = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        shooter.run(speed);
        timer.delay(OI.getAnalogIn(1));
        start = timer.getFPGATimestamp();

        ballGatherer.runConveyor(true, false);//run conveyor up
        if(gather)
            ballGatherer.runBottomRoller(true, false);//run roller in
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        //This is technically redundant as it should keep this state from initialize()
        shooter.run(speed);
        ballGatherer.runConveyor(true, false);//run conveyor up
        ballGatherer.runBottomRoller(true, false);//run roller in
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //System.out.println(start - timer.getFPGATimestamp());
        return timer.getFPGATimestamp() - start > timeLength;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        shooter.run(0);
        ballGatherer.runBottomRoller(false, false);
        ballGatherer.runConveyor(false, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        //System.out.println("!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!");
        shooter.run(0);
        ballGatherer.runBottomRoller(false, false);
        ballGatherer.runConveyor(false, false);
    }
}