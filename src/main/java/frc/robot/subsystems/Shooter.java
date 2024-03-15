package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {

    // get instance
    static Shooter mInstance = new Shooter();

    public static Shooter getInstance() {
        return mInstance;
    }

    //variables
    public CANSparkMax feedingMotorLeft;
    public CANSparkMax feedingMotorRight;
    public CANSparkMax flywheelMotorTop;
    public CANSparkMax flywheelMotorBottom;
    public RelativeEncoder shooterEncoderLeft;
    public RelativeEncoder shooterEncoderRight;

    public double leftTrig, rightTrig;
    public boolean leftShoot, rightShoot, timeToShoot; 
    public double speakerTargetSpeed = -3300;
    public double ampTargetSpeed = -1100;

    public Shooter(){
        feedingMotorLeft = new CANSparkMax(Setup.ShooterFeedingMotorLeftID, MotorType.kBrushless);
        feedingMotorRight = new CANSparkMax(Setup.ShooterFeedingMotorRightID, MotorType.kBrushless);
        flywheelMotorTop = new CANSparkMax(Setup.ShooterFlywheelMotorTopID, MotorType.kBrushless);
        flywheelMotorBottom = new CANSparkMax(Setup.ShooterFlywheelMotorBotttomID,MotorType.kBrushless);
        shooterEncoderLeft = flywheelMotorTop.getEncoder();
        shooterEncoderRight = flywheelMotorBottom.getEncoder();
    }

    public void speakerShoot(){

    }

    public void ampShoot(){

    }

    @Override
    public void updateSubsystem(){
        rightShoot = Setup.getInstance().getSecondaryAmpShoot(); 
        rightTrig = Setup.getInstance().getSecondaryAmp();

        leftShoot = Setup.getInstance().getSecondarySpeakerShoot(); 
        leftTrig = Setup.getInstance().getSecondarySpeaker();







    }

    @Override
    public void outputToSmartDashboard(){

    }

    @Override
    public void stop(){
        feedingMotorLeft.set(0);
        feedingMotorRight.set(0);
        flywheelMotorTop.set(0);
        flywheelMotorBottom.set(0);
    }
}