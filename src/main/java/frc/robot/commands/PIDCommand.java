/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public abstract class PIDCommand extends Command implements PIDOutput, PIDSource{

  private double P,I,D;
  private PIDController controller;
  private PIDSourceType type;

  public PIDCommand(double P, double I, double D) {
    this.P = P;
    this.I = I;
    this.D = D;
    this.controller = new PIDController(this.P, this.I, this.D, this, this);
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {
    this.type = pidSource;
  }

  @Override
  public PIDSourceType getPIDSourceType() {
    return this.type;
  }

  public void setOutputRange(double min, double max){
    this.controller.setOutputRange(min, max);
  }

  public void setContinuous(boolean cont){
    this.controller.setContinuous(cont);
  }

  public void setInputRange(double min, double max){
    this.controller.setInputRange(min, max);
  }

  public void setAbsoluteTolerance(double tollerance){
    this.controller.setAbsoluteTolerance(tollerance);
  }

  public void setSetpoint(double setpoint){
    this.controller.setSetpoint(setpoint);
  }

  public void reset(){
    this.controller.reset();
  }

  public double getPIDOut(){
    return this.controller.get();
  }
  public void enable() {
    this.controller.enable();
  }

  public void disable(){
    this.controller.disable();
  }

  public boolean onTarget() {
	  return this.controller.onTarget();
  }

  /*
  @Override
  protected void execute(){
    //this.pidWrite(this.pidGet());
    SmartDashboard.putNumber("PID OUT", this.controller.get());

    this.executes();
  }
  */

  protected abstract void executes();
}