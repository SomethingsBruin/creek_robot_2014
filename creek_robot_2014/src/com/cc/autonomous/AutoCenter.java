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
        //Creates and starts the timer that counts to when the hotgoal is active.
        Timer timer = new Timer();
        timer.start();
        
        //Counts the number of blobs from the camera.
        double blobs = getNumBlobs();
        
        //Moves 12 feet forward at 0.55 speed. Equivalent is 2.7 seconds at same speed.
        _chassis.move( 144, 0.55 );
        
        //Stops the robot.
        _chassis.stop();
        
        //If the left side was hot, turn right to the hot goal
        if( blobs < 2 )
        {
            _chassis.turn( 15, 0.45 );
        }
        else//Else turn left to the hot goal
        {
            _chassis.turn( -15, 0.45 );
        }
        
        //Wait until a least 6 seconds have passed in Auto
        while( timer.get() < 6.00 )
        {
            //Do Nothing.
            ;
        }
        
        //Then shoot the mechanism.
        _mechanism.shoot();
    }
}