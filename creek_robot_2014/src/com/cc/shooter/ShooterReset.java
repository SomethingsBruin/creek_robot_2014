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
     * @param mechanism The object which the thread will eject the ball.
     */
    public ShooterReset( Shooter shooter, Mechanism mechanism )
    {
        //Initializes the shooter and mechanism object in the class.
        _shooter = shooter;
        _mechanism = mechanism;
    }
    
    public void run()
    {
        //Start ejecting the ball and wait one second to shoot the ball.
        _mechanism.eject();
        Timer.delay( 1.0 );
        
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
        
        //Turn off the shooter and the ejection when the limit switch is pressed.
        _shooter.turnOff();
        _mechanism.stopIntake();
    }
}