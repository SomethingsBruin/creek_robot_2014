package com.cc.shooter;

import edu.wpi.first.wpilibj.Timer;

/**
 * The class which is a thread that shoots the shooter then resets the shooter.
 */
public class ShooterReset extends Thread
{
    //The shooter object in the thread.
    Shooter _shooter;
    
    /**
     * Creates the thread which shoots the shooter and then resets the shooter.
     * 
     * @param shooter The object which the thread will shoot and reset.
     */
    public ShooterReset( Shooter shooter )
    {
        //Initializes the shooter object in the class.
        _shooter = shooter;
    }
    
    public void run()
    {
        //Run the shooter until limit switch is released
        _shooter.turnOn();

        //Wait one second so it can clear the switch before monitoring the limit switch again.
        Timer.delay( 1.0 );
        
        //Run motor until limit switch is pressed
        while ( _shooter.getLimit() == false )
        {
            //Do nothing.
            ShooterReset.yield();
        }
        
        //Turn off the shooter when the limit switch is pressed.
        _shooter.turnOff();
    }
}