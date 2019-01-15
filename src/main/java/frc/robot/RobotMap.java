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
<<<<<<< HEAD
  public static final int LEFT_MOTOR_1 = 5; //motor PDP values
  public static final int LEFT_MOTOR_2 = 2;
  public static final int RIGHT_MOTOR_1 = 4;
  public static final int RIGHT_MOTOR_2 = 3;

  public static final int LEFT_INTAKE = 0;
  public static final int RIGHT_INTAKE = 1;

  public static final int FRONT_CLIMBER = 6;
  public static final int BACK_CLIMBER = 7;
=======
  public static final int LEFT_MOTOR_1 = 6; //motor PDP values
  public static final int LEFT_MOTOR_2 = 7;
  public static final int RIGHT_MOTOR_1 = 2;
  public static final int RIGHT_MOTOR_2 = 5;
  public static final int LEFT_INTAKE = 0;
  public static final int RIGHT_INTAKE = 1;
>>>>>>> a264d33f2de8c293758f8f70ff35c91642448fd5

  //solenoids and pnumantics 
  public static final int DRIVE_GEAR = 0;

  //driver limitations and values
  public static final double MAX_SPEED = 0.95; // 0-1, max speed
  public static final double RAMP_RATE = 0; //time in seconds for the motor to reach max speed
  public static final double TURNING_RATE = 0.7;
  public static final int CURRENT_LIMIT = 27;

<<<<<<< HEAD
  //operator limitations and values
  public static final double MAX_INTAKE_SPEED = 1; // 0 - 1

  //climber values
  public static final double CLIMBER_MAX_SPEED = 0.5; //0 - 1
=======
  //intake values
  public static final int MAX_INTAKE_SPEED = 1;

>>>>>>> a264d33f2de8c293758f8f70ff35c91642448fd5
}
