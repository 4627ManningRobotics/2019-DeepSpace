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
import frc.robot.Robot;
import frc.robot.RobotMap;

public class TurnToAngle extends Command {
  private PIDController PID;
	private boolean isfinished;
  private long startTime;
  private final double GLOBAL_ANGLE;
	
    private class PIDOut implements PIDOutput{
    	public void pidWrite(double output){
    		Robot.driveTrain.setRightMotor(-output);
    		Robot.driveTrain.setLeftMotor(output);
    	}
    }
    
    public TurnToAngle(double angle) {
    	
        super.requires(Robot.driveTrain);
    	this.PID = new PIDController(RobotMap.TURN_P, RobotMap.TURN_I, RobotMap.TURN_D, new gyroPID(), new PIDOut());
    	this.PID.setOutputRange(-RobotMap.MAX_TURN_SPEED, RobotMap.MAX_TURN_SPEED);
      this.PID.setAbsoluteTolerance(RobotMap.GYRO_GAY);
      this.GLOBAL_ANGLE = Robot.sensors.getRotation() + angle;
    	this.PID.setSetpoint(this.GLOBAL_ANGLE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.PID.reset();
    	
    	Robot.driveTrain.setLeftMotor(0);
      Robot.driveTrain.setRightMotor(0);
      
    	this.isfinished = false;
		  this.startTime = System.currentTimeMillis();
    	this.PID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(this.PID.onTarget() || System.currentTimeMillis() >= this.startTime + RobotMap.TURN_TIMEOUT){
				this.isfinished = true;
			}else {
				this.startTime = System.currentTimeMillis();
			}
		}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isfinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	this.PID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}

class gyroPID implements PIDSource {

  private PIDSourceType type;

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {
    this.type = pidSource;
  }

  @Override
  public PIDSourceType getPIDSourceType() {
    return this.type;
  }

  @Override
  public double pidGet() {
    return Robot.sensors.getRotation();
	}
    
}