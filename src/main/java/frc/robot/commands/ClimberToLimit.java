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

public class ClimberToLimit extends Command {
  LimitSwitch m_switch;

  public ClimberToLimit(LimitSwitch limit) {
    m_switch=limit;
    // Use requires() here to declare subsystem dependencies
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.climber.setBack(-10);
    Robot.climber.setFront(-10);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if ( (Robot.climber.getBackAppliedOutput()==0) && (Robot.climber.getFrontAppliedOutput() == 0)){
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.zeroEncoder(Dart.FRONT);
    Robot.climber.zeroEncoder(Dart.BACK);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
