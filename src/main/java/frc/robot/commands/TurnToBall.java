/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.Sensors;

/**
 * Add your docs here.
 */
public class TurnToBall extends InstantCommand {
  /**
   * Add your docs here.
   */
  public TurnToBall() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Scheduler.getInstance().add(new TurnToAngle(Sensors.ballReqester.getAngle()));
  }

}
