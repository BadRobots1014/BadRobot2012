/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium;

import com.sun.squawk.io.BufferedReader;
import edu.wpi.first.wpilibj.Timer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.*;

/**
 *
 * @author Jon Buckley
 */
public class PacketListener extends Thread
{
    //Connection to the BeagleBone
    String port = "1180";
    ServerSocketConnection s;
    SocketConnection server;

    //Stores the actual input data
    double[] depth = new double[4];
    double[] offAxis = new double[4];
    protected String buffer;

    protected boolean running = true;

    //These control the state machine of the current action being done
    static int step = 0;
    static final int startOfFrame = 1;
    static final int readingDepth = 2;
    static final int readingSplitChar = 3;
    static final int readingAxis = 4;
    static final int findingEndOfFrame = 5;
    protected double timeSinceLastUpdated = -1;
    protected double timeOfLastUpdate = 0;

    public PacketListener() throws IOException
    {
        this("PacketListener");
    }

    public PacketListener(String name) throws IOException
    {
        super(name);
        step = startOfFrame;
    }

    //Checks for updates from the BeagleBone, parses the input into usable data
    public void receiveData() throws IOException
    {
        //open the server socket connection
        String hp = "socket://:" + port;
        System.out.println("Connecting on " + hp);
        s = (ServerSocketConnection) Connector.open(hp);
        System.out.println("Connected!");

        //instatiate the server
        server = (SocketConnection)s.acceptAndOpen();

        //get data input from the server
        DataInputStream is = server.openDataInputStream(); 

        //reads in data as a byte array, converts to String
        byte[] bytes = new byte[256];
        is.read(bytes);
        String dat = new String(bytes);

        String parsed = "";     

        //Parses data
        System.out.println("Recieved: " + dat);
        if (dat.length() > 10)
        {
            for (int i = 0; i < dat.length(); i++)
            {
                switch (step)
                {
                    case startOfFrame:
                        if (dat.charAt(i) == 'A')//A is before the depth data
                        {
                            step = readingDepth;
                        }
                        break;

                    case readingDepth://if | appears, the data has switched to axis reading
                        if (dat.charAt(i) == '|')
                            step = readingAxis;
                        
                        else
                            parsed += dat.charAt(i);//store the depth reading data

                        break;

                    case readingSplitChar://what is this?
                        //TODO: here
                        break;

                    case readingAxis://B signifies end of data
                        if (dat.charAt(i) == 'B')
                            i = dat.length();//end for loop
                        
                        else if (dat.charAt(i) == '|')//Sends it back to reading depth.
                            step = readingDepth;
                          
                        else
                            parsed += dat.charAt(i);//Store Axis data
                        
                        break;

                    case findingEndOfFrame://Is this a placeholder?
                        break;
                }
            }

            //Stores each section of 8 numbers as a double in the array of data.
            for (int i = 0; i < parsed.length()/8; i++)//@Jon: Won't depth and offAxis be the same data by this logic? -Lucas
            {
                //These should not be identical -Lucas (Unless I missed something)
                depth[i] = Double.parseDouble(parsed.substring((i*8), (i*8) + 8));
                offAxis[i] = Double.parseDouble(parsed.substring((i*8), (i*8) + 8));
            }
           
            System.out.println("depth " + depth + " offAxis: " + offAxis);
            
            timeOfLastUpdate = Timer.getFPGATimestamp();
        }
        else
        {
            System.out.println("Too short");
        }
        //Close all connections
        is.close();
        server.close();
        s.close();
    }

    //This gets called to begin the thread operations
    int counter = 0;
    public void run()
    {
        while (true)
        {
            if (running)
            {
                try
                {
                    System.out.println("Running" + counter);
                    receiveData();
                    counter++;
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }

        }
    }

    public double[] getDepth()
    {
        return depth;
    }

    public double[] getOffAxis()//return between -1 and 1
    {
        //return offAxis/320;
        return offAxis;
    }

    //Return true if it has been updated in the last 1000 time units
    public boolean isUpdated()
    {
        timeSinceLastUpdated = Timer.getFPGATimestamp() - timeOfLastUpdate;
        
        if (timeSinceLastUpdated == -1)
            System.out.println("Never updated");//Shouldn't this return false then? -Lucas
        else if (timeSinceLastUpdated > 1000)
            return false;
        
        return true;
    }

    //Whether or not anything is done (Thread still runs though)
    public void setRunning(boolean run)
    {
        running = run;
    }
}
