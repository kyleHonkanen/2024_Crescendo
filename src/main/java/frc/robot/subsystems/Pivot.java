package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends Subsystem {

   //get instance
   static Pivot mInstance = new Pivot();

   public static Pivot getInstance() {
      return mInstance;
   }

   public CANSparkMax pivotMotor;
   public AbsoluteEncoder encoder;
   private double pivotMotorSpeed = 0;
   public boolean intakeButton;
   public boolean primaryIntakeButton;
   public boolean ampButton;
   public boolean speakerButton1;
   public boolean speakerButton2;

   //Variables - All angles not final
   public double pivotMaxAngle = 350;
   public double pivotMinAngle = 280;
   public double intakeAngle = 300;
   public double ampAngle = 300;
   public double speakerAngle1 = 310;
   public double speakerAngle2 = 329;
   public double slowZone = 10;
   public double extraSlowZone = 2;

   //Speed for preset angle positions
   public double buttonSpeed = 0.5;
   public double slowButtonSpeed = 0.15;
   public double extraSlowButtonSpeed = 0.025;
   
   public Pivot() {
      pivotMotor = new CANSparkMax(Setup.PivotMotorID, MotorType.kBrushless);
      encoder = pivotMotor.getAbsoluteEncoder(com.revrobotics.SparkAbsoluteEncoder.Type.kDutyCycle);
   }

   public CANSparkMax getPivotMotor(){
      return pivotMotor;
   }

   public AbsoluteEncoder getPivotEncoder(){
      return encoder;
   }

   public double getPivotPosition(){
      return encoder.getPosition()*360;
   }

   @Override
   public void updateSubsystem(){
      pivotMotorSpeed = -Setup.getInstance().getSecondaryManualPivot();
      double armPosition = encoder.getPosition()*360;

      //Button assignments
      intakeButton = Setup.getInstance().getSecondaryYButton();
      primaryIntakeButton = Setup.getInstance().getPrimaryGroundIntake();
      ampButton = Setup.getInstance().getSecondaryAButton();
      speakerButton1 = Setup.getInstance().getSecondaryBButton();
      speakerButton2 = Setup.getInstance().getSecondaryXButton();

    /* ---------------------------------------------- Preset Angles -------------------------------------------------- */
      if (/*primaryIntakeButton == true || */intakeButton == true || ampButton == true || speakerButton1 == true || speakerButton2 == true) {
   
         //get outa da danger zone
         if(armPosition > 0 && armPosition < 5){
            pivotMotor.set(-0.4);

         //Source
         } else if(primaryIntakeButton == true || intakeButton == true){
            if(armPosition < intakeAngle){

               if(armPosition > (intakeAngle - extraSlowZone)){
                  pivotMotor.set(extraSlowButtonSpeed);
               } else if(armPosition > (intakeAngle - slowZone)){ 
                  pivotMotor.set(slowButtonSpeed);
               } else{ 
               pivotMotor.set(buttonSpeed);
               }

            } else if(armPosition > intakeAngle){

               if(armPosition < (intakeAngle + extraSlowZone)){
                  pivotMotor.set(-extraSlowButtonSpeed);
               } else if(armPosition < (intakeAngle + slowZone)){
                  pivotMotor.set(-slowButtonSpeed);
               }else{
               pivotMotor.set(-buttonSpeed);

               }
            }

         //Amp
         } else if(ampButton==true){
            if(armPosition < ampAngle){

               if((armPosition > (ampAngle - extraSlowZone))){
                  pivotMotor.set(extraSlowButtonSpeed);
               } else if(armPosition > (ampAngle - slowZone)){
                  pivotMotor.set(slowButtonSpeed);
               } else{
               pivotMotor.set(buttonSpeed);
               }

            } else if(armPosition > ampAngle){

               if(armPosition < (ampAngle + extraSlowZone)){
                  pivotMotor.set(-extraSlowButtonSpeed);
               } else if(armPosition < (ampAngle + slowZone)){
                  pivotMotor.set(-slowButtonSpeed);
               } else{
               pivotMotor.set(-buttonSpeed);
               }

            }

         //Primary Speaker Angle
         } else if(speakerButton1==true){
            if(armPosition < speakerAngle1){

               if(armPosition > (speakerAngle1 - extraSlowZone)) {
                  pivotMotor.set(extraSlowButtonSpeed);
               } else if(armPosition > (speakerAngle1 - slowZone)){
                  pivotMotor.set(slowButtonSpeed);
               } else{
               pivotMotor.set(buttonSpeed);
               }

            } else if(armPosition > speakerAngle1){

               if(armPosition < (speakerAngle1 + extraSlowZone)){
                  pivotMotor.set(-extraSlowButtonSpeed);
               }else if(armPosition < (speakerAngle1 + slowZone)){
                  pivotMotor.set(-slowButtonSpeed);
               } else{
               pivotMotor.set(-buttonSpeed);
               }

            }

         //Secondary Speaker Angle
         } else if(speakerButton2==true){
            if(armPosition < speakerAngle2){

               if(armPosition > (speakerAngle2 - extraSlowZone)){
                  pivotMotor.set(extraSlowButtonSpeed);
               } else if(armPosition > (speakerAngle2 - slowZone)){
                  pivotMotor.set(slowButtonSpeed);
               } else{
               pivotMotor.set(buttonSpeed);
               }

            } else if(armPosition > speakerAngle2){

               if(armPosition < (speakerAngle2 + extraSlowZone)){
                  pivotMotor.set(-extraSlowButtonSpeed);
               } else if(armPosition < (speakerAngle2 + slowZone)){
                  pivotMotor.set(-slowButtonSpeed);
               } else{
               pivotMotor.set(-buttonSpeed);
               }

            }
         }
    /* ---------------------------------------------- Manual Pivot -------------------------------------------------- */
      } else if (pivotMotorSpeed < -.1 || pivotMotorSpeed > .1){

            //Limits & Soft Stops
            if(armPosition > 0 && armPosition < 5){
                pivotMotorSpeed = -0.1;
            } else if(armPosition > pivotMaxAngle && pivotMotorSpeed > 0){
                pivotMotorSpeed = 0;
            } else if (armPosition < pivotMinAngle && pivotMotorSpeed < 0){
                pivotMotorSpeed = 0;
            } else if((armPosition > (pivotMaxAngle - 5)) || (armPosition < (pivotMinAngle + 5))){
                pivotMotorSpeed /= 2;
            }     

            //Double Speed when Pressed
            if(Setup.getInstance().getSecondaryRightStickPressed()){
                pivotMotorSpeed *= 2;
            }

            pivotMotor.set(pivotMotorSpeed/2.2);

        } else if(Setup.getInstance().getSecondaryJoystick().getRawButton(7)){
            //DONT DO ANYTHING (for auto aim)

        } else {
            pivotMotor.set(0);
        } 
   } 
         
   @Override
   public void outputToSmartDashboard(){
      SmartDashboard.putNumber("pivot angle",encoder.getPosition()*360);
   }

   @Override
   public void stop(){
   pivotMotor.set(0);
   }
}