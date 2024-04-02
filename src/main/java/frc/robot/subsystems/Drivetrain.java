package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.pathplanner.lib.auto.AutoBuilder;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.SparkAnalogSensor;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Setup;
import frc.robot.util.math.Vector2;
import frc.robot.util.Utilities;
import frc.robot.util.drivers.Mk2SwerveModuleBuilder;
import frc.robot.util.drivers.NavX;
import frc.robot.util.drivers.SwerveModule;

public class Drivetrain extends SubsystemBase{
        
           
        //get instance
        private static Drivetrain instance;

        public static Drivetrain getInstance() {
                if (instance == null) {
                        instance = new Drivetrain();
                }
                return instance;
        }

        //Establish motor variables
        private double frontLeftMotor = 0;
        private double fronttRightMotor = 0;
        private double backRightMotor = 0;
        private double backLeftMotor= 0;

        //Establish motors
        private CANSparkMax flMotAng;
        private CANSparkMax frMotAng;
        private CANSparkMax blMotAng;
        private CANSparkMax brMotAng;

        //Init Drive Motors
        private CANSparkMax flMotDri;
        private CANSparkMax frMotDri;
        private CANSparkMax blMotDri;
        private CANSparkMax brMotDri;

        //Init Sens
        public SparkAnalogSensor flSens;
        public SparkAnalogSensor frSens;
        public SparkAnalogSensor blSens;
        public SparkAnalogSensor brSens;

        //Establishes Swerve Module variables
        public final SwerveModule frontLeftModule;
        public final SwerveModule frontRightModule;
        public final SwerveModule backLeftModule;
        public final SwerveModule backRightModule;

        SwerveDriveOdometry odometry;

        //Establishes speed throttle
        public double speedChanger = 0;

        double speed = .15;
        public String speedSetting = "medium";


