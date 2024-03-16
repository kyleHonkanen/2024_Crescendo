package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GroundIntake extends Subsystem {

    // get instance
    static GroundIntake mInstance = new GroundIntake();

    public static GroundIntake getInstance() {
        return mInstance;
    }

    //variables
    public CANSparkMax frontIntakeMotor;
    public CANSparkMax backIntakeMotor;
    public DigitalInput gamePieceSensor;

    public double intakeSpeed = 0.5; //placeholder value
    public double outtakeSpeed = -0.7; //placeholder value
    public boolean noteInShooter;

    public GroundIntake(){
        frontIntakeMotor = new CANSparkMax(Setup.IntakeMotorFrontID, MotorType.kBrushless);
        backIntakeMotor = new CANSparkMax(Setup.IntakeMotorBackID, MotorType.kBrushless);
        gamePieceSensor = new DigitalInput(Setup.GamePieceSensorID);
    }

    public void Intake(){
        frontIntakeMotor.set(intakeSpeed);
        backIntakeMotor.set(-intakeSpeed);
    }

    public void Outtake(){
        frontIntakeMotor.set(outtakeSpeed);
        backIntakeMotor.set(-outtakeSpeed);
    }

    public boolean getNoteInShooter(){
        return gamePieceSensor.get();
    }
    
    @Override
    public void updateSubsystem(){

        if (Setup.getPrimaryGroundIntake() && getNoteInShooter() == false){ // Intake
            frontIntakeMotor.set(intakeSpeed);
            backIntakeMotor.set(-intakeSpeed);
         } else if (Setup.getPrimaryOutake() && getNoteInShooter() == true){ // Outtake
            frontIntakeMotor.set(outtakeSpeed);
            backIntakeMotor.set(-outtakeSpeed);
         } else {
          frontIntakeMotor.set(0);
          backIntakeMotor.set(0);
         }
         
    }

    @Override
    public void outputToSmartDashboard() {
        
    }

    @Override
    public void stop(){
        frontIntakeMotor.set(0);
        backIntakeMotor.set(0);
    }
}