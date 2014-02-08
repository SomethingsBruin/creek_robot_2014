package com.cc.inputs.driver;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The class which represents the driver type for the XBox Controller.
 */
public class AttackThree extends Driver
{
    //The contoller's X constants.
    private final double _XMAX = 1.0;
    private final double _XMIN = -1.0;
    private final double _XCENTER = 0.0;
    
    //The contoller's Y constants. Y Max and Min are switched for normalization process.
    private final double _YMAX = 1.0;
    private final double _YMIN = -1.0;
    private final double _YCENTER = 0.0;
    
    //The contoller's Rotation constants.
    private final double _ROTMAX = 1.0;
    private final double _ROTMIN = -1.0;
    private final double _ROTCENTER = 0.0;

    //The joysticks of the controller.
    private Joystick _attackOne; 
    private Joystick _attackTwo;
    
    /**
     * Creates the joystick driver object.
     */
    private AttackThree()
    {
        //Calls the contructor in Dirver.
        super();
        
        //Initializes the joystick on channel 1 and channel 2.
        _attackOne = new Joystick( 1 );
        _attackTwo = new Joystick( 2 );
    }
    
    /**
     * Returns the singleton instance of the driver.
     * 
     * @return Returns the singleton instance of the driver.
     */
    public static Driver getInstance()
    {
        //If the dirver is not an AttackThree, than make it an AttackThree
        if( !( _instance instanceof AttackThree ) )
        {
            _instance = new AttackThree();
        }
        
        //Return the driver.
        return _instance;
    }
    
    /**
     * Prints all the axes in the controller.
     */
    public void printAxes()
    {
        //Goes through 12 axis channels on the joystick and prints their current value.
        for( int i = 0; i <= 12; i++ )
        {
            System.out.print( i + "-One: " + _attackOne.getRawAxis( i ) + " " );
            System.out.print( i + "-Two: " + _attackTwo.getRawAxis( i ) + " " );
        }
        
        //Moves to a new line.
        System.out.println();
    }
  
    /**
     * Prints all the buttons in the controller.
     */
    public  void printButtons()
    {
        //Goes through 12 button channels on the joystick and prints their current value.
        for( int i = 0; i <= 12; i++ )
        {
            System.out.print( i + "-One: " + _attackOne.getRawButton( i ) + " " );
            System.out.print( i + "-Two: " + _attackTwo.getRawButton( i ) + " " );
        }
        
        //Moves to a new line.
        System.out.println();
    }
    
    /**
     * Gets the x value of the controller.
     * 
     * @return Returns the x value.
     */
    public  double getX()
    {
        //Finds the normalized x value of the controller, and then expos it.
        double xValue = normalize( _attackOne.getRawAxis( 1 ), _XMIN, _XMAX, _XCENTER );
        xValue = xValue * xValue * xValue;
        
        //Returns the x value.
        return xValue;
    }
    
    /**
     * Gets the y value of the controller.
     * 
     * @return Returns the y value.
     */
    public double getY()
    {
        //Finds the normalized y value of the controller, and then expos it.
        double yValue = -1 * normalize( _attackOne.getRawAxis( 2 ), _YMIN, _YMAX, _YCENTER );//Multiplies by -1 because the y-axis is inverted.
        yValue = yValue * yValue * yValue;
        
        //Returns the y value.
        return yValue;
    }
    
    /**
     * Gets the rotation value of the controller.
     * 
     * Returns the rotation value.
     */
    public double getRot()
    {
        //Finds the normalized rotation value of the controller, and then expos it.
        double rValue = normalize( _attackTwo.getRawAxis( 1 ), _ROTMIN, _ROTMAX, _ROTCENTER );
        rValue = rValue * rValue * rValue;
        
        //Returns the rotation value.
        return rValue;
    }
    
    /**
     * Gets the primary (Joystick One Trigger) button state.
     * 
     * @return Returns the primary button state.
     */
    public boolean getPriButton()
    {
        //Returns state of Joystick One Trigger.
        boolean state = _attackOne.getRawButton( 1 );
        return state;
    }
    
    /**
     * Gets the secondary (Joystick Two Trigger) button state.
     * 
     * @return Returns the secondary button state.
     */
    public boolean getSecButton()
    {
        //Returns state of Joystick Two Trigger.
        boolean state = _attackTwo.getRawButton( 1 );
        return state;
    }   
}