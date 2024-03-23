package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Setup;

public class Climber extends Subsystem{
 
    //get instance
    static Climber mInstance = new Climber();

    public static Climber getInstance() {
        return mInstance;
    }

    //Varibales
    AnalogPotentiometer ClimberPotentiometerLeft;
    AnalogPotentiometer ClimberPotentiometerRight;
    CANSparkMax LeftClimbermotor;
    CANSparkMax RightClimbermotor;
    
    double LY, RY;
    double LPot, RPot;
    double leftMin = 14;
    double rightMin = 14;
    double leftMax = 40;
    double rightMax = 40;
    double speed = 1;

    public double getPotentiometerLeft(){
        return ClimberPotentiometerLeft.get();
    }

    public double getPotentiometerRight(){
        return ClimberPotentiometerRight.get();
    }

    public Climber(){
        //scaled in quargajohns
        LeftClimbermotor = new CANSparkMax(Setup.ClimberMotorLeftID, MotorType.kBrushless);
        ClimberPotentiometerLeft = new AnalogPotentiometer(1,100);
        RightClimbermotor = new CANSparkMax(Setup.ClimberMotorRightID, MotorType.kBrushless);
        ClimberPotentiometerRight = new AnalogPotentiometer(2,100);
    }

    @Override
    public void updateSubsystem(){
        LY = Setup.getInstance().getSecondaryLeftClimber();
        RY = Setup.getInstance().getSecondaryRightClimber();
        LPot = ClimberPotentiometerLeft.get();
        RPot = ClimberPotentiometerRight.get();


        //deadband
        if(LY > -0.1 && LY < 0.1){
            LY = 0;
        }
        if (RY > -0.1 && RY < 0.1){
            RY = 0;
        }

        //Left Limits
        if (getPotentiometerLeft()>=leftMax && LY<0) {
            LeftClimbermotor.set(0);
        } else if (getPotentiometerLeft()<=leftMin && LY>0) {
            LeftClimbermotor.set(0);
        } else {
            LeftClimbermotor.set(LY*speed);
        }

        //Right Limits
        if (getPotentiometerRight()>=rightMax && RY<0) {
            RightClimbermotor.set(0);
         } else if (getPotentiometerRight()<=rightMin && RY>0) {
            RightClimbermotor.set(0);
        } else {
            RightClimbermotor.set(-RY*speed);
        }
    }

    @Override
    public void outputToSmartDashboard(){
        SmartDashboard.putNumber("ClimberPotentiometerLeft", LPot);
        SmartDashboard.putNumber("ClimberPotentiometerRight", RPot);
    }

    @Override
    public void stop(){
        RightClimbermotor.set(0);
        LeftClimbermotor.set(0);
    }
}