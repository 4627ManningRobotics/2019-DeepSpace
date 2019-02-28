/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Utilities;

public class IncrementElevator extends InstantCommand {
  private double m_inc;

  public IncrementElevator(double inc) {
    this.m_inc = inc;
    // Use requires() here to declare subsystem dependencies
    super.requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    double newSetpoint=Robot.elevator.getCurrentSetpoint() + m_inc;
    newSetpoint = Utilities.constrain(newSetpoint, RobotMap.ELEVATOR_GROUND, RobotMap.ELEVATOR_MAX);
    Robot.elevator.setElevator(newSetpoint);
  }
}
