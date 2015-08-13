package org.usfirst.frc.team4536.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Solenoid;
        
public class Arm {
    Talon roller;
    Solenoid solenoid1;
    Solenoid solenoid2;
   
    public Arm (int rollerSlot, int solenoidSlot1, int solenoidSlot2)   {
        roller = new Talon(rollerSlot);
        solenoid1 = new Solenoid (solenoidSlot1);
        solenoid2 = new Solenoid (solenoidSlot2);
 
    }
    
    public void setThrottle(double throttle) {
        roller.set(throttle);
       
    }
    
    /* True values will make arms go out
       False values will make arms go in 
    */
    public void setArmPosition(boolean position) {
        solenoid1.set(position);
        solenoid2.set(!position);
    }
    
    /* Returns true when arms are out
       Returns false when arms are in
    */
    public boolean getArmPosition() {
        return solenoid1.get();
    }
}

