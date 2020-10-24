/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // NAMES
  XboxController controller;
  // FRONT
  WPI_TalonSRX FrontRightTalon;
  WPI_VictorSPX FrontLeftTalon;

  // BACK
   WPI_TalonSRX BackRightTalon;
 WPI_TalonSRX BackLeftTalon;

// THIS FOR TURN TABLE BELOW!
WPI_TalonSRX turnTable;

WPI_TalonSRX shooterTop;
WPI_TalonSRX shooterBot;
WPI_TalonSRX shooterBot2;
WPI_TalonSRX laserMotor;

  // DOUBLES AND BOOLEANS
  double rightSide;
  double turningTable;
  double leftSide;

  boolean buttonAIsPressed = false;
  boolean inPreciseMode = false;
 


  int precisionValue;
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    // NAME
    FrontRightTalon = new WPI_TalonSRX(15);
    FrontLeftTalon = new WPI_VictorSPX(17);
     BackRightTalon = new WPI_TalonSRX(2);
     BackLeftTalon = new WPI_TalonSRX(6);

     // FOLOW COMMANDS
    FrontLeftTalon.follow(FrontRightTalon);
    BackLeftTalon.follow(BackRightTalon);

    
     shooterTop = new WPI_TalonSRX(4);
     shooterBot = new WPI_TalonSRX(5);
     shooterBot2 = new WPI_TalonSRX(7);
    turnTable = new WPI_TalonSRX(9);
    laserMotor = new WPI_TalonSRX(10);

    controller = new XboxController(0);
  }

  private WPI_TalonSRX WPI_TalonSRX(int i) {
    return null;

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {





double leftTrigger = controller.getRawAxis(2);

if (leftTrigger>0){
  shooterBot.set(ControlMode.PercentOutput, 0.8);
  shooterBot2.set(ControlMode.PercentOutput, 0.8);

}

    // TURN TABLE
   //  turningTable = controller.getRawAxis();
   // turnTable.set(turningTable);
    // getBumperPressed(GenericHID.Hand hand)
    // FRONT WHEELS
    leftSide = controller.getRawAxis(1);
    FrontRightTalon.set(leftSide);

    // BACK WHEELS
    rightSide = controller.getRawAxis(4);
     BackRightTalon.set(rightSide);


     // SHOOT TIME!

 if (controller.getXButtonPressed()) {

shooterTop.set(ControlMode.PercentOutput, 0.2);

 } else if (controller.getYButtonPressed()) {

  shooterBot.set(ControlMode.PercentOutput, 0.4);
  shooterBot2.set(ControlMode.PercentOutput, 0.4);

 }else if (controller.getBButtonPressed()){

  shooterBot.set(ControlMode.PercentOutput, 0.6);
  shooterBot2.set(ControlMode.PercentOutput, 0.6);

 }else if (leftTrigger>0){

  shooterBot.set(ControlMode.PercentOutput, 0.8);
  shooterBot2.set(ControlMode.PercentOutput, 0.8);

 }else {

  shooterTop.set(ControlMode.PercentOutput, 0);
  shooterBot.set(ControlMode.PercentOutput, 0);
  shooterBot2.set(ControlMode.PercentOutput, 0);
  }


    // WAIT/ PRECSION MODE INCOMING!!!!!!!!!
    if (controller.getAButtonPressed() && buttonAIsPressed == false) {
      inPreciseMode = !inPreciseMode;
      buttonAIsPressed = true;
    }
    
    if (controller.getAButtonReleased()) {
      buttonAIsPressed = false;
    }
    
    if (inPreciseMode == true) {
      precisionValue = 10;
    } else if (inPreciseMode == false) {
      precisionValue = 1;
    } 

    // BUMPER/TURN TABLE AND LASERS
    if (controller.getBumperPressed(Hand.kLeft)) {
      turnTable.set(ControlMode.PercentOutput, 0.2);


    }else if (controller.getBumperPressed((Hand.kRight))){
      turnTable.set(ControlMode.PercentOutput, -0.2);


    }else {
      turnTable.set(ControlMode.PercentOutput, 0);
    }

if (controller.getStartButtonPressed()) {
laserMotor.set(ControlMode.PercentOutput, 0.2);

}else if (controller.getBackButtonPressed()){
  laserMotor.set(ControlMode.PercentOutput, -0.2);

}else {
  laserMotor.set(ControlMode.PercentOutput, 0);
}


  }
  




  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
