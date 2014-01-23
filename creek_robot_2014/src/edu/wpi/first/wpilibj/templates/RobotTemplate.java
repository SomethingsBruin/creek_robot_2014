/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;

import com.cc.inputs.driver.*;
import com.cc.systems.Chassis;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot 
{
    //The robot driver.
    private Driver _driver;
    
    //The robot chassis.
    private Chassis _chassis;
    
    //A flag that insure autonomous only goes once.
    private boolean autoFlag = true;    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
        //Gives the driver the type of XBoxController.
        _driver = AirplaneController.getInstance();

        //Get the chassis object.
        _chassis = Chassis.getInstance();   
    }
    
    /**
     * This function is called once when robot is disabled.
     */ 
    public void disabledInit()
    {
        //Prompts that the robot is disabled.
        System.out.println( "Robot is Disabled" );
        
        //Sets the automous flag to be false.
        autoFlag =  false;
    }

    /**
     * This function is called periodically during autonomous.
     */
    public void autonomousPeriodic() 
    {
        //If the flag hasn't been raised...
        if( !autoFlag )
        {
            //Moves the 48 inches and raise the flag.
            _chassis.move( 48, 0.5 );
            autoFlag = true;
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() 
    {
        //Drives the chassis relative to the driver.
        _chassis.relativeHoloDrive( _driver.getY() , _driver.getX() , _driver.getRot() );
        
        //If the primary button is pressed...
        if( _driver.getPriButton() )
        {
            //Square the robot back to the wall.
            _chassis.square( 0.8 );
        }
    }
    
    /**
     * This function is called periodically during test mode.
     */
    public void testPeriodic() 
    {

    }
}