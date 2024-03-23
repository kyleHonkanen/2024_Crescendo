package frc.robot.subsystems;

//note to self: get everything checked to make sure it's correct
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight extends Subsystem {

    //get instance
    private static Limelight instance;
    
    public static Limelight getInstance() {
        if(instance == null) {
            instance = new Limelight();
        }
        return instance;
    }
    public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    
    //sees if the limelight sees any acceptable targets
    public boolean Target() {
        boolean Target;
        if(table.getEntry("tv").getDouble(0.0)==1) {
            Target = true;
        } else {
            Target = false;
        }
        return Target;
    }
/**
 * Distance - When given proper parameters, will find the distance from limelight to apriltag/ target
 * h1 - Represents height of Limelight off of ground
 * h2 - Represents height of target off of ground
 * a - Represents angle of Limelight relative to the ground
 * ty - Represents angle of center of Limelight to the target. This double is coming from the Limelight itself
 * *Note* Some of these parameters will need to be changed from year-to-year, such as h1,h2, and a1
 * Resources: https://learn.texastorque.org/Tutorials/Vendor/Limelight
 * @return
 */
    public double getDistance(double h1, double h2, double a) {
        return (h2-h1)/Math.tan(a+Limelight.getInstance().table.getEntry("ty").getDouble(0));
    }

    //changes the limelight pipeline
    public void setPipe(int pipeNum) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeNum);
    }
    @Override
    public void updateSubsystem(){}

    @Override
    public void outputToSmartDashboard(){
        SmartDashboard.putBoolean("Acceptable Target Seen?", Target());
        SmartDashboard.putNumber("LimelightX", table.getEntry("tx").getDouble(0));
        SmartDashboard.putNumber("LimelightY", table.getEntry("ty").getDouble(0));
        SmartDashboard.putNumber("LImelightarea", table.getEntry("ta").getDouble(0));
        Double PipeNum = SmartDashboard.getNumber("Pipeline Number", 1);
        table.getEntry("pipeline").setNumber(PipeNum);
    }

    @Override
    public void stop(){}
}
/* List of Important Values to get (limelight.getTable().getEntry(<put desired value here in quotes>).getDouble(0.0);)
 * "tv" is there a target
 * "tx" X offset of apriltag in degrees
 * "ty" Y offset of apriltag in degrees
 * "ta" area of apriltag on camera (finds the distance between the AprilTag and the LimeLight)
 * more can be found on the official website at https://docs.limelightvision.io/en/latest/networktables_api.html
 */