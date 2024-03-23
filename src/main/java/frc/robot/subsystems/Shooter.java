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
    public double speakerTargetSpeed = -5000;
    public double ampTargetSpeed = .4;
    public final double shooterP = 0.0002;
    public final double shooterI = 0.0001;
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
        flywheelMotorBottom.set(PIDValue);
    }

    public void ampShoot(){
        flywheelMotorTop.set(-ampTargetSpeed);
        flywheelMotorBottom.set(-ampTargetSpeed);
    }

    public void feedForward(double speed){
        feedingMotorLeft.set(speed);
        feedingMotorRight.set(-speed);
    }

    public void feedBackward(double speed){
        feedingMotorLeft.set(-speed);
        feedingMotorRight.set(speed);
    }

    @Override
    public void updateSubsystem(){
        rightShoot = Setup.getInstance().getSecondarySpeakerShoot(); 
        rightTrig = Setup.getInstance().getSecondarySpeaker();

        leftShoot = Setup.getInstance().getSecondaryAmpShoot(); 
        leftTrig = Setup.getInstance().getSecondaryAmp();

        if (rightTrig >= 0.7){ // flywheel control
            speakerShoot();
        } else if (leftTrig >= 0.7) {
            ampShoot();
        } else {
            flywheelMotorTop.set(0);
            flywheelMotorBottom.set(0);
        }

       
        if (Setup.getInstance().getPrimaryGroundIntake() && GroundIntake.getInstance().getNoteInShooter() == true){ // Intake
            feedForward(GroundIntake.getInstance().intakeSpeed);
        } else if (Setup.getInstance().getPrimaryOuttake()){ // Outtake
            feedBackward(GroundIntake.getInstance().outtakeSpeed);
        } else if(rightShoot){
            feedForward(1);
        } else if(leftShoot){
            feedForward(.5);
        } else {
            feedingMotorLeft.set(0);
            feedingMotorRight.set(0);
        }
    
    }

    @Override
    public void outputToSmartDashboard(){
        SmartDashboard.putNumber("PIDValue", getLeftShooterEncoder());
    }

    @Override
    public void stop(){
        feedingMotorLeft.set(0);
        feedingMotorRight.set(0);
        flywheelMotorTop.set(0);
        flywheelMotorBottom.set(0);
    }
}