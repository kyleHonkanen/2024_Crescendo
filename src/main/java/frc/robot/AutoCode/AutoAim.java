package frc.robot.AutoCode;

import javax.swing.RowFilter.Entry;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Setup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
import frc.robot.util.drivers.NavX;
import frc.robot.subsystems.Limelight;
//import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;


public class AutoAim {

    static AutoAim instance = new AutoAim();
      
    public static AutoAim getInstance() {
        if (instance == null) {
            instance = new AutoAim();
        }
        return instance;
    }

    Limelight limelight;
    public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    Drivetrain drivetrain;

    public void autoOrientRed() {
        limelight.setPipe(1);
        if (Setup.getInstance().getPrimaryJoystick().getRawButtonPressed(9)) {
            if (limelight.table.getEntry("tx").getDouble(0) <= -.1) {
            drivetrain.drive(new Translation2d(0, 0), limelight.table.getEntry("tx").getDouble(0) * -1, true, .05);
            drivetrain.periodic();
        } else {
            if (limelight.table.getEntry("tx").getDouble(0) >= .1) {
                drivetrain.drive(new Translation2d(0, 0), limelight.table.getEntry("tx").getDouble(0) * 1, true, .05);
                drivetrain.periodic();
            }
        }
    }
}

    public void autoOrientBlue() {
        limelight.setPipe(0);
        if (Setup.getInstance().getPrimaryJoystick().getRawButtonPressed(9)) {
        if (limelight.table.getEntry("tx").getDouble(0) <= -.1) {
            drivetrain.drive(new Translation2d(0, 0), limelight.table.getEntry("tx").getDouble(0) * -1, true, .05);
            drivetrain.periodic();
        } else {
            if (limelight.table.getEntry("tx").getDouble(0) >= .1) {
                drivetrain.drive(new Translation2d(0, 0), limelight.table.getEntry("tx").getDouble(0) * 1, true, .05);
                drivetrain.periodic();
                }
            }
        }
    }
}
