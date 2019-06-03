/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Requesters.*;
import frc.robot.subsystems.Sensors;

/*
 * Continuosly runs and loop through all incoming messages to 
 * be systematicaly sorted through and sent to their respective 
 * Requester object 
 */
public class Senses extends Command {

  // the continual stream of information
  private Queue<String> inQueue;
  public static String recent;

  // The requester objects to be referenced by other commands

 public Senses() {
    // Use requires() here to declare subsystem dependencies
    super.requires(Robot.sensors);
    this.inQueue = Requester.inQueue;
    Senses.recent = "";
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Sensors.ballReqester.request(true);
    Sensors.rtsReqester.request(true);
    Sensors.rtrReqester.request(true);

    while(!this.inQueue.isEmpty()){ // If the queue is not empty
      String s = this.inQueue.remove(); // Get the least recent string
      Senses.recent = s;
      //SmartDashboard.putString("Senses recent", Senses.recent);

      // Check recent for each string
      for(Requester r: Requester.getAllRequesters()){
        if(s.contains(r.getRequestType())){
          r.setData(s);
        }
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
