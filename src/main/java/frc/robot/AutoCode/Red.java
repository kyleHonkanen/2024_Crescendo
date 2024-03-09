package frc.robot.AutoCode;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
//import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Setup;


// public class Red {

//     static Red instance = new Red();
      
//     public static Red getInstance() {
//       if (instance == null) {
//         instance = new Red();
//       }
//         return instance;
//     }

//     Drivetrain drivetrain = Drivetrain.getInstance();
//     Pivot pivot = Pivot.getInstance();
//     Shooter shooter = Shooter.getInstance();
//     Timer timer = new Timer();
//     //private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");


//     public int step = 0;
//     public int counter = 0;
//     public void Shootdrive() {
//         switch (step) {
//             case 0:
//             if (Pivot.getInstance().getPivotPosition() < 298){
//                 Pivot.getInstance().pivotMotor.set(0.15);
//             } else if(Pivot.getInstance().getPivotPosition() > 302){
//                 Pivot.getInstance().pivotMotor.set(-0.15);
//             } else {
//                 Pivot.getInstance().pivotMotor.set(0);
//                 step++;
//             }
//                 break;
//             case 1:
//             //if (Shooter.getInstance().leftShooterEncoder.getVelocity() < Shooter.getInstance().speakerTargetSpeed && Shooter.getInstance().rightShooterEncoder.getVelocity() < Shooter.getInstance().speakerTargetSpeed) {
//                 Shooter.getInstance().leftShooterMotor.set(-.6);
//                 Shooter.getInstance().rightShooterMotor.set(.6);
//            /* }*/if (Math.abs(Shooter.getInstance().rightShooterEncoder.getVelocity()) >= 3300) {
//                 Shooter.getInstance().solenoidFast.set(true);
//                 step++;
//             }
//                 break;
//             case 2:
//             if (counter < 60) {
//                 counter++;
//             } else {
//                 step++;
//             }
//                 break;
//             case 3:
//             if (counter <= 240){
//                 Shooter.getInstance().leftShooterMotor.set(0);
//                 Shooter.getInstance().rightShooterMotor.set(0);
//                 Shooter.getInstance().solenoidFast.set(false);
//                 drivetrain.drive(new Translation2d(.3, -.6), 0, true, 0);
//                 Drivetrain.getInstance().periodic();
//                 counter++;
//             } else {
//                 step++;
//             }
//                 break;
//             case 4:
//                 Drivetrain.getInstance().stop();
//                 Drivetrain.getInstance().periodic();
//                 break;
//         }
//     }

//     public void Shootdriveleft() {

//         switch (step) {
//             case 0:
//                 if (pivot.getPivotPosition() < 298){
//                     pivot.pivotMotor.set(0.15);
//                 } else if(pivot.getPivotPosition() > 302){
//                     pivot.pivotMotor.set(-0.15);
//                 } else {
//                     pivot.pivotMotor.set(0);
//                     step++;
//                 }
//                 break;
    
//             case 1:
            
//                 Shooter.getInstance().leftShooterMotor.set(-.6);
//                 Shooter.getInstance().rightShooterMotor.set(.6);
//                 if (Math.abs(Shooter.getInstance().rightShooterEncoder.getVelocity()) >= 3300) {
//                     Shooter.getInstance().solenoidFast.set(true);
//                     step++;
//                 }
//                 break;
    
//             case 2:
//                 if (counter < 60) {
//                     counter++;
//                 } else {
//                     step++;
//                 }
//                 break;
    
//             case 3:
//                 if (counter <= 240){
//                     shooter.leftShooterMotor.set(0);
//                     shooter.rightShooterMotor.set(0);
//                     shooter.solenoidFast.set(false);
//                     drivetrain.drive(new Translation2d(-.3, .3), 0, true, .2);
//                     drivetrain.periodic();
//                     counter++;
//                 } else {
//                     step++;
//                 }
//                 break;
    
