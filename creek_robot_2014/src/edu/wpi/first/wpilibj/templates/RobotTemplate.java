/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import com.cc.inputs.driver.*;
import com.cc.systems.Chassis;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    
    //Declares the Smart Dashboard device which chooses the Driver.
    private SendableChooser _driverChooser;
    
    //A flag that insure autonomous only goes once.
    private boolean autoFlag = true;    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
        //Get the chassis object.
        _chassis = Chassis.getInstance();   
        
        //Initializes the driver chooser device.
        _driverChooser = new SendableChooser();
        
        //Assigns a index number to each Driver type. The Airplane Controller is the default selection.
        _driverChooser.addDefault( "Airplane Controller" , new Integer( 0 ) );//0 for the Airplane Controller.
        _driverChooser.addObject( "Attack Three" , new Integer( 1 ) );//1 for the Attack Three joysticks.
        _driverChooser.addObject( "XBox Controller" , new Integer( 2 ) );//2 for the XBox Controller.
        
        //Puts the driver chooser device on the Smart Dashboard.
        SmartDashboard.putData( "Driver", _driverChooser );      
    }
    
    /**
     * This function is called once when robot is disabled and prompts the user
     * that the robot is disabled.
     */ 
    public void disabledInit()
    {
        //Prompts that the robot is disabled.
        System.out.println( "Robot is Disabled" );
        
        //Sets the automous flag to be false.
        autoFlag =  false;
    }

    /**
     * This function is called periodically during autonomous and the robot will
     * turn 90 degrees to the right once.
     */
    public void autonomousPeriodic() 
    {
        //If the flag hasn't been raised...
        if( !autoFlag )
        {
            //Turn the robot 90 degrees to the right and raise the flag.
            _chassis.turn( 90, 0.8 );
            autoFlag = true;
        }
    }
    
    /**
     * A function which is called once at the beginning of Tele-Op and finds which
     * driver type is wanted.
     */
    public void teleopInit()
    {
        //Finds the assigned index value of the driver type choosen
        int index = ( (Integer) _driverChooser.getSelected() ).intValue();
        
        //The type of the driver will be choosen from the given index value from the Smart Dashboard
        switch( index )
        {
            //The XBox Controller if the index is 2.
            case 2:
                _driver = XBoxController.getInstance();
                break;
                
            //The Attack Three joysticks if the index is 1.
            case 1:
                _driver = AttackThree.getInstance();
                break;
            
            //The Airplane Controller if the index is 0 (or anything else).
            default:
            case 0:
                _driver = AirplaneController.getInstance();
                break;
        }
    }

    /**
     * This function is called periodically during operator control and it will
     * drive the robot relative to the driver, not its starting position. The robot
     * will square itself to the wall on command.
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
     * A function which is called once at the beginning of Test and finds which
     * driver type is wanted.
     */
    public void testInit()
    {
        //Finds the assigned index value of the driver type choosen
        int index = ( (Integer) _driverChooser.getSelected() ).intValue();
        
        //The type of the driver will be choosen from the given index value from the Smart Dashboard
        switch( index )
        {
            //The XBox Controller if the index is 2.
            case 2:
                _driver = XBoxController.getInstance();
                break;
                
            //The Attack Three joysticks if the index is 1.
            case 1:
                _driver = AttackThree.getInstance();
                break;
            
            //The Airplane Controller if the index is 0 (or anything else).
            default:
            case 0:
                _driver = AirplaneController.getInstance();
                break;
        }
    }
    
    /**
     * This function is called periodically during test mode.
     */
    public void testPeriodic() 
    {
        
    }
}