package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;

public class Telescope extends Subsystem{

    //get instance
    static Telescope mInstance = new Telescope();

    public static Telescope getInstance() {
        return mInstance;
    }

    public CANSparkMax telescopeMotor;
    public RelativeEncoder encoder;
    public DigitalInput ArmMax;
    public DigitalInput ArmMin;

    double armExtend = 0;
    double armRetract = 0;

    public Telescope(){
        telescopeMotor = new CANSparkMax(Setup.TelescopeMotorID, MotorType.kBrushless);
        encoder = telescopeMotor.getEncoder();
        ArmMax = new DigitalInput(Setup.TelescopeMaximumID);
        ArmMin = new DigitalInput(Setup.TelescopeMinimumID);
    }
    
    public CANSparkMax getTelescopeMotor(){
        return telescopeMotor;
    }

    public RelativeEncoder getTelescopeEncoder(){
        return encoder;
    }

    public boolean getArmMax(){
        return ArmMax.get();
    }

    public boolean getArmMin(){
        return ArmMin.get();
    }

    @Override
    public void updateSubsystem(){
        armExtend = Setup.getInstance().getSecondaryTelescope();
        
        if(armExtend < -.1 && ArmMax.get() == true ){ // If the joystick is being pushed forwards and the arm is not at the maximum then make the arm move.
            telescopeMotor.set(-armExtend/2); // As a safety pre-caution the robot will extend very slowly. Please edit this if you want it to go faster (not reccomended)
        
         } else {
            if(ArmMin.get() == false) { // If the arm is at it's minimum set the motor to false.
                telescopeMotor.set(0);

             } else {         
                if (armExtend > -0.01) { // If the joystick is in the middle begin to pull the arm backwards.
                    telescopeMotor.set(-0.25);

                } 
            }

        } 

    }

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){}
}