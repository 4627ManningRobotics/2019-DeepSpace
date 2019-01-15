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
import frc.robot.commands.OperatorControls;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {

  private final TalonSRX front_climber = new TalonSRX(RobotMap.FRONT_CLIMBER); // drive train motors
  private final TalonSRX back_climber = new TalonSRX(RobotMap.BACK_CLIMBER);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    super.setDefaultCommand(new OperatorControls());
  }

  public void set_front(double motorSetting){
    motorSetting = Utilities.scale(motorSetting, RobotMap.CLIMBER_MAX_SPEED);
    this.front_climber.set(ControlMode.PercentOutput, motorSetting);
  }

  public void set_back(double motorSetting){
    motorSetting = Utilities.scale(motorSetting, RobotMap.CLIMBER_MAX_SPEED);
    this.back_climber.set(ControlMode.PercentOutput, motorSetting);
  }

}
