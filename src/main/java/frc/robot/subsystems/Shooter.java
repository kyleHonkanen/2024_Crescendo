package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Setup;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Shooter extends Subsystem {

    // get instance
    static Shooter mInstance = new Shooter();

    public static Shooter getInstance() {
        return mInstance;
    }

    // naming all physical pieces
    CANSparkMax leftShooterMotor;
    CANSparkMax rightShooterMotor;
    RelativeEncoder leftShooterEncoder;
    RelativeEncoder rightShooterEncoder;
    Solenoid solenoidSlow;
    Solenoid solenoidFast;

    //naming mutible variables (assigned later)
    public boolean pushSolenoidSlow, pushSolenoidFast;
    public double leftTrig, rightTrig; // how much is each trigger held down
    public boolean leftPush, rightPush, leftShoot, rightShoot, succ; //is a bumper pressed?, is a trigger pressed enough?
    public boolean timeToShoot; 
    public double ShooterFlywheelSpeed;
    public double leftSpeed = -ShooterFlywheelSpeed;
    public double rightSpeed = ShooterFlywheelSpeed;

    //initializing constants
    public double speakerSpeed = -0.6; //all 3 values are pretty much placeholders
    public double speakerTargetSpeed = -3300;
    public double ampSpeed = -0.2;  // must be slower than speaker
    public double ampTargetSpeed = -1100;
    public double intakeSpeed = 0.07; // must be opposite of speaker speed

    public Shooter(){
        //initializes all physical pieces
        leftShooterMotor = new CANSparkMax(Setup.ShooterMotorLeftID, MotorType.kBrushless);
        leftShooterEncoder = leftShooterMotor.getEncoder();
        rightShooterMotor = new CANSparkMax(Setup.ShooterMotorRightID, MotorType.kBrushless);
        rightShooterEncoder = rightShooterMotor.getEncoder();
        solenoidSlow = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
        solenoidFast = new Solenoid(PneumaticsModuleType.CTREPCM,1);
    }

    //tests if a flywheel is up to speed and the system is ready to shoot
    public boolean TimetoSpeakerShoot(){
        return rightShooterEncoder.getVelocity() <= speakerTargetSpeed;
    }

    //sets motor to correct speed
    public void SpeakerShoot(){
        ShooterFlywheelSpeed = speakerSpeed;   
        timeToShoot = TimetoSpeakerShoot();
    }

    //tests if a flywheel is up to speed and the system is ready to shoot
    public boolean TimetoAmpShoot(){
        return rightShooterEncoder.getVelocity() <= ampTargetSpeed;
    }

    //sets motor to correct speed
    public void AmpShoot(){
        ShooterFlywheelSpeed = ampSpeed;
        timeToShoot = TimetoAmpShoot();
    }

    public void Intake(){
        ShooterFlywheelSpeed = intakeSpeed; 
    }

    @Override
    public void updateSubsystem(){
        //amp
        rightPush = Setup.getInstance().getSecondaryAmpShoot(); 
        rightTrig = Setup.getInstance().getSecondaryAmp();
        rightShoot = rightTrig > 0.7;

        //speaker
        leftPush = Setup.getInstance().getSecondarySpeakerShoot(); 
        leftTrig = Setup.getInstance().getSecondarySpeaker();
        leftShoot = leftTrig > 0.7;
        
        succ = Setup.getInstance().getSecondaryTelescope()<-0.4;//along with telescoping?
        
        //Decide flywheel speeds and/or direction
        if (succ) {
             Intake();
        } else if (leftShoot) {
            SpeakerShoot();
        } else if (rightShoot) {
            AmpShoot();
        } else {
            stop();
        }

        //piston
        if (leftPush) {
            pushSolenoidFast = true;
        } else if(rightPush){
            pushSolenoidSlow = true;
        } else {
            pushSolenoidFast = false;
            pushSolenoidSlow = false;
        }
    
        //Set all speeds
        leftShooterMotor.set(ShooterFlywheelSpeed);
        rightShooterMotor.set(ShooterFlywheelSpeed);
        solenoidFast.set(pushSolenoidFast);
        solenoidSlow.set(pushSolenoidSlow);
    }

    @Override
    public void outputToSmartDashboard(){
        SmartDashboard.putBoolean("time to shoot?", timeToShoot);
        SmartDashboard.putNumber("RPM", rightShooterEncoder.getVelocity());
    }

    @Override
    public void stop(){
        ShooterFlywheelSpeed = 0;

        //removed for testing - prevents pneumatics from firing without flywheels moving
        //solenoidFast.set(false);
        //solenoidSlow.set(false);
    }
}