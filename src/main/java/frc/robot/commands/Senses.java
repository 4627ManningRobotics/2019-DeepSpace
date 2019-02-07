/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Queue;
import java.util.concurrent.SynchronousQueue;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Senses extends Command {

  private Queue<String> inQueue = new SynchronousQueue<String>();
  public static String recent;

  public static BallRequester ballReqester = new BallRequester();
  public static StripRequester stripReqester = new StripRequester();

 public Senses(Queue<String> q) {
    // Use requires() here to declare subsystem dependencies
    super.requires(Robot.sensors);
    this.inQueue = q;
    Senses.recent = "";
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!this.inQueue.isEmpty()){
      String s = this.inQueue.remove();
      Senses.recent = s;
      SmartDashboard.putString("Senses recent", s);

      if(s.contains(Requester.BALL)){
        Senses.ballReqester.setData(s);
      }else if(s.contains(Requester.STRIP)){
        Senses.stripReqester.setData(s);
      }
    }
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
