/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.sql.Time;

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
  private final CANSparkMax motor = new CANSparkMax(0, MotorType.kBrushless);
  private final CANPIDController pidController = new CANPIDController(motor);

  private double currentSetpoint;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void initPID(){
    this.pidController.setOutputRange(RobotMap.ELEVATOR_MIN_POWER, RobotMap.ELEVATOR_MAX_POWER);
    this.pidController.setP(RobotMap.ELEVATOR_P);
    this.pidController.setI(RobotMap.ELEVATOR_I);
    this.pidController.setD(RobotMap.ELEVATOR_D);
    this.pidController.setIZone(RobotMap.ELEVATOR_IZONE);
    this.motor.setSmartCurrentLimit(RobotMap.CURRENT_LIMIT);

    SmartDashboard.putNumber("P", this.pidController.getP());
    SmartDashboard.putNumber("I", this.pidController.getI());
    SmartDashboard.putNumber("D", this.pidController.getD());
    SmartDashboard.putNumber("I Zone", this.pidController.getIZone());
    SmartDashboard.putNumber("Output Min", this.pidController.getOutputMin());
    SmartDashboard.putNumber("Output Max", this.pidController.getOutputMax());
    //SmartDashboard.putNumber("set point", 0);
    SmartDashboard.putNumber("pos", this.getPosition());
    SmartDashboard.putNumber("set", this.getCurrentSetpoint());
    SmartDashboard.putNumber("out", this.getAppliedOutput());
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

  public void setElevator(double height){
    double rotations = (height/RobotMap.ELEVATOR_WINCH_CIRC)*RobotMap.ELEVATOR_GEARING;
    this.pidController.setReference(rotations, ControlType.kPosition);
    this.currentSetpoint = rotations;
  }

}
