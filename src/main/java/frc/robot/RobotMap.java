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
  
  public static final int LEFT_MOTOR_1 = 0; //motor PDP values
  public static final int LEFT_MOTOR_2 = 1;
  public static final int RIGHT_MOTOR_1 = 2;
  public static final int RIGHT_MOTOR_2 = 3;

  public static final double RAMP_RATE = 0; //time in seconds for the motor to reach max speed

  //driver limitations and values
  public static final double MAX_SPEED = 0.95; // 0-1, max speed
  public static final int CURRENT_LIMIT = 27;

}
