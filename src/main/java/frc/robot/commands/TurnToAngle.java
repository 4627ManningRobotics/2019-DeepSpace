/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class TurnToAngle extends PIDCommand {
  private double m_angle;
  private double setpoint;
  private PIDController pidController = this.getPIDController();

  public TurnToAngle(double angle) {
    super(RobotMap.TURN_P, RobotMap.TURN_I, RobotMap.TURN_D);
    super.requires(Robot.driveTrain);
    this.m_angle = angle;
    System.out.println(this.m_angle);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.setpoint = Robot.sensors.getRotation() + m_angle;

    SmartDashboard.putNumber("Turn setpoint", this.setpoint);
    this.pidController.reset();
    this.setInputRange(-360, 360);
    this.pidController.setOutputRange(-RobotMap.MAX_TURN_SPEED, RobotMap.MAX_TURN_SPEED);
    this.pidController.setAbsoluteTolerance(RobotMap.GYRO_GAY);
    super.setSetpoint(this.setpoint);

    Robot.driveTrain.setLeftMotor(0);
    Robot.driveTrain.setRightMotor(0);

    super.setTimeout(RobotMap.COMMAND_TIMEOUT);
    this.pidController.enable();
  }

  @Override
  protected void execute(){
    SmartDashboard.putNumber("PID OUT", this.pidController.get());
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return super.isTimedOut() || this.pidController.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.pidController.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

  @Override
  protected double returnPIDInput() {
    return Robot.sensors.getRotation();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.driveTrain.setRightMotor(-output);
    Robot.driveTrain.setLeftMotor(output);
  }
}