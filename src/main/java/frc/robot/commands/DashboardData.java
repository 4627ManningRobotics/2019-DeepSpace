/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class DashboardData extends Command {
  public DashboardData() {
    // Use requires() here to declare subsystem dependencies
    
    //does not require any subsystem
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putNumber("P", Robot.elevator.getP());
    SmartDashboard.putNumber("I", Robot.elevator.getI());
    SmartDashboard.putNumber("D", Robot.elevator.getD());
    SmartDashboard.putNumber("elevator set point", 0);
    SmartDashboard.putNumber("elevator position", Robot.elevator.getPosition());
    SmartDashboard.putNumber("elevator setpoint", Robot.elevator.getCurrentSetpoint());
    SmartDashboard.putNumber("elevator output value", Robot.elevator.getAppliedOutput());
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double P = SmartDashboard.getNumber("P", 0); // constantly get and set the elevator PID
    double I = SmartDashboard.getNumber("I", 0);
    double D = SmartDashboard.getNumber("D", 0);
    Robot.elevator.setPID(P, I, D);

    SmartDashboard.putNumber("elevator set point", Robot.elevator.getCurrentSetpoint());
    SmartDashboard.putNumber("elevator position", Robot.elevator.getPosition());
    SmartDashboard.putNumber("elevator output value", Robot.elevator.getAppliedOutput());
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
}
