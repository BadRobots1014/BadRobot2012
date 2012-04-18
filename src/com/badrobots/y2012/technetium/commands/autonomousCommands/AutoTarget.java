/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.commands.autonomousCommands;

import com.badrobots.y2012.technetium.OI;
import com.badrobots.y2012.technetium.commands.CommandBase;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 * @author 1014
 */
public class AutoTarget extends CommandBase {

    int count;
    boolean turning;
    int destination;
    boolean aligned;
    double turretTurn;

    public AutoTarget()
    {
        requires(shooter);
        requires(driveTrain);
        requires(ballGatherer);
        requires(sensors);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        count = 0;
        turning = false;
        destination = 0;
        aligned = false;
        turretTurn = 0;  
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        autoAlign();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return aligned;
    }

    // Called once after isFinished returns true
    protected void end()
    {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {}


    public void autoAlign()
    {
        double xCoord;

        if (!OI.cameraOn)
            return;

        if (imageProcessor.getCoords() == null || imageProcessor.getCoords()[0] < 0)
        {
            aligned = false;
            turretTurn = 0;
            return;
        }
                
        else       
            xCoord = imageProcessor.getCoords()[0];
        
        try
        {
            if (OI.smartdashboardImageProcessingOn && SmartDashboard.getBoolean("rectangleFound"))
            {
                xCoord = SmartDashboard.getInt("rectangleXCoord");
            }
        }
        catch (NetworkTableKeyNotDefined ex)
        {
            
        }

        System.out.println("coords: " + xCoord);

        double offAxis = 80 - xCoord;
        turretTurn = -offAxis;

        if (Math.abs(turretTurn) < 8)
        {
            count++;
            System.out.println("lined up! -- Artemis execute()");
            turretTurn = 0;
            if(count > 10)//was 3 testme
                aligned = true;
            else
                aligned = false;
        }
        else
        {
            count = 0;
            aligned = false;
        }

        if(aligned)
            return;

        if(turretTurn < 0)
            turretTurn = -1;
        else if(turretTurn > 0)
            turretTurn = 1;

        if(!turning)
        {
            destination = shooter.encoderValue() + (int)turretTurn * 15 ;
            shooter.turnByEncoderTo(destination);
            turning = true;
        }
        else
        {
            if(shooter.turnByEncoderTo(destination))
            {
                System.out.println("Arrived");
                turning = false;
            }
        }

    }
}