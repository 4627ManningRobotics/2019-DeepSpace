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
    pidController.setOutputRange(RobotMap.ELEVATOR_MIN_POWER, RobotMap.ELEVATOR_MAX_POWER);
    pidController.setP(RobotMap.ELEVATOR_P);
    pidController.setI(RobotMap.ELEVATOR_I);
    pidController.setD(RobotMap.ELEVATOR_D);
    pidController.setIZone(RobotMap.ELEVATOR_IZONE);
  }

  public double getCurrentSetpoint(){
    return currentSetpoint;
  }

  public double getPosition(){
    return motor.getEncoder().getPosition();
  }

  public void setElevator(double height){
    double rotations = (height/RobotMap.ELEVATOR_WINCH_CIRC)/RobotMap.ELEVATOR_GEARING;
    pidController.setReference(rotations, ControlType.kPosition);
    currentSetpoint = rotations;
  }

}
