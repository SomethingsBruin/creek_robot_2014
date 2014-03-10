package com.cc.autonomous;

import edu.wpi.first.wpilibj.Timer;

/**
 * The class which represents the AutoCommand which goes forward and then shoots
 * without determining if the goal is hot or not.
 */
public class AutoSide extends AutoCommand
{
    /**
     * Calls the constructor from AutoCommand.
     */
    public AutoSide()
    {
        super();
    }
    
    /**
     * Runs this particular AutoCommand which goes forward, then either shoots if
     * the hot goal is active or waits until it is active to shoot.
     */
    public void runAutoCommand()
    {
        //Creates and starts the timer that counts to when the hotgoal is active.
        Timer timer = new Timer();
        timer.start();
        
        //Counts the number of blobs from the camera.
        double blobs = getNumBlobs();
        
        //Drives forward for 1.8 seconds.
        _chassis.holoDrive( 0.75, 0.0, 0.0 );
        Timer.delay( 1.8 );
        
        //Stops the robot.
        _chassis.stop();
        
        //If the goal is not hot.
        if( blobs < 2 )
        {
            //Wait until the goal is a hot goal.
            while( timer.get() < 6.0 )
            {
                //Do nothing.
                ;
            }
            
            //Then shoot the mechanism.
            _mechanism.shoot();
        }
        else//Else shoot immeadiatly.
        {
            _mechanism.shoot();
        }
    }
}