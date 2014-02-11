package com.cc.systems;

import com.cc.outputs.motors.CCTalon;
import com.cc.outputs.motors.CCVictor;
import com.cc.shooter.*;

/**
 * This class represents the Mechanism on the robot and is responsible for
 * operating the shooter and the pick-up mechanism of the robot.
 */
public class Mechanism 
{

    //The singleton object of the Mechanism class.
    private static Mechanism _instance = null;

    //The talon which represents the pivot of the mechanism arm.
    private CCTalon _pivot;

    //The victors which represent the motors which intake the balls into the mechanism.
    private CCVictor _intakeOne;
    private CCVictor _intakeTwo;

    //The actual shooter mechanism and the thread which cocks the shooter.
    private Shooter _shooter;
    private ShooterReset _shooterReset;

    private Mechanism() 
    {
        //Initializes the pivot talon.
        _pivot = new CCTalon( 5, false );

        //Initializes the victors for the intake.
        _intakeOne = new CCVictor( 6, false );
        _intakeTwo = new CCVictor( 7, false );
        
        //Gets singleton of the shooter obejct.
        _shooter = Shooter.getInstance();
    }

    /**
     * Returns the singleton object of the Mechanism..
     *
     * @return The singleton object of the Mechanism.
     */
    public static Mechanism getInstance() 
    {
        //If the instance of the mechanism has not been initialized yet then...
        if ( _instance == null ) 
        {
            //Initialize the singletone Mechanism object.
            _instance = new Mechanism();
        }

        //Return the singleton object of the Mechanism.
        return _instance;
    }

    /**
     * Shoots the shooter and then cocks the shooter.
     */
    public void shoot() 
    {
        //If we are not already shooting...
        if ( !_shooterReset.isAlive() ) 
        {
            //Create a new shooter thread and then run that thread.
            _shooterReset = new ShooterReset( _shooter );
            _shooterReset.start();
        }
    }
    
    /**
     * Sets the motors on the mechanism to intake the ball.
     */
    public void intake()
    {
        //Sets both motors to full forward to intake the ball.
        _intakeOne.set( 1.0 );
        _intakeTwo.set( 1.0 );       
    }
    
    /**
     * Sets the motors on the mechanism to eject the ball.
     */
    public void eject()
    {
        //Set both motors to full reverse to eject the ball.
        _intakeOne.set( -1.0 );
        _intakeTwo.set( -1.0 );      
    }
    
    /**
     * Stops the intake motors on the mechanism.
     */
    public void stopIntake()
    {
        //Stops both motors.
        _intakeOne.set( 0.0 );
        _intakeTwo.set( 0.0 );       
    }
    
    /**
     * Raises the arm on the mechanism at the given speed.
     * 
     * @param speed The speed at which to raise the arm.
     */
    public void raiseArm( double speed )
    {
        //Raises the arm at the given speed.
        _pivot.set( speed );
    }
    
    /**
     * Lowers the arm on the mechanism at the given speed.
     * 
     * @param speed The speed at which to lower the arm.
     */
    public void lowerArm( double speed )
    {
        //Lowers the arm at the given speed.
        _pivot.set( -speed );
    }
}