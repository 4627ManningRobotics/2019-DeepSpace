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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.SetElevator;
import frc.robot.commands.SetVacuumAngle;

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
	Button dStartButton = new JoystickButton(this.driverController, RobotMap.START_BUTTON);
	
	Button oButtonA = new JoystickButton(this.operatorController, RobotMap.BUTTON_A);
	Button oButtonB = new JoystickButton(this.operatorController, RobotMap.BUTTON_B);
	Button oButtonY = new JoystickButton(this.operatorController, RobotMap.BUTTON_Y);
	Button oButtonX = new JoystickButton(this.operatorController, RobotMap.BUTTON_X);
	Button oButtonBack = new JoystickButton(this.operatorController, RobotMap.BACK_BUTTON);
	Button oButtonStart = new JoystickButton(this.operatorController, RobotMap.START_BUTTON);
  Button oButtonRightStick = new JoystickButton(this.operatorController, RobotMap.RIGHT_STICK_BUTTON);
  
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
		this.oButtonX.whenPressed(new SetVacuumAngle(90));
		this.oButtonB.whenPressed(new SetVacuumAngle(0));
		//this.oButtonRightStick.whenPresssed(new WristControls());
	}
}
