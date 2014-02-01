package com.cc.systems;

import com.cc.outputs.motors.CCTalon;
import com.cc.outputs.motors.CCVictor;
import edu.wpi.first.wpilibj.Relay;

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
    
    //The relay which will shoot the balls.
    private Relay _shooter;
    
    private Mechanism()
    {
        //Initializes the pivot talon.
        _pivot = new CCTalon( 5, false );
        
        //Initializes the victors for the intake.
        _intakeOne = new CCVictor( 6, false );
        _intakeTwo = new CCVictor( 7, false );
    }
    
    /**
     * Returns the singleton object of the Mechanism..
     * 
     * @return The singleton object of the Mechanism.
     */
    public static Mechanism getInstance()
    {
        //If the instance of the mechanism has not been initialized yet then...
        if( _instance == null )
        {
            //Initialize the singletone Mechanism object.
            _instance = new Mechanism();
        }
        
        //Return the singleton object of the Mechanism.
        return _instance;
    }
    
}
