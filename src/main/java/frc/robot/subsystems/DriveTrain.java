/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Utilities;
import frc.robot.commands.DriverControls;

/**
 * VROOM VROOM
 */
public class DriveTrain extends Subsystem {

  private final TalonSRX leftMotor1 = new TalonSRX(RobotMap.MOTORS.LEFT_MOTOR_1.ordinal()); // drive train motors
  private final VictorSPX leftMotor2 = new VictorSPX(RobotMap.MOTORS.LEFT_MOTOR_2.ordinal());
  private final TalonSRX rightMotor1 = new TalonSRX(RobotMap.MOTORS.RIGHT_MOTOR_1.ordinal());
  private final VictorSPX rightMotor2 = new VictorSPX(RobotMap.MOTORS.RIGHT_MOTOR_2.ordinal());

  public DriveTrain() {
    // configure the time it takes for the motors to reach max speed
    this.leftMotor1.configOpenloopRamp(RobotMap.RAMP_RATE, 0);
    this.leftMotor2.configOpenloopRamp(RobotMap.RAMP_RATE, 0);
    this.rightMotor1.configOpenloopRamp(RobotMap.RAMP_RATE, 0);
    this.rightMotor2.configOpenloopRamp(RobotMap.RAMP_RATE, 0);

    // configure peak outputs for both the driver and the PID
    this.leftMotor1.configPeakOutputForward(1.0);
    this.leftMotor2.configPeakOutputForward(1.0);
    this.rightMotor1.configPeakOutputForward(1.0);
    this.rightMotor2.configPeakOutputForward(1.0);

    this.leftMotor1.configPeakOutputReverse(-1.0);
    this.leftMotor2.configPeakOutputReverse(-1.0);
    this.rightMotor1.configPeakOutputReverse(-1.0);
    this.rightMotor2.configPeakOutputReverse(-1.0);
 
    //enslave the second motors (Victors) to the first (Talons)
    this.leftMotor2.follow(this.leftMotor1); 
    this.rightMotor2.follow(this.rightMotor1);

    this.leftMotor1.setInverted(false);
    this.leftMotor2.setInverted(false);
    this.rightMotor1.setInverted(true); // the right side is mounted backwards
    this.rightMotor2.setInverted(true);

    //current limiting 
    this.leftMotor1.configPeakCurrentLimit(RobotMap.CURRENT_LIMIT, 0); 
    this.leftMotor1.configPeakCurrentDuration(RobotMap.CURRENT_LIMIT_DURATION, 0);
    this.leftMotor1.configContinuousCurrentLimit(RobotMap.CONTINUOUS_CURRENT_LIMIT, 0); 
    this.leftMotor1.enableCurrentLimit(false);

    this.rightMotor1.configPeakCurrentLimit(RobotMap.CURRENT_LIMIT, 0); 
    this.rightMotor1.configPeakCurrentDuration(RobotMap.CURRENT_LIMIT_DURATION, 0);
    this.rightMotor1.configContinuousCurrentLimit(RobotMap.CONTINUOUS_CURRENT_LIMIT, 0); 
    this.rightMotor1.enableCurrentLimit(false);
    
  }

  public void initDefaultCommand() {
     super.setDefaultCommand(new DriverControls());
  }

  public void setLeftMotor(double motorSetting) {
    motorSetting = Utilities.scale(motorSetting, RobotMap.MAX_SPEED);
    this.leftMotor1.set(ControlMode.PercentOutput, motorSetting); // 2 is following 1

    // check current and ensure safe limits
    //if (this.leftMotor1.getOutputCurrent() > RobotMap.CURRENT_LIMIT) {
      //this.leftMotor1.set(ControlMode.Current, RobotMap.CURRENT_LIMIT);
    //}
  }

  public void setRightMotor(double motorSetting) {
    motorSetting = Utilities.scale(motorSetting, RobotMap.MAX_SPEED);
    this.rightMotor1.set(ControlMode.PercentOutput, motorSetting); //2 is following 1

    // check current and ensure safe limits
    //if (this.rightMotor1.getOutputCurrent() > RobotMap.CURRENT_LIMIT) {
      //this.rightMotor1.set(ControlMode.Current, RobotMap.CURRENT_LIMIT);
    //}
  }
}
