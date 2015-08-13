package org.usfirst.frc.team4536.robot;

import java.lang.Math;

public class Utilities {
       
    public static double speedCurve(double input, double curve) {
        double output;
        double adjustedCurve = curve;
        
        if(curve <= 0.1) {         //We don't want to have bases smaller than 0.1 because of weird math
            adjustedCurve = 0.1;
        }
        
        if(input < 0) {            //If the input is negative then pretend that it's positive 
            output = -(Math.pow(-input, adjustedCurve));       //and reflect it over the X axis
        }
        else {                     //If it's positive then simply take it to the power of the adjustedCurve
            output = Math.pow(input, adjustedCurve);
        }
        
        if (output < -1) {        
            output = -1;    
        }
        if (output > 1) {       
            output = 1;             
        }
        return output;
    }
    
    public static double deadZone (double input, double deadZone) {
        if ((input < deadZone) && (input > -deadZone))     
            return 0;
        else
            return input;
    }
}
