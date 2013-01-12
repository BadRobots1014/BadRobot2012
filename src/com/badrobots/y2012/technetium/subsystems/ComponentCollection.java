/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

/**
 *
 * @author Jon Buckley
 */
public class ComponentCollection
{
    protected Object[] components;
    protected boolean run = true;
    
    public ComponentCollection(Object[] arr)
    {
        components = arr;
    }
    
    public ComponentCollection(int size)
    {
        components = new Object[size];
    }
    
    public void add(Object o, Object[] params)
    {
       try 
       {
          o = o.getClass().newInstance();
       }
       catch (Exception e)
       {
           
       }
               
    }
    
    public void disable()
    {
        run = false;
    }
    
    public void enable()
    {
        run = true;
    }
}
