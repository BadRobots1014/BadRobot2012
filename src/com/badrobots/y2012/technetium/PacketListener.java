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

    String port = "1180";
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




        step = startOfFrame;
    }

    public void receiveData() throws IOException
    {
        String hp = "socket://:" + port;
        System.out.println("Connecting on " + hp);
        s = (ServerSocketConnection) Connector.open(hp);
        System.out.println("Connected!");


        //server = (SocketConnection) s.acceptAndOpen();

        //StreamConnection sc = s.acceptAndOpen();
        server = (SocketConnection)s.acceptAndOpen();
        //if (server == null)
        //{
        //    System.out.println("server is null");
        //    return;
        //}
        //server.setSocketOption(SocketConnection.DELAY, 0);
        //server.setSocketOption(SocketConnection.LINGER, 0);
        //server.setSocketOption(SocketConnection.KEEPALIVE, 0);
        //server.setSocketOption(SocketConnection.RCVBUF, 256);

        //System.out.println("Gotcha:" + server.getSocketOption(SocketConnection.RCVBUF));
        DataInputStream is = server.openDataInputStream(); //server.openDataInputStream();
        
        byte[] bytes = new byte[256];
        is.read(bytes);
        String dat = new String(bytes);
        //System.out.println("We made it so far and got:" + dat);

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
                        {
                            step = readingAxis;
                        } else
                        {
                            parsed += dat.charAt(i);
                        }

                        break;

                    case readingSplitChar:
                        //TODO: here
                        break;

                    case readingAxis:
                        if (dat.charAt(i) == 'B')
                        {
                            i = dat.length();
                        } else
                        {
                            parsed += dat.charAt(i);
                        }
                        break;

                    case findingEndOfFrame:
                        //TODO: this
                        break;
                }
            }

            //if (parsed.length() != 16)
            //return;

            depth = Double.parseDouble(parsed.substring(0, 8));
            offAxis = Double.parseDouble(parsed.substring(8, 16));
            System.out.println("depth " + depth + " offAxis: " + offAxis);
        }
        else
        {
            System.out.println("Too short");
        }
        is.close();
        //server.close();
        server.close();
        s.close();
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
