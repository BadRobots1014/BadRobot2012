package com.badrobots.y2012.technetium.commands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.subsystems.Helios;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This command is used to control the ball collection system while under manual control
 * @author 1014 Programming Team
 */
public class GatherBallsAndManualShoot extends CommandBase
{
    /**
     * Counts down iteration of the automatic ball spacing to provide a delay
     */
    protected int spaceUp = 0;
    /**
     * How many iterations of spacing are used
     */
    protected int startingSpaceUp = 35;
    /**
     * Set to true to send the conveyor up
     */
    protected boolean conveyorUp = false;
    /**
     * Set to true to send the conveyor down
     */
    protected boolean conveyorDown = false;
    /**
     * Set to true to roll the roller in
     */
    protected boolean rollerIn = false;
    /**
     * Set to true to roll the roller out
     */
    protected boolean rollerOut = false;
    /**
     * Set to true when the robot is in manual control, false for semi-auto control
     */
    protected boolean manualOveride = false;
    /**
     * The speed at which the shooter moves when under manual operation
     */
    protected double manualShooterSpeed = .35 ;
    /**
     * The speed the shooter is set to
     */
    protected double shooterSpeed = 0;

    /**
     * The value the turntable motor is set to
     */
    protected double turretTurn = 0;
    /**
     * If the shooter is currently running
     */
    public static boolean shootingNow = false;
    protected boolean shooterTriggerDown = false;
    private static boolean maxPower;
    protected boolean switchSpeedUp;
    protected boolean switchSpeedDown;
    


    public GatherBallsAndManualShoot() 
    {
        requires(ballGatherer);
        requires(shooter);
    }

    /**
     * Initialize required variables
     */
    protected void initialize() 
    {
        shooterSpeed = .45;
        maxPower = false;
    }

    /**
     * If the garage door sensor is blocked, then a ball is in the
     * gatherer, and the conveyor system runs until the sensor is no longer obsscured.
     * 
     * If the user wants to shoot, he/she first hits the secondary trigger,
     * revving the shooter. Then, while the shooter is revved, if the primary trigger is
     * pressed, the conveyors roll, decreasing the tracked ball count
     */
    protected void execute() 
    {
        
        //sets booleans for running Gatherer
        runBallGathererOperations();

        //Code for shooting
        runShootingOperations();
        
        //turrets
        runTurretingOperations();

         if(sensors.bottomChannelBlocked())
            OI.setDigitalOutput(7, true);
         else
            OI.setDigitalOutput(7, false);

        ballGatherer.runBottomRoller(rollerIn, rollerOut);
        ballGatherer.runConveyor(conveyorUp, conveyorDown);

        shooter.run(shooterSpeed, maxPower);
    }

    /**
     * Runs the ball gatherer based off of operator controls, selects manual or semi-auto control
     */
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

    /**
     * Runs the ball gathering system manually through the driver controls
     */
    private void manualControl()
    {
        conveyorUp = false;
        conveyorDown = false;
        rollerIn = false;
        rollerOut = false;
        spaceUp = 0;

        if(OI.secondXboxRBButton())//conveyor up
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
        
    }

    private boolean switchGather = false;
    /**
     * Runs the ball gathering system using a mix of autonomous actions and driver control
     */
    private void semiAutoControl()
    {
        conveyorUp = false;
        conveyorDown = false;
        rollerIn = false;
        rollerOut = false;

        if(OI.secondXboxAButton())//AutoCollection
        {
            conveyorUp = false;
            conveyorDown = false;
            rollerIn = false;
            rollerOut = false;

            rollerIn = true;
            conveyorUp = false;

            if(sensors.bottomChannelBlocked())
            {
                spaceUp = startingSpaceUp;
                conveyorUp = true;
            }
        }
        if(spaceUp > 0)//space the ball
        {
            spaceUp--;
            conveyorUp = true;
        }

        if(OI.secondXboxYButton())//Run conveyor down
        {
           conveyorUp = false;
           conveyorDown = true;
        }

        if (OI.getPrimaryTrigger()) // push balls into shooter
        {
            conveyorDown = false;
            conveyorUp = true;
        }

      
    }

    /**
     * The current speed the shooter is going
     * @return shooterSpeed
     */
    public double getShooterSpeed()
    {
        return shooterSpeed;
    }

    /**
     * Checks to see if the robot should be in manual or semi-auto control
     */
    public void checkControlSetup()
    {
        if(OI.secondXboxLeftJoyClick())
            manualOveride = true;
        else if(OI.secondXboxRightJoyClick())
            manualOveride = false;
        
        ballGatherer.setManualOverride(manualOveride);
    }

