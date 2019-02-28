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
import frc.robot.Utilities;
import frc.robot.Utilities.Counter;

/*
 * Sets the vacuum PID to a setposition and adjusts the PID values
 * based on the direction of rotation.
 */
public class SetVacuumAngle extends Command {

  private double angle;
  private final Counter counter;

  public SetVacuumAngle(double angle) {
    this.angle = angle;
    this.counter = new Counter(RobotMap.VACUUM_PID_TIMEOUT);
    super.requires(Robot.vacuum);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.vacuum.resetI(); // make sure the continual error does NOT carry through
    //adjust PID based on direction of rotation
    if(this.angle > Robot.vacuum.getPosition()){
      Robot.vacuum.setPID(RobotMap.VACUUM_P_DOWN, RobotMap.VACUUM_I_DOWN, RobotMap.VACUUM_D_DOWN);
    }else{
      Robot.vacuum.setPID(RobotMap.VACUUM_P_UP, RobotMap.VACUUM_I_UP, RobotMap.VACUUM_D_UP);
    }
    Robot.vacuum.setSetpoint(this.angle);
    Robot.vacuum.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.vacuum.isOnTarget()){
      this.counter.count();
    }else{
      this.counter.reset();
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.vacuum.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.vacuum.disable();
  }
}
