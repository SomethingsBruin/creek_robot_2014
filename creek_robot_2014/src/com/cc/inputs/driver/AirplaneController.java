package com.cc.inputs.driver;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The class which represents the driver type for the Airplane Controller.
 */
public class AirplaneController extends Driver
{
    //The contoller's X constants.
    private final double _XMAX = 0.80315;
    private final double _XMIN = -0.71875;
    private final double _XCENTER = 0.0;
    
    //The contoller's Y constants.
    private final double _YMAX = -0.59375;
    private final double _YMIN = 0.69291;
    private final double _YCENTER = 0.00787;
    
    //The contoller's Rotation constants.
    private final double _ROTMAX = 0.84252;
    private final double _ROTMIN = -0.67188;
    private final double _ROTCENTER = 0.03937;
    
    //The joystick of the controller.
    private Joystick _joy; 
    
    /**
     * Creates the airplane controller driver object.
     */
    private AirplaneController()
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
        //If the dirver is not an AirplaneController, than make it an AirplaneController
        if( !( _instance instanceof AirplaneController ) )
        {
            _instance = new AirplaneController();
        }
        
        //Return the driver.
        return _instance;
    }
    
    /**
     * Prints all the axes in the controller.
     */
    public void getAxes()
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
    public  void getButtons()
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
        double xValue = normalize( _joy.getRawAxis( 1 ), _XMAX, _XMIN, _XCENTER );
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
        double yValue = normalize( _joy.getRawAxis( 2 ), _YMIN, _YMAX, _YCENTER );
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
        double rValue = normalize( _joy.getRawAxis( 5 ), _ROTMIN, _ROTMAX, _ROTCENTER );
        rValue = rValue * rValue * rValue;
        
        //Returns the rotation value.
        return rValue;
    }
    
    /**
     * Gets the primary (Red) button state.
     * 
     * @return Returns the primary button state.
     */
    public boolean getPriButton()
    {
        //Returns state of red button
        boolean state = _joy.getRawButton( 1 );
        return state;
    }
    
    /**
     * Gets the secondary (Black) button state.
     * 
     * @return Returns the secondary button state.
     */
    public boolean getSecButton()
    {
        //Returns state of black button
        boolean state = _joy.getRawButton( 4 );
        return state;
    }   
}
