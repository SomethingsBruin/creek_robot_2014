package com.cc.autonomous;

import com.cc.systems.Chassis;
import com.cc.systems.Mechanism;
import edu.wpi.first.wpilibj.Timer;

/**
 * The abstract class which represents any command that is called for Autonomous
 * but does not define any particular auto command.
 */
public abstract class AutoCommand 
{
    //The chassis that is used in autonomous.
    protected Chassis _chassis;
    
    //The mechanism that is used in autonomous.
    protected Mechanism _mechanism;
    
    /**
     * Default constructor for an AutoCommand that will get the singleton objects
     * of the chassis and the mechanism.
     */
    public AutoCommand()
    {
        //Gets the singleton of the chassis and the mechanism.
        _chassis = Chassis.getInstance();
        _mechanism = Mechanism.getInstance();
    }
    
    /**
     * The method which runs the given AutoCommand.
     */
    public abstract void runAutoCommand();
    
    /**
     * Raises the arm to the correct orientation to shoot in autonomous.
     */
    protected void setArm()
    {
        //Raise the arm at 0.5 speed for 1.5 seconds.
        _mechanism.raiseArm( 0.5 );      
        Timer.delay( 1.5 );
        
        //Stop raising the arm.
        _mechanism.stopArm();
    }
}