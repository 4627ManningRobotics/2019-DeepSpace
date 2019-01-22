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
  
  private final TalonSRX intake = new TalonSRX(RobotMap.INTAKE);
  
  @Override
  public void initDefaultCommand() {
  }

  public void setSpeed(double speed){
    speed = Utilities.constrain(speed, -RobotMap.MAX_INTAKE_SPEED, RobotMap.MAX_INTAKE_SPEED);
    this.intake.set(ControlMode.PercentOutput, speed);
  }
}
