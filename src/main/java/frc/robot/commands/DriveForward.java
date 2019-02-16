/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveForward extends PIDCommand {

  private final double distance;

  public DriveForward(double inches) {
    super(RobotMap.DRIVE_P, RobotMap.DRIVE_I, RobotMap.DRIVE_D);
    super.requires(Robot.driveTrain);
    super.setOutputRange(-RobotMap.MAX_DRIVE_SPEED, RobotMap.MAX_DRIVE_SPEED);
    super.setAbsoluteTolerance(RobotMap.DRIVE_TOLLERANCE);
    this.distance = inches;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.reset();
    super.setSetpoint(this.distance);
    super.setTimeout(RobotMap.COMMAND_TIMEOUT);
    super.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return super.isTimedOut() || super.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.setLeftMotor(0);
    Robot.driveTrain.setRightMotor(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

  @Override
  public void pidWrite(double output) {
    Robot.driveTrain.setRightMotor(output);
    Robot.driveTrain.setLeftMotor(output);
  }

  @Override
  public double pidGet() {
    return Robot.driveTrain.getDistance();
  }
}