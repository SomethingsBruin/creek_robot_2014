package com.cc.shooter;

import com.cc.systems.Mechanism;

/**
 * Sets the arm to the given state, either the bottom extreme, the upper extreme,
 * or in the middle.
 */
public class ArmSet extends Thread
{
    //The mechanism object in the the thread.
    private Mechanism _mechanism;
    
    //The state that the arm will be set to.
    //0 = bottom ; 1 = top ; 2 = middle.
    private int _state;
    
    /**
     * Creates the thread that sets the mechanism to the given arm.
     * 
     * @param state 
     */
    public ArmSet( int state )
    {
        //Sets the state to the given state.
        _state = state;
        
        //Gets the singleton version of the Mechanism.
        _mechanism = Mechanism.getInstance();
    }
    
    /**
     * Sets the arm to the given state.
     */
    public void run()
    {
        //Change the operation of the thread based off the given state.
        switch( _state )
        {
            case 0://The state to set the arm to the bottom extreme.
                
                break;
                
            case 1://The state to set the arm to the top extreme.
                
                break;
                
            case 2://The state to set the arm to the middle.
                
                break;
            
        }
    }
}
