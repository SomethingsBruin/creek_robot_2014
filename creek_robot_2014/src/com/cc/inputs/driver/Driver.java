package com.cc.inputs.driver;

import com.cc.utility.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** 
 * Driver provides an interface for the three controller types to implement.
 * It allows the three controller types to be called driver and provides the 
 * ability to have a singleton.
 */
public abstract class Driver
{
    //The object where the singleton is stored.
    protected static Driver _instance = null;
    
    //The maximum up and down speed of the arm.
    protected final double _ARM_UP_SPEED = 0.7;
    protected final double _ARM_DOWN_SPEED = -0.6;
   
    /**
     * A default constructor that does nothing but makes it protected.
     */
    protected Driver()
    {                            
        //Puts the arm speeds into the Smart Dashboard so they are dynamicly changable.
        SmartDashboard.putNumber( " Arm Up-Speed: " , _ARM_UP_SPEED );
        SmartDashboard.putNumber( " Arm Down-Speed: " , _ARM_DOWN_SPEED );
    }
    
    /**
     * Prints all the axes in the controller.
     */
    public abstract void printAxes();
  
    /**
     * Prints all the buttons in the controller.
     */
    public abstract void printButtons();
    
    /**
     * Gets the x value of the controller.
     * 
     * @return Returns the x value.
     */
    public abstract double getX();
    
    /**
     * Gets the y value of the controller.
     * 
     * @return Returns the y value.
     */
    public abstract double getY();
    
    /**
     * Gets the rotation value of the controller.
     * 
     * @return Returns the rotation value.
     */
    public abstract double getRot();
    
    /**
     * Gets the arm value from the controller.
     * 
     * @return Returns the arm value.
     */
    public abstract double getArm();
    
    /**
     * Gets the primary button state.
     * 
     * @return Returns the primary button state.
     */
    public abstract boolean getPriButton();
    
    /**
     * Gets the secondary button state.
     * 
     * @return Returns the secondary button state.
     */
    public abstract boolean getSecButton();
    
    /**
     * Gets the third button state.
     * 
     * @return Returns the third button state.
     */
    public abstract boolean getThirdButton();
    
    /**
     * Gets the fourth button state.
     * 
     * @return Returns the fourth button state.
     */
    public abstract boolean getFourthButton();
    
    /**
     * Gets the fifth button state.
     * 
     * @return Returns the fifth button state.
     */
    public abstract boolean getFifthButton();
    
    /**
     * Gets the sixth button state.
     * 
     * @return Returns the sixth button state.
     */
    public abstract boolean getSixthButton();
    
    /**
     * Prints the x, y, rotation, primary button, and secondary button values.
     */
    public void print()
    {
        System.out.println( "X: " + getX() + " Y: " + getY() + " ROT: " + getRot() + " ARM: " + getArm() + " Pri: " + getPriButton() + " Sec: " + getSecButton() + " 5: " + getFifthButton() + " 6: " + getSixthButton() );
    }
    
    /**
     * Normalizes a axis value based on its constants.
     * 
     * @param inValue  The raw value given.
     * @param minValue Minimum value of joystick.
     * @param maxValue Maximum value of joystick.
     * @param cValue   Center value of joystick.
     * 
     * @return Returns the normalized value.
     */
    public double normalize( double inValue, double minValue, double maxValue, double cValue )
    {
      
        //Subtracts the center constant from the given value.
        inValue -= cValue;
        
        if( inValue < 0.0 )//If the value is negative...
        {
            //Divide the value by the minimum constant.
            inValue /= -minValue;
        }
        else//If the value is positive... 
        {
            //Divide the value by the maximum constant.
            inValue /= maxValue;
        }
        
        //Limit the value between 1.0 and -1.0.
        inValue = Utility.limitRange( inValue );
        
        //Returns the normalized value.
        return inValue;       
    }
}