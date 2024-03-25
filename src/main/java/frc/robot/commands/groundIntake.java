package frc.robot.commands;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.GroundIntake;

public class groundIntake extends Command{
    private final CANSparkMax m_bintake;
    private final CANSparkMax m_fintake;
    private Timer m_timer;
    public double IntakeSpeedConstant = 0.5;//placeholder value
    public double frontSpeed = -IntakeSpeedConstant;
    public double backSpeed = IntakeSpeedConstant;

    public groundIntake() {
        m_fintake= GroundIntake.getInstance().frontIntakeMotor;
        m_bintake = GroundIntake.getInstance().backIntakeMotor;
        m_timer=new Timer();
    }

    @Override
    public void initialize(){
        /*
        Attempt at setup so all commands can be seen in one tab
        data from: https://docs.wpilib.org/en/stable/docs/software/dashboards/shuffleboard/layouts-with-code/sending-data.html
        ShuffleboardTab autoCommandsTab = Shuffleboard.getTab("Auto Commands");
        Shuffleboard.getTab("Auto Commands").add("Intake", True).getEntry();
        Shuffleboard.getEntry("Intake")
        */
        SmartDashboard.putBoolean("Auto Intake active?", !isFinished());
    }
    @Override
    public void execute() {
        m_fintake.set(frontSpeed);
        m_bintake.set(backSpeed);
        m_timer.start();
    }
    @Override
    public void end(boolean interrupted) {
        m_fintake.set(0);
        m_bintake.set(0);
        SmartDashboard.putBoolean("Auto Intake active?", !isFinished());
    }
    @Override
    public boolean isFinished() {
        return m_timer.get()>4;
    }

}