        public Drivetrain() {
                /*sets up the swerve modules for CANbus, sets a frame rate limit to prevent errors, establishes a center point for vector math, 
                *gets encoder position, makes motor objects for rotation and translation, the compiles into one object.*/

                         flMotAng = new CANSparkMax(Setup.DrivetrainSubsystem_FRONT_LEFT_ANGLE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
                         frMotAng = new CANSparkMax(Setup.DrivetrainSubsystem_FRONT_RIGHT_ANGLE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
                         blMotAng = new CANSparkMax(Setup.DrivetrainSubsystem_BACK_LEFT_ANGLE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
                         brMotAng = new CANSparkMax(Setup.DrivetrainSubsystem_BACK_RIGHT_ANGLE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);

                         flMotDri = new CANSparkMax(Setup.DrivetrainSubsystem_FRONT_LEFT_DRIVE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
                         frMotDri = new CANSparkMax(Setup.DrivetrainSubsystem_FRONT_RIGHT_DRIVE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
                         blMotDri = new CANSparkMax(Setup.DrivetrainSubsystem_BACK_LEFT_DRIVE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
                         brMotDri = new CANSparkMax(Setup.DrivetrainSubsystem_BACK_RIGHT_DRIVE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
                        
                         flSens = flMotAng.getAnalog(SparkAnalogSensor.Mode.kAbsolute);
                         frSens = frMotAng.getAnalog(SparkAnalogSensor.Mode.kAbsolute);
                         blSens = blMotAng.getAnalog(SparkAnalogSensor.Mode.kAbsolute);
                         brSens = brMotAng.getAnalog(SparkAnalogSensor.Mode.kAbsolute);

                        //Backup for Roborio
                        //brSens = new AnalogInput(2);
                        //blSens = new AnalogInput(3);
                        //flSens = new AnalogInput(0);
                        //frSens = new AnalogInput(1);


                //CANCoder frontLeftCANCoder = new CANCoder(Setup.DrivetrainSubsystem_FRONT_LEFT_ANGLE_ENCODER);
                //frontLeftCANCoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData,100,100);
                Mk2SwerveModuleBuilder frontLeftModuleBuilder = new Mk2SwerveModuleBuilder(new Vector2(Setup.instance.TRACKWIDTH / 2.0, Setup.instance.WHEELBASE / 2.0),new SwerveModulePosition(0.0, new Rotation2d(Setup.instance.FRONT_LEFT_ANGLE_OFFSET)));
                //frontLeftModuleBuilder.angleEncoder(-Math.toRadians(flSens.getVoltage()*72), Setup.instance.FRONT_LEFT_ANGLE_OFFSET);
                frontLeftModuleBuilder.angleEncoder(Math.toRadians(flSens.getVoltage()*109.090909091), Setup.instance.FRONT_LEFT_ANGLE_OFFSET);
                frontLeftModuleBuilder.angleMotor(flMotAng,Mk2SwerveModuleBuilder.MotorType.NEO);
                frontLeftModuleBuilder.driveMotor(flMotDri,Mk2SwerveModuleBuilder.MotorType.NEO);
                frontLeftModule = frontLeftModuleBuilder.build();


                //CANCoder frontRightCANCoder = new CANCoder(Setup.DrivetrainSubsystem_FRONT_RIGHT_ANGLE_ENCODER);
                //frontRightCANCoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData,100,100);
                Mk2SwerveModuleBuilder frontRightModuleBuilder = new Mk2SwerveModuleBuilder(new Vector2(Setup.instance.TRACKWIDTH / 2.0, -Setup.instance.WHEELBASE / 2.0),new SwerveModulePosition(0.0, new Rotation2d(Setup.instance.FRONT_RIGHT_ANGLE_OFFSET)));
                //frontRightModuleBuilder.angleEncoder(-Math.toRadians(frSens.getVoltage()*72), Setup.instance.FRONT_RIGHT_ANGLE_OFFSET);
                frontRightModuleBuilder.angleEncoder(Math.toRadians(frSens.getVoltage()*109.090909091), Setup.instance.FRONT_RIGHT_ANGLE_OFFSET);
                frontRightModuleBuilder.angleMotor(frMotAng,Mk2SwerveModuleBuilder.MotorType.NEO);
                frontRightModuleBuilder.driveMotor(frMotDri,Mk2SwerveModuleBuilder.MotorType.NEO);
                frontRightModule = frontRightModuleBuilder.build();

                //CANCoder backLeftCANCoder = new CANCoder(Setup.DrivetrainSubsystem_BACK_LEFT_ANGLE_ENCODER);
                //backLeftCANCoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData,100,100);
                Mk2SwerveModuleBuilder backLeftModuleBuilder = new Mk2SwerveModuleBuilder(new Vector2(-Setup.instance.TRACKWIDTH / 2.0, Setup.instance.WHEELBASE / 2.0),new SwerveModulePosition(0.0, new Rotation2d(Setup.instance.BACK_LEFT_ANGLE_OFFSET)));
                //backLeftModuleBuilder.angleEncoder(-Math.toRadians(blSens.getVoltage()*72), Setup.instance.BACK_LEFT_ANGLE_OFFSET);
                backLeftModuleBuilder.angleEncoder(Math.toRadians(blSens.getVoltage()*109.090909091), Setup.instance.BACK_LEFT_ANGLE_OFFSET);
                backLeftModuleBuilder.angleMotor(blMotAng,Mk2SwerveModuleBuilder.MotorType.NEO);
                backLeftModuleBuilder.driveMotor(blMotDri,Mk2SwerveModuleBuilder.MotorType.NEO);
                backLeftModule = backLeftModuleBuilder.build();


                //CANCoder backRightCANCoder = new CANCoder(Setup.DrivetrainSubsystem_BACK_RIGHT_ANGLE_ENCODER);
                //backRightCANCoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData,100,100);
                Mk2SwerveModuleBuilder backRightModuleBuilder = new Mk2SwerveModuleBuilder(new Vector2(-Setup.instance.TRACKWIDTH / 2.0, -Setup.instance.WHEELBASE / 2.0),new SwerveModulePosition(0.0, new Rotation2d(Setup.instance.BACK_RIGHT_ANGLE_OFFSET)));
                backRightModuleBuilder.angleEncoder(Math.toRadians(brSens.getVoltage()*109.090909091), Setup.instance.BACK_RIGHT_ANGLE_OFFSET);
                //backRightModuleBuilder.angleEncoder(-Math.toRadians(brSens.getVoltage()*72), Setup.instance.BACK_RIGHT_ANGLE_OFFSET);
                backRightModuleBuilder.angleMotor(brMotAng,Mk2SwerveModuleBuilder.MotorType.NEO);
                backRightModuleBuilder.driveMotor(brMotDri,Mk2SwerveModuleBuilder.MotorType.NEO);
                backRightModule = backRightModuleBuilder.build();

                Setup.instance.gyroscope.setInverted(true);                 

                odometry = new SwerveDriveOdometry(Setup.getInstance().kinematics, 
                NavX.getInstance().getRotation2d(), getPositions());

                AutoBuilder.configureHolonomic(
                        this::getPose,
                        this::resetPose,
                        this::getSpeeds,
                        this::driveForAuto, 
                        Setup.pathFollowConfig, 
                        ()-> {var alliance = DriverStation.getAlliance();
                                if (alliance.isPresent()) {
                                        //trying not for flipping
                                return alliance.get()==DriverStation.Alliance.Red;
                                }
                        return false;
                        }, 
                        this);
        } 

        //updates sensors and corresponding states periodically
        public void periodic(){

                frontLeftModule.updateSensors();
                frontRightModule.updateSensors();
                backLeftModule.updateSensors();
                backRightModule.updateSensors();

                frontLeftModule.updateState(TimedRobot.kDefaultPeriod);
                frontRightModule.updateState(TimedRobot.kDefaultPeriod);
                backLeftModule.updateState(TimedRobot.kDefaultPeriod);
                backRightModule.updateState(TimedRobot.kDefaultPeriod);

                odometry.update(NavX.getInstance().getRotation2d(), getPositions());
        }

        //Assigning x,y,z coordinates in m/s to the swerve modules and initializ ing them as 0
        ChassisSpeeds speeds;
        public SwerveModuleState[] states=Setup.instance.kinematics.toSwerveModuleStates(new ChassisSpeeds(0, 0, 0));

        //calculates where it's moving
        public void drive(Translation2d translation, double rotation, boolean fieldOriented, double throttle) {

                //calculates how the wheel spins, converts rotational speeds to inches
                rotation *= -12.0 /
                 Math.hypot(Setup.instance.WHEELBASE, Setup.instance.TRACKWIDTH);

                if(fieldOriented) {

                        //field oriented
                        speeds = ChassisSpeeds.fromFieldRelativeSpeeds(Math.signum(translation.getX()), (translation.getY()), (rotation), Rotation2d.fromDegrees(Setup.instance.gyroscope.getAngle().toDegrees()+180));
                     
                } else {
        
                        //robot oriented
                        speeds = new ChassisSpeeds(translation.getX(), translation.getY(), rotation);
                }

                //calculates swerve module states, results in an array of angles and speeds
                //states = Setup.instance.kinematics.toSwerveModuleStates(speeds,new Translation2d(Setup.instance.TRACKWIDTH/2,0));
                states = Setup.instance.kinematics.toSwerveModuleStates(speeds,new Translation2d(0,0));
               
                //makes it 0 to 1 instead of -1 to 1
                speedChanger = ((throttle-1)/-2);

                //makes speed grow exponentially 
                speedChanger = (speedChanger*speedChanger);

                //deadbanding these values is VERY important, otherwise it'll never spin
                //double x = Utilities.deadband(Setup.getInstance().getPrimaryX(),.2);
                //double y = Utilities.deadband(Setup.getInstance().getPrimaryY(),.2);
                //double z = Utilities.deadband(Setup.getInstance().getPrimaryZ(),.2);

                //sets the modules and motors target velocity and angle in accordance with the joystick
                //if(x==0 && y==0 && z!=0){
                //       frontLeftModule.setTargetVelocity(z*speedChanger, -.25 * Math.PI);
                //     frontRightModule.setTargetVelocity(z*speedChanger, .25 * Math.PI);
                //   backLeftModule.setTargetVelocity(z*speedChanger, .25 * Math.PI);
                // backRightModule.setTargetVelocity(-z*speedChanger, -.25 * Math.PI);
                // } else
                 
                if (speeds.omegaRadiansPerSecond!=0 || speeds.vxMetersPerSecond!=0 || speeds.vyMetersPerSecond!=0){
                        frontLeftModule.setTargetVelocity(states[0].speedMetersPerSecond*speedChanger, states[0].angle.getRadians());
                        frontRightModule.setTargetVelocity(states[1].speedMetersPerSecond*speedChanger, states[1].angle.getRadians());
                        backLeftModule.setTargetVelocity(states[2].speedMetersPerSecond*speedChanger, states[2].angle.getRadians());
                        backRightModule.setTargetVelocity(states[3].speedMetersPerSecond*speedChanger, states[3].angle.getRadians());

                        frontLeftMotor = states[0].angle.getRadians();
                        fronttRightMotor = states[1].angle.getRadians();
                        backLeftMotor = states[2].angle.getRadians();
                        backRightMotor = states[3].angle.getRadians();

                //if the joystick is not moving set velocity to 0        
                }else {
                        frontLeftModule.setTargetVelocity(0, frontLeftMotor);
                        frontRightModule.setTargetVelocity(0, fronttRightMotor);
                        backLeftModule.setTargetVelocity(0, backRightMotor);
                        backRightModule.setTargetVelocity(0, backLeftMotor);
                
        }
    }

        public void driveForAuto(ChassisSpeeds moveSpeeds) {
                double rotation = moveSpeeds.omegaRadiansPerSecond *=-12 /
                Math.hypot(Setup.instance.WHEELBASE, Setup.instance.TRACKWIDTH);
                
                speeds = new ChassisSpeeds(-moveSpeeds.vxMetersPerSecond,moveSpeeds.vyMetersPerSecond, rotation);
                
                //swerve module states are a list of angles and speeds converted from the variables used in chassis. speeds below calculates them
                states = Setup.instance.kinematics.toSwerveModuleStates(speeds, new Translation2d(Setup.instance.TRACKWIDTH/2,0));

                //deadbanding these values is VERY important, otherwise it'll never spin
                double x = Utilities.deadband(Setup.getInstance().getPrimaryX());
                double y = Utilities.deadband(Setup.getInstance().getPrimaryY());
                double z = Utilities.deadband(Setup.getInstance().getPrimaryZ());

                if(x==0 && y==0 && z!=0){
                        frontLeftModule.setTargetVelocity(z*speedChanger, -.25 * Math.PI);
                        frontRightModule.setTargetVelocity(z*speedChanger, .25 * Math.PI);
                        backLeftModule.setTargetVelocity(z*speedChanger, .25 * Math.PI);
                        backRightModule.setTargetVelocity(-z*speedChanger, -.25 * Math.PI);
                }
                if (speeds.omegaRadiansPerSecond!=0 || speeds.vxMetersPerSecond!=0 || speeds.vyMetersPerSecond!=0){
                        frontLeftModule.setTargetVelocity(states[0].speedMetersPerSecond*speedChanger, states[0].angle.getRadians());
                        frontRightModule.setTargetVelocity(states[1].speedMetersPerSecond*speedChanger, states[1].angle.getRadians());
                        backLeftModule.setTargetVelocity(states[2].speedMetersPerSecond*speedChanger, states[2].angle.getRadians());
                        backRightModule.setTargetVelocity(states[3].speedMetersPerSecond*speedChanger, states[3].angle.getRadians());

                        frontLeftMotor = states[0].angle.getRadians();
                        fronttRightMotor = states[1].angle.getRadians();
                        backLeftMotor = states[2].angle.getRadians();
                        backRightMotor = states[3].angle.getRadians();
                }
        }

        //determine which speed setting the driver sets
        public String getSpeedSetting(){
                if(Setup.getInstance().getDeathButton()){
                        speedSetting = "death";
                } else if(Setup.getInstance().getPrimaryDriverXButton()){
                        speedSetting = "fast";
                } else if(Setup.getInstance().getPrimaryDriverAButton()){
                        speedSetting = "medium";
                } else if(Setup.getInstance().getPrimaryDriverBButton()){
                        speedSetting = "slow";
                } else if(Setup.getInstance().getPrimaryDriverYButton()){
                        speedSetting = "reallySlow";
                }
                return speedSetting;
    
        }

        //set the speed based on the current speed setting
        public double getSpeed(String speedSetting) {
                String whichSpeed = speedSetting;
                if(whichSpeed == "death"){
                        speed =-1;
                } else if(whichSpeed == "fast"){
                        speed=-.825;
                } else if(whichSpeed == "medium"){
                        speed=0;
                } else if(whichSpeed == "slow"){
                        speed=0.325;
                } else if(whichSpeed == "reallySlow"){
                        speed = .5;
                }
                return speed;
        }

        //change rotation based on the current speed setting
        public double getRotation(String speedSetting, double rotation) {
                double rotate = rotation;
                String whichSpeed = speedSetting;
                if(whichSpeed == "death"){
                        rotate /=.975;
                }else if(whichSpeed == "fast"){
                        rotate /=2.75;
                } else if(whichSpeed == "medium"){
                        rotate /=4;
                } else if(whichSpeed == "slow"){
                        rotate /=6;
                } else if(whichSpeed == "reallySlow"){
                        
                        rotate /=4;
                }
                return rotate;
        }

        //stops the robot lol lmao pog poggers lolz omegalul cream suckle memez
        public void stop() {
                frontLeftModule.setTargetVelocity(0, 0);
                frontRightModule.setTargetVelocity(0, 0);
                backLeftModule.setTargetVelocity(0, 0);
                backRightModule.setTargetVelocity(0, 0);
        }

        //resets the gyro
        public void resetGyroscope() {
                Setup.instance.gyroscope.setAdjustmentAngle(Setup.instance.gyroscope.getUnadjustedAngle());
        }

        public SwerveModulePosition[] getPositions() {
        SwerveModulePosition[] list= new SwerveModulePosition[4];
        list[0]=frontLeftModule.getCurrentPos();
        list[1]=frontRightModule.getCurrentPos();
        list[2]=backRightModule.getCurrentPos();
        list[3]=backLeftModule.getCurrentPos();
        return list;
        }

        public Pose2d getPose() {
                return odometry.getPoseMeters();
        }

        public void resetPose(Pose2d pose) {
                odometry.resetPosition(NavX.getInstance().getRotation2d(), getPositions(), pose);
        }

        public ChassisSpeeds getSpeeds() {
                return Setup.getInstance().kinematics.toChassisSpeeds(states);
        }
        //makes wheels point straight
        public void straightenA() {
                frontLeftModule.setTargetVelocity(0, 0);
                frontRightModule.setTargetVelocity(0, 0);
                backRightModule.setTargetVelocity(0, 0);
                backLeftModule.setTargetVelocity(0, 0);
        }

        //for pathplanner
        public void makePathplannerWork() {
                frontLeftMotor = states[0].angle.getRadians();
                fronttRightMotor = states[1].angle.getRadians();
                backLeftMotor = states[2].angle.getRadians();
                backRightMotor = states[3].angle.getRadians();
        }
}