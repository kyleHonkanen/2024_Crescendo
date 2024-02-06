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
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.sendable.Sendable;
import frc.robot.util.Setup;
import frc.robot.util.Utilities;
import frc.robot.util.drivers.Mk2SwerveModuleBuilder;
import frc.robot.util.math.Vector2;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot {
  private static Setup oi;
  boolean FieldOrientedONS = false;
  double forward;
  double strafe;
  double rotation;  
  Setup setup;
  double flTurn;
  double frTurn;
  double blTurn;
  double brTurn;
  boolean orient;



  public static Setup getOi() {
    return oi;
  }

  public void updateAllSubsystems() {
  }

  public void stopAllSubsystems() {
  }

  public void outputAllSmartDashboard() {
  }

  //---------------------------------------------------------------------------------------------------------

  @Override
   public void robotInit() {
     oi = Setup.getInstance();
  //   setup=Setup.getInstance();

  }

  @Override
  public void robotPeriodic() {

      // SparkMax SmartDashboard Output
    SmartDashboard.putNumber("flMotAng", (DrivetrainSubsystem.getInstance().flSens.getVoltage()*109.090909091));
    SmartDashboard.putNumber("frMotAng", (DrivetrainSubsystem.getInstance().frSens.getVoltage()*109.090909091));
    SmartDashboard.putNumber("blMotAng", (DrivetrainSubsystem.getInstance().blSens.getVoltage()*109.090909091));
    SmartDashboard.putNumber("brMotAng", (DrivetrainSubsystem.getInstance().brSens.getVoltage()*109.090909091));

      // Roborio SmartDashboard output
    // SmartDashboard.putNumber("flMotAng", (DrivetrainSubsystem.getInstance().flSens.getVoltage()*72));
    // SmartDashboard.putNumber("frMotAng", (DrivetrainSubsystem.getInstance().frSens.getVoltage()*72));
    // SmartDashboard.putNumber("blMotAng", (DrivetrainSubsystem.getInstance().blSens.getVoltage()*72));
    // SmartDashboard.putNumber("brMotAng", (DrivetrainSubsystem.getInstance().brSens.getVoltage()*72));

  }

  @Override
  public void autonomousInit() {
  //m_autoSelected = m_chooser.getSelected();
  //Setup.getInstance();
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    Setup.getInstance();
  }
  
  @Override
  public void teleopPeriodic() {
    updateAllSubsystems();
    outputAllSmartDashboard();

      if (Setup.getInstance().getPrimaryJoystick().getRawButton(15)) {
    DrivetrainSubsystem.getInstance().drive(new Translation2d(.00000000000000000000000001, 0), 0, false, 0);
      } else {
    double speed = DrivetrainSubsystem.getInstance().getSpeed(DrivetrainSubsystem.getInstance().getSpeedSetting());
    boolean fieldoriented = FieldOrientedONS;

    forward = Robot.getOi().getPrimaryJoystick().getRawAxis(1);
    forward = Math.copySign(Math.pow(forward, 2.0), forward);
    forward = Utilities.deadband(forward);

    strafe = Robot.getOi().getPrimaryJoystick().getRawAxis(0);
    strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);

    rotation = Robot.getOi().getPrimaryJoystick().getRawAxis(6);
    rotation = Utilities.deadband(rotation);
    rotation = DrivetrainSubsystem.getInstance().getRotation(DrivetrainSubsystem.getInstance().getSpeedSetting(), rotation);

    DrivetrainSubsystem.getInstance().drive(new Translation2d(-forward, -strafe), -rotation, false, speed);
    DrivetrainSubsystem.getInstance().periodic();

    SmartDashboard.putBoolean("FieldOriented", fieldoriented);
    }
  }
}