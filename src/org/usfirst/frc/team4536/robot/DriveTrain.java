package org.usfirst.frc.team4536.robot;

import edu.wpi.first.wpilibj.Talon;

public class DriveTrain {
	Talon leftTalon;
	Talon rightTalon;
	
	public DriveTrain(int leftTalonChannel, int rightTalonChannel) {
		leftTalon = new Talon(leftTalonChannel);
		rightTalon = new Talon(rightTalonChannel);
	}
	
	/*
    Drive method requires two parameters, both between -1 and 1
    Value of 1 for forwardThrottle is full forward
    Value of 1 for turnThrottle is maximum turning to the right
  */
  public void drive(double forwardThrottle, double turnThrottle) {    
      double leftTalonThrottle  = forwardThrottle + turnThrottle;
      double rightTalonThrottle = -forwardThrottle + turnThrottle; 
      
      //set of if statements to make sure values are between -1 and 1 
      if (leftTalonThrottle > 1) {
    	  leftTalonThrottle = 1;
      }
      if(leftTalonThrottle < -1) {
    	  leftTalonThrottle = -1;
      }
      if (rightTalonThrottle > 1) {
    	  rightTalonThrottle = 1;
      }
      if(rightTalonThrottle < -1) {
    	  rightTalonThrottle = -1;
      }
      
      leftTalon.set(leftTalonThrottle);
      rightTalon.set(rightTalonThrottle);
  }
}
