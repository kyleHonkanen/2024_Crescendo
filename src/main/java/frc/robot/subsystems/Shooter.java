package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Setup;

public class Shooter extends Subsystem {
    CANSparkMax leftShooterMotor;
    CANSparkMax rightShooterMotor;
    RelativeEncoder leftShooterEncoder;
    RelativeEncoder rightShooterEncoder;
    public Solenoid Solenoid;
    public boolean pushSolenoid;
    public double leftTrig;
    public boolean leftPush;
    public boolean leftShoot;
    public double rightTrig;
    public boolean rightPush;
    public boolean rightShoot;
    public boolean succ;
    public boolean unSuccFast;
    public boolean unSuccSlow;
    public double ShooterFlywheelSpeed;
    public double leftSpeed = -ShooterFlywheelSpeed;
    public double rightSpeed = ShooterFlywheelSpeed;
    public double speakerSpeed = -100; //all 3 values are pretty much placeholders
    public double ampSpeed = -2;  // must be slower than speaker
    public double intakeSpeed = 1.5; // must be opposite of speaker speed
    public boolean timeToShoot;
    
    //get instance
    static Shooter mInstance = new Shooter();

    public static Shooter getInstance() {
        return mInstance;
    }

    public Shooter(){
        leftShooterMotor = new CANSparkMax(Setup.ClimberMotorLeftID, MotorType.kBrushless);
        leftShooterEncoder = leftShooterMotor.getEncoder();
        rightShooterMotor = new CANSparkMax(Setup.ClimberMotorRightID, MotorType.kBrushless);
        rightShooterEncoder = rightShooterMotor.getEncoder();
        Solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    }
    public boolean TimetoSpeakerShoot(){
        return rightShooterEncoder.getVelocity() == speakerSpeed;
    }
    public void SpeakerShoot() {
        if (unSuccFast){
            ShooterFlywheelSpeed = speakerSpeed;  
            
        }
    }
    public boolean TimetoAmpShoot(){
        return leftShooterEncoder.getVelocity() == ampSpeed;
    }
    public void AmpShoot() {
        if (unSuccSlow){
            ShooterFlywheelSpeed = ampSpeed;
        }
  
    }
  
    public void Intake() {
        if (succ){
            ShooterFlywheelSpeed = intakeSpeed; 
        }
    }

    @Override
    public void updateSubsystem(){
        leftPush = Setup.getInstance().getSecondaryAmpShoot(); 
        leftTrig = Setup.getInstance().getSecondaryAmp();
        leftShoot = leftTrig >0.7;
        rightPush = Setup.getInstance().getSecondarySpeakerShoot(); 
        rightTrig = Setup.getInstance().getSecondarySpeaker();
        rightShoot = rightTrig >0.7;
        succ = Setup.getInstance().getSecondaryTelescope()<-0.4;//along with telescoping?
        if(leftShoot){
          unSuccFast = true;
        }else{
          unSuccFast = false;
        }
        if(rightShoot){
          unSuccSlow = true;
        }else{
          unSuccSlow = false;
        }
        
        //Decide flywheel speeds
        if (succ){
            Intake();
        }else if (unSuccFast){
            SpeakerShoot();
        }else if (unSuccSlow){
            AmpShoot();
        }else{
            stop();
        }
        //piston
        if (leftPush||rightPush){
            pushSolenoid = true;
        }else{
            pushSolenoid = false;
        }
    
        //Set all speeds
        leftShooterMotor.set(ShooterFlywheelSpeed);
        rightShooterMotor.set(ShooterFlywheelSpeed);
        //Solenoid.set(pushSolenoid);
    }

    @Override
    public void outputToSmartDashboard(){
       
        SmartDashboard.putBoolean("time to shoot?", timeToShoot);

    }

    @Override
    public void stop(){
        ShooterFlywheelSpeed = 0;
        //Solenoid.set(false);
    }
}
