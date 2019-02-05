/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Utilities;
import frc.robot.commands.OperatorControls;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {

  private final CANSparkMax front_climber = new CANSparkMax(RobotMap.FRONT_CLIMBER, MotorType.kBrushless); // drive train motors
  private final CANSparkMax back_climber = new CANSparkMax(RobotMap.BACK_CLIMBER, MotorType.kBrushless);
  private final DigitalInput FRONT_MAX = new DigitalInput(RobotMap.DIO.CLIMBER_FRONT_MAX.ordinal());
  private final DigitalInput FRONT_MIN = new DigitalInput(RobotMap.DIO.CLIMBER_FRONT_MIN.ordinal());
  private final DigitalInput BACK_MAX = new DigitalInput(RobotMap.DIO.CLIMBER_BACK_MAX.ordinal());
  private final DigitalInput BACK_MIN = new DigitalInput(RobotMap.DIO.CLIMBER_BACK_MIN.ordinal());
  
  public Climber(){
    this.front_climber.getPIDController().setP(RobotMap.FRONT_CLIMBER_P);
    this.front_climber.getPIDController().setI(RobotMap.FRONT_CLIMBER_I);
    this.front_climber.getPIDController().setD(RobotMap.FRONT_CLIMBER_D);
    
    this.back_climber.getPIDController().setP(RobotMap.BACK_CLIMBER_P);
    this.back_climber.getPIDController().setI(RobotMap.BACK_CLIMBER_I);
    this.back_climber.getPIDController().setD(RobotMap.BACK_CLIMBER_D);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    super.setDefaultCommand(new OperatorControls());
  }

  public void set_front(double motorSetting){
    motorSetting = Utilities.scale(motorSetting, RobotMap.CLIMBER_MAX_SPEED);
    this.front_climber.set(motorSetting);
  }

  public void set_back(double motorSetting){
    motorSetting = Utilities.scale(motorSetting, RobotMap.CLIMBER_MAX_SPEED);
    this.back_climber.set(motorSetting);
  }

}
