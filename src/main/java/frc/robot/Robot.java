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
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.util.Utilities;
import frc.robot.util.drivers.NavX;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.GroundIntake;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.AutoCode.AutoAim;

public class Robot extends TimedRobot {
  boolean fieldOriented = false;
  double forward, strafe, rotation, flTurn, frTurn, blTurn, brTurn;
  boolean orient = true, toggle = false;
  CameraServer cServer;
  Climber climber;
  Pivot pivot;
  Shooter shooter;
  GroundIntake groundIntake;
  Drivetrain drivetrain;
  AutoAim autoaim;
  Setup setup;
  boolean buttpress = false;
  Limelight limelight;
  private static final String aaRed = "AutoAimRed";
  private static final String aaBlue = "AutoAimBlue";
  private static final String notMove = "noMove";
  private SendableChooser<String> chooser=new SendableChooser<>();
  private SendableChooser<String> options=new SendableChooser<>();
  String Option;
  String Chooser;

  Command autoCommand;
  RobotContainer robotContainer;


  public void updateSubsystemsA(){
    pivot.updateSubsystem();
    shooter.updateSubsystem();
    groundIntake.updateSubsystem();
    climber.stop();
    setup.getSecondaryJoystick().setRumble(RumbleType.kBothRumble, 0);
  }

  public void updateSubsystemsB(){
    climber.updateSubsystem();
    pivot.stop();
    groundIntake.stop();
    shooter.stop();
    setup.getSecondaryJoystick().setRumble(RumbleType.kBothRumble, .1);
  }

  public void stopAllSubsystems(){
    climber.stop();
    pivot.stop();
    shooter.stop();
    groundIntake.stop();
  }

  public void outputAllSmartDashboard(){
    SmartDashboard.putBoolean("ClimberMode TM", toggle);
    climber.outputToSmartDashboard();
    pivot.outputToSmartDashboard();
    shooter.outputToSmartDashboard();
    groundIntake.outputToSmartDashboard();
  }

