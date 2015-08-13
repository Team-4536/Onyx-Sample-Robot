package org.usfirst.frc.team4536.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends SampleRobot {
	
    Joystick mainStick;
    Joystick secondaryStick;
    
    DriveTrain driveTrain;
    Arm frontArm;
    Arm backArm;
    Compressor compressor;
    
    Gyro gyroSensor;
 
    Timer autoTimer;
    Timer teleOpTimer;

    public Robot() {
        mainStick = new Joystick(0);
        secondaryStick = new Joystick(1);
        
        driveTrain = new DriveTrain(Constants.LEFT_MOTOR, Constants.RIGHT_MOTOR);
        frontArm = new Arm(Constants.FRONT_ROLLER_MOTOR, 
        					Constants.FRONT_SOLENOID_1, 
        					Constants.FRONT_SOLENOID_2);
        backArm = new Arm(Constants.BACK_ROLLER_MOTOR,
        					Constants.BACK_SOLENOID_1, 
        					Constants.BACK_SOLENOID_2);
        compressor = new Compressor();
        
        gyroSensor = new Gyro(1);
        
        autoTimer = new Timer();
        teleOpTimer = new Timer();
    }

    public void autonomous() {
    	autoTimer.reset();
    	autoTimer.start();
    	
    	compressor.start();
    	
    	gyroSensor.reset();
    	
    	while(isAutonomous() && isEnabled()) {
            double autoTime = autoTimer.get();  
            double fullSpeedTime = Constants.AUTO_FULL_SPEED_TIME;
            
            if(autoTime <= 1.0) {
                double forwardThrottle = autoTime;
                driveTrain.drive(forwardThrottle, forwardThrottle * (gyroSensor.getAngle() / -10));
            }
            else if(autoTime > 1.0 && autoTime <= 1+fullSpeedTime) {
                driveTrain.drive(1, gyroSensor.getAngle() / -10);
            }
            else if( (autoTime > 1+fullSpeedTime) && (autoTime <= 2+fullSpeedTime) ) {
                double forwardThrottle = (-autoTime + (2+fullSpeedTime));
                driveTrain.drive(forwardThrottle, forwardThrottle * (gyroSensor.getAngle() / -10));
            }
            else if( (autoTime >= 2+fullSpeedTime) && (autoTime <= 3+fullSpeedTime)) { 
                frontArm.setThrottle(-0.7);
                backArm.setThrottle(0.7);
            }
            else {
                driveTrain.drive(0.0, 0.0);
                frontArm.setThrottle(0.0);
                backArm.setThrottle(0.0);
            }
        }
    	
    	compressor.stop();
    }

    public void operatorControl() {
    	compressor.start();
    	double forwardThrottle;
    	double turnThrottle;
    	
        while (isOperatorControl() && isEnabled()) {
        	forwardThrottle = Utilities.speedCurve(Utilities.deadZone(-mainStick.getY(), Constants.DEAD_ZONE), Constants.SPEED_CURVE);
            turnThrottle = Utilities.speedCurve(Utilities.deadZone(mainStick.getX(), Constants.DEAD_ZONE), Constants.SPEED_CURVE);
        	driveTrain.drive(forwardThrottle, turnThrottle);      
            
            frontArm.setArmPosition(mainStick.getRawButton(3));
            backArm.setArmPosition(mainStick.getRawButton(2));
            
            //Switches range of z-axis from [1,-1] to [0,1]
            double secondaryStickZ = (-0.5 * secondaryStick.getZ()) + 0.5;
            System.out.println("secondaryStickZ: " + secondaryStickZ);
            
            /*
             * If front arm is out and the trigger is pressed, in-take balls
             * Otherwise, shoot the ball based off of the value of the z-axis
             */
            if(frontArm.getArmPosition() && mainStick.getTrigger()) {
                frontArm.setThrottle(1.0);
            }
            else {               
                if(secondaryStick.getRawButton(3))
                    frontArm.setThrottle(-secondaryStickZ);
                else if(secondaryStick.getRawButton(2))
                    frontArm.setThrottle(secondaryStickZ);
                else 
                    frontArm.setThrottle(0.0);
            }
            
            /*
             * If back arm is out and the trigger is pressed, in-take balls
             * Otherwise, shoot the ball based off of the value of the z-axis
             */
            if(backArm.getArmPosition() && mainStick.getTrigger()) {
                backArm.setThrottle(1.0);
            }
            else {               
                if(secondaryStick.getRawButton(3))
                    backArm.setThrottle(secondaryStickZ);
                else if(secondaryStick.getRawButton(2))
                    backArm.setThrottle(-secondaryStickZ);
                else 
                    backArm.setThrottle(0.0);
            }            
            
            Timer.delay(0.005);	
        }
        
        compressor.stop();
    }

    public void test() {}
}
