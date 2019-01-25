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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Vacuum extends PIDSubsystem {

  private final TalonSRX VacMotor = new TalonSRX(RobotMap.VACCUM_MOTOR);
  private final double GEAR_RATIO = 45d / 56d;
  private final double DEGREES_PER_TICK = 360d / 4096d; //360 degrees divided by ticks per revolution

  /**
   * Add your docs here.
   */
  public Vacuum() {
    super("Vacuum", RobotMap.VACUUM_P, RobotMap.VACUUM_I, RobotMap.VACUUM_D);
    super.getPIDController().setOutputRange(-RobotMap.MAX_WRIST_SPEED, RobotMap.MAX_WRIST_SPEED);
    super.getPIDController().setAbsoluteTolerance(RobotMap.VACUUM_TOLLERANCE);
    //this.VacMotor.getSensorCollection().setPulseWidthPosition(0, 0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getTicks(){
    return this.VacMotor.getSensorCollection().getPulseWidthPosition();
  }

  @Override
  protected double returnPIDInput() {
    return this.VacMotor.getSensorCollection().getPulseWidthPosition() * this.DEGREES_PER_TICK; //* this.GEAR_RATIO;
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    this.VacMotor.set(ControlMode.PercentOutput, output);
  }
}
