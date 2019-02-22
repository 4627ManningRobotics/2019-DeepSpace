/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Dummy extends Command {
  
  private PIDController controller;
  private double m_angle;
  private double setpoint;
  private static int i = 0;
  private Source s;
  private Out o;

  public Dummy(double angle) {
    super.requires(Robot.driveTrain);
    this.s = new Source();
    this.o = new Out();
    this.controller = new PIDController(RobotMap.DRIVE_P, RobotMap.DRIVE_I, RobotMap.DRIVE_D, s, o);
    this.m_angle = angle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.setpoint = Robot.sensors.getRotation() + this.m_angle;
    this.controller.reset();
    SmartDashboard.putNumber("Turn setpoint", this.setpoint);
    this.controller.setSetpoint(this.setpoint);
    this.controller.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
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
    this.end();
  }

  
  class Out implements PIDOutput{

    @Override
    public void pidWrite(double output) {
      SmartDashboard.putNumber("PID OUT", output);
      Robot.driveTrain.setRightMotor(-output);
      Robot.driveTrain.setLeftMotor(output);
    }

  }

  class Source implements PIDSource {

    PIDSourceType type;

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
      this.type = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
      return this.type;
    }

    @Override
    public double pidGet() {
      return Robot.sensors.getRotation();
	}

  }
}
