/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium;

import com.sun.squawk.io.BufferedReader;
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

    String port = "1140";
    ServerSocketConnection s;
    SocketConnection server;
    double depth, offAxis;
    protected String buffer;
    protected boolean running = true;
    static int step = 0;
    static final int startOfFrame = 1;
    static final int readingDepth = 2;
    static final int readingSplitChar = 3;
    static final int readingAxis = 4;
    static final int findingEndOfFrame = 5;

    public PacketListener() throws IOException
    {
        this("PacketListener");
    }

    public PacketListener(String name) throws IOException
    {
        super(name);

        String hp = "socket://:" + port;
        System.out.println("Connecting on " + hp);
        s = (ServerSocketConnection) Connector.open(hp);
        System.out.println("Connected!");


        step = startOfFrame;
    }

    public void receiveData() throws IOException
    {
        if (server == null)
        {
            System.out.println("server is null");
            return;
        }

        server = (SocketConnection) s.acceptAndOpen();

        DataInputStream is = server.openDataInputStream();



        String parsed = "";

        System.out.println("Recieved: " + is.available());

        for (int i = 0; i < is.available(); i++)
        {
            switch (step)
            {
                case startOfFrame:
                    if ((char) is.read() == 'A')
                    {
                        step = readingDepth;
                    }
                    break;

                case readingDepth:
                    if ((char) is.read() == '|')
                    {
                        step = readingAxis;
                    } else
                    {
                        parsed += (char) is.read();
                    }

                    break;

                case readingSplitChar:
                    //TODO: here
                    break;

                case readingAxis:
                    if ((char) is.read() == 'B')
                    {
                        i = is.available();
                    } else
                    {
                        parsed += (char) is.read();
                    }
                    break;

                case findingEndOfFrame:
                    //TODO: this
                    break;
            }
        }

        //if (parsed.length() != 16)
        //return;

        depth = Double.parseDouble(parsed.substring(0, 7));
        offAxis = Double.parseDouble(parsed.substring(8, 15));
        System.out.println("depth " + depth + " offAxis: " + offAxis);

    }

    public void run()
    {
        while (true)
        {
            if (running)
            {
                try
                {
                    receiveData();
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }

        }
    }

    public double getDepth()
    {
        return depth;
    }

    public double getOffAxis()
    {
        return offAxis;
    }

    public void setRunning(boolean run)
    {
        running = run;
    }
}
