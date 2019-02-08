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

/*
 * Set the hight of the front linear actuator and constantly check for limits
 */
public class SetFrontClimber extends Command {

  private double position;

  public SetFrontClimber(double pos) {
    // Use requires() here to declare subsystem dependencies
    this.position = pos;
    // no requires(), front and back should both be able to run
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.climber.setFront(position);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Check for if the linear actualor is exceding the hard limits
    if(Robot.climber.frontIsMaximized()){
      this.position -= RobotMap.CLIMBER_SAFE_LIMIT;
      Robot.climber.setFront(this.position);
    }else if(Robot.climber.frontIsMinimized()){
      this.position += RobotMap.CLIMBER_SAFE_LIMIT;
      Robot.climber.setFront(this.position);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.climber.frontOnTarget();
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
