/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Sensors;

public class TurnDirectVision extends PIDCommand {
  public TurnDirectVision() {
    super(RobotMap.TURN_P, RobotMap.TURN_I, RobotMap.TURN_D);
    super.setOutputRange(-RobotMap.MAX_TURN_SPEED, RobotMap.MAX_TURN_SPEED);
    super.requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.reset();
    super.setSetpoint(0);
    super.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    super.onTarget();
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
  }

  @Override
  public void pidWrite(double output) {
    Robot.driveTrain.setRightMotor(-output);
    Robot.driveTrain.setLeftMotor(output);
  }

  @Override
  public double pidGet() {
    return Sensors.ballReqester.getAngle();
  }
}
