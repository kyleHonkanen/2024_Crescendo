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
        shooter.speakerShoot();
        timer.restart();
        timer.stop();
    }
    @Override
    public void execute() {
         double target = 305;
        SmartDashboard.putNumber("speed", shooter.getTopShooterEncoder());
        SmartDashboard.putNumber("angle", pivot.getPivotPosition());
        
        double targetSpeed = ((pivot.speakerAngle1-pivot.getPivotPosition())/pivot.speakerAngle1);
        double max = target+20;
        double min = target-20;
        double Speed = 0;
        SmartDashboard.putNumber("min", min);
        
            if (pivot.getPivotPosition()>max) {
               Speed=-.1;
            } else if (pivot.getPivotPosition()<min) {
               Speed=.1;
            } else {
               Speed =((pivot.getPivotPosition()-min)/(max-min)*-2-1)*.1;
            }

         if((pivot.getPivotPosition()>target+5)||(pivot.getPivotPosition()<target-5)){
            pivot.getPivotMotor().set(Speed);
         } else {
            pivot.getPivotMotor().set(0);
            if (shooter.getTopShooterEncoder()<-4750&&shooter.getBottomShooterEncoder()<-4750) {
               pivot.getPivotMotor().set(0);
               shooter.feedForward(1);
               timer.start();
         }
         }
         SmartDashboard.putNumber("pivotspeed", Speed);
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
