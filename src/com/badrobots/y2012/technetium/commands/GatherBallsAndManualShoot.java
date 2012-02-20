package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Helios;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Relay;

/*
 * @author 1014 Programming Team
 */
public class GatherBallsAndManualShoot extends CommandBase //We need to rename this. Just maybe
{    
    private static boolean done = true;
    private static int spaceUp = 0;//If delay is needed, make this >1
    private boolean conveyorUp = false;
    private boolean conveyorDown = false;
    private boolean rollerIn = false;
    private boolean rollerOut = false;
    private boolean manualOveride = false;
    private boolean autoSpeed = true;
    private double manualShooterSpeed = 0;
    private double autoShooterSpeed = 0;
    private double shooterSpeed = 0;
    private boolean switchSpeedUp;
    private boolean switchSpeedDown;
    

    
    public GatherBallsAndManualShoot() 
    {
        requires(ballGatherer);
        requires(shooter);
    }

    protected void initialize() 
    {
        //if (Helios.getInstance().topChannelBlocked())
            //topBlocked = true;
    }

    /*
     * Gathers Balls -- if the garage door sensor is blocked, then a ball is in the
     * gatherer, and the conveyor system runs until the sensor is no longer obsscured.
     * 
     * If the user wants to shoot, he/she first hits the secondary trigger,
     * revving the shooter. Then, while the shooter is revved, if the primary trigger is
     * pressed, the conveyors roll, decreasing the tracked ball count
     *
     * LUCAS WARNING: Uncommon circumstances could throw off counts!!
     * There needs to be greater self correcting added after the collector is built.
     */

    protected void execute() 
    {

        if(OI.secondXboxSelect())
            manualOveride = true;
        if(OI.secondXboxStart())
            manualOveride = false;

        

        //Code for shooting
        if (OI.getSecondaryTrigger())   //warm up the shooter -- think gatling gun
        {
            if(autoSpeed)
            {
                if(OI.getAnalogIn(4) > 1)
                    shooterSpeed = 1;
                else
                    shooterSpeed = OI.getAnalogIn(4);
            }
            else
            {
                shooterSpeed = manualShooterSpeed;
            }

            if (OI.getPrimaryTrigger()) // push balls into shooter
                conveyorUp = true;
        }

        if(manualOveride)
            manualControl();
        else
            semiAutoControl();

        ballGatherer.runBottomRoller(rollerIn, rollerOut);
        ballGatherer.runConveyor(conveyorUp, conveyorDown);
        shooter.run(shooterSpeed);
    }


    private void manualControl()
    {
        conveyorUp = false;
        conveyorDown = false;
        rollerIn = false;
        rollerOut = false;
        spaceUp = 0;

        if(OI.secondXboxX())//conveyor up
        {
            conveyorUp = true;
            conveyorDown = false;
            spaceUp = 0;
        }

        if(OI.secondXboxB())//roller out
        {
            rollerIn = false;
            rollerOut = true;
        }

        if(OI.secondXboxY())//conveyor down
        {
            conveyorDown = true;
            conveyorUp = false;
        }

        if(OI.secondXboxA())//roller in
        {
            rollerIn = true;
            rollerOut = false;
        }
        
    }

    private void semiAutoControl()
    {
        conveyorUp = false;
        conveyorDown = false;
        rollerIn = false;
        rollerOut = false;

         //Ball Spacing
        if(OI.secondXboxA())
        {
            conveyorUp = false;
            conveyorDown = false;
            rollerIn = false;
            rollerOut = false;

            rollerIn = true;
            conveyorUp = false;

            if(sensors.bottomChannelBlocked())
            {
                spaceUp = 35;//was 20
                conveyorUp = true;
            }
        }

        if(spaceUp > 0)//space the ball
        {
            spaceUp--;
            conveyorUp = true;
        }

        if(OI.secondXboxX())
        {
            autoShooterSpeed = 1;
        }
        
        if(OI.secondXboxY())
        {
            switchSpeedUp = true;
        }
        else if(switchSpeedUp)
        {
            switchSpeedUp = false;
            if(manualShooterSpeed < .8)
                manualShooterSpeed += .2;
            else 
                manualShooterSpeed = 1;
        }
        
        if(OI.secondXboxB())
        {
            switchSpeedDown = true;
        }
        else if(switchSpeedDown)
        {
            switchSpeedDown = false;
            if(manualShooterSpeed > .2)
                manualShooterSpeed -= .2;
            else
                manualShooterSpeed = 0;
        }

        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        //ballGatherer.runBottomRoller(false, false);
        //ballGatherer.runConveyor(false, false);
        //shooter.run(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}