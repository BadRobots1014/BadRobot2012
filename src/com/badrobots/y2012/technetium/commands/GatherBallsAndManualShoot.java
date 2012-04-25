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
    protected int spaceUp = 0;//If delay is needed, make this >1
    protected int startingSpaceUp = 35;
    protected boolean conveyorUp = false;
    protected boolean conveyorDown = false;
    protected boolean rollerIn = false;
    protected boolean rollerOut = false;
    protected boolean manualOveride = false;
    protected double manualShooterSpeed = .35 ;
    protected double shooterSpeed = 0;
    protected boolean switchSpeedUp;
    protected boolean switchSpeedDown;
    protected double turretTurn = 0;
    protected boolean shooterTriggerDown = false;
    public static boolean shootingNow = false;
    private static boolean collectingNow = false;
    

    
    public GatherBallsAndManualShoot() 
    {
        requires(ballGatherer);
        requires(shooter);
    }

    protected void initialize() 
    {
        //if (Helios.getInstance().topChannelBlocked())
            //topBlocked = true;

        shooterSpeed = .35;
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

         if(sensors.bottomChannelBlocked())
            OI.setDigitalOutput(7, true);
         else
            OI.setDigitalOutput(7, false);

        ballGatherer.runBottomRoller(rollerIn, rollerOut);
        ballGatherer.runConveyor(conveyorUp, conveyorDown);
        //System.out.println("Shooting at: " + shooterSpeed);

        shooter.run(shooterSpeed);


        //System.out.println("HEREAAAAA: s" + sensors.getGyroAngle());
        
        //System.out.println("Encoder: " + shooter.encoderValue());
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
    private void semiAutoControl()
    {
        conveyorUp = false;
        conveyorDown = false;
        rollerIn = false;
        rollerOut = false;


        if(OI.secondXboxAButton())
        {
        //System.out.println("Spacing");
            conveyorUp = false;
            conveyorDown = false;
            rollerIn = false;
            rollerOut = false;

            rollerIn = true;
            conveyorUp = false;

            if(sensors.bottomChannelBlocked())
            {
                spaceUp = startingSpaceUp;//was 20
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

    public double getShooterSpeed()
    {
        return shooterSpeed;
    }
    
    public void checkControlSetup()
    {
        if(OI.secondXboxLeftJoyClick())
            manualOveride = true;
        else if(OI.secondXboxRightJoyClick())
            manualOveride = false;
        
        ballGatherer.setManualOverride(manualOveride);
    }

    public void runShootingOperations()
    {

        if(OI.secondXboxStartButton())
        {
            switchSpeedUp = true;
        }
        else if(switchSpeedUp)
        {
            System.out.println("speed up");

            switchSpeedUp = false;
            if(manualShooterSpeed < 1)
                manualShooterSpeed += .05;
            else
                manualShooterSpeed = 1;
        }

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
            manualShooterSpeed = .35;
        }

        

       if (OI.getSecondaryTrigger())   //Toggle Shooter
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
           if(OI.secondXboxLeftTrigger())//run with the manual control
            {
                shooterSpeed = OI.getAnalogIn(4);
                System.out.println("##########AnologIN##########");
            }
            else
            {
                shooterSpeed = manualShooterSpeed;
            }
       }
        else
             shooterSpeed = 0;
        UpdateDS(manualShooterSpeed , shootingNow);

        //OI.printToDS("ultra " + sensors.getUltraBackRange());
        //System.out.println("Manual COntrl: " + manualOveride);
    }
    
    public void runTurretingOperations()
    {
        OI.setDigitalOutput(1, false);
        turretTurn = .7 * OI.secondXboxLeftX();
        //System.out.println("Turret Turn: " + turretTurn);
        shooter.turn(-turretTurn);
        //System.out.println("Turreting:" + turretTurn);
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

        return;

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        shootingNow = false;
        shooter.run(0);
    }
}