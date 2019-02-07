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

public class OperatorControls extends Command {
  public OperatorControls() {
    // Use requires() here to declare subsystem dependencies
    super.requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.sensors.run();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.claw.setSpeed(Robot.oi.getOperatorRawAxis(RobotMap.RIGHT_TRIGGER) - Robot.oi.getOperatorRawAxis(RobotMap.LEFT_TRIGGER));
    /*
    Robot.climber.set_front(Robot.oi.getOperatorRawAxis(RobotMap.RIGHT_TRIGGER) - Robot.oi.getOperatorRawAxis(RobotMap.LEFT_TRIGGER));
    if(Robot.oi.getOperatorButton(RobotMap.BUTTON_Y)){
      Robot.climber.set_back(1);
    }else if(Robot.oi.getOperatorButton(RobotMap.BUTTON_A)){
      Robot.climber.set_back(-1);
    }
    */
    if(Robot.oi.getOperatorButton(RobotMap.BUTTON_Y)){
      Robot.vacuum.activateVacuum();
    }else if(Robot.oi.getOperatorButton(RobotMap.BUTTON_A)){
      Robot.vacuum.deactivateVacuum();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.vacuum.deactivateVacuum();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
