package com.cc.autonomous;

import edu.wpi.first.wpilibj.Timer;

/**
 * The class which represents the AutoCommand which starts in the center position
 * figures out which goal is the hot goal, moves forward into position, turns towards
 * the hot goal, and then shoots the ball.
 */
public class AutoCenter extends AutoCommand
{
    /**
     * Calls the constructor from AutoCommand.
     */
    public AutoCenter()
    {
        super();
    }
    
    /**
     * Runs this particular AutoCommand which starts in the center position
     * figures out which goal is the hot goal, moves forward into position, turns towards
     * the hot goal, and then shoots the ball.
     */
    public void runAutoCommand()
    {
        //Moves the chassis forward 12 feet at 0.75 speed.
        _chassis.move( 144, 0.75 );
        
        //Tunrs the robot 20 degrees at 0.75 speed.
        _chassis.turn( 20, 0.75 );
        
        //Set the arm to the correct orientation.
        setArm();
        
        //Shoots the mechanism.
        _mechanism.shoot();
        
        // Delays the program. 
        Timer.delay( 1.0 );
        
        //Turns the robot back 20 degrees at 0.75 speed.
        _chassis.square( 0.75 );
    }
}