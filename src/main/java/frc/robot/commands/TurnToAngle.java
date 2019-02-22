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

public class TurnToAngle extends PIDCommand {
  private double m_angle;
  private double setpoint;

  public TurnToAngle(double angle) {
    super(RobotMap.TURN_P, RobotMap.TURN_I, RobotMap.TURN_D);
    super.requires(Robot.driveTrain);
    m_angle = angle;
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    this.setpoint = Robot.sensors.getRotation() + m_angle;

    SmartDashboard.putNumber("Turn setpoint", this.setpoint);
    super.reset();
    super.setInputRange(-360, 360);
    super.setContinuous(true);
    super.setOutputRange(-RobotMap.MAX_TURN_SPEED, RobotMap.MAX_TURN_SPEED);
    super.setAbsoluteTolerance(RobotMap.GYRO_GAY);
    super.setSetpoint(this.setpoint);

    Robot.driveTrain.setLeftMotor(0);
    Robot.driveTrain.setRightMotor(0);

    super.setTimeout(RobotMap.COMMAND_TIMEOUT);
    super.enable();
  }

  protected void execute(){
    SmartDashboard.putNumber("PID OUT", super.getPIDOut());
  }
  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return super.isTimedOut() || super.onTarget();
  }

  // Called once after isFinished returns true
  protected void end() {
    super.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
    this.end();
  }

  @Override
  public void pidWrite(double output) {
    Robot.driveTrain.setRightMotor(-output);
    Robot.driveTrain.setLeftMotor(output);
  }

  @Override
  public double pidGet() {
    return Robot.sensors.getRotation();
  }

  @Override
  public void executes() {

  }
}