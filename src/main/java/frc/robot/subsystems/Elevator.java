/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  private final CANSparkMax motor = new CANSparkMax(RobotMap.ELEVATOR_MOTOR, MotorType.kBrushless);
  private final CANPIDController pidController = new CANPIDController(motor);

  private double currentSetpoint;

  public Elevator(){
    this.pidController.setOutputRange(RobotMap.ELEVATOR_MIN_POWER, RobotMap.ELEVATOR_MAX_POWER);
    this.pidController.setP(RobotMap.ELEVATOR_P);
    this.pidController.setI(RobotMap.ELEVATOR_I);
    this.pidController.setD(RobotMap.ELEVATOR_D);
    this.pidController.setIZone(RobotMap.ELEVATOR_IZONE);
    this.motor.setSmartCurrentLimit(RobotMap.CURRENT_LIMIT);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getCurrentSetpoint(){
    return this.currentSetpoint;
  }

  public double getAppliedOutput(){
    return this.motor.getAppliedOutput();
  }

  public double getPosition(){
    return this.motor.getEncoder().getPosition();
  }

  public void setPID(double P, double I, double D){
    this.pidController.setP(P);
    this.pidController.setI(I);
    this.pidController.setD(D);
  }

  public double getP(){
    return this.pidController.getP();
  }

  public double getI(){
    return this.pidController.getI();
  }

  public double getD(){
    return this.pidController.getD();
  }

  public void setElevator(double height){
    double rotations = ((height/RobotMap.ELEVATOR_WINCH_CIRC)*RobotMap.ELEVATOR_GEARING)/2;
    this.pidController.setReference(rotations, ControlType.kPosition);
    this.currentSetpoint = rotations;

    SmartDashboard.putNumber("elevator setpoint", this.getCurrentSetpoint());
  }

}
