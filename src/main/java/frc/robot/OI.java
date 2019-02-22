/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.IncrementElevator;
import frc.robot.commands.SetBackClimber;
import frc.robot.commands.SetClaw;
import frc.robot.commands.SetElevator;
import frc.robot.commands.SetFrontClimber;
import frc.robot.commands.SetVacuumAngle;
import frc.robot.commands.ToggleClaw;
import frc.robot.commands.TurnToAngle;
import frc.robot.commands.ZeroBothClimbers;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  XboxController driverController = new XboxController(RobotMap.DRIVER_CONTROLLER);
	XboxController operatorController = new XboxController(RobotMap.OPERATOR_CONTROLLER);
	
	Button dButtonA = new JoystickButton(this.driverController, RobotMap.BUTTON_A);
	Button dButtonB = new JoystickButton(this.driverController, RobotMap.BUTTON_B);
	Button dButtonX = new JoystickButton(this.driverController, RobotMap.BUTTON_X);
	Button dButtonY = new JoystickButton(this.driverController, RobotMap.BUTTON_Y);
	Button dButtonBack = new JoystickButton(this.driverController, RobotMap.BACK_BUTTON);
	Button dStartButton = new JoystickButton(this.driverController, RobotMap.START_BUTTON);
	
	Button oButtonA = new JoystickButton(this.operatorController, RobotMap.BUTTON_A);
	Button oButtonB = new JoystickButton(this.operatorController, RobotMap.BUTTON_B);
	Button oButtonY = new JoystickButton(this.operatorController, RobotMap.BUTTON_Y);
	Button oButtonX = new JoystickButton(this.operatorController, RobotMap.BUTTON_X);
	Button oButtonBack = new JoystickButton(this.operatorController, RobotMap.BACK_BUTTON);
	Button oButtonStart = new JoystickButton(this.operatorController, RobotMap.START_BUTTON);
  	Button oButtonRightStick = new JoystickButton(this.operatorController, RobotMap.RIGHT_STICK_BUTTON);
	Button oButtonRightBumper = new JoystickButton(this.operatorController, RobotMap.RIGHT_BUMPER);
	Button oButtonLeftBumper = new JoystickButton(this.operatorController, RobotMap.LEFT_BUMPER);

  public boolean getOperatorButton(int axis) {
		return this.operatorController.getRawButton(axis);
		
	}
	
	public boolean getDriverButton(int axis) {
		return this.driverController.getRawButton(axis);
	}
	
	public double getOperatorRawAxis(int axis) {
		return this.operatorController.getRawAxis(axis);
	}
	
	public double getDriverRawAxis(int axis) {
		return this.driverController.getRawAxis(axis);
	}

	public OI () {
		//this.oButtonA.whenPressed(new SetElevator(SmartDashboard.getNumber("set point", 10)));
		this.oButtonA.whenPressed(new SetElevator(0)); 
		this.oButtonB.whenPressed(new SetElevator(12)); 
		this.oButtonY.whenPressed(new SetElevator(40));
		//this.oButtonX.whenPressed(new TurnToAngle(-45));
		this.oButtonX.whenPressed(new TurnToAngle(45));
		this.oButtonBack.whileHeld(new IncrementElevator(-1));
		this.oButtonStart.whileHeld(new IncrementElevator(1));

		if (Robot.vacuumMode) {
			//this.oButtonLeftBumper.whenPressed(new SetVacuumAngle(45));
			//this.oButtonRightBumper.whenPressed(new SetVacuumAngle(0));
		}else{
			this.oButtonLeftBumper.whenPressed(new SetClaw(false));
			this.oButtonRightBumper.whenPressed(new SetClaw(true));
			this.oButtonBack.whenPressed(new ToggleClaw());
		}

		this.dButtonA.whenPressed(new SetFrontClimber(RobotMap.CLIMBER_GROUND, RobotMap.CLIMBER_GROUND_SLOT));
		this.dButtonX.whenPressed(new SetBackClimber(RobotMap.CLIMBER_GROUND, RobotMap.CLIMBER_GROUND_SLOT));
		this.dButtonB.whenPressed(new SetFrontClimber(RobotMap.CLIMBER_LIFT, RobotMap.CLIMBER_GROUND_SLOT));
		this.dButtonY.whenPressed(new SetBackClimber(RobotMap.CLIMBER_LIFT, RobotMap.CLIMBER_GROUND_SLOT));


		//this.dButtonBack.whenPressed(new ZeroClimber(Dart.FRONT));
		this.dButtonBack.whenPressed(new ZeroBothClimbers());
	}
}
