/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  //remote values 
	public static final int DRIVER_CONTROLLER = 0;
	public static final int OPERATOR_CONTROLLER = 1;

	public static final int BUTTON_A = 1;
	public static final int BUTTON_B = 2;
	public static final int BUTTON_X = 3;
	public static final int BUTTON_Y = 4;
	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;
	public static final int BACK_BUTTON = 7;
	public static final int START_BUTTON = 8;
	public static final int LEFT_STICK_BUTTON = 9;
	public static final int RIGHT_STICK_BUTTON = 10;
	
	public static final int LEFT_STICK_X = 0;
	public static final int LEFT_STICK_Y = 1;
	public static final int RIGHT_STICK_Y = 5;
	public static final int RIGHT_STICK_X = 4;
	
	public static final int LEFT_TRIGGER = 2;
	public static final int RIGHT_TRIGGER = 3;
  	//end of remote values

  //motors
  public static final int LEFT_MOTOR_1 = 3; //Talon
  public static final int LEFT_MOTOR_2 = 0; //Victor
  public static final int RIGHT_MOTOR_1 = 2; //Talon
  public static final int RIGHT_MOTOR_2 = 1; //Victor

  public static final int FRONT_CLIMBER = 9;
  public static final int BACK_CLIMBER = 8;

  public static final int ELEVATOR_MOTOR = 12;

  public static final int VACCUM_MOTOR = 10; 
  public static final int WRIST_MOTOR = 11;

  //driver limitations and values
  public static final double MAX_SPEED = 0.95; // 0-1, max speed
  public static final double RAMP_RATE = 0; //time in seconds for the motor to reach max speed
  public static final double TURNING_RATE = 0.7;
  public static final int CURRENT_LIMIT = 35;
  public static final int CONTINUOUS_CURRENT_LIMIT = 30;
  public static final int CURRENT_LIMIT_DURATION = 50; // 50 milliseconds

  //climber values
  public static final double CLIMBER_MAX_SPEED = 0.5; //0 - 1

  //elevator values
  public static final double ELEVATOR_WINCH_RAD = 0.625;
  public static final double ELEVATOR_WINCH_CIRC = ELEVATOR_WINCH_RAD * 2 * Math.PI;
  public static final double ELEVATOR_GEARING = 21;
  public static final double ELEVATOR_MIN_POWER = -0.35;
  public static final double ELEVATOR_MAX_POWER = 0.35;
  public static final double ELEVATOR_P = 0.035;
  public static final double ELEVATOR_I = 0.0006;
  public static final double ELEVATOR_D = 0;
  public static final double ELEVATOR_IZONE = 0.5;
  public static final double ELEVATOR_DEAD_ZONE = 0.0005;
  public static final int ELEVATOR_PID_TIMEOUT = 30; //# of control loops

  //Vacuum values
  public static final double VACUUM_P_DOWN = 0.015;
  public static final double VACUUM_I_DOWN = 0.00;
  public static final double VACUUM_D_DOWN = 0.01;
  public static final double VACUUM_P_UP = 0.02;
  public static final double VACUUM_I_UP = 0.0001;
  public static final double VACUUM_D_UP = 0.00;
  public static final double VACUUM_TOLLERANCE = 3; //degrees
  public static final double MAX_WRIST_SPEED = 0.5;
  public static final double VAC_MOTOR_SPEED = 0.75;
  public static final int VACUUM_PID_TIMEOUT = 30; //# of control loops
}
