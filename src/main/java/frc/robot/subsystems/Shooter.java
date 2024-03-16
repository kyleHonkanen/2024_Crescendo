package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
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
    public PIDController shooterController;

    public double leftTrig, rightTrig, PIDValue;
    public boolean leftShoot, rightShoot, timeToShoot; 
    public double speakerTargetSpeed = -3300;
    public double ampTargetSpeed = .5;
    public final double shooterP = 0.00013;
    public final double shooterI = 0.000004;
    public final double shooterD = 0;

    public Shooter(){
        feedingMotorLeft = new CANSparkMax(Setup.ShooterFeedingMotorLeftID, MotorType.kBrushless);
        feedingMotorRight = new CANSparkMax(Setup.ShooterFeedingMotorRightID, MotorType.kBrushless);
        flywheelMotorTop = new CANSparkMax(Setup.ShooterFlywheelMotorTopID, MotorType.kBrushless);
        flywheelMotorBottom = new CANSparkMax(Setup.ShooterFlywheelMotorBotttomID,MotorType.kBrushless);
        shooterController = new PIDController(shooterP, shooterI, shooterD);
        shooterEncoderLeft = flywheelMotorTop.getEncoder();
        shooterEncoderRight = flywheelMotorBottom.getEncoder();
        shooterController.setSetpoint(speakerTargetSpeed);
        shooterController.setTolerance(50, 20);
    }

    public double getLeftShooterEncoder(){
        return shooterEncoderLeft.getVelocity();
    }

    public double getRightShooterEncoder(){
        return shooterEncoderRight.getVelocity();
    }

    public void speakerShoot(){
        PIDValue = shooterController.calculate(getLeftShooterEncoder(),speakerTargetSpeed);
        flywheelMotorTop.set(PIDValue);
        flywheelMotorBottom.set(-PIDValue);
    }

    public void ampShoot(){
        flywheelMotorTop.set(ampTargetSpeed);
        flywheelMotorBottom.set(-ampTargetSpeed);
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
        SmartDashboard.putNumber("PIDValue", PIDValue);
    }

    @Override
    public void stop(){
        feedingMotorLeft.set(0);
        feedingMotorRight.set(0);
        flywheelMotorTop.set(0);
        flywheelMotorBottom.set(0);
    }
}