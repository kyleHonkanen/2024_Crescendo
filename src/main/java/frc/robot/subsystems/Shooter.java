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
    Solenoid Solenoid;

    //naming mutible variables (assigned later)
    public boolean pushSolenoid;
    public double leftTrig, rightTrig; // how much is each trigger held down
    public boolean leftPush, rightPush, leftShoot, rightShoot; //is a bumper pressed?, is a trigger pressed enough?
    public boolean succ, unSuccFast, unSuccSlow; // intake, speaker shoot , amp shoot
    public boolean timeToShoot;
    public double ShooterFlywheelSpeed;
    public double leftSpeed = -ShooterFlywheelSpeed;
    public double rightSpeed = ShooterFlywheelSpeed;

    //initializing constants
    public double speakerSpeedInput = -0.6; //all 3 values are pretty much placeholders
    public double speakerSpeedOutput = -3300;
    public double ampSpeedInput = -0.2;  // must be slower than speaker
    public double ampSpeedOutput = -1100;
    public double intakeSpeed = 0.07; // must be opposite of speaker speed

    public Shooter(){
        //initializes all physical pieces
        leftShooterMotor = new CANSparkMax(Setup.ClimberMotorLeftID, MotorType.kBrushless);
        leftShooterEncoder = leftShooterMotor.getEncoder();
        rightShooterMotor = new CANSparkMax(Setup.ClimberMotorRightID, MotorType.kBrushless);
        rightShooterEncoder = rightShooterMotor.getEncoder();
        Solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    }

    //tests if a flywheel is up to speed and the system is ready to shoot
    public boolean TimetoSpeakerShoot(){
        return rightShooterEncoder.getVelocity() <= speakerSpeedOutput;
    }

    //sets motor to correct speed
    public void SpeakerShoot(){
        if (unSuccFast) {
            ShooterFlywheelSpeed = speakerSpeedInput;   
        } 
        timeToShoot = TimetoSpeakerShoot();
    }

    //tests if a flywheel is up to speed and the system is ready to shoot
    public boolean TimetoAmpShoot(){
        return rightShooterEncoder.getVelocity() <= ampSpeedOutput;
    }

    //sets motor to correct speed
    public void AmpShoot(){
        if (unSuccSlow){
            ShooterFlywheelSpeed = ampSpeedInput;
        }
        timeToShoot = TimetoAmpShoot();
  
    }

    public void Intake(){
        if (succ) {
            //sets motor to correct speed
            ShooterFlywheelSpeed = intakeSpeed; 
        }
    }

    @Override
    public void updateSubsystem(){
        //Assigning functions to buttons on secondary contoller 
        // left and right shoot show if the trigger is pressed down enough
        leftPush = Setup.getInstance().getSecondaryAmpShoot(); 
        leftTrig = Setup.getInstance().getSecondaryAmp();
        leftShoot = leftTrig >0.7;
        rightPush = Setup.getInstance().getSecondarySpeakerShoot(); 
        rightTrig = Setup.getInstance().getSecondarySpeaker();
        rightShoot = rightTrig >0.7;
        succ = Setup.getInstance().getSecondaryTelescope()<-0.4;//along with telescoping?
        
        //if the correct button is pressed or released, conditionals will be changed for next section
        if (leftShoot) {
          unSuccFast = true;
        } else {
          unSuccFast = false;
        }
        if (rightShoot) {
          unSuccSlow = true;
        } else {
          unSuccSlow = false;
        }
        
        //Decide flywheel speeds and/or direction
        if (succ) {
            Intake();
        } else if (unSuccFast) {
            SpeakerShoot();
        } else if (unSuccSlow) {
            AmpShoot();
        } else {
            stop();
        }

        //piston
        if (leftPush||rightPush) {
            pushSolenoid = true;
        } else {
            pushSolenoid = false;
        }
    
        //Set all speeds
        leftShooterMotor.set(ShooterFlywheelSpeed);
        rightShooterMotor.set(ShooterFlywheelSpeed);
        Solenoid.set(pushSolenoid);
    }

    @Override
    public void outputToSmartDashboard(){
        SmartDashboard.putBoolean("time to shoot?", timeToShoot);
        SmartDashboard.putNumber("RPM", rightShooterEncoder.getVelocity());
    }

    @Override
    public void stop(){
        ShooterFlywheelSpeed = 0;
        Solenoid.set(false);
    }
}