  @Override
   public void robotInit(){
    climber = Climber.getInstance();
    pivot = Pivot.getInstance();
    shooter = Shooter.getInstance();
    groundIntake = GroundIntake.getInstance();
    setup = Setup.getInstance();
    chooser.setDefaultOption(notMove, "noMove");
    options.addOption(aaBlue, "autoAimBlue");
    options.addOption(aaRed, "autoAimRed");
    SmartDashboard.putData("Options", chooser);
    SmartDashboard.putData("Options", options);
    drivetrain = Drivetrain.getInstance();
    robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic(){
    //SmartDashboard.putString("Option =", Option);
    SmartDashboard.putNumber("Distance", AutoAim.getInstance().distance);
    SmartDashboard.putNumber("pivAng", AutoAim.getInstance().pivAng);
    SmartDashboard.putNumber("pivot Speed", AutoAim.getInstance().pivSpeed);
    // SparkMax SmartDashboard Output
    //SmartDashboard.putNumber("rotSpeed", AutoAim.getInstance().rotspeed);
    //SmartDashboard.putNumber("AngNum", Limelight.getInstance().table.getEntry("tx").getDouble(0));
    //SmartDashboard.putNumber("Chasis angle", Setup.instance.gyroscope.getAngle().toDegrees());
    //SmartDashboard.putNumber("X", NavX.getInstance().getX());
    //SmartDashboard.putNumber("Y", NavX.getInstance().getY());
    //SmartDashboard.putNumber("flMotAng", (Drivetrain.getInstance().flSens.getVoltage()*109.090909091));
    //SmartDashboard.putNumber("frMotAng", (Drivetrain.getInstance().frSens.getVoltage()*109.090909091));
    //SmartDashboard.putNumber("blMotAng", (Drivetrain.getInstance().blSens.getVoltage()*109.090909091));
    //SmartDashboard.putNumber("brMotAng", (Drivetrain.getInstance().brSens.getVoltage()*109.090909091));
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit(){
    Chooser = chooser.getSelected();
    fieldOriented=false;
    autoCommand = robotContainer.getAutonomousCommand();
    autoCommand.initialize();
    if (autoCommand != null) {
      autoCommand.schedule();
    }
    drivetrain.straightenA();
  }

  @Override
  public void autonomousPeriodic(){
    //fieldOriented = true;
  }

  @Override
  public void teleopInit(){
    Setup.getInstance();
    AutoAim.getInstance();
    Option = options.getSelected();
  }
  
  @Override
  public void teleopPeriodic(){

    // SmartDashboard.putString("AutoAim", Option);
    // switch (Option) {
    //     case aaBlue:
    //         //AutoAim.getInstance().autoOrientBlue();
    //         AutoAim.getInstance().autoAimBlue();
    //         Limelight.getInstance().setPipe(0);
    //         break;
    //     case aaRed:
    //         AutoAim.getInstance().autoOrientRed();
    //         AutoAim.getInstance().autoAimRed();
    //         Limelight.getInstance().setPipe(1);
    //         break;
    //     default:
    //         AutoAim.getInstance().autoAimBlue();
    //         Limelight.getInstance().setPipe(0);
    //         break;
            
    // }

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
      Drivetrain.getInstance().drive(new Translation2d(-.00000000000000000000000001, 0), 0, false, 0);
    } else {
      double speed = Drivetrain.getInstance().getSpeed(Drivetrain.getInstance().getSpeedSetting());

      forward = setup.getPrimaryY();
      forward = Math.copySign(Math.pow(forward, 2.0), forward);
      forward = Utilities.deadband(forward);

      strafe = setup.getPrimaryX();
      strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);

      rotation = setup.getPrimaryZ();
      
      //AutoAim.getInstance().autoOrientBlue();
      //rotspeed = Limelight.getInstance().table.getEntry("tx").getDouble(0);


    //   if (Setup.getInstance().getPrimaryJoystick().getRawButtonPressed(9)) {
    //     buttpress = true;
    // } else {
    //     if (Setup.getInstance().getPrimaryJoystick().getRawButtonReleased(9)) {
    //         buttpress = false;
    //     }
    // }
    // if (Setup.getInstance().getSecondaryJoystick().getRawButton(7) && Limelight.getInstance().Target()) {
        if (Option == aaBlue) {
            AutoAim.getInstance().autoOrientBlue();
            AutoAim.getInstance().autoAimBlue();
           Limelight.getInstance().setPipe(0);
        } else if (Option == aaRed) {
            AutoAim.getInstance().autoOrientRed();
            AutoAim.getInstance().autoAimRed();
            Limelight.getInstance().setPipe(1);
        } else {
            AutoAim.getInstance().autoOrientBlue();
            AutoAim.getInstance().autoAimBlue();
            Limelight.getInstance().setPipe(0);
        }
    //     Pivot.getInstance().pivotMotor.set(-AutoAim.getInstance().pivSpeed);
    //     rotation = -AutoAim.getInstance().rotspeed;
    //     //Pivot.getInstance().pivotMotor.set(0.1);
    // } else {
    //     //Pivot.getInstance().pivotMotor.set(0);
    // }


      if (Setup.getInstance().getPrimaryJoystick().getRawButton(9)) {
        speed = Drivetrain.getInstance().getSpeed(Drivetrain.getInstance().getSpeedSetting());
        Drivetrain.getInstance().speedSetting = "medium";
        Drivetrain.getInstance().drive(new Translation2d(-forward, -strafe), AutoAim.getInstance().rotspeed, fieldOriented, speed);
        Drivetrain.getInstance().periodic();
      } else {
      rotation = Utilities.deadband(rotation);
      rotation = Drivetrain.getInstance().getRotation(Drivetrain.getInstance().getSpeedSetting(), rotation);

      Drivetrain.getInstance().drive(new Translation2d(-forward, -strafe), -rotation, fieldOriented, speed);
      Drivetrain.getInstance().periodic();

      SmartDashboard.putBoolean("FieldOriented", fieldOriented);
      }
    }
  }
}