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
import frc.robot.Utilities;

/**
 * In charge of the front and back linear actuators that are inteded to 
 * lift the robot. Also contains limit switches and PID controllers to ensure 
 * the linear actuators are reaching their intended positions.
 */
public class Climber extends Subsystem {

  private final CANSparkMax front_climber = new CANSparkMax(RobotMap.MOTORS.FRONT_CLIMBER.ordinal(), MotorType.kBrushless); // drive train motors
  private final CANSparkMax back_climber = new CANSparkMax(RobotMap.MOTORS.BACK_CLIMBER.ordinal(), MotorType.kBrushless);
  private final CANPIDController frontController = new CANPIDController(this.front_climber);
  private final CANPIDController backController = new CANPIDController(this.back_climber);

  private double frontTarget = 0;
  private double backTarget = 0;
  
  public Climber(){
    this.initPID();

    this.setFront(RobotMap.CLIMBER_ZERO, RobotMap.CLIMBER_GROUND_SLOT);
    this.setBack(RobotMap.CLIMBER_ZERO, RobotMap.CLIMBER_GROUND_SLOT);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
  public static enum Dart{
    FRONT,
    BACK
  }

  public static enum LimitSwitch{
    TOP,
    BOTTOM
  }
  
  public void zeroEncoder(Dart dart){
    if(dart == Dart.FRONT){
      this.front_climber.setEncPosition(0);
    }else{
      this.back_climber.setEncPosition(0);
    }
  }

  public void setFront(double position, int slot){
    
    this.frontTarget = position;
    //use seperate PID values for lifting vs moving
    //this.frontController.setReference(this.frontTarget / RobotMap.CLIMBER_INCHES_PER_ROTATON, ControlType.kPosition, slot);
    frontController.setReference(this.frontTarget / RobotMap.CLIMBER_INCHES_PER_ROTATON, ControlType.kPosition, slot);
  }

  public double getFront(){
    return this.front_climber.getEncoder().getPosition() * RobotMap.CLIMBER_INCHES_PER_ROTATON;
  }
  
  public void setBack(double position, int slot){
    this.backTarget = position;
    //use seperate PID values for lifting vs moving
  
    this.backController.setReference(this.backTarget / RobotMap.CLIMBER_INCHES_PER_ROTATON, ControlType.kPosition, slot);
    
  }

  public double getBack(){
    return this.back_climber.getEncoder().getPosition() * RobotMap.CLIMBER_INCHES_PER_ROTATON;
  }

  public boolean frontOnTarget(){
    return Utilities.within(this.getFront(), this.frontTarget - RobotMap.CLIMBER_TOLLERANCE, this.frontTarget + RobotMap.CLIMBER_TOLLERANCE);
  }

  public boolean backOnTarget(){
    return Utilities.within(this.getBack(), this.backTarget - RobotMap.CLIMBER_TOLLERANCE, this.backTarget + RobotMap.CLIMBER_TOLLERANCE);
  }

  public double getFrontSetpoint(){
    return this.frontTarget;
  }

  public double getBackSetpoint(){
    return this.backTarget;
  }

  public double getFrontRaw(){
    return this.front_climber.getEncoder().getPosition();
  }

  public double getBackRaw(){
    return this.back_climber.getEncoder().getPosition();
  }

  public double getFrontRawSetpoint(){
    return this.frontTarget / RobotMap.CLIMBER_INCHES_PER_ROTATON;
  }
  
  public double getBackRawSetpoint(){
    return this.backTarget / RobotMap.CLIMBER_INCHES_PER_ROTATON;
  }

  public double getAppliedOutput(Dart dart){
    if(dart==Dart.FRONT){
      return this.front_climber.getAppliedOutput();
    }else{
      return this.back_climber.getAppliedOutput();
    }
  }

 

  // This is just a mess of PID setup and tweaks
  private void initPID(){
    this.frontController.setP(RobotMap.CLIMBER_FRONT_GROUND_P, RobotMap.CLIMBER_GROUND_SLOT);
    this.frontController.setI(RobotMap.CLIMBER_FRONT_GROUND_I, RobotMap.CLIMBER_GROUND_SLOT);
    this.frontController.setD(RobotMap.CLIMBER_FRONT_GROUND_D, RobotMap.CLIMBER_GROUND_SLOT);
    
    this.backController.setP(RobotMap.CLIMBER_BACK_GROUND_P, RobotMap.CLIMBER_GROUND_SLOT);
    this.backController.setI(RobotMap.CLIMBER_BACK_GROUND_I, RobotMap.CLIMBER_GROUND_SLOT);
    this.backController.setD(RobotMap.CLIMBER_BACK_GROUND_D, RobotMap.CLIMBER_GROUND_SLOT);

    this.frontController.setP(RobotMap.CLIMBER_FRONT_LIFT_P, RobotMap.CLIMBER_LIFT_SLOT);
    this.frontController.setI(RobotMap.CLIMBER_FRONT_LIFT_I, RobotMap.CLIMBER_LIFT_SLOT);
    this.frontController.setD(RobotMap.CLIMBER_FRONT_LIFT_D, RobotMap.CLIMBER_LIFT_SLOT);
    
    this.backController.setP(RobotMap.CLIMBER_BACK_LIFT_P, RobotMap.CLIMBER_LIFT_SLOT);
    this.backController.setI(RobotMap.CLIMBER_BACK_LIFT_I, RobotMap.CLIMBER_LIFT_SLOT);
    this.backController.setD(RobotMap.CLIMBER_BACK_LIFT_D, RobotMap.CLIMBER_LIFT_SLOT);

    this.frontController.setOutputRange(-RobotMap.CLIMBER_MAX_SPEED, RobotMap.CLIMBER_MAX_SPEED);
    this.backController.setOutputRange(-RobotMap.CLIMBER_MAX_SPEED, RobotMap.CLIMBER_MAX_SPEED);

    this.front_climber.setInverted(false);
    this.back_climber.setInverted(false);

  }
}
