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
   public boolean sourceButton;
   public boolean ampButton;
   public boolean speakerButton1;
   public boolean speakerButton2;

   //Variables - All angles not final
   public double pivotMaxAngle = 340;
   public double pivotMinAngle = 260;
   public double sourceAngle = 50;
   public double ampAngle = 100;
   public double speakerAngle1 = 80;
   public double speakerAngle2 = 100;

   //Speed for preset angle positions
   public double buttonSpeed = 0.3;
   public double slowButtonSpeed = 0.1;
   
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
      pivotMotorSpeed = Setup.getInstance().getSecondaryManualPivot();
      double armPosition = encoder.getPosition()*360;

      //Button assignments
      sourceButton = Setup.getInstance().getSecondaryYButton();
      ampButton = Setup.getInstance().getSecondaryAButton();
      speakerButton1 = Setup.getInstance().getSecondaryBButton();
      speakerButton2 = Setup.getInstance().getSecondaryXButton();

    /* ---------------------------------------------- Preset Angles -------------------------------------------------- */
      if (sourceButton == true || ampButton == true || speakerButton1 == true || speakerButton2 == true) {
         //Source
         if(sourceButton == true){
            if(armPosition > sourceAngle){
               if(armPosition < (sourceAngle + 10)){
                  pivotMotor.set(slowButtonSpeed);
               } else{
               pivotMotor.set(buttonSpeed);
               }
            } else if(armPosition < sourceAngle){
               if(armPosition > (sourceAngle - 10)){
                  pivotMotor.set(-slowButtonSpeed);
               } else{
               pivotMotor.set(-buttonSpeed);
               }
            }
         //Amp
         } else if(ampButton==true){
            if(armPosition > ampAngle){
               if(armPosition < (ampAngle + 10)){
                  pivotMotor.set(slowButtonSpeed);
               } else{
               pivotMotor.set(buttonSpeed);
               }
            } else if(armPosition < ampAngle){
               if(armPosition > (ampAngle - 10)){
                  pivotMotor.set(-slowButtonSpeed);
               } else{
               pivotMotor.set(-buttonSpeed);
               }
            }
         //Primary Speaker Angle
         } else if(speakerButton1==true){
            if(armPosition > speakerAngle1){
               if(armPosition < (speakerAngle1 + 10)){
                  pivotMotor.set(slowButtonSpeed);
               } else{
               pivotMotor.set(buttonSpeed);
               }
            } else if(armPosition < speakerAngle1){
               if(armPosition > (speakerAngle1 - 10)){
                  pivotMotor.set(-slowButtonSpeed);
               } else{
               pivotMotor.set(-buttonSpeed);
               }
            }
         //Secondary Speaker Angle
         } else if(speakerButton2==true){
            if(armPosition > speakerAngle2){
               if(armPosition < (speakerAngle2 + 10)){
                  pivotMotor.set(slowButtonSpeed);
               } else{
               pivotMotor.set(buttonSpeed);
               }
            } else if(armPosition < speakerAngle2){
               if(armPosition > (speakerAngle2 - 10)){
                  pivotMotor.set(-slowButtonSpeed);
               } else{
               pivotMotor.set(-buttonSpeed);
               }
            }
         }
    /* ---------------------------------------------- Manual Pivot -------------------------------------------------- */
      } else if (pivotMotorSpeed < -.1 || pivotMotorSpeed > .1){

         //Crescendo Limits & Soft Stops
         if(armPosition > pivotMaxAngle && pivotMotorSpeed > 0){
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

      } else{
         pivotMotor.set(0);
      } 
   } 
         
   @Override
   public void outputToSmartDashboard(){
      SmartDashboard.putNumber("pivot angle",encoder.getPosition()*360);
      SmartDashboard.putNumber("pivotMotorSpeed",pivotMotorSpeed);
   }

   @Override
   public void stop(){
   pivotMotor.set(0);
   }
}