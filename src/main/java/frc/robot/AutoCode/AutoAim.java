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

    Limelight limelight=Limelight.getInstance();
    public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    Drivetrain drivetrain = Drivetrain.getInstance();
    public boolean aab = false;
    public boolean aar = false;
    public double rotspeed;
    boolean buttpress;

    public void autoOrientRed() {
        if (Setup.getInstance().getPrimaryJoystick().getRawButton(9)) {
            limelight.setPipe(1);
            aar = true;
            if (limelight.table.getEntry("tx").getDouble(0) <= -5) {
                drivetrain.drive(new Translation2d(0, 0), limelight.table.getEntry("tx").getDouble(0) / -52, true, .05);
                drivetrain.periodic();
            } else if (limelight.table.getEntry("tx").getDouble(0) >= 5){
                
                drivetrain.drive(new Translation2d(0, 0), limelight.table.getEntry("tx").getDouble(0) / 52, true, .05);
                drivetrain.periodic();
                
            }
    }
}
    public double Rotspeed() {
        return Limelight.getInstance().table.getEntry("tx").getDouble(0) / -52;
    }

    public void autoOrientBlue() {

        //limelight.setPipe(0);
        if (limelight.table.getEntry("tx").getDouble(0) <= -2) {
             //drivetrain.drive(new Translation2d(0, 0), limelight.table.getEntry("tx").getDouble(0) / -52, true, .05);
             //drivetrain.periodic();
            //  rotspeed = limelight.table.getEntry("tx").getDouble(rotspeed) / -52;
            rotspeed = Limelight.getInstance().table.getEntry("tx").getDouble(0) / -152;
            //rotspeed = 0.55;
            } else {
            if (limelight.table.getEntry("tx").getDouble(0) >= 2) {
                // drivetrain.drive(new Translation2d(0, 0), limelight.table.getEntry("tx").getDouble(0) / 52, true, .05);
                // drivetrain.periodic();
            //  rotspeed = limelight.table.getEntry("tx").getDouble(rotspeed) / 52;
            rotspeed = Limelight.getInstance().table.getEntry("tx").getDouble(0) / -152;
            //rotspeed = 0.55;
            }
        }
    }
}
