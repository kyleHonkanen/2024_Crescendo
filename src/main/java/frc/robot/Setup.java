package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Setup {
    
    public static Setup instance = new Setup();

    public static Setup getInstance() {
        if (instance == null) {
            instance = new Setup();
        }
	    return instance;
    }   

 //---------------------------------------------------------Swerve Drive------------------------------------------------------------------







 //----------------------------------------------------------Controls----------------------------------------------------------------------

    //Flight Stick (Primary)
    private static Joystick primaryJoystick = new Joystick(0);

    //Xbox Controller (Secondary)
    private static Joystick secondaryJoystick = new Joystick(1);

 //---------------------------------------------------------Hardware------------------------------------------------------------------------







 //-----------------------------------------------------------IDs------------------------------------------------------------------------------

    //Swerve Drive
    public static final int DrivetrainSubsystem_FRONT_LEFT_DRIVE_MOTOR = 10; 
    public static final int DrivetrainSubsystem_FRONT_LEFT_ANGLE_MOTOR = 11; 

    public static final int DrivetrainSubsystem_BACK_LEFT_DRIVE_MOTOR = 12; 
    public static final int DrivetrainSubsystem_BACK_LEFT_ANGLE_MOTOR = 13; 

    public static final int DrivetrainSubsystem_BACK_RIGHT_DRIVE_MOTOR = 14; 
    public static final int DrivetrainSubsystem_BACK_RIGHT_ANGLE_MOTOR = 15; 

    public static final int DrivetrainSubsystem_FRONT_RIGHT_DRIVE_MOTOR = 16; 
    public static final int DrivetrainSubsystem_FRONT_RIGHT_ANGLE_MOTOR = 17; 
  
    //Pivot
    public static final int PivotMotorID = 20;
    
    //telescope
    public static final int TelescopeMotorID = 21;
    public static final int TelescopeMaximumID = 0;
    public static final int TelescopeMinimumID = 1;

    //Shooter
    public static final int ShooterMotorLeftID = 22;
    public static final int ShooterMotorRightID = 23;
    public static final int ShooterSolenoidID = 0;

    //Climber
    public static final int ClimberMotorLeftID = 24;
    public static final int ClimberMotorRightID = 25;
    public static final int ClimberPotentiometerLeft = 2;
    public static final int ClimberPotentiometerRight = 3;

}