    /**
     * Runs the shooter based off of operator controls
     */
    public void runShootingOperations()
    {

        //When the start button is pressed and released, the shooter speed is incremented up
        if(OI.secondXboxStartButton())
        {
            switchSpeedUp = true;
        }
        else if(switchSpeedUp)
        {
            switchSpeedUp = false;
            if(manualShooterSpeed < 1)
                manualShooterSpeed += .05;
            else
                manualShooterSpeed = 1;
        }

        //When the select button is pressed and released, the shooter speed is incremented downward
        if(OI.secondXboxSelectButton())
        {
            switchSpeedDown = true;
        }
        else if(switchSpeedDown)
        {
            System.out.println("speed down");
            switchSpeedDown = false;
            if(manualShooterSpeed > .25)
                manualShooterSpeed -= .05;
            else
                manualShooterSpeed = .25;
        }


        if(OI.secondXboxXButton())//Reset to key speed
        {
            manualShooterSpeed = .45;
        }

       if (OI.getSecondaryTrigger())//Toggle Shooter
       {
            shooterTriggerDown = true;
       }
       else if(shooterTriggerDown)
       {
           shootingNow = !shootingNow;
           shooterTriggerDown = false;
       }
       if (shootingNow)
       {
           if(OI.secondXboxLeftTrigger())//run with the OI speed input
            {
                shooterSpeed = OI.getAnalogIn(4);
            }
            else
            {
                shooterSpeed = manualShooterSpeed;
            }
            if(!manualOveride && OI.secondXboxBButton())//Shooter fires at maximum power
            {
                maxPower = true;
            }
            else
                maxPower = false;
        }
        else
             shooterSpeed = 0;

        UpdateDS(manualShooterSpeed , shootingNow);
    }

    /**
     * Turns the turret according to user controls
     */
    public void runTurretingOperations()
    {
        OI.setDigitalOutput(1, false);
        turretTurn = .3 * OI.secondXboxLeftX();
        if(turretTurn < 0)
            turretTurn *= 1.6;
        shooter.turn(-turretTurn);
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        shootingNow = false;
    }

    /**
     * Updates the Drivers station with information. Currently the SmartDashboard is in use.
     * @param speed
     * @param shooting
     */
    protected void UpdateDS(double speed, boolean shooting)
    {
        boolean one, two, three, four;

        if(speed < .26)
        {
            one = false;
            two = false;
            three = false;
            four = false;
        }
        else if(speed < .31)
        {
            one = false;
            two = false;
            three = false;
            four = true;
        }
        else if(speed < .36)
        {
            one = false;
            two = false;
            three = true;
            four = false;
        }
        else if(speed < .41)
        {
            one = false;
            two = false;
            three = true;
            four = true;
        }
        else if(speed < .46)
        {
            one = false;
            two = true;
            three = false;
            four = false;
        }
        else if(speed < .51)
        {
            one = false;
            two = true;
            three = false;
            four = true;
        }
        else if(speed < .56)
        {
            one = false;
            two = true;
            three = true;
            four = false;
        }
        else if(speed < .61)
        {
            one = false;
            two = true;
            three = true;
            four = true;
        }
        else if(speed < .66)
        {
            one = true;
            two = false;
            three = false;
            four = false;
        }
        else if(speed < .71)
        {
            one = true;
            two = false;
            three = false;
            four = true;
        }
        else if(speed < .76)
        {
            one = true;
            two = false;
            three = true;
            four = false;
        }
        else if(speed < .81)
        {
            one = true;
            two = false;
            three = true;
            four = true;
        }
        else if(speed < .86)
        {
            one = true;
            two = true;
            three = false;
            four = false;
        }
        else if(speed < .91)
        {
            one = true;
            two = true;
            three = false;
            four = true;
        }
        else if(speed < .96)
        {
            one = true;
            two = true;
            three = true;
            four = false;
        }
        else
        {
            one = true;
            two = true;
            three = true;
            four = true;
        }

        OI.setDigitalOutput(5, four);
        OI.setDigitalOutput(4, three);
        OI.setDigitalOutput(3, two);
        OI.setDigitalOutput(2, one);
        OI.setDigitalOutput(6, shooting);
        SmartDashboard.putBoolean(" ", shooting);

        return;

    }

    /**
     * Stops the shooter if command is interrupted
     */
    protected void interrupted() 
    {
        shootingNow = false;
        shooter.run(0);
    }
}