package frc.robot.util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAnalogSensor;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.util.drivers.Gyroscope;
import frc.robot.util.drivers.NavX;
import edu.wpi.first.wpilibj.Joystick;




public class Setup {

  public static Setup instance = new Setup();

  public static Setup getInstance() {
    if (instance == null) {
      instance = new Setup();
    }
	  return instance;
  }   
  //---------------------------------------------------------------SwerveDrive---------------------------------------------------------------//

  //measurments of robot in meters from center of wheel (19.25 inches squared, 39.37 inches in a meter)
  public double TRACKWIDTH = 0.635;
  public double WHEELBASE = 0.489;
  

  
  //offset of wheels sets the angle to start - CHANGE DIS BRO
  public double FRONT_LEFT_ANGLE_OFFSET = -Math.toRadians(0);
  public double FRONT_RIGHT_ANGLE_OFFSET = -Math.toRadians(0);
  public double BACK_LEFT_ANGLE_OFFSET = -Math.toRadians(0);
  public double BACK_RIGHT_ANGLE_OFFSET = -Math.toRadians(180);

  //finds position of the wheels based on the position of the center
  public final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(

    new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
    new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),        
    new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
    new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0)
  );

  //---------------------------------------------------------------Controls---------------------------------------------------------------//

  //flight stick ~Primary~
  private static Joystick primaryJoystick = new Joystick(0);

  public Joystick getPrimaryJoystick() {
    return primaryJoystick;
  }

  public double getPrimaryX(){
    return primaryJoystick.getRawAxis(0);
  }

  public double getPrimaryY(){
    return primaryJoystick.getRawAxis(1);
  }

  public double getPrimaryZ(){
    return primaryJoystick.getRawAxis(6);
  }

  public boolean getPrimaryDriverXButton(){
    return primaryJoystick.getRawButton(5);
  }
  public boolean getPrimaryDriverAButton(){
    return primaryJoystick.getRawButton(6);
  }
  public boolean getPrimaryDriverBButton(){
    return primaryJoystick.getRawButton(7);
  }
  public boolean getPrimaryDriverYButton(){
    return primaryJoystick.getRawButton(8);
  }
  public boolean getFieldOrientedOn(){
    return primaryJoystick.getRawButton(1);
  }
  public boolean getFieldOrientedOff(){
    return primaryJoystick.getRawButton(4);
  }

  //Xbox Controller ~Secondary~
  private static Joystick secondaryJoystick = new Joystick(1);

  public Joystick getSecondaryJoystick() {
    return secondaryJoystick;
  }

  public double getSecondaryDriverRightTrigger() {
    return secondaryJoystick.getRawAxis(3);
  }

  public double getSecondaryDriverLeftTrigger() {
    return secondaryJoystick.getRawAxis(2);
  }

  public boolean getSecondaryDriverRightBumper() {
    return secondaryJoystick.getRawButton(6);
  }

  public boolean getSecondaryDriverLeftBumper() {
    return secondaryJoystick.getRawButton(5);
  }

  public double getSecondaryDriverLeftXAxis() {
    return secondaryJoystick.getRawAxis(0);
  }

  public double getSecondaryDriverRightYAxis() {
    return secondaryJoystick.getRawAxis(5);
  }

  public boolean getSecondaryDriverAButton() {
    return secondaryJoystick.getRawButton(1);
  }

  public boolean getSecondaryDriverBButton(){
    return secondaryJoystick.getRawButton(2);
  }

  public boolean getSecondaryDriverXButton() {
    return secondaryJoystick.getRawButton(3);
  }

  public boolean getSecondaryDriverYButton() {
    return secondaryJoystick.getRawButton(4);
  }

  public boolean getSecondaryDriverRightStickPressed(){
    return secondaryJoystick.getRawButton(10);
  }

  public int getSecondaryDriverPOV() {
    return secondaryJoystick.getPOV(0);
  }

  //---------------------------------------------------------------Hardware Map---------------------------------------------------------------//

  //Gyroscope
  public final Gyroscope gyroscope = NavX.getInstance();

  //----------------------------------------------------------------Constants-----------------------------------------------------------------//

  //Drive Train
  public static final int DrivetrainSubsystem_FRONT_RIGHT_ANGLE_MOTOR = 17; // CAN
  //public static final int DrivetrainSubsystem_FRONT_RIGHT_ANGLE_ENCODER = 31; // Analog Encoder
  public static final int DrivetrainSubsystem_FRONT_RIGHT_DRIVE_MOTOR = 16; // CAN

  public static final int DrivetrainSubsystem_FRONT_LEFT_ANGLE_MOTOR = 11; // CAN
  //public static final int DrivetrainSubsystem_FRONT_LEFT_ANGLE_ENCODER = 32; // Analog Encoder
  public static final int DrivetrainSubsystem_FRONT_LEFT_DRIVE_MOTOR = 10; // CAN

  public static final int DrivetrainSubsystem_BACK_RIGHT_ANGLE_MOTOR = 15; // CAN
  //public static final int DrivetrainSubsystem_BACK_RIGHT_ANGLE_ENCODER = 33; // Analog Encoder
  public static final int DrivetrainSubsystem_BACK_RIGHT_DRIVE_MOTOR = 14; // CAN

  public static final int DrivetrainSubsystem_BACK_LEFT_ANGLE_MOTOR = 13; // CAN
  //public static final int DrivetrainSubsystem_BACK_LEFT_ANGLE_ENCODER = 34; // Analog Encoder
  public static final int DrivetrainSubsystem_BACK_LEFT_DRIVE_MOTOR = 12; // CAN


/*   
  //Pivot
  public static int PivotMotorID = 13;

  //Telescope
  public static int TelescopeMotorID = 14;
  public static int ArmMaxID = 0;
  public static int ArmMinID = 1;

  //Wrist
  public static int WristMotorID = 15;
  public static int wristRightMaxID = 2;
  public static int wristLeftMaxID = 3;

  //claw
  public static int ClawMotorID = 16;
  public static int gamePieceSensorID = 4;
  */
}