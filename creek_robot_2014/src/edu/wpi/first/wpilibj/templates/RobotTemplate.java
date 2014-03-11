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
import com.cc.utility.Utility;

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
    
    //Initializes the original PID constants for the chassis. These are dynamically changable in the Smart Dashboard.
    private final double _KP = 0.7;
    private final double _KI = 0.008;
    private final double _KD = 0.04;
    
    //The robot mechanism.
    private Mechanism _mechanism;
    
    //The maximum and minimum extremes for the position of the arm
    private final double _MAX_ARM_EXTREME = 450.0;
    private final double _MIN_ARM_EXTREME = 980.0;
    
    //The maximum up and down speed of the arm.
    private final double _ARM_UP_SPEED = 0.7;
    private final double _ARM_DOWN_SPEED = -0.6;
          
    //The delay between ejecting the ball and shooting the ball.
    private final double _DELAY = 0.1;
    
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
        
        //Puts the PID constants into the Smart Dashboard so they are dynamicly changable.
        SmartDashboard.putNumber( " P-Constant: ", _KP );
        SmartDashboard.putNumber( " I-Constant: ", _KI );
        SmartDashboard.putNumber( " D-Constant: ", _KD );
        
        //Get the mechanism object.
        _mechanism = Mechanism.getInstance();
        
        //Puts the maximum arm up and down speeds into the SmartDashboard
        SmartDashboard.putNumber( " Arm Up-Speed: " , _ARM_UP_SPEED );
        SmartDashboard.putNumber( " Arm Down-Speed: " , _ARM_DOWN_SPEED );
        
        //Puts the minimum and maximum arm extremes into the SmartDashboard.
        SmartDashboard.putNumber( " Arm Maximum Extreme: " , _MAX_ARM_EXTREME );
        SmartDashboard.putNumber( " Arm Minimum Extreme: " , _MIN_ARM_EXTREME );
        
        //Puts the arm shooting delay into the SmartDashboard.
        SmartDashboard.putNumber( " Arm Shooting Delay: " , _DELAY );
        
        //Initializes the chooser devices.
        _driverChooser = new SendableChooser();
        _driveTypeChooser = new SendableChooser();
        _autoCommandChooser = new SendableChooser();
        
        //Assigns a index number to each Driver type. The Airplane Controller is the default selection.
        _driverChooser.addDefault( "Airplane Controller" , new Integer( 0 ) );//0 for the Airplane Controller.
        _driverChooser.addObject( "Attack Three" , new Integer( 1 ) );//1 for the Attack Three joysticks.
        _driverChooser.addObject( "XBox Controller" , new Integer( 2 ) );//2 for the XBox Controller.
        
        //Assigns a index number to each drive type. The Normal Holo Drive is the default selection.
        _driveTypeChooser.addDefault( "Normal Holo Driver" , new Integer( 1 ) );//1 for the Normal Holo Dirve.
        _driveTypeChooser.addObject( "Relative Holo Drive" , new Integer( 0 ) );//0 for the Relative Holo Drive.
        
        //Adds each AutoCommand into the Smart Dashboard.
        _autoCommandChooser.addDefault( "Do Nothing" , new AutoNothing() );
        _autoCommandChooser.addObject( "Center Auto Command" , new AutoCenter() );
        _autoCommandChooser.addObject( "Side Auto Command" , new AutoSide() );
        
        //Puts the chooser devices into the Smart Dashboard.
        SmartDashboard.putData( "Driver Controller" , _driverChooser );
        SmartDashboard.putData( "Drive Type" , _driveTypeChooser );
        SmartDashboard.putData( "Auto Command" , _autoCommandChooser );
        
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
        _autoFlag = false;
        
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
                _chassis.relativeHoloDrive( _driver.getY() , _driver.getX() , Utility.limitRange( _driver.getRot(), 0.75, -0.75 ) );
                break;
        }
        
        //If the analog button's sum is negative and the arm is below the minimum extreme...
        if( !_mechanism.isSettingArm() && _driver.getArm() < 0.0 && _mechanism.getPotentValue() < SmartDashboard.getNumber( " Arm Minimum Extreme: " ) )
        {
            //The arm on the mechanism will raise at the analog speed.
            _mechanism.lowerArm( _driver.getArm() );
        }
        else if( !_mechanism.isSettingArm() && _driver.getArm() > 0.0 && _mechanism.getPotentValue() > SmartDashboard.getNumber( " Arm Maximum Extreme: " ) )//Else if the analog button's sum is positive and the arm is above the minimum extreme...
        {
            //The arm on the mechanism will lower at analog speed.
            _mechanism.raiseArm( -1 * _driver.getArm() );
        }
        else//Else stop the arm.
        {
            _mechanism.stopArm();
        }
        
        //If the primary button is pressed...
        if( _driver.getPriButton() )
        {
            //Square the robot back to the wall.
            _chassis.square( 0.8 );
        }
        
        //If the secondary button is pressed and the arm isn't being set...
        if( _driver.getSecButton() && !_mechanism.isSettingArm() )
        {
            //Shoot the mechanism.
            _mechanism.shoot();
        }
        
        //If the arm is currently not being set.
        if( !_mechanism.isSettingArm() )
        {
            //If the third button is pressed...
            if( _driver.getThirdButton() )
            {
                //Set the arm to the bottom state.
                _mechanism.setArm( 0 );
            }
            else if( _driver.getFourthButton() )//If the fourth button is pressed...
            {
                //Set the arm to the top state.
                _mechanism.setArm( 1 );
            }
            else if( _driver.getFifthButton() )//If the fifth button is pressed...
            {
                //Set the arm to the middle state.
                _mechanism.setArm( 2 );
            }
        }
        
        //If the fifth button is pressed...
        if( _driver.getSixthButton() )
        {
            //The mechanism will eject the ball.
            _mechanism.eject();
        }
        else if( _driver.getSeventhButton() )//Else if the sixth button is pressed...
        {
            //The mechanism will intake the ball.
            _mechanism.intake();
        }
        else if( !_mechanism.isShooting() )//Else if the mechanism is not shooting...
        {
            //The intake motors on the mechanism will stop.
            _mechanism.stopIntake();
        }
    }
    
    /**
     * A function which is called once at the beginning of test.
     */
    public void testInit()
    {
        //Does nothing in test.
        _chassis.resetEncoder();
        _chassis.move( 200 , 0.33 );
    }
    
    /**
     * This function is called periodically during test mode.
     */
    public void testPeriodic() 
    {
        //Does nothing in test.
    }
}