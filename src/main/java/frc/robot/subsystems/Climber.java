package frc.robot.subsystems;

public class Climber extends Subsystem{
 
    //get instance
    static Climber mInstance = new Climber();

    public static Climber getInstance() {
        return mInstance;
    }

    public Climber(){}

    @Override
    public void updateSubsystem(){}

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){}
}
