package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
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
        if (pivot.getPivotPosition()>pivot.speakerAngle1+5) {
            pivot.getPivotMotor().set(-.2);
        } else if (pivot.getPivotPosition()<pivot.speakerAngle1-5) {
            pivot.getPivotMotor().set(.2);
        } else if((shooter.getTopShooterEncoder()<-4000)&&(pivot.getPivotPosition()>pivot.speakerAngle1+5)&&(pivot.getPivotPosition()<pivot.speakerAngle1-5)) {
            timer.start();
            shooter.feedForward(1);
            //shoot the thing
        }
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
