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
    String host, port;
    UDPDatagramConnection server;
    double depth, offAxis;
    protected String buffer;
    
    public PacketListener()
    {
        super("PacketListener");
    }
    
    public PacketListener(String name) throws IOException 
    {
        super(name);
        server = (UDPDatagramConnection) Connector.open("Datagram:" + host + "//" + port);

    } 
    
    public void recieveData() throws IOException
    {
       Datagram d = server.newDatagram(255);
       server.receive(d);
       
       String recieved = new String(d.getData());
       String parsed = "";
       
       int step = 1; 
       
       System.out.println("Recieved: " + recieved);
       
       for (int i = 0; i < recieved.length() ; i++)
       {
           switch (step)
           {
               case 1: //looks for start
                   if (recieved.charAt(i) == 'A')
                   step = 2;    
                   break;
                   
               case 2: //reads
                   if (recieved.charAt(i) == '|')
                       step = 3;
                   
                   else
                       parsed += recieved.charAt(i);
                   
                   break;
                   
               case 3: //reads
                   if (recieved.charAt(i) == 'B')
                       i = recieved.length();
                   
                   else
                       parsed += recieved.charAt(i);
                   
                   break;
           }
       }
       
       //if (parsed.length() != 16)
           //return;
       
       depth = Double.parseDouble(parsed.substring(0,7));
       offAxis = Double.parseDouble(parsed.substring(8, 15));       
       System.out.println("depth " + depth + " offAxis: " + offAxis);

    }
    
    public double getDepth()
    {
        return depth;
    }
    
    public double getOffAxis()
    {
        return offAxis;
    }
    
    
}
