/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Senses;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static DriveTrain driveTrain = new DriveTrain();
  public static Climber climber = new Climber();
  public static Elevator elevator = new Elevator();
  public static Sensors sensors = new Sensors();
  public static Vacuum vacuum = new Vacuum();
  public static OI oi;
  //private Command dash = new DashboardData();

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    Robot.oi = new OI();
    //this.chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", this.chooser); // this is the ONLY smart dashboard put that isn't a part of Dashboard Data

    //this.dash.start();
    //Scheduler.getInstance().add(new DashboardData());
    CameraServer.getInstance().startAutomaticCapture();
    
    this.initSmartDashboard();
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    updateSmartDashboard();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    this.autonomousCommand = chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (this.autonomousCommand != null) {
      this.autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (this.autonomousCommand != null) {
      this.autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void initSmartDashboard(){
    SmartDashboard.putNumber("P", Robot.elevator.getP());
    SmartDashboard.putNumber("I", Robot.elevator.getI());
    SmartDashboard.putNumber("D", Robot.elevator.getD());
    
    SmartDashboard.putNumber("wrist angle", Robot.vacuum.getPosition());
    SmartDashboard.putNumber("wrist ticks", Robot.vacuum.getTicks());
    SmartDashboard.putNumber("wrist setpoint", Robot.vacuum.getSetpoint());
    SmartDashboard.putNumber("Vacuum motor current", Robot.vacuum.getVacCurrent());
    
    SmartDashboard.putNumber("elevator position", Robot.elevator.getPosition());
    SmartDashboard.putNumber("elevator setpoint", Robot.elevator.getCurrentSetpoint());
    SmartDashboard.putNumber("elevator output value", Robot.elevator.getAppliedOutput());

    SmartDashboard.putString("Senses recent", Senses.recent);
  }

  public void updateSmartDashboard(){
    SmartDashboard.putNumber("wrist angle", Robot.vacuum.getPosition());
    SmartDashboard.putNumber("wrist ticks", Robot.vacuum.getTicks());
    SmartDashboard.putNumber("wrist setpoint", Robot.vacuum.getSetpoint());
    SmartDashboard.putNumber("Vacuum motor current", Robot.vacuum.getVacCurrent());
    
    SmartDashboard.putNumber("elevator position", Robot.elevator.getPosition());
    SmartDashboard.putNumber("elevator setpoint", Robot.elevator.getCurrentSetpoint());
    SmartDashboard.putNumber("elevator output value", Robot.elevator.getAppliedOutput());

    SmartDashboard.putString("Senses recent", Senses.recent);
  }
}
