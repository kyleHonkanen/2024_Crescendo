package frc.robot.AutoCode;

// import javax.swing.RowFilter.Entry;
// import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
// import frc.robot.Setup;
import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.Pivot;
// import frc.robot.subsystems.Shooter;
// import frc.robot.util.drivers.NavX;
import frc.robot.subsystems.Limelight;
//import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableInstance;
// import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Pivot;


public class AutoAim {

    static AutoAim instance = new AutoAim();
      
    public static AutoAim getInstance() {
        if (instance == null) {
            instance = new AutoAim();
            

        }
        return instance;
    }

    Limelight limelight=Limelight.getInstance();
    public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    Drivetrain drivetrain = Drivetrain.getInstance();
    public double rotspeed;
    public double pivSpeed;
    public double pivAng;
    public double distance;


    public void autoOrientRed() {
        if (limelight.table.getEntry("tx").getDouble(0) <= -2) {
           rotspeed = Limelight.getInstance().table.getEntry("tx").getDouble(0) / -152;
           } else {
           if (limelight.table.getEntry("tx").getDouble(0) >= 2) {
           rotspeed = Limelight.getInstance().table.getEntry("tx").getDouble(0) / -152;
        }
    }
}
    public void autoOrientBlue() {
        if (limelight.table.getEntry("tx").getDouble(0) <= -2) {
            rotspeed = Limelight.getInstance().table.getEntry("tx").getDouble(0) / -152;
            } else {
            if (limelight.table.getEntry("tx").getDouble(0) >= 2) {
            rotspeed = Limelight.getInstance().table.getEntry("tx").getDouble(0) / -152;
            }
        }
    }

    public void autoAimBlue() {
        distance = (2.14 / Limelight.getInstance().getDistance(1.47, .52, 12.3));
        pivAng = Math.acos(distance);
        //pivAng = distance /10;
        if (Pivot.getInstance().getPivotPosition() >= pivAng) {
            pivSpeed = (Pivot.getInstance().getPivotPosition() - pivAng) / 300;
        } else if (Pivot.getInstance().getPivotPosition() <= pivAng) {
            pivSpeed = (Pivot.getInstance().getPivotPosition() - pivAng) / -300;
        }
    }
    public void autoAimRed() {
        if (limelight.table.getEntry("ty").getDouble(0) <= -4) {
            pivSpeed = Limelight.getInstance().table.getEntry("ty").getDouble(0) / -300;
        } else if (limelight.table.getEntry("ty").getDouble(0) >= -2) {
            pivSpeed = Limelight.getInstance().table.getEntry("ty").getDouble(0) / 300;
        }
    }
}
