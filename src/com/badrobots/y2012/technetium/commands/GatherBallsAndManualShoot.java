package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Helios;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;

/*
 * @author 1014 Programming Team
 */
public class GatherBallsAndManualShoot extends CommandBase //We need to rename this. Just maybe
{    
    protected static boolean done = true;
    protected static int spaceUp = 0;//If delay is needed, make this >1
    protected boolean conveyorUp = false;
    protected boolean conveyorDown = false;
    protected boolean rollerIn = false;
    protected boolean rollerOut = false;
    protected boolean manualOveride = false;
    protected boolean autoSpeed = true;
    protected double manualShooterSpeed = 0;
    protected double autoShooterSpeed = 0;
    protected double shooterSpeed = 0;
    protected boolean switchSpeedUp;
    protected boolean switchSpeedDown;
    protected double turretTurn = 0;
    

    
    public GatherBallsAndManualShoot() 
    {
        requires(ballGatherer);
        //requires(shooter);
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
        //sets booleans for running Gatherer
        runBallGathererOperations();

        //Code for shooting
        runShootingOperations();
        
        //turrets
        runTurretingOperations();

        ballGatherer.runBottomRoller(rollerIn, rollerOut);
        ballGatherer.runConveyor(conveyorUp, conveyorDown);
        System.out.println("Shooting at: " + shooterSpeed);
        shooter.run(shooterSpeed);
        shooter.turn(-turretTurn);
    }

    public void runBallGathererOperations()
    {
        //change between auto and manual
        checkControlSetup();
        
        if(manualOveride)
        {
            manualControl();
            DriverStation.getInstance().setDigitalOut(8, true);
        }
        
        else
        {
            semiAutoControl();
            DriverStation.getInstance().setDigitalOut(8, false);
        }
    }
    
    private void manualControl()
    {
        conveyorUp = false;
        conveyorDown = false;
        rollerIn = false;
        rollerOut = false;
        spaceUp = 0;

        if(OI.secondXboxXButton())//conveyor up
        {
            conveyorUp = true;
            conveyorDown = false;
            spaceUp = 0;
        }

        if(OI.secondXboxBButton())//roller out
        {
            rollerIn = false;
            rollerOut = true;
        }

        if(OI.secondXboxYButton())//conveyor down
        {
            conveyorDown = true;
            conveyorUp = false;
        }

        if(OI.secondXboxAButton())//roller in
        {
            rollerIn = true;
            rollerOut = false;
        }

        autoSpeed = true;//for testing
        
    }
    
    private void semiAutoControl()
    {
        conveyorUp = false;
        conveyorDown = false;
        rollerIn = false;
        rollerOut = false;

         //Ball Spacing
        if(OI.secondXboxAButton())
        {
            System.out.println("Spacing");
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

        if(OI.secondXboxXButton())
        {
            autoShooterSpeed = 1;
            autoSpeed = true;
        }
        
        if(OI.secondXboxYButton())
        {
            switchSpeedUp = true;
        }
        else if(switchSpeedUp)
        {
            switchSpeedUp = false;
            autoSpeed = false;
            if(manualShooterSpeed < .8)
                manualShooterSpeed += .2;
            else 
                manualShooterSpeed = 1;
        }
        
        if(OI.secondXboxBButton())
        {
            switchSpeedDown = true;
        }
        else if(switchSpeedDown)
        {
            switchSpeedDown = false;
            autoSpeed = false;
            if(manualShooterSpeed > .2)
                manualShooterSpeed -= .2;
            else
                manualShooterSpeed = 0;
        }

        if(OI.secondXboxLeftTrigger())//run with the manual control
        {
            autoSpeed = false;
        }
    }
    
    public void checkControlSetup()
    {
        if(OI.secondXboxSelectButton())
            manualOveride = true;
        else if(OI.secondXboxStartButton())
            manualOveride = false;
    }

    public void runShootingOperations()
    {
        if (OI.getSecondaryTrigger())   //warm up the shooter -- think gatling gun
        {
            if(autoSpeed)
            {
                if(OI.getAnalogIn(4) > 1) // Lucas, is this old code? Are we still using an analog input
                    shooterSpeed = 1;     //Jon, yeah, it's older, but we still will need it for testing soon
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
        else
            shooterSpeed = 0;
    }
    
    public void runTurretingOperations()
    {
        turretTurn = OI.secondXboxLeftX();
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