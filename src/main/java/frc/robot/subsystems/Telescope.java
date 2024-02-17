package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

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
    public double telescopeMax = 80;
    public double telescopeMin = 5;

    public Telescope(){
        telescopeMotor = new CANSparkMax(Setup.TelescopeMotorID, MotorType.kBrushless);
        encoder = telescopeMotor.getEncoder();
        potentiometer = new AnalogPotentiometer(0, 180, 30);
    }
    
    public CANSparkMax getTelescopeMotor(){
        return telescopeMotor;
    }

    public RelativeEncoder getTelescopeEncoder(){
        return encoder;
    }

    public double getPotentiometer(){
        return potentiometer.get();
    }

    @Override
    public void updateSubsystem(){
        telescopePotentiometer = getPotentiometer();
        armExtend = Setup.getInstance().getSecondaryTelescope();
        
        if (armExtend < -.1 && telescopePotentiometer < telescopeMax) { // If the joystick is being pushed forwards and the arm is not at the maximum then make the arm move.
            telescopeMotor.set(-armExtend/2); // As a safety pre-caution the robot will extend very slowly. Please edit this if you want it to go faster (not reccomended)
        
        } else {
            if (telescopePotentiometer < telescopeMin) { // If the arm is at it's minimum set the motor to false.
                telescopeMotor.set(0);

            } else {         
                if (armExtend > -0.05) { // If the joystick is in the middle begin to pull the arm backwards.
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