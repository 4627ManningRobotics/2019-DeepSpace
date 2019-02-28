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
import frc.robot.subsystems.Climber.Dart;
import frc.robot.subsystems.Climber.LimitSwitch;

public class ClimberToLimit extends Command {
  private LimitSwitch m_switch;
  private Dart m_dart;

  public ClimberToLimit(Dart dart, LimitSwitch limit) {
    this.m_switch = limit;
    this.m_dart = dart;
    // Use requires() here to declare subsystem dependencies
    super.requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (this.m_dart == Dart.FRONT){
      if (this.m_switch == LimitSwitch.TOP){
        Robot.climber.setFront(-10, RobotMap.CLIMBER_GROUND_SLOT);
      }else{
        Robot.climber.setFront(10, RobotMap.CLIMBER_GROUND_SLOT);
      }
    }else{
      if (this.m_switch == LimitSwitch.TOP){
        Robot.climber.setBack(-10, RobotMap.CLIMBER_GROUND_SLOT);
      }else{
        Robot.climber.setBack(10, RobotMap.CLIMBER_GROUND_SLOT);
      }
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if ( (Robot.climber.getAppliedOutput(this.m_dart) == 0) ){
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.zeroEncoder(this.m_dart);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
