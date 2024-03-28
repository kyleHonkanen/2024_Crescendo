package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;

//look more into Command

public class aimAndShoot extends Command{
    private final Shooter shooter;
    private final Pivot pivot;
    private Timer timer;
    


    public aimAndShoot() {
        shooter=Shooter.getInstance();
        pivot=Pivot.getInstance();
        timer=new Timer();
    }
    @Override

    public void initialize() {
        //make flywheels start spinning and makes time
        shooter.flywheelMotorTop.set(shooter.speakerTargetSpeed);
        shooter.flywheelMotorBottom.set(shooter.speakerTargetSpeed);
        timer.restart();
        timer.stop();
    }
    @Override
    public void execute() {
        SmartDashboard.putNumber("speed", shooter.getTopShooterEncoder());
        SmartDashboard.putNumber("angle", pivot.getPivotPosition());
        double targetSpeed = ((pivot.speakerAngle1-pivot.getPivotPosition())/pivot.speakerAngle1);
        double max = pivot.speakerAngle1+20;
        double min = pivot.speakerAngle1-20;
        double Speed = 0;
        
            if (pivot.getPivotPosition()>max) {
               Speed=-1;
            } else if (pivot.getPivotPosition()<min) {
               Speed=1;
            } else {
               Speed =(pivot.getPivotPosition()-min)/(max-min)*-2-1;
            }

         if((pivot.getPivotPosition() > pivot.speakerAngle1+5)&&(pivot.getPivotPosition() < pivot.speakerAngle1-5)){
            pivot.getPivotMotor().set(Speed);
         } else if (shooter.getTopShooterEncoder()<-4750) {
            pivot.getPivotMotor().set(0);
            shooter.feedForward(1);
            timer.start();
         } else {
            pivot.getPivotMotor().set(0);
         }
         SmartDashboard.putNumber("speed", Speed);
    }
    @Override
    public void end(boolean interrupted) {
        shooter.flywheelMotorBottom.set(0);
        shooter.flywheelMotorTop.set(0);
        pivot.getPivotMotor().set(0);
        shooter.feedForward(0);
    }
    @Override
    public boolean isFinished() {
        return timer.get()>1;
    }
}
