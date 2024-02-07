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
    double LY;
    double RY;
    double Leftmin = 1;
    double Rightmin = 1;
    double Leftmax = 50;
    double Rightmax = 50;
    double Leftspeed = 2;
    double Rightspeed = 2;

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
        // To give a Variable the climbers stick 
        LY = Setup.getInstance().getSecondaryLeftClimber()*Leftspeed;
        RY = Setup.getInstance().getSecondaryRightClimber()*Rightspeed;

        // To Set a length to the analongPotentiometer for both Left and right climber
        if ((ClimberPotentiometerLeft.get()<50)&&(LY<0)) {
            LeftClimbermotor.set(LY);
        } else if ((LY>0)&&(ClimberPotentiometerLeft.get()>10)) {
            LeftClimbermotor.set(LY);
        }

        if ((ClimberPotentiometerRight.get()<50)&&(RY<0)) {
            RightClimbermotor.set(RY);
         }else if ((RY>0)&&(ClimberPotentiometerRight.get()>10)) {
            RightClimbermotor.set(RY);
        }

        // To change the speed of both left and right climber 
        if (Setup.getInstance().getSecondaryLeftClimberButton()) {
            Leftspeed=2;
        } else {
            Leftspeed=.5;
        }

        if (Setup.getInstance().getSecondaryRightClimberButton()) {
            Rightspeed=2;
        } else {
            Rightspeed=.5;
        }
    }

    @Override
    public void outputToSmartDashboard(){
        // To send numbers to SmartDashboard
        SmartDashboard.putNumber("ClimberPotentiometerLeft", ClimberPotentiometerLeft.get());
        SmartDashboard.putNumber("ClimberPotentiometerRight", ClimberPotentiometerRight.get());
    }

    @Override
    public void stop(){}
}
