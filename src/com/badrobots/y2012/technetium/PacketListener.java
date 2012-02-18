/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium;

import com.sun.squawk.io.BufferedReader;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.SocketConnection;
import javax.microedition.io.UDPDatagramConnection;

/**
 *
 * @author Jon Buckley
 */
public class PacketListener extends Thread
{

    String port = "1180";
    UDPDatagramConnection server;
    double depth, offAxis;
    protected String buffer;
    protected boolean running = true;
    static int step = 0;
    static final int startOfFrame = 1;
    static final int readingDepth = 2;
    static final int readingSplitChar = 3;
    static final int readingAxis = 4;
    static final int findingEndOfFrame = 5;

    public PacketListener()
    {
        super("PacketListener");
    }

    public PacketListener(String name) throws IOException
    {
        super(name);
        server = (UDPDatagramConnection) Connector.open("Datagram://" + port);
        step = startOfFrame;

    }

    public void receiveData() throws IOException
    {
        Datagram d = server.newDatagram(255);
        server.receive(d);

        String recieved = new String(d.getData());
        String parsed = "";

        System.out.println("Recieved: " + recieved);

        for (int i = 0; i < recieved.length(); i++)
        {
            switch (step)
            {
                case startOfFrame:
                    if (recieved.charAt(i) == 'A')
                    {
                        step = readingDepth;
                    }
                    break;

                case readingDepth:
                    if (recieved.charAt(i) == '|')
                    {
                        step = readingAxis;
                    } else
                    {
                        parsed += recieved.charAt(i);
                    }

                    break;

                case readingSplitChar:
                    //TODO: here
                    break;

                case readingAxis:
                    if (recieved.charAt(i) == 'B')
                    {
                        i = recieved.length();
                    } else
                    {
                        parsed += recieved.charAt(i);
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
                } 
                
                catch (IOException ex)
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
