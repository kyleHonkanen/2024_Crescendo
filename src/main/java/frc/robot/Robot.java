/* 

      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@           @@@@@@@@@@@@@          @@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
     @@@@@@@@@@@@@@@            @@@@@@@@@@@@          @@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@         @@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@         @@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@        @@@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@        @@@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@        @@@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@        @@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
    @@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       @@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
   @@@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       @@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
   @@@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       @@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
    @@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@      @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
         @@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@      @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@          
             @@@@@@@@@@@@@@@    @@@@@@@@@@@@@@      @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@              
            @@@@@@@@@@@@@@@     @@@@@@@@@@@@@@      @@@@@@@@@@@@@@     @@@@@@@@@@@@@@@             
           @@@@@@@@@@@@@@@@     @@@@@@@@@@@@@@@     @@@@@@@@@@@@@@      @@@@@@@@@@@@@@@            
           @@@@@@@@@@@@@@@      @@@@@@@@@@@@@@@     @@@@@@@@@@@@@@       @@@@@@@@@@@@@@@           
          @@@@@@@@@@@@@@@       @@@@@@@@@@@@@@@     @@@@@@@@@@@@@@       @@@@@@@@@@@@@@@@          
         @@@@@@@@@@@@@@@        @@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@        @@@@@@@@@@@@@@@          
        @@@@@@@@@@@@@@@@         @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@         @@@@@@@@@@@@@@@         
       @@@@@@@@@@@@@@@@          @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@        
      @@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@@       
     @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@             @@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@            @@@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@             @@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@             @@@@@@@@@@@@@@@@    
   @@@@@@@@@@@@@@@@              @@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@              @@@@@@@@@@@@@@@@    
  @@@@@@@@@@@@@@@@               @@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@               @@@@@@@@@@@@@@@@   
 @@@@@@@@@@@@@@@@                @@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@                @@@@@@@@@@@@@@@@  
                                                                                                   
*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.util.Utilities;
import frc.robot.util.drivers.NavX;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Telescope;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.AutoCode.AutoAim;
import frc.robot.AutoCode.Blue;
import frc.robot.AutoCode.Red;
// import edu.wpi.first.cscore.VideoSource;
// import edu.wpi.first.cameraserver.CameraServer;
// import edu.wpi.first.cscore.CvSink;
// import edu.wpi.first.cscore.CvSource;
// import edu.wpi.first.cscore.UsbCamera;
// import edu.wpi.first.wpilibj.TimedRobot;
// import org.opencv.core.Mat;
// import org.opencv.core.Point;
// import org.opencv.core.Scalar;
// import org.opencv.imgproc.Imgproc;

public class Robot extends TimedRobot {
  boolean fieldOriented = false;
  double forward, strafe, rotation, flTurn, frTurn, blTurn, brTurn;
  boolean orient = true, toggle = false;
  CameraServer cServer;
  Climber climber;
  Pivot pivot;
  Shooter shooter;
  Telescope telescope;
  Drivetrain drivetrain;
  Setup setup;
  private static final String aaRed = "AutoAimRed";
  private static final String aaBlue = "AutoAimBlue";
  private static final String notMove = "noMove";
  private static final String blueSE = "blueShootEsc";
  private static final String blueSER = "blueShootEscRight";
  private static final String blueSEL = "blueShootEscLeft";
  private static final String blueE = "blueEscape";
  private static final String redSER = "redShootEsc";
  private static final String redSEL = "redShootEscRight";
  private static final String redSE = "redShootEscLeft";
  private static final String redE = "redEscape";
  private SendableChooser<String> chooser=new SendableChooser<>();
  private SendableChooser<String> options=new SendableChooser<>();
  String Option;
  String Chooser;


  public void updateSubsystemsA(){
    pivot.updateSubsystem();
    shooter.updateSubsystem();
    telescope.updateSubsystem();
    climber.stop();
  }

  public void updateSubsystemsB() {
    climber.updateSubsystem();
    pivot.stop();
    telescope.stop();
    shooter.stop();
  }

  public void stopAllSubsystems(){
    climber.stop();
    pivot.stop();
    shooter.stop();
    telescope.stop();
  }

  public void outputAllSmartDashboard(){
    SmartDashboard.putBoolean("ClimberMode TM", toggle);
    climber.outputToSmartDashboard();
    pivot.outputToSmartDashboard();
    shooter.outputToSmartDashboard();
    telescope.outputToSmartDashboard();
  }

  @Override
   public void robotInit() {
    climber = Climber.getInstance();
    pivot = Pivot.getInstance();
    shooter = Shooter.getInstance();
    telescope = Telescope.getInstance();
    setup = Setup.getInstance();
    chooser.setDefaultOption(notMove, "noMove");
    options.addOption(aaBlue, "autoAimBlue");
    options.addOption(aaRed, "autoAimRed");
    chooser.addOption(blueSE, "blueShootEsc");
    chooser.addOption(blueSER, "blueShootEscRight");
    chooser.addOption(blueSEL, "blueShootEscLeft");
    chooser.addOption(blueE, "blueEscape");
    chooser.addOption(redSE, "redShootEsc");
    chooser.addOption(redSER, "redShootEscRight");
    chooser.addOption(redSEL, "redShootEscLeft");
    chooser.addOption(redE, "redEscape");
    SmartDashboard.putData("Options", chooser);
    SmartDashboard.putData("Options", options);
  }

  @Override
  public void robotPeriodic() {
    // SparkMax SmartDashboard Output
    SmartDashboard.putNumber("Chasis angle", Setup.instance.gyroscope.getAngle().toDegrees());
    SmartDashboard.putNumber("counter", Blue.getInstance().counter);
    SmartDashboard.putNumber("Step", Blue.getInstance().step);
    SmartDashboard.putNumber("X", NavX.getInstance().getX());
    SmartDashboard.putNumber("Y", NavX.getInstance().getY());
    SmartDashboard.putNumber("leftFlyWheelSpeed", Shooter.getInstance().leftShooterEncoder.getVelocity());
    SmartDashboard.putNumber("rightFlyWheelSpeed", Shooter.getInstance().rightShooterEncoder.getVelocity());
    SmartDashboard.putNumber("flMotAng", (Drivetrain.getInstance().flSens.getVoltage()*109.090909091));
    SmartDashboard.putNumber("frMotAng", (Drivetrain.getInstance().frSens.getVoltage()*109.090909091));
    SmartDashboard.putNumber("blMotAng", (Drivetrain.getInstance().blSens.getVoltage()*109.090909091));
    SmartDashboard.putNumber("brMotAng", (Drivetrain.getInstance().brSens.getVoltage()*109.090909091));
  }

  @Override
  public void autonomousInit() {
    Chooser = chooser.getSelected();
    Option = options.getSelected();
    Blue.getInstance().step = 0;
    Red.getInstance().step = 0;
  }

  @Override
  public void autonomousPeriodic() {
    fieldOriented = true;
        SmartDashboard.putString("Auto", Chooser);
switch (Chooser) {
    case blueSE:
    Blue.getInstance().Shootdrive();
        break;
case blueSER:
    Blue.getInstance().Shootdriveright();
    break;
case blueSEL:
    Blue.getInstance().Shootdriveleft();
    break;
case blueE:
    Blue.getInstance().Esc();
        break;
case redSE:
    Red.getInstance().Shootdrive();
        break;
    case redSER:
    Red.getInstance().Shootdriveright();
    break;
case redSEL:
    Red.getInstance().Shootdriveleft();
    break;
case redE:
    Red.getInstance().Esc();
            break;
}
switch (Option) {
    case aaBlue:
        AutoAim.getInstance().autoOrientBlue();
        break;
    case aaRed:
        AutoAim.getInstance().autoOrientRed();
        break;
}

  }

  @Override
  public void teleopInit() {
    Setup.getInstance();
  }
  
  @Override
  public void teleopPeriodic() {



    //TOGGLE
     if (toggle) {
       updateSubsystemsB();
       if (setup.getSecondaryToggleClimberMode()) {
         toggle=false;
       }
     } else if (!toggle) {
       updateSubsystemsA();
       if (setup.getSecondaryToggleClimberMode()) {
        toggle=true;
       }
     }

    //field oriented
     if (orient) {
       fieldOriented = true;
       if(setup.getFieldOriented()){
     orient = false;
       }
    } else if (!orient) {
       fieldOriented = false;
       if(setup.getFieldOriented()) {
         orient = true;
       }
     }


    outputAllSmartDashboard();

    if (Setup.getInstance().getPrimaryJoystick().getRawButton(15)) {
      Drivetrain.getInstance().drive(new Translation2d(.00000000000000000000000001, 0), 0, false, 0);
    } else {
      double speed = Drivetrain.getInstance().getSpeed(Drivetrain.getInstance().getSpeedSetting());

      forward = setup.getPrimaryY();
      forward = Math.copySign(Math.pow(forward, 2.0), forward);
      forward = Utilities.deadband(forward);

      strafe = setup.getPrimaryX();
      strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);

      rotation = setup.getPrimaryZ();
      rotation = Utilities.deadband(rotation);
      rotation = Drivetrain.getInstance().getRotation(Drivetrain.getInstance().getSpeedSetting(), rotation);

      Drivetrain.getInstance().drive(new Translation2d(-forward, -strafe), -rotation, fieldOriented, speed);
      Drivetrain.getInstance().periodic();

      SmartDashboard.putBoolean("FieldOriented", fieldOriented);
    }
  }
}