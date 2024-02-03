package frc.robot.subsystems;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;

public class Shooter extends Subsystem {
    public static XboxController controller;
    CANSparkMax leftShooterMotor;
    CANSparkMax rightShooterMotor;
    RelativeEncoder leftShooterEncoder;
    RelativeEncoder rightShooterEncoder;
    public Solenoid Solenoid;
    public boolean pushSolenoid;
    public boolean leftPush;
    public boolean rightPush;
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
        controller = new XboxController(0);
        leftShooterMotor = new CANSparkMax(22, MotorType.kBrushless);
        leftShooterEncoder = leftShooterMotor.getEncoder();
        rightShooterMotor = new CANSparkMax(23, MotorType.kBrushless);
        rightShooterEncoder = rightShooterMotor.getEncoder();
        Solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    }
    public void SpeakerShoot() {
        ShooterFlywheelSpeed = speakerSpeed;  
  
        if(rightShooterEncoder.getVelocity() == speakerSpeed){
            //tell smartdashboard its time
            SmartDashboard.putBoolean("time to shoot?", timeToShoot);
        }
  
    }
    public void AmpShoot() {
        ShooterFlywheelSpeed = ampSpeed;
        if(rightShooterEncoder.getVelocity() == ampSpeed){
            //tell smartdashboard its time
            SmartDashboard.putBoolean("time to shoot?", timeToShoot);
        } 
  
    }
  
    public void Intake() {
        ShooterFlywheelSpeed = intakeSpeed;  
    }

    @Override
    public void updateSubsystem(){
        leftPush = controller.getLeftBumperPressed(); 
        rightPush = controller.getRightBumperPressed(); 
        succ = controller.getLeftY()<-0.4;//along with telescoping?
        if(controller.getRightTriggerAxis()> 0.7){
          unSuccFast = true;
        }
        if (controller.getRightTriggerAxis()< 0.5){
          unSuccFast = false;
        }
        if(controller.getLeftTriggerAxis()> 0.7){
          unSuccSlow = true;
        }
        if(controller.getLeftTriggerAxis()< 0.5){
          unSuccSlow = false;
        }
        
        //Decide flywheel speeds
        if(succ){
            Intake();
        }else if (unSuccSlow){
            AmpShoot();
        }else if (unSuccFast){
            SpeakerShoot();
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
        leftShooterMotor.set(-ShooterFlywheelSpeed);
        rightShooterMotor.set(ShooterFlywheelSpeed);
        //Solenoid.set(pushSolenoid);
    }

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){
        ShooterFlywheelSpeed = 0;
        //Solenoid.set(false);
    }
}
