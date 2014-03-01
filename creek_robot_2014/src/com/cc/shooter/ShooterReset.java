package com.cc.shooter;

import com.cc.systems.Mechanism;
import edu.wpi.first.wpilibj.Timer;

/**
 * The class which is a thread that shoots the shooter then resets the shooter.
 */
public class ShooterReset extends Thread
{
    //The shooter object in the thread.
    Shooter _shooter;
    
    //The mechanism object in the thread.
    Mechanism _mechanism;
    
    /**
     * Creates the thread which shoots the shooter and then resets the shooter.
     * 
     * @param shooter The object which the thread will shoot and reset.
     */
    public ShooterReset( Shooter shooter )
    {
        //Initializes the shooter and mechanism object in the class.
        _shooter = shooter;
        _mechanism = Mechanism.getInstance();
    }
    
    public void run()
    {
        //Start ejecting the ball and wait one second to shoot the ball.
        _mechanism.eject();
        
        //Wait 0.2 seconds to shoot the mechanism.
        Timer.delay( 0.1 );
        
        //Run the shooter until limit switch is released
        _shooter.turnOn();

        //Wait one second so it can clear the switch before monitoring the limit switch again.
        Timer.delay( 1.0 );
        
        //Turn off the ejection.
        _mechanism.stopIntake();
        
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