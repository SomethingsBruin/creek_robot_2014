package com.cc.inputs.driver;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The class which represents the driver type for the Airplane Controller.
 */
public class AirplaneController extends Driver
{
    //The contoller's X constants.
    private final double _XMAX = 0.81102;
    private final double _XMIN = -0.69531;
    private final double _XCENTER = 0.00787;
    
    //The contoller's Y constants. Y Max and Min are switched for normalization process.
    private final double _YMAX = 0.66926;
    private final double _YMIN = -0.56250;
    private final double _YCENTER = 0.01575;
    
    //The contoller's Rotation constants.
    private final double _ROTMAX = 0.81890;
    private final double _ROTMIN = -0.68750;
    private final double _ROTCENTER = 0.02756;
    
    //The joysticks of the controller.
    private Joystick _joyOne;
    private Joystick _joyTwo;
    
    /**
     * Creates the airplane controller driver object.
     */
    private AirplaneController()
    {
        //Calls the contructor in Driver.
        super();
        
        //Initializes the joysticks on channel 1 and 2.
        _joyOne = new Joystick( 2 );
        _joyTwo = new Joystick( 1 );
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
    public void printAxes()
    {
        //Goes through 12 axis channels on the joystick and prints their current value.
        for( int i = 0; i <= 12; i++ )
        {
            System.out.print( i + " One: " + _joyOne.getRawAxis( i ) + " " );
            System.out.print( i + " Two: " + _joyTwo.getRawAxis( i ) + " " );
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
            System.out.print( i + " One: " + _joyOne.getRawButton( i ) + " " );
            System.out.print( i + " Two: " + _joyTwo.getRawButton( i ) + " " );
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
        double xValue = normalize( _joyOne.getRawAxis( 1 ), _XMIN, _XMAX, _XCENTER );
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
        double yValue = -1 * normalize( _joyOne.getRawAxis( 2 ), _YMIN, _YMAX, _YCENTER );//Multiplies by -1 because the y-axis is inverted.
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
        double rValue = normalize( _joyOne.getRawAxis( 5 ), _ROTMIN, _ROTMAX, _ROTCENTER );
        rValue = rValue * rValue * rValue;
        
        //Returns the rotation value.
        return rValue;
    }
    
    /**
     * Gets the primary (Red) button state on the airplane controller.
     * 
     * @return Returns the primary button state.
     */
    public boolean getPriButton()
    {
        //Returns state of red button
        boolean state = _joyOne.getRawButton( 1 );
        return state;
    }
    
    /**
     * Gets the secondary (blue) button state on the x-box controller.
     * 
     * @return Returns the secondary button state.
     */
    public boolean getSecButton()
    {
        //Returns state of the blue button.
        boolean state = _joyTwo.getRawButton( 3 );
        return state;
    }
    
    /**
     * Gets the third (yellow) button state on the x-box controller.
     * 
     * @return Returns the third button state.
     */
    public boolean getThirdButton()
    {
        //Returns state of the yellow button.
        boolean state = _joyTwo.getRawButton( 4 );
        return state;
    }

    /**
     * Gets the fourth (green) button state on the x-box controller.
     * 
     * @return Returns the fourth button state.
     */
    public boolean getFourthButton()
    {
        //Returns state of the red button.
        boolean state = _joyTwo.getRawButton( 1 );
        return state;
    }

    /**
     * Gets the fifth (right) button state on the x-box controller.
     * 
     * @return Returns the fifth button state.
     */
    public boolean getFifthButton()
    {
        //Returns state of the right button.
        boolean state = _joyTwo.getRawButton( 6 );
        return state;
    }

    /**
     * Gets the sixth (left) button state on the x-box controller.
     * 
     * @return Returns the sixth button state.
     */
    public boolean getSixthButton()
    {
        //Returns state of the left button.
        boolean state = _joyTwo.getRawButton( 5 );
        return state;
    }
}