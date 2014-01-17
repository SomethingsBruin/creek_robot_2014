package com.cc.systems;

import com.cc.outputs.motors.CCTalon;

import edu.wpi.first.wpilibj.Gyro;

/**
 * This class represents the chassis of the robot and is responsible for moving
 * the robot.
 */
public class Chassis 
{
    //The object where the singleton of the chassis is stored
    private static Chassis _instance = null;
    
    //The four talons which we control the motors from.
    private CCTalon _leftFront;
    private CCTalon _rightFront;
    private CCTalon _leftRear;
    private CCTalon _rightRear;
    
    //The gyro which gives the angle of the robot.
    private Gyro _gyro;
    
    private Chassis()
    {
        //Initializes each talon speed controler.
        _leftFront = new CCTalon( 1, false );
        _rightFront = new CCTalon( 3, true );
        _leftRear = new CCTalon( 2, false );
        _rightRear = new CCTalon( 4, true );
        
        //Initializes the gyro of the robot.
        _gyro = new Gyro( 1 );
    }
    
    /**
     * Returns the singleton object of the Chassis.
     * 
     * @return  The singleton object of the chassis.
     */
    public static Chassis getInstance()
    {
        //If no chassis object has been previously constructed.
        if( _instance == null )
        {
            //Create the singleton Chassis object.
            _instance = new Chassis();
        }
        
        //Return the singleton object.
        return _instance;
    }
    
    /**
     * Moves the robot using the mecanum wheels given a forward, slide, and
     * rotation value.
     * 
     * @param fwd The forward value from a driver.
     * @param sld The slide value from a driver.
     * @param rot The rotation value from a driver.
     */
    public void holoDrive( double fwd, double sld, double rot )
    {
        //Calcuates the vector for each motor given the forward, slide, and rotation values.
        double lf = fwd + sld + rot;
        double rf = fwd - sld - rot;
        double lr = fwd - sld + rot;
        double rr = fwd + sld - rot;
        
        //Makes the max value equal to the left motor.
        double maxVal = Math.abs(lf);
        
        //If any motor value is greater than the max value, make the max value equal to that motor value.
        if ( Math.abs(rf) > maxVal)
        {
            maxVal = Math.abs(rf);
        }
        
        if ( Math.abs(lr) > maxVal)
        {
            maxVal = Math.abs(lr);
        }
        
        if ( Math.abs(rr) > maxVal)
        {
            maxVal = Math.abs(rr);
        }
        
        //If the max value was greater than one or less than negative one...
        if( maxVal > 1.0 )
        {
            //Limit that motor value and scale the other motor values appropriatly.
            lf /= maxVal;
            rf /= maxVal;
            lr /= maxVal;
            rr /= maxVal;
        }
         
        //Set each motor to its corresponding value.
        _leftFront.set( lf );
        _rightFront.set( rf );
        _leftRear.set( lr );
        _rightRear.set( rr );
    }
    
    /**
     * Moves the robot based on the mecanum system given a forward value, a slide value,
     * and a rotation value. Moves relative to driver instead of the starting position. 
     * 
     * @param fwd The forward value from a driver.
     * @param sld The slide value from a driver.
     * @param rot The rotation value from a driver.
     */
    public void relativeHoloDrive( double fwd, double sld, double rot )
    {
        //Calculates the relative forward and slide values.
        double fwdTemp = fwd * Math.cos( getGyro() ) + sld * Math.sin( getGyro() );
        double sldTemp = sld * Math.cos( getGyro() ) - fwd * Math.sin( getGyro() );
        
        //Inputs the calculated values into the normal holoDrive function.
        holoDrive( fwdTemp, sldTemp, rot );     
    }
    
    /**
     * Returns the angle of the gyro on the chassis.
     * 
     * @return The angle of the gyro.
     */
    public double getGyro()
    {
        //Gets the raw angle from the gyro
        double gyroValue = _gyro.getAngle();
        
        //If the angle is greater than 360 or less than -360...
        if ( Math.abs( gyroValue ) >= 360)
        {
            //Reset the gyro to zero degrees.
            _gyro.reset();
            gyroValue = _gyro.getAngle();
        }
        
        //Converts the angle of the gyro from degrees to radians.
        gyroValue = Math.toRadians( gyroValue );
        
        //Returns the gyro angle.
        return gyroValue;
    }
    
    /**
     * Prints the angle of gyro.
     */
    public void printGyro()
    {
        //Prints the angle of gyro.
        System.out.println( _gyro.getAngle() );
    }
    
}
