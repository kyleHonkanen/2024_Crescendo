package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.aimAndShoot;
import frc.robot.commands.stopTheBot;

public class RobotContainer {
    
    private final SendableChooser<Command> autoChooser;

    public RobotContainer(){
        NamedCommands.registerCommand("aimAndShoot", new aimAndShoot());
        NamedCommands.registerCommand("stop robot", new stopTheBot());
        
        configureBindings();
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto chosen", autoChooser);
    }

    private void configureBindings() {
        SmartDashboard.putData("Auto", new PathPlannerAuto("New Auto"));
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}

