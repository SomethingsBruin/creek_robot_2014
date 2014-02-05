package com.cc.inputs.driver;

import com.cc.utility.Utility;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class representing XBox Controller.
 */
public class XBoxController extends Driver
{
   //The contoller's X constants.
    private final double _XMAX = 1.0;
    private final double _XMIN = -1.0;
    private final double _XCENTER = 0.0;
    
    //The contoller's Y constants. Y Max and Min are switched for normalization process.
    private final double _YMAX = 1.0;
    private final double _YMIN = -1.0;
    private final double _YCENTER = -0.0078125;
    
    //The contoller's Rotation constants.
    private final double _ROTMAX = 1.0;
    private final double _ROTMIN = -1.0;
    private final double _ROTCENTER = 0.0;
    
    //The joystick of the controller.
    private Joystick _joy; 
    
    /**
     * Creates the XBox Controller driver object.
     */
    private XBoxController()
    {
        //Calls the contructor in Dirver.
        super();
        
        //Initializes the joystick on channel 1.
        _joy = new Joystick( 1 );
    }
    
    /**
     * Returns the singleton instance of the driver.
     * 
     * @return Returns the singleton instance of the driver.
     */
    public static Driver getInstance()
    {
        //If the dirver is not an XBox Controller, than make it an XBoxController
        if( !( _instance instanceof XBoxController ) )
        {
            _instance = new XBoxController();
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
            System.out.print( i + ": " + _joy.getRawAxis( i ) + " " );
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
            System.out.print( i + ": " + _joy.getRawButton( i ) + " " );
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
        double xValue = normalize( _joy.getRawAxis( 4 ), _XMIN, _XMAX, _XCENTER );
      //  xValue = xValue * xValue * xValue;
        
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
        double yValue = -1 * normalize( _joy.getRawAxis( 5 ), _YMIN, _YMAX, _YCENTER );//Multiplies by -1 because the y-axis is inverted.
        // yValue = yValue * yValue * yValue;
        
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
        double rValue = normalize( _joy.getRawAxis( 1 ), _ROTMIN, _ROTMAX, _ROTCENTER );
        //rValue = rValue * rValue * rValue;
        
        //Limits the turn of the robot with this controller to only half.
        rValue = Utility.limitRange( rValue, 0.5, -0.5 );
        
        //Returns the rotation value.
        return rValue;
    }
    
    /**
     * Gets the primary (Right Trigger) button state.
     * 
     * @return Returns the primary button state.
     */
    public boolean getPriButton()
    {
        //Returns state of right trigger.
        boolean state = _joy.getRawButton( 6 );
        return state;
    }
    
    /**
     * Gets the secondary (Left Trigger) button state.
     * 
     * @return Returns the secondary button state.
     */
    public boolean getSecButton()
    {
        //Returns state of left trigger.
        boolean state = _joy.getRawButton( 5 );
        return state;
    }     
}
