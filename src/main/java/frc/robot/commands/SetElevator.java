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



public class SetElevator extends Command {
  private double m_height;
  private int counter;

  public SetElevator(double height) {
    m_height=height;
    counter=0;
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevator.setElevator(m_height);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    
    double m_setpoint=Robot.elevator.getCurrentSetpoint();
    if(Utilities.within(Robot.elevator.getPosition(), m_setpoint - RobotMap.ELEVATOR_DEAD_ZONE, m_setpoint + RobotMap.ELEVATOR_DEAD_ZONE)){
      counter+=1;
    } else {
      counter = 0;
    }
    
    return (counter>15);
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
