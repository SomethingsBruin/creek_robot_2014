package com.cc.autonomous;

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
     * Runs this particular AutoCommand which goes forward and then shoots
     * without determining if the goal is hot or not.
     */
    public void runAutoCommand()
    {
        //Moves the chassis forward 12 feet and 75% speed.
        _chassis.move( 144, 0.75 );
        
        //Shoots the mechanism.
        _mechanism.shoot();
    }
}