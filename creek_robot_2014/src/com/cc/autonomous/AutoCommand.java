package com.cc.autonomous;

import com.cc.systems.Chassis;
import com.cc.systems.Mechanism;

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
}