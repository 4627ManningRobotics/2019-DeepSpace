/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Utilities;

/**
 * An example command.  You can replace me with your own command.
 */
public class DriverControls extends Command {
  public DriverControls() {
    // Use requires() here to declare subsystem dependencies
    super.requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //get driver input
    double triggerVal = Robot.oi.getDriverRawAxis(RobotMap.RIGHT_TRIGGER) - Robot.oi.getDriverRawAxis(RobotMap.LEFT_TRIGGER);
    double stick = Utilities.scale(Robot.oi.getDriverRawAxis(RobotMap.LEFT_STICK_X), RobotMap.TURNING_RATE);
    
    Robot.driveTrain.setLeftMotor(triggerVal + stick);
    Robot.driveTrain.setRightMotor(triggerVal - stick);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
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
    Robot.driveTrain.setLeftMotor(0); //ensure motors stop
    Robot.driveTrain.setRightMotor(0);
  }
}
