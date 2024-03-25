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
    private final GroundIntake groundIntake;
    private final Shooter shooter;
    private final Pivot pivot;
    private Timer m_timer;
    public double IntakeSpeedConstant = 0.5;//placeholder value
    public double frontSpeed = -IntakeSpeedConstant;
    public double backSpeed = IntakeSpeedConstant;
    public boolean end = false;
    public double feedSpeed = 0.3;
    public double margin = 5;
    public double IDEALPOS = 320;

    public groundIntake() {
        groundIntake = GroundIntake.getInstance();
        shooter = Shooter.getInstance();
        pivot=Pivot.getInstance();
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
        if(pivot.getPivotPosition()< IDEALPOS-margin){
            pivot.getPivotMotor().set(0.1);
        }else if (pivot.getPivotPosition()> margin+IDEALPOS){
            pivot.getPivotMotor().set(-0.1);
        }else{
            pivot.getPivotMotor().set(0);
            groundIntake.Intake();
            shooter.feedForward(feedSpeed);
            m_timer.start();
            if (GroundIntake.getInstance().getNoteInShooter()!=true){
                end = true;
            }else{
                end=false;
            }
            end(end);

        }
        

    }
    @Override
    public void end(boolean interrupted) {
        if (interrupted){
            groundIntake.stop();
            SmartDashboard.putBoolean("Auto Intake active?", !isFinished());

        }
    }
    @Override
    public boolean isFinished() {
        end = false;
        return m_timer.get()>4;
    }

}
