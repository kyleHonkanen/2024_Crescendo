package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Telescope extends Subsystem{

    //get instance
    static Telescope mInstance = new Telescope();

    public static Telescope getInstance() {
        return mInstance;
    }

    public CANSparkMax telescopeMotor;
    public RelativeEncoder encoder;
    public AnalogPotentiometer potentiometer;
    public double telescopePotentiometer;
    public double armExtend;

    //telescope limit variables
    public double telescopeMax = 6;
    public double telescopeMin = 25;

    public Telescope(){
        telescopeMotor = new CANSparkMax(Setup.TelescopeMotorID, MotorType.kBrushed);
        potentiometer = new AnalogPotentiometer(0,100);
    }
    
    public CANSparkMax getTelescopeMotor(){
        return telescopeMotor;
    }

    public double getPotentiometer(){
        return potentiometer.get();
    }

    @Override
    public void updateSubsystem(){
        telescopePotentiometer = getPotentiometer();
        armExtend = Setup.getInstance().getSecondaryTelescope();
      
        if (armExtend < -.1 && telescopePotentiometer > telescopeMax) { // If the joystick is being pushed forwards and the arm is not at the maximum then make the arm move.
            telescopeMotor.set(-armExtend/2); // As a safety pre-caution the robot will extend very slowly. Please edit this if you want it to go faster (not reccomended)
        
        } else {
            if(telescopePotentiometer<telescopeMin && armExtend > -.05){
            telescopeMotor.set(-.25);
            } else {
                telescopeMotor.set(0);
            }
        }
    } 

    @Override
    public void outputToSmartDashboard(){
        SmartDashboard.putNumber("TelescopePot", getPotentiometer());
        SmartDashboard.putNumber("arm Extend",armExtend);
    }

    @Override
    public void stop(){}
}