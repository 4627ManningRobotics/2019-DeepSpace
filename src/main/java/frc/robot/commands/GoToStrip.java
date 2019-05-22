/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.Utilities;
import frc.robot.subsystems.Sensors;

public class GoToStrip extends CommandGroup {
  /**
   * Add your docs here.
   */

  public GoToStrip() {
    
    super.addSequential(new FaceStrips());
    super.addSequential(new WaitCommand(0.2));
    //super.addSequential(new StoreAngle( Math.atan2(Sensors.rtsReqester.getY() / 2d, Sensors.rtsReqester.getX()) + Sensors.rtsReqester.getAngle()));
    super.addSequential(new StoreAngle(Math.atan2(Sensors.rtsReqester.getY() / 2d, Sensors.rtsReqester.getX()) - 90));
    super.addSequential(new TurnToAngle( Sensors.rtsReqester.getAngle() - Math.atan2(Sensors.rtsReqester.getY() / 2d, Sensors.rtsReqester.getX())));
    super.addSequential(new DriveForward(Utilities.metersToInches(Math.sqrt( (Sensors.rtsReqester.getY() / 2d) * (Sensors.rtsReqester.getY() / 2d) + Sensors.rtsReqester.getX() * Sensors.rtsReqester.getX()))));
    super.addSequential(new TurnToAngle(Robot.sensors.getRotation()));
    super.addSequential(new FaceStrips());
    //super.addSequential(new WaitCommand(0.25));
    //super.addSequential(new DriveForward(Utilities.metersToInches(Sensors.rtsReqester.getY())));
  }
}
