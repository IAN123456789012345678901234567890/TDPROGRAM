/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  // NAMES
  XboxController controller;
  // MOTORS / WHEEL
  Talon FrontRightDriveTalon;
  WPI_TalonSRX FrontLeftDriveTalon;
  Talon BackRightDriveTalon;
  WPI_VictorSPX BackLeftDriveTalon;

  // OTHER MOTORS
  WPI_TalonSRX turnTable;
  WPI_TalonSRX shooterTop;
  WPI_TalonSRX shooterBot;
  WPI_TalonSRX shooterBot2;
  WPI_TalonFX laserMotor;

  // DOUBLES AND BOOLEANS
  double rightSide;
  double turningTable;
  double leftSide;
  boolean buttonAIsPressed = false;
  boolean inPreciseMode = false;

  int precisionValue;

  @Override
  public void robotInit() {

    // NAME
    //
    FrontRightDriveTalon = new Talon(8);
    FrontLeftDriveTalon = new WPI_TalonSRX(10);
    BackRightDriveTalon = new Talon(9);
    BackLeftDriveTalon = new WPI_VictorSPX(11);

    // FOLOW COMMANDS
    BackLeftDriveTalon.follow(FrontLeftDriveTalon);

    shooterTop = new WPI_TalonSRX(4);
    shooterBot = new WPI_TalonSRX(5);
    shooterBot2 = new WPI_TalonSRX(7);
    turnTable = new WPI_TalonSRX(9);

    controller = new XboxController(0);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopPeriodic() {

    double leftTrigger = controller.getRawAxis(2);

    // if (leftTrigger > 0) {
    //   shooterBot.set(ControlMode.PercentOutput, 0.8);
    //   shooterBot2.set(ControlMode.PercentOutput, 0.8);

    // }

    // TURN TABLE
    // turningTable = controller.getRawAxis();
    // turnTable.set(turningTable);
    // getBumperPressed(GenericHID.Hand hand)

    // SHOOT TIME!

    if (controller.getXButtonPressed()) {

      shooterTop.set(ControlMode.PercentOutput, 0.1);

    } else if (controller.getYButtonPressed()) {

      shooterBot.set(ControlMode.PercentOutput, 0.4);
      shooterBot2.set(ControlMode.PercentOutput, 0.4);

    } else if (controller.getBButtonPressed()) {

      shooterBot.set(ControlMode.PercentOutput, 0.6);
      shooterBot2.set(ControlMode.PercentOutput, 0.6);

    } else if (leftTrigger > 0) {

      shooterBot.set(ControlMode.PercentOutput, 0.8);
      shooterBot2.set(ControlMode.PercentOutput, 0.8);

    } else {

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

      // DRIVE TRAIN CODE
    // double differenceVal = controller.getRawAxis(1) > 0 ? -0.02 : 0.03;

    // DIFFERENTIAL DRIVE CALCULATIONS
    leftSide = (-controller.getRawAxis(4) + controller.getRawAxis(1)) * precisionValue;
    rightSide = (-controller.getRawAxis(4) - controller.getRawAxis(1)) * -precisionValue;
    
    // FRONT DRIVE WHEELS
    FrontLeftDriveTalon.set(ControlMode.PercentOutput, -leftSide);
    FrontRightDriveTalon.set(rightSide);

    // BACK DRIVE WHEELS
    BackRightDriveTalon.set(rightSide);

    // BUMPER/TURN TABLE AND LASERS
    if (controller.getBumperPressed(Hand.kLeft)) {
      turnTable.set(ControlMode.PercentOutput, 0.2);

    } else if (controller.getBumperPressed((Hand.kRight))) {
      turnTable.set(ControlMode.PercentOutput, -0.2);

    } else {
      turnTable.set(ControlMode.PercentOutput, 0);
    }

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
// LINE 206
// !!!!!!!!!!!!!!!!!!!!!
