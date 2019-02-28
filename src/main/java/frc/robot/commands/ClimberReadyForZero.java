/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Climber.Dart;

public class ClimberReadyForZero extends Command {
  private Dart m_dart;
  public ClimberReadyForZero(Dart dart) {
    this.m_dart = dart;
    // Use requires() here to declare subsystem dependencies
    super.requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(this.m_dart == Dart.FRONT){
      Robot.climber.setFront(1, RobotMap.CLIMBER_GROUND_SLOT);
    }else{
      Robot.climber.setBack(1, RobotMap.CLIMBER_GROUND_SLOT);
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (this.m_dart == Dart.FRONT){
      return (Robot.climber.frontOnTarget()); 
    }else{
      return (Robot.climber.backOnTarget());
    }
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
