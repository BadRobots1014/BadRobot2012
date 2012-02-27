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
    String port = "1180";
    ServerSocketConnection s;
    SocketConnection server;
    double[] depth = new double[4];
    double[] offAxis = new double[4];
    protected String buffer;
    protected boolean running = true;
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

    public void receiveData() throws IOException
    {
        String hp = "socket://:" + port;
        System.out.println("Connecting on " + hp);
        s = (ServerSocketConnection) Connector.open(hp);
        System.out.println("Connected!");

        server = (SocketConnection)s.acceptAndOpen();
        
        DataInputStream is = server.openDataInputStream(); 
        
        byte[] bytes = new byte[256];
        is.read(bytes);
        String dat = new String(bytes);

        String parsed = "";     

        System.out.println("Recieved: " + dat);
        if (dat.length() > 10)
        {
            for (int i = 0; i < dat.length(); i++)
            {
                switch (step)
                {
                    case startOfFrame:
                        if (dat.charAt(i) == 'A')
                        {
                            step = readingDepth;
                        }
                        break;

                    case readingDepth:
                        if (dat.charAt(i) == '|')
                            step = readingAxis;
                        
                        else
                            parsed += dat.charAt(i);

                        break;

                    case readingSplitChar:
                        //TODO: here
                        break;

                    case readingAxis:
                        if (dat.charAt(i) == 'B')
                            i = dat.length();
                        
                        else if (dat.charAt(i) == '|')
                            step = readingDepth;
                          
                        else
                            parsed += dat.charAt(i);
                        
                        break;

                    case findingEndOfFrame:
                        break;
                }
            }

            for (int i = 0; i < parsed.length()/8; i++)
            {
                depth[i] = Double.parseDouble(parsed.substring(i*8, (i*8) + 8));
                offAxis[i] = Double.parseDouble(parsed.substring((i*8), (i*8)+8));
            }
           
            System.out.println("depth " + depth + " offAxis: " + offAxis);
            
            timeOfLastUpdate = Timer.getFPGATimestamp();
        }
        else
        {
            System.out.println("Too short");
        }
        is.close();
        server.close();
        s.close();
    }

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
    
    public boolean isUpdated()
    {
        timeSinceLastUpdated = Timer.getFPGATimestamp() - timeOfLastUpdate;
        
        if (timeSinceLastUpdated == -1)
            System.out.println("Never updated");
                    
        else if (timeSinceLastUpdated > 1000)
            return false;
        
        return true;
    }

    public void setRunning(boolean run)
    {
        running = run;
    }
}
