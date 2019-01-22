/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Vacuum extends PIDSubsystem {

  private final TalonSRX VacMotor = new TalonSRX(RobotMap.VACCUM_MOTOR);

  /**
   * Add your docs here.
   */
  public Vacuum() {
    super("Vacuum", RobotMap.VACUUM_P, RobotMap.VACUUM_I, RobotMap.VACUUM_D);
    super.getPIDController().setOutputRange(-1, 1);
    super.getPIDController().setAbsoluteTolerance(RobotMap.VACUUM_TOLLERANCE);
    this.VacMotor.getSensorCollection().setAnalogPosition(0, 0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    return this.VacMotor.getSensorCollection().getPulseWidthPosition() / 10d;
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    this.VacMotor.set(ControlMode.PercentOutput, output);
  }
}
