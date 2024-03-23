package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class stopTheBot extends Command{
    private final Drivetrain drivetrain;

    public stopTheBot() {
        drivetrain=Drivetrain.getInstance();
    }
    @Override
    public void initialize() {
        drivetrain.drive(new Translation2d(0,0), 0, false, 0);
    }
}
