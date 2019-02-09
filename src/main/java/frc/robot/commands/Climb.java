/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;

public class Climb extends CommandGroup {
  /**
   * The sequence for climbing onto the platform
   */
  public Climb() {
    super.addSequential(new SetFrontClimber(RobotMap.CLIMBER_GROUND)); // Set both actuator to go to the ground position
    super.addSequential(new SetBackClimber(RobotMap.CLIMBER_GROUND)); 
    super.addSequential(new SetFrontClimber(RobotMap.CLIMBER_LIFT)); // Front tilt/lift
    super.addSequential(new TimedDriveForward(0.5, 0.2)); // Drive forward to force more of the robot onto the platform
    super.addSequential(new SetFrontClimber(RobotMap.CLIMBER_ZERO)); // Bring the front up onto the platform
    super.addSequential(new SetBackClimber(RobotMap.CLIMBER_LIFT)); // Lift the back up
    super.addSequential(new TimedDriveForward(0.5, 0.2)); // Move the body onto the platform
    super.addSequential(new SetBackClimber(RobotMap.CLIMBER_ZERO)); // Raise the back up
    super.addSequential(new TimedDriveForward(0.5, 0.2)); // Get 100% onto platform
  }
}
