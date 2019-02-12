/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Climber.Dart;
import frc.robot.subsystems.Climber.LimitSwitch;

public class ClimberReadyForZero extends Command {
  public ClimberReadyForZero() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(Robot.climber.getLimitSwitch(Dart.FRONT, LimitSwitch.TOP)){
      Robot.climber.setFront(1);
    }
    if(Robot.climber.getLimitSwitch(Dart.BACK, LimitSwitch.TOP)){
      Robot.climber.setBack(1);
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(!Robot.climber.getLimitSwitch(Dart.FRONT, LimitSwitch.TOP) && !Robot.climber.getLimitSwitch(Dart.BACK, LimitSwitch.TOP)){
      return true;
    }else
      return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
