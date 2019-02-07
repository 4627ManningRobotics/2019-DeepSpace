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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Utilities;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {

  private final CANSparkMax front_climber = new CANSparkMax(RobotMap.MOTORS.FRONT_CLIMBER.ordinal(), MotorType.kBrushless); // drive train motors
  private final CANSparkMax back_climber = new CANSparkMax(RobotMap.MOTORS.BACK_CLIMBER.ordinal(), MotorType.kBrushless);
  private final CANPIDController frontController = new CANPIDController(this.front_climber);
  private final CANPIDController backController = new CANPIDController(this.back_climber);
  private final Counter FRONT_MAX = new Counter(new DigitalInput(RobotMap.DIO.CLIMBER_FRONT_MAX.ordinal()));
  private final Counter FRONT_MIN = new Counter(new DigitalInput(RobotMap.DIO.CLIMBER_FRONT_MIN.ordinal()));
  private final Counter BACK_MAX = new Counter(new DigitalInput(RobotMap.DIO.CLIMBER_BACK_MAX.ordinal()));
  private final Counter BACK_MIN = new Counter(new DigitalInput(RobotMap.DIO.CLIMBER_BACK_MIN.ordinal()));

  private double target = 0;
  
  public Climber(){
    this.initPID();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
  public void setFront(double position){
    this.target = Utilities.constrain(position, RobotMap.CLIMBER_ZERO, RobotMap.CLIMBER_LIFT);
    if(this.target > RobotMap.CLIMBER_GROUND){
      this.frontController.setReference(this.target / RobotMap.CLIMBER_INCHES_PER_ROTATON, ControlType.kPosition, RobotMap.CLIMBER_LIFT_SLOT);
    } else {
      this.frontController.setReference(this.target / RobotMap.CLIMBER_INCHES_PER_ROTATON, ControlType.kPosition, RobotMap.CLIMBER_GROUND_SLOT);
    }
  }

  public double getFront(){
    return this.front_climber.getEncoder().getPosition() * RobotMap.CLIMBER_INCHES_PER_ROTATON;
  }
  
  public void setBack(double position){
    this.target = Utilities.constrain(position, RobotMap.CLIMBER_ZERO, RobotMap.CLIMBER_LIFT);
    if(this.target > RobotMap.CLIMBER_GROUND){
      this.backController.setReference(this.target / RobotMap.CLIMBER_INCHES_PER_ROTATON, ControlType.kPosition, RobotMap.CLIMBER_LIFT_SLOT);
    } else {
      this.backController.setReference(this.target / RobotMap.CLIMBER_INCHES_PER_ROTATON, ControlType.kPosition, RobotMap.CLIMBER_GROUND_SLOT);
    }
  }

  public double getBack(){
    return this.back_climber.getEncoder().getPosition() * RobotMap.CLIMBER_INCHES_PER_ROTATON;
  }

  public boolean frontOnTarget(){
    return Utilities.within(this.getFront(), this.target - RobotMap.CLIMBER_TOLLERANCE, this.target + RobotMap.CLIMBER_TOLLERANCE);
  }

  public boolean backOnTarget(){
    return Utilities.within(this.getBack(), this.target - RobotMap.CLIMBER_TOLLERANCE, this.target + RobotMap.CLIMBER_TOLLERANCE);
  }

  public boolean frontIsMaximized(){
    boolean state = this.FRONT_MAX.get() > 0;
    this.FRONT_MAX.reset();
    return state;
  }

  public boolean frontIsMinimized(){
    boolean state = this.FRONT_MIN.get() > 0;
    this.FRONT_MIN.reset();
    return state;
  }
  
  public boolean backIsMaximized(){
    boolean state = this.BACK_MAX.get() > 0;
    this.BACK_MAX.reset();
    return state;
  }

  public boolean backIsMinimized(){
    boolean state = this.BACK_MIN.get() > 0;
    this.BACK_MIN.reset();
    return state;
  }

  private void initPID(){
    this.front_climber.getPIDController().setP(RobotMap.CLIMBER_GROUND_P, RobotMap.CLIMBER_GROUND_SLOT);
    this.front_climber.getPIDController().setI(RobotMap.CLIMBER_GROUND_I, RobotMap.CLIMBER_GROUND_SLOT);
    this.front_climber.getPIDController().setD(RobotMap.CLIMBER_GROUND_D, RobotMap.CLIMBER_GROUND_SLOT);
    
    this.back_climber.getPIDController().setP(RobotMap.CLIMBER_GROUND_P, RobotMap.CLIMBER_GROUND_SLOT);
    this.back_climber.getPIDController().setI(RobotMap.CLIMBER_GROUND_I, RobotMap.CLIMBER_GROUND_SLOT);
    this.back_climber.getPIDController().setD(RobotMap.CLIMBER_GROUND_D, RobotMap.CLIMBER_GROUND_SLOT);

    this.front_climber.getPIDController().setP(RobotMap.CLIMBER_LIFT_P, RobotMap.CLIMBER_LIFT_SLOT);
    this.front_climber.getPIDController().setI(RobotMap.CLIMBER_LIFT_I, RobotMap.CLIMBER_LIFT_SLOT);
    this.front_climber.getPIDController().setD(RobotMap.CLIMBER_LIFT_D, RobotMap.CLIMBER_LIFT_SLOT);
    
    this.back_climber.getPIDController().setP(RobotMap.CLIMBER_LIFT_P, RobotMap.CLIMBER_LIFT_SLOT);
    this.back_climber.getPIDController().setI(RobotMap.CLIMBER_LIFT_I, RobotMap.CLIMBER_LIFT_SLOT);
    this.back_climber.getPIDController().setD(RobotMap.CLIMBER_LIFT_D, RobotMap.CLIMBER_LIFT_SLOT);

    this.frontController.setOutputRange(RobotMap.CLIMBER_ZERO, RobotMap.CLIMBER_LIFT);
    this.backController.setOutputRange(RobotMap.CLIMBER_ZERO, RobotMap.CLIMBER_LIFT);
  }
}
