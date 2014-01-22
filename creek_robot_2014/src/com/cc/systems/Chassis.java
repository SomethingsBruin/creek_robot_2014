package com.cc.systems;

import com.cc.inputs.sensors.Sonar;
import com.cc.outputs.motors.CCTalon;
import com.cc.utility.Utility;

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
    
    //The sonar which determines the distance of the robot from an object in front of it.
    private Sonar _sonar;
    
    private Chassis()
    {
        //Initializes each talon speed controler.
        _leftFront = new CCTalon( 1, false );
        _rightFront = new CCTalon( 3, true );
        _leftRear = new CCTalon( 2, false );
        _rightRear = new CCTalon( 4, true );
        
        //Initializes the gyro of the robot.
        _gyro = new Gyro( 2 );
        _gyro.reset();
        
        //Initializes the sonar of the robot.
        _sonar = new Sonar( 1 );
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
        double maxVal = Math.abs( lf );
        
        //If any motor value is greater than the max value, make the max value equal to that motor value.
        if ( Math.abs( rf ) > maxVal )
        {
            maxVal = Math.abs( rf );
        }
        
        if ( Math.abs( lr ) > maxVal )
        {
            maxVal = Math.abs( lr );
        }
        
        if ( Math.abs( rr ) > maxVal )
        {
            maxVal = Math.abs( rr );
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
     * Line the Robot up with the wall.
     * 
     * @param speed The speed at which to square the robot.
     */
    public void square( double speed )
    {
        //Finds the original angle of the robot (which is the negative of the angle needed to turn).
        double angle = Math.toDegrees( getGyro() );
        
        //If the angle is above 180...
        if( angle >= 180 )
        {
            //Then change the angle to turn to turn more efficently.
            angle = -( 360 - angle );
        }
        else if ( angle <= -180 )//Else if the angle is below -180...
        {
            //Then change the angle to turn to turn more efficently.
            angle += 360;     
        }
        
        //Turn the angle found to square the robot back to 0 degrees.
        turn( -angle , speed );
        
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
     * Moves the robot a given distance at a given speed autonomously.
     * 
     * @param distance The distance in inches for the robot to travel.
     * @param speed The speed the robot will travel.
     */
    public void move( double distance , double speed )
    {
        //Gets the inital distance from an object in front of the robot.
        double initialDistance = _sonar.getDistance();
        
        //Makes sure that the speed is limited to 1 and -1.
        speed = Utility.limitRange( speed );
        
        //While the distance traveled is less than distance wanted...
        while( _sonar.getDistance() > initialDistance - distance )
        {
            //Drive the robot forward at the given speed.
            holoDrive( speed , 0.0 ,0.0 );
        }
        
        //When the robot is done moving forward, stop the motors.
        stop();
    }
    
    /**
     * Turns the robot at a given angle.
     * 
     * @param angle The angle that the robot will turn.
     */
    public void turn( double angle, double speed )
    {
        //The flag which represents if the turn is done.
        boolean done = false;
        
        //Finds the angle that the robot needs to turn to.
        angle += Math.toDegrees( getGyro() );
        
        //The three constants for the PID loop.
        final double KP = 0.7;
        final double KI = 0.006;
        final double KD = 0.04;
        
        //The error, the previous error, the sum of all errors, and the sum's limit that are used in the PID loop.
        double error = 0.0;
        double preError = 0.0;
        double errorSum = 0.0;
        final double sumLimit = 20.0;
        
        //The variables which represents the separate parts to the PID loop.
        double p = 0.0;
        double i = 0.0;
        double d = 0.0;
        
        //The sum of the p, i, and d parts which is inputed into the motors.
        double output = 0.0;
        
        //While the turn is not done...
        while( !done )
        {
            //Sets the previous error to the error of the last interation of the loop.
            preError = error;
            
            //Finds the current error between the given angle and the current angle of the robot.
            error = ( angle - Math.toDegrees( getGyro() ) ) / 100;
            error = Utility.limitRange( error );
            
            //Adds the new error to the error sum of the robot.
            errorSum += error;
            errorSum = Utility.limitRange( errorSum, sumLimit, -sumLimit );
            
            //Calculates the p, i, and d parts of the PID loop.
            p = error * KP;
            i = errorSum * KI;
            d = ( preError - error ) * KD;
            
            //Adds the p, i, and d parts of the loop together, limits it between 1 and -1, then gives it to output.
            output = Utility.limitRange( p + i + d, speed, -speed );
            
            //Turns the robot based on the output of the PID loop.
            holoDrive( 0, 0, output );
            
            //If the error is below 5 percent, then end the loop.
            if( Math.abs( error ) < 0.01 )
            {
                //Raise the flag to end the loop.
                done = true;       
            }
        }
        
        //Stop the motors.
        stop();
    }
    
    /**
     * Stops all the motors on the chassis.
     */
    public void stop()
    {
        //Stops each motor individually.
        _leftFront.stopMotor();
        _leftRear.stopMotor();
        _rightFront.stopMotor();
        _rightRear.stopMotor();
        
    }
}