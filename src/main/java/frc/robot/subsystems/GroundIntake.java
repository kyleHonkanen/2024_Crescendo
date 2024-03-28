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

    public double outtakeSpeed = 0.7; //placeholder value
    public boolean intake, outtake;

    public GroundIntake(){
        frontIntakeMotor = new CANSparkMax(Setup.IntakeMotorFrontID, MotorType.kBrushed);
        backIntakeMotor = new CANSparkMax(Setup.IntakeMotorBackID, MotorType.kBrushed);
        gamePieceSensor = new DigitalInput(Setup.GamePieceSensorID);
    }

    public void Intake(double intakeSpeed){
        frontIntakeMotor.set(-intakeSpeed);
        backIntakeMotor.set(-intakeSpeed);
    }

    public void Outtake(){
        frontIntakeMotor.set(outtakeSpeed);
        backIntakeMotor.set(outtakeSpeed);
    }

    public boolean getNoteInShooter(){
        return gamePieceSensor.get();
    }
    
    @Override
    public void updateSubsystem(){
        intake = Setup.getInstance().getPrimaryGroundIntake();
        outtake = Setup.getInstance().getPrimaryOuttake();

        if (intake && getNoteInShooter() == true){ // Intake
            Intake(1);
         } else if (outtake){ // Outtake
            Outtake();
         } else {
            frontIntakeMotor.set(0);
            backIntakeMotor.set(0);
         }
    }

    @Override
    public void outputToSmartDashboard() {
        SmartDashboard.putBoolean("NoteInShooter", !getNoteInShooter());
    }

    @Override
    public void stop(){
        frontIntakeMotor.set(0);
        backIntakeMotor.set(0);
    }
}