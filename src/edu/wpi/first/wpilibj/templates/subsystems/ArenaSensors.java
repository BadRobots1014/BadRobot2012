/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Kinect.Point4;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Jon Buckley
 */
public class ArenaSensors extends Subsystem
{
    private static ArenaSensors sensors;
    private static AxisCamera camera;
    private static Ultrasonic lFront, lBack, rFront, rBack;
    private static final double spacing = 25;

    public static ArenaSensors getInstance()
    {
        if (sensors == null)
        {
            sensors = new ArenaSensors();
        }

        return sensors;
    }

    private ArenaSensors()
    {
        camera = AxisCamera.getInstance();
        
        lFront = new Ultrasonic(1, 1);
        lBack = new Ultrasonic(2, 2);
        rFront = new Ultrasonic(3, 3);
        rBack = new Ultrasonic(4, 4);
    }

    public double getDifferenceInSensorsFromWall(boolean left)
    {
        if (left)            
            return (lFront.getRangeInches() - lBack.getRangeInches());

        else
             return (rFront.getRangeInches() - rBack.getRangeInches());
    }

    /*
     * @return the angle at which the robot is oriented. 0 degrees is when
     * the robot is parallel with the arena walls.  
     */
    public double getAngleOfOrientation()
    {
        double difference = lFront.getRangeInches() - lBack.getRangeInches();

        double theta = MathUtils.atan(difference/spacing);
        theta *= (180/3.14159);

        return theta;
    }

    public Point4[] getRectanglePoints() throws AxisCameraException
    {
        Point4[] points = new Point4[4];
        
        ColorImage img;
        int height, width = 0;
        
        try 
        {
            img = camera.getImage();
            img.getBluePlane();
            img.thresholdRGB(0, 10, 0, 10, 200, 256);

            height = img.getHeight();
            width = img.getWidth();
        }
        
        catch (NIVisionException ex)
        {
            ex.printStackTrace();
        }

        return points;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}