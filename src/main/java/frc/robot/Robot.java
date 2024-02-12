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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.util.Utilities;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Telescope;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Robot extends TimedRobot {
  boolean FieldOrientedONS = false;
  double forward, strafe, rotation, flTurn, frTurn, blTurn, brTurn;
  boolean orient, toggle;
  CameraServer cServer;
  Climber climber;
  Pivot pivot;
  Shooter shooter;
  Telescope telescope;
  Drivetrain drivetrain;
  Setup setup;



  public void updateSubsystemsA(){
    climber.updateSubsystem();
    pivot.updateSubsystem();
    shooter.updateSubsystem();
    telescope.updateSubsystem();
  }

  public void updateSubsystemsB() {
    climber.updateSubsystem();
  }

  public void stopAllSubsystems(){
    climber.stop();
    pivot.stop();
    shooter.stop();
    telescope.stop();
  }

  public void outputAllSmartDashboard(){
    SmartDashboard.putBoolean("ClimberMode TM", Setup.getInstance().getSecondaryToggleClimberMode());
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
  }

  @Override
  public void robotPeriodic() {
    // SparkMax SmartDashboard Output
    SmartDashboard.putNumber("flMotAng", (Drivetrain.getInstance().flSens.getVoltage()*109.090909091));
    SmartDashboard.putNumber("frMotAng", (Drivetrain.getInstance().frSens.getVoltage()*109.090909091));
    SmartDashboard.putNumber("blMotAng", (Drivetrain.getInstance().blSens.getVoltage()*109.090909091));
    SmartDashboard.putNumber("brMotAng", (Drivetrain.getInstance().brSens.getVoltage()*109.090909091));
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    Setup.getInstance();
    toggle=true;
  }
  
  @Override
  public void teleopPeriodic() {

    //TOGGLE
    if (toggle) {
      updateSubsystemsA();
      if (setup.getSecondaryToggleClimberMode()) {
        toggle=false;
      }
    } else {
      updateSubsystemsB();
      if (setup.getSecondaryToggleClimberMode()) {
        toggle=true;
      }
    }

    outputAllSmartDashboard();

    if (Setup.getInstance().getPrimaryJoystick().getRawButton(15)) {
      Drivetrain.getInstance().drive(new Translation2d(.00000000000000000000000001, 0), 0, false, 0);
    } else {
      double speed = Drivetrain.getInstance().getSpeed(Drivetrain.getInstance().getSpeedSetting());
      boolean fieldoriented = FieldOrientedONS;

      forward = setup.getPrimaryY();
      forward = Math.copySign(Math.pow(forward, 2.0), forward);
      forward = Utilities.deadband(forward);

      strafe = setup.getPrimaryX();
      strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);

      rotation = setup.getPrimaryZ();
      rotation = Utilities.deadband(rotation);
      rotation = Drivetrain.getInstance().getRotation(Drivetrain.getInstance().getSpeedSetting(), rotation);

      Drivetrain.getInstance().drive(new Translation2d(-forward, -strafe), -rotation, false, speed);
      Drivetrain.getInstance().periodic();

      SmartDashboard.putBoolean("FieldOriented", fieldoriented);
    }
  }
}