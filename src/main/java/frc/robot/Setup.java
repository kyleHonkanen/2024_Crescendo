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
        return primaryJoystick.getRawAxis(5);
    }


    //Xbox Controller (Secondary)
    private static Joystick secondaryJoystick = new Joystick(1);


    //Pivot
    public Joystick getSecondaryJoystick() {
        return secondaryJoystick;
    }

    public boolean getSecondaryAButton(){
        return secondaryJoystick.getRawButton(1);
    }

    public boolean getSecondaryBButton(){
        return secondaryJoystick.getRawButton(2);
    }

    public boolean getSecondaryXButton(){
        return secondaryJoystick.getRawButton(3);
    }

    public boolean getSecondaryYButton(){
        return secondaryJoystick.getRawButton(4);
    }

    public double getSecondaryManualPivot(){
        return secondaryJoystick.getRawAxis(5);
    }


    //Telescope
    public double getSecondaryTelescope(){
        return secondaryJoystick.getRawAxis(1);
    }


    //Shooter
    public double getSecondarySpeaker(){
        return secondaryJoystick.getRawAxis(2);
    }

    public boolean getSecondarySpeakerShoot(){
        return secondaryJoystick.getRawButton(5);
    }
    
    public double getSecondaryAmp(){
        return secondaryJoystick.getRawAxis(3);
    }

    public boolean getSecondaryAmpShoot(){
        return secondaryJoystick.getRawButton(6);
    }


    //Climber
    public boolean getSecondaryToggleClimberMode(){
        return secondaryJoystick.getRawButton(8);
    }

    public double getSecondaryRightClimber(){
        return secondaryJoystick.getRawAxis(5);
    }

    public double getSecondaryLeftClimber(){
        return secondaryJoystick.getRawAxis(1);
    }

    public boolean getSecondaryRightClimberButton(){
        return secondaryJoystick.getRawButton(10);
    }

    public boolean getSecondaryLeftClimberButton(){
        return secondaryJoystick.getRawButton(9);
    }

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