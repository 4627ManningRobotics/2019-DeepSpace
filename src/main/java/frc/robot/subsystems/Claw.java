/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Utilities;

/**
 * Add your docs here.
 */
public class Claw extends Subsystem {
  
  private final TalonSRX left_intake = new TalonSRX(RobotMap.LEFT_INTAKE); // drive train motors
  private final TalonSRX right_intake = new TalonSRX(RobotMap.RIGHT_INTAKE);
  
  @Override
  public void initDefaultCommand() {
  }

  public void setSpeed(double speed){
    speed = Utilities.constrain(speed, -RobotMap.MAX_INTAKE_SPEED, RobotMap.MAX_INTAKE_SPEED);
    this.left_intake.set(ControlMode.PercentOutput, speed);
    this.right_intake.set(ControlMode.PercentOutput, -speed); // reverse direction to intake object 
  }
}
