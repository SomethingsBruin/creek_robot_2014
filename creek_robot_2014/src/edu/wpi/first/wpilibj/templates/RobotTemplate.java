/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import com.cc.autonomous.*;
import com.cc.inputs.driver.*;
import com.cc.systems.*;

import edu.wpi.first.wpilibj.IterativeRobot;
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
    
    //The robot mechanism.
    private Mechanism _mechanism;
    
    //The AutoCommand to be run in autonomous.
    private AutoCommand _autoCommand;
    
    //Declares the Smart Dashboard device which chooses the Driver and the drive type.
    private SendableChooser _driverChooser;
    private SendableChooser _driveTypeChooser;
    
    //Declares the Smart Dashboard device which chooses which AutoCommand to run.
    private SendableChooser _autoCommandChooser;
    
    //A flag that insure autonomous only goes once.
    private boolean _autoFlag = true;

    /**
     * This function is run when the robot is first started up and initializes
     * the mechanism and chassis, and does all the Smart Dashboard initialization.
     */
    public void robotInit() 
    {
        //Get the chassis object.
        _chassis = Chassis.getInstance();
        
        //Get the mechanism object.
        _mechanism = Mechanism.getInstance();
        
        //Initializes the chooser devices.
        _driverChooser = new SendableChooser();
        _driveTypeChooser = new SendableChooser();
        _autoCommandChooser = new SendableChooser();
        
        //Assigns a index number to each Driver type. The Airplane Controller is the default selection.
        _driverChooser.addDefault( "Airplane Controller" , new Integer( 0 ) );//0 for the Airplane Controller.
        _driverChooser.addObject( "Attack Three" , new Integer( 1 ) );//1 for the Attack Three joysticks.
        _driverChooser.addObject( "XBox Controller" , new Integer( 2 ) );//2 for the XBox Controller.
        
        //Assigns a index number to each drive type. The Relative Holo Drive is the default selection.
        _driveTypeChooser.addDefault( "Relative Holo Drive" , new Integer( 0 ) );//0 for the Relative Holo Drive.
        _driveTypeChooser.addObject( "Normal Holo Driver" , new Integer( 1 ) );//1 for the Normal Holo Dirve.
        
        //Adds each AutoCommand into the Smart Dashboard.
        _autoCommandChooser.addDefault( "Do Nothing" , new AutoNothing() );
        _autoCommandChooser.addObject( "Center Auto Command" , new AutoCenter() );
        _autoCommandChooser.addObject( "Side Auto Command" , new AutoSide() );
        
        //Puts the chooser devices into the Smart Dashboard.
        SmartDashboard.putData( "Driver Controller" , _driverChooser );
        SmartDashboard.putData( "Drive Type" , _driveTypeChooser );
        SmartDashboard.putData( "Auto Command" , _autoCommandChooser );
    }
    
    /**
     * This function is called once when robot is disabled and prompts the user
     * that the robot is disabled. Also resets the gyro on the robot to 0 degrees.
     */ 
    public void disabledInit()
    {
        //Prompts that the robot is disabled.
        System.out.println( "Robot is Disabled" );
        
        //Sets the automous flag to be false.
        _autoFlag =  false;
        
        //Resets the gyro to 0 degrees.
        _chassis.resetGyro();
    }
    
    /**
     * A function which is called once at the beginning of Autonomous and finds which
     * AutoCommand that will be run, resets the gyro, and cocks the mechanism.
     */
    public void autonomousInit()
    {
        //Finds the selected AutoCommand.
        _autoCommand = (AutoCommand) _autoCommandChooser.getSelected(); 
        
        //Resets the gyro
        _chassis.resetGyro();
        
        //Initially cocks the mechanism shooter.
        _mechanism.shoot();
    }

    /**
     * This function is called periodically during autonomous and runs the given
     * AutoCommand once.
     */
    public void autonomousPeriodic() 
    {
        //If the flag hasn't been raised...
        if( !_autoFlag )
        {
            //Runs the given AutoCommand and set the auto flag to be true.
            _autoCommand.runAutoCommand();
            _autoFlag = true;
        }
    }
    
    /**
     * A function which is called once at the beginning of Tele-Op and finds which
     * driver type is wanted, resets the gyro, and cocks the mechanism.
     */
    public void teleopInit()
    {
        //Finds the assigned index value of the driver type choosen
        int index = ( (Integer) _driverChooser.getSelected() ).intValue();
        
        //The type of the driver will be choosen from the given index value from the Smart Dashboard.
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
        
        //Reset the gyro.
        _chassis.resetGyro();
        
        //Initially cocks the mechanism.
        _mechanism.shoot();
    }

    /**
     * This function is called periodically during operator control and it will
     * drive the robot relative to the driver, not its starting position. The robot
     * will square itself to the wall on command.
     */
    public void teleopPeriodic() 
    {
        //Finds which drive type is wanted from the SmartDashBoard.
        int index = ( (Integer) _driveTypeChooser.getSelected() ).intValue();
        
        //Based on the selection above, choose and run the selected drive type.
        switch( index )
        {
            //1 is Normal Holo Drive.
            case 1:
                //Drives the chassis not relative to the driver.
                _chassis.holoDrive( _driver.getY() , _driver.getX() , _driver.getRot() );
                break;
            
            //0 and default is Relative Holo Drive.
            default:
            case 0:
                //Drives the chassis relative to the driver.
                _chassis.relativeHoloDrive( _driver.getY() , _driver.getX() , _driver.getRot() );
                break;
        }      
        
        //If the primary button is pressed...
        if( _driver.getPriButton() )
        {
            //Square the robot back to the wall.
            _chassis.square( 0.8 );
        }
        
        //If the secondary button is pressed...
        if( _driver.getSecButton() )
        {
            //Shoot the mechanism.
            _mechanism.shoot();
        }
        
        //If the third button is pressed...
        if( _driver.getThirdButton() )
        {
            //The arm on the mechanism will raise at 0.8 speed.
            _mechanism.raiseArm( 0.8 );
        }
        else if( _driver.getFourthButton() )//Else if the fourth button is pressed...
        {
            //The arm on the mechanism will lower at 0.8 speed.
            _mechanism.lowerArm( 0.8 );
        }
        
        //If the fifth button is pressed...
        if( _driver.getFifthButton() )
        {
            //The mechanism will eject the ball.
            _mechanism.eject();
        }
        else if( _driver.getSixthButton() )//Else if the sixth button is pressed...
        {
            //The mechanism will intake the ball.
            _mechanism.intake();
        }
        else//Else the intake motors on the mechanism will stop.
        {
            _mechanism.stopIntake();
        }
    }
    
    /**
     * A function which is called once at the beginning of test and finds which
     * driver type is wanted and resets the gyro to 0.
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
        
        //Resets the gyro on the chassis
        _chassis.resetGyro();
    }
    
    /**
     * This function is called periodically during test mode.
     */
    public void testPeriodic() 
    {
        
    }
}