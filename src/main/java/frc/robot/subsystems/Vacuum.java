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
import frc.robot.Utilities;

/**
 * Add your docs here.
 */
public class Vacuum extends PIDSubsystem {

  private final TalonSRX WristMotor = new TalonSRX(RobotMap.WRIST_MOTOR);
  private final TalonSRX VacMotor = new TalonSRX(RobotMap.VACCUM_MOTOR);
  private final double GEAR_RATIO = 45d / 56d;
  private final double DEGREES_PER_TICK = 360d / 4096d; //360 degrees divided by ticks per revolution

  /**
   * Add your docs here.
   */
  public Vacuum() {
    super("Vacuum", RobotMap.VACUUM_P_DOWN, RobotMap.VACUUM_I_DOWN, RobotMap.VACUUM_D_DOWN);
    super.getPIDController().setOutputRange(-RobotMap.MAX_WRIST_SPEED, RobotMap.MAX_WRIST_SPEED);
    super.getPIDController().setAbsoluteTolerance(RobotMap.VACUUM_TOLLERANCE);

    this.WristMotor.setInverted(true);
    this.WristMotor.setSelectedSensorPosition(0);//.getSensorCollection().setPulseWidthPosition(0, 0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getTicks(){
    return this.WristMotor.getSelectedSensorPosition();
    //return this.VacMotor.getSensorCollection().getPulseWidthPosition();
  }

  @Override
  protected double returnPIDInput() {
    return this.WristMotor.getSelectedSensorPosition() * this.DEGREES_PER_TICK * this.GEAR_RATIO;
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    this.WristMotor.set(ControlMode.PercentOutput, output);
  }

  public void setPID(double P, double I, double D){
    this.getPIDController().setPID(P, I, D);
  }

  public void activateVacuum(){
    this.VacMotor.set(ControlMode.PercentOutput, RobotMap.VAC_MOTOR_SPEED);
  }
  
  public void deactivateVacuum(){
    this.VacMotor.set(ControlMode.PercentOutput, 0);
  }

  public void resetSensors(){
    this.WristMotor.setSelectedSensorPosition(0);
  }

  public void resetI(){
    this.getPIDController().reset();
  }

  public boolean isOnTarget(){
    return Utilities.within(this.getPosition(), this.getSetpoint() - RobotMap.VACUUM_TOLLERANCE, this.getSetpoint() + RobotMap.VACUUM_TOLLERANCE);
  }
}
