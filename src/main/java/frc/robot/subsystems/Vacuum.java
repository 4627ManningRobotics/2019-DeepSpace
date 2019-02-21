/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;
import frc.robot.Utilities;
import frc.robot.commands.OperatorControls;

/**
 * The wrist and the mechanism to suck up game pieces
 */
public class Vacuum extends PIDSubsystem {

  private final TalonSRX wristMotor = new TalonSRX(RobotMap.MOTORS.WRIST_MOTOR.ordinal());
  private final TalonSRX vacMotor = new TalonSRX(RobotMap.MOTORS.INTAKE_MOTOR.ordinal());
  private final Solenoid releaseValve = new Solenoid(RobotMap.DIO.VACUUM_RELEASE.ordinal());
  private final double GEAR_RATIO = 45d / 56d;
  private final double DEGREES_PER_TICK = 360d / 4096d; //360 degrees divided by ticks per revolution

  /**
   * Add your docs here.
   */
  public Vacuum() {
    super("Vacuum", RobotMap.VACUUM_P_DOWN, RobotMap.VACUUM_I_DOWN, RobotMap.VACUUM_D_DOWN);
    super.getPIDController().setOutputRange(-RobotMap.MAX_WRIST_SPEED, RobotMap.MAX_WRIST_SPEED);
    super.getPIDController().setAbsoluteTolerance(RobotMap.VACUUM_TOLLERANCE);

    this.releaseValve.set(false); //closed
    this.wristMotor.setInverted(true);
    this.wristMotor.setSelectedSensorPosition(0);//.getSensorCollection().setPulseWidthPosition(0, 0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    super.setDefaultCommand(new OperatorControls());
  }

  public double getTicks(){
    return this.wristMotor.getSelectedSensorPosition();
    //return this.VacMotor.getSensorCollection().getPulseWidthPosition();
  }


  @Override
  protected double returnPIDInput() {
    return this.wristMotor.getSelectedSensorPosition() * this.DEGREES_PER_TICK * this.GEAR_RATIO;
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    this.wristMotor.set(ControlMode.PercentOutput, output);
  }

  public void setPID(double P, double I, double D){
    this.getPIDController().setPID(P, I, D);
  }

  public void activateVacuum(){
    this.releaseValve.set(false); // closed
    this.vacMotor.set(ControlMode.PercentOutput, RobotMap.VAC_MOTOR_SPEED);
  }
  
  public void deactivateVacuum(){
    this.releaseValve.set(true);
    this.vacMotor.set(ControlMode.PercentOutput, 0);
  }

  public void resetSensors(){
    this.wristMotor.setSelectedSensorPosition(0);
  }

  public void resetI(){
    this.getPIDController().reset();
  }

  public boolean isOnTarget(){
    return Utilities.within(this.getPosition(), this.getSetpoint() - RobotMap.VACUUM_TOLLERANCE, this.getSetpoint() + RobotMap.VACUUM_TOLLERANCE);
  }
  
  public double getVacCurrent(){
    return this.vacMotor.getOutputCurrent();
  }
  
}
