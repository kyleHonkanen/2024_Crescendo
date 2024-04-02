package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class startAuto extends Command{
   
    private final Drivetrain drivetrain;

    public startAuto() {
        drivetrain = Drivetrain.getInstance();
    }
    public boolean isFinished() {
        return true;
    }
    public void initialize() {
        /*drivetrain.frontLeftMotor = drivetrain.states[0].angle.getRadians();
        drivetrain.fronttRightMotor = drivetrain.states[1].angle.getRadians();
        drivetrain.backLeftMotor = drivetrain.states[2].angle.getRadians();
        drivetrain.backRightMotor = drivetrain.states[3].angle.getRadians();*/
        drivetrain.driveForAuto(new ChassisSpeeds(0, 0, 0));
        drivetrain.periodic();
    }
}
