package com.cc.inputs.sensors;

import edu.wpi.first.wpilibj.ADXL345_I2C;

/**
 * The class which represents the acceleration of the robot in any given axis X, Y, or Z.
 * Gives the acceleration of the axis in units of Gs.
 */
public class CCAccelerometer 
{
    //The accelerometer of the robot which gives us acceleration in either the X, Y, or Z axis.
    private ADXL345_I2C _accelerometer;
    
    /**
     * Initializes the accelerometer for the robot.
     */
    public CCAccelerometer()
    {
        //Initializes the accelerometer for digital module on 1 and for a range of -4 to 4 Gs.
        _accelerometer = new ADXL345_I2C( 1, ADXL345_I2C.DataFormat_Range.k4G );
    }
    
    /**
     * Gets the acceleration of the robot on the x-axis relative to the robot.
     * 
     * @return The acceleration on the x-axis relative to the robot in Gs.
     */
    public double getXAccel()
    {
        //Finds the x-axis acceleration in Gs.
        double xAccel = _accelerometer.getAcceleration( ADXL345_I2C.Axes.kY );
        
        //Returns the acceleration.
        return xAccel;
    }
    
    /**
     * Gets the acceleration of the robot on the y-axis relative to the robot.
     * 
     * @return The acceleration on the y-axis relative to the robot in Gs.
     */
    public double getYAccel()
    {
        //Finds the x-axis acceleration in Gs and inverts the axis to be relative to the robot.
        double yAccel = _accelerometer.getAcceleration( ADXL345_I2C.Axes.kX );
        yAccel *= -1;
        
        //Returns the acceleration.
        return yAccel;
    }
    
    /**
     * Gets the acceleration of the robot on the z-axis relative to the robot.
     * 
     * @return The acceleration on the z-axis relative to the robot in Gs.
     */
    public double getZAccel()
    {
        //Finds the z-axis acceleration in Gs.
        double zAccel = _accelerometer.getAcceleration( ADXL345_I2C.Axes.kZ );
        
        //Returns the acceleration.
        return zAccel;
    }
}