//             case 4:
//                 drivetrain.stop();
//                 drivetrain.periodic();
//                 break;
//         }
//         }
        
    
//         public void Shootdriveright() {
    
//             switch (step) {
//                 case 0:
//                     if (pivot.getPivotPosition() < 298){
//                         pivot.pivotMotor.set(0.15);
//                     } else if(pivot.getPivotPosition() > 302){
//                         pivot.pivotMotor.set(-0.15);
//                     } else {
//                         pivot.pivotMotor.set(0);
//                         step++;
//                     }
//                     break;
    
//                 case 1:
                
//                     Shooter.getInstance().leftShooterMotor.set(-.6);
//                     Shooter.getInstance().rightShooterMotor.set(.6);
//                     if (Math.abs(Shooter.getInstance().rightShooterEncoder.getVelocity()) >= 3300) {
//                         Shooter.getInstance().solenoidFast.set(true);
//                         step++;
//                     }
//                     break;
    
//                 case 2:
//                     if (counter < 60) {
//                         counter++;
//                     } else {
//                         step++;
//                     }
//                     break;
    
//                 case 3:
//                     if (counter <= 240){
//                         shooter.leftShooterMotor.set(0);
//                         shooter.rightShooterMotor.set(0);
//                         shooter.solenoidFast.set(false);
//                         drivetrain.drive(new Translation2d(-.3, -.3), 0, true, .2);
//                         drivetrain.periodic();
//                         counter++;
//                     } else {
//                         step++;
//                     }
//                     break;
    
//                 case 4:
//                     drivetrain.stop();
//                     drivetrain.periodic();
//                     break;
//             }
//         }

//     public void Esc() {
//         if (counter < 260) {
//             Drivetrain.getInstance().drive(new Translation2d(.3, 0), 0, true, .2);
//             Drivetrain.getInstance().periodic();
//             counter++;
//         } else {
//             Drivetrain.getInstance().stop();
//             drivetrain.periodic();
//         }
//     }
















//     package frc.robot.AutoCode;

// import edu.wpi.first.math.geometry.Translation2d;
// import frc.robot.Setup;
// import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.Pivot;
// import frc.robot.subsystems.Shooter;
// import frc.robot.util.drivers.NavX;
// //import edu.wpi.first.networktables.NetworkTable;
// //import edu.wpi.first.networktables.NetworkTableInstance;
// import edu.wpi.first.wpilibj.Timer;

public class Red {

    static Red instance = new Red();
      
    public static Red getInstance() {
        if (instance == null) {
            instance = new Red();
        }
        return instance;
    }

    Drivetrain drivetrain = Drivetrain.getInstance();
    Pivot pivot = Pivot.getInstance();
    Shooter shooter = Shooter.getInstance();

    Timer timer = new Timer();
   // private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    public int step = 0;
    public int counter = 0;

    public void Shootdrive() {

        switch (step) {
            case 0:
                if (pivot.getPivotPosition() < 298){
                    pivot.pivotMotor.set(0.15);
                } else if(pivot.getPivotPosition() > 302){
                    pivot.pivotMotor.set(-0.15);
                } else {
                    pivot.pivotMotor.set(0);
                    step++;
                }
                break;

            case 1:
            
                Shooter.getInstance().leftShooterMotor.set(-.6);
                Shooter.getInstance().rightShooterMotor.set(.6);
                if (Math.abs(Shooter.getInstance().rightShooterEncoder.getVelocity()) >= 3300) {
                    Shooter.getInstance().solenoidFast.set(true);
                    step++;
                }
                break;

            case 2:
                if (counter < 60) {
                    counter++;
                } else {
                    step++;
                }
                break;

            case 3:
                if (counter <= 180){
                    shooter.leftShooterMotor.set(0);
                    shooter.rightShooterMotor.set(0);
                    shooter.solenoidFast.set(false);
                    drivetrain.drive(new Translation2d(-.4, 0), 0, false, -.6);
                    drivetrain.periodic();
                    counter++;
                } else {
                    step++;
                }
                break;

            case 4:
                drivetrain.stop();
                drivetrain.periodic();
                break;
        }
    }

