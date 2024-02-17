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
    boolean LFast, RFast;
    double min = 2;
    double max = 50;
    double speed = 1.5;

    public double getPotentiometerLeft(){
        return ClimberPotentiometerLeft.get();
    }

    public double getPotentiometerRight(){
        return ClimberPotentiometerRight.get();
    }

    public Climber(){
        LeftClimbermotor = new CANSparkMax(Setup.ClimberMotorLeftID, MotorType.kBrushless);
        ClimberPotentiometerLeft = new AnalogPotentiometer(2, 180, -2.6);
        RightClimbermotor = new CANSparkMax(Setup.ClimberMotorRightID, MotorType.kBrushless);
        ClimberPotentiometerRight = new AnalogPotentiometer(3,180,-2.6);
    }

    @Override
    public void updateSubsystem(){
        LY = Setup.getInstance().getSecondaryLeftClimber()*speed;
        RY = Setup.getInstance().getSecondaryRightClimber()*speed;
        LFast = Setup.getInstance().getSecondaryLeftClimberButton();
        RFast = Setup.getInstance().getSecondaryRightClimberButton();

        //Left Limits
        if (getPotentiometerLeft()>=max && LY<0) {
            LeftClimbermotor.set(0);
        } else if (getPotentiometerLeft()<=min && LY>0) {
            LeftClimbermotor.set(0);
        } else {
            LeftClimbermotor.set(LY/speed);
        }

        //Right Limits
        if (getPotentiometerRight()>=max && RY<0) {
            RightClimbermotor.set(0);
         }else if (getPotentiometerRight()<=min && RY>0) {
            RightClimbermotor.set(0);
        } else {
            RightClimbermotor.set(RY/speed);
        }

        //change speed when either joystick is held down
        if (LFast || RFast) {
            speed=.5;
        } else {
            speed=1.5;
        }
    }

    @Override
    public void outputToSmartDashboard(){
        SmartDashboard.putNumber("ClimberPotentiometerLeft", ClimberPotentiometerLeft.get());
        SmartDashboard.putNumber("ClimberPotentiometerRight", ClimberPotentiometerRight.get());
    }

    @Override
    public void stop(){
        RightClimbermotor.set(0);
        LeftClimbermotor.set(0);
    }
}