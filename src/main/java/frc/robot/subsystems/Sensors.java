/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Requesters.*;
import frc.robot.commands.Senses;

/**
 * The collection of all Sensors and information streams that aren't specific to
 * any subsystem or command. The main part of which being the serial input from
 * the rasberry pi. There are a few threads and a lot of code that can break the
 * robot so be careful!!!
 */
public class Sensors extends Subsystem {

  public static final BallRequester ballReqester = new BallRequester();
  public static final RTSRequester rtsReqester = new RTSRequester();
  public static final RTRRequester rtrReqester = new RTRRequester();
  public static final MouseRequester mouseReqester = new MouseRequester();

  private final Solenoid light = new Solenoid(RobotMap.LIGHT_SOLENOID);

  private double storedAngle;

  private PigeonIMU gyro = new PigeonIMU(0);
  private double[] gyroRotation = new double[3];

  public Sensors() {

    this.storedAngle = 0;

    // this.serial_in.run(); // We cant run the serial yet because the robot needs
    // this.serial_out.run();// more time to start up, moved to the default command
  }

  @Override
  public void initDefaultCommand() {
    super.setDefaultCommand(new Senses());
  }

  public double getRotation() {
    this.gyro.getYawPitchRoll(this.gyroRotation);
    return -this.gyroRotation[0];
  }

  public void stopAllRequests() {
    for (Requester r : Requester.getAllRequesters()) {
      r.request(false);
    }
  }

  public void setStoredAngle(double angle){
    this.storedAngle = angle;
  }

  public double getStoredAngle(){
    return this.storedAngle;
  }

  public void setLight(boolean state){
    this.light.set(state);
  }
}