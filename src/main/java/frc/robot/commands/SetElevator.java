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
import frc.robot.RobotMap;
import frc.robot.Utilities;

/*
 * Set the height of the elevator and check for changes to the PID value 
 */
public class SetElevator extends Command {
  private double m_height;
  private Utilities.counter counter;

  public SetElevator(double height) {
    this.m_height = height;
    this.counter = new Utilities.counter(RobotMap.ELEVATOR_PID_TIMEOUT);
    super.requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevator.setElevator(this.m_height);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double P = SmartDashboard.getNumber("P", 0); // constantly get and set the elevator PID
    double I = SmartDashboard.getNumber("I", 0);
    double D = SmartDashboard.getNumber("D", 0);
    Robot.elevator.setPID(P, I, D);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // Check for if the elevator is consistently on its setpoint
    double m_setpoint=Robot.elevator.getCurrentSetpoint();
    if(Utilities.within(Robot.elevator.getPosition(), m_setpoint - RobotMap.ELEVATOR_DEAD_ZONE, m_setpoint + RobotMap.ELEVATOR_DEAD_ZONE)){
      this.counter.count();
    } else {
      this.counter.reset();
    }
    
    return this.counter.isDoneCounting();
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