    public void Shootdriveleft() {

    switch (step) {
        case 0:
            if (pivot.getPivotPosition() < 298){
                pivot.pivotMotor.set(0.15);
            } else if(pivot.getPivotPosition() > 302){
                pivot.pivotMotor.set(-0.15);
            } else {
                pivot.pivotMotor.set(0);
                step++;
            }
            break;

        case 1:
        
            Shooter.getInstance().leftShooterMotor.set(-.6);
            Shooter.getInstance().rightShooterMotor.set(.6);
            if (Math.abs(Shooter.getInstance().rightShooterEncoder.getVelocity()) >= 3300) {
                Shooter.getInstance().solenoidFast.set(true);
                step++;
            }
            break;

        case 2:
            if (counter < 60) {
                drivetrain.stop();
                drivetrain.periodic();
                counter++;
            } else {
                step++;
            }
            break;

        case 3:
            if (counter <= 180){
                shooter.leftShooterMotor.set(0);
                shooter.rightShooterMotor.set(0);
                shooter.solenoidFast.set(false);
                drivetrain.drive(new Translation2d(-1, -3), 0, false, .4);
                drivetrain.periodic();
                counter++;
            } else {
                drivetrain.stop();
                drivetrain.periodic();
                step++;
            }
            break;

        case 4:
            if (counter < 220) {
                drivetrain.drive(new Translation2d(-.2, 0), 0, false, .1);
                drivetrain.periodic();
                counter++;
            } else {
            drivetrain.stop();
            step++;
            }
            break;
    }
    }
    

    public void Shootdriveright() {

        switch (step) {
            case 0:
                if (pivot.getPivotPosition() < 298){
                    pivot.pivotMotor.set(0.15);
                } else if(pivot.getPivotPosition() > 302){
                    pivot.pivotMotor.set(-0.15);
                } else {
                    pivot.pivotMotor.set(0);
                    step++;
                }
                break;

            case 1:
            
                Shooter.getInstance().leftShooterMotor.set(-.6);
                Shooter.getInstance().rightShooterMotor.set(.6);
                if (Math.abs(Shooter.getInstance().rightShooterEncoder.getVelocity()) >= 3300) {
                    Shooter.getInstance().solenoidFast.set(true);
                    step++;
                }
                break;

            case 2:
                if (counter < 60) {
                    drivetrain.stop();
                    drivetrain.periodic();
                    counter++;
                } else {
                    step++;
                }
                break;

            case 3:
                if (counter <= 170){
                    shooter.leftShooterMotor.set(0);
                    shooter.rightShooterMotor.set(0);
                    shooter.solenoidFast.set(false);
                    drivetrain.drive(new Translation2d(-.3, .6), 0, false, -.1);
                    drivetrain.periodic();
                    counter++;
                } else {
                    step++;
                }
                break;
            case 4:
            if (counter < 200) {
                drivetrain.drive(new Translation2d(-.4, 0), 0, false, -.5);
                drivetrain.periodic();
                counter++;
            } else {
                step++;
            }
                break;
            case 5:
                if (Setup.instance.gyroscope.getAngle().toDegrees() <= 180) {
                    Drivetrain.getInstance().drive(new Translation2d(0, 0), .2, false, .5);
                    drivetrain.periodic();
                } else {
                    drivetrain.stop();
                    step++;
                }
                    break;
            case 6:
                drivetrain.stop();
                drivetrain.periodic();
                break;
        }
    }

    public void Esc() {
        if (counter < 150) {
            drivetrain.drive(new Translation2d(-.3, 0), 0, false, -.6);
            drivetrain.periodic();
            counter++;
        } else {
            drivetrain.stop();
            drivetrain.periodic();
        }
    }
}