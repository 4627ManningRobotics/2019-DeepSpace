/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Climber.Dart;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static boolean vacuumMode = false;
  public static boolean jankMode = false;

  public static DriveTrain driveTrain = new DriveTrain();
  public static Climber climber = new Climber();
  public static Elevator elevator = new Elevator();
  public static Claw claw;
  public static Vacuum vacuum;
  public static OI oi;
  private static final Compressor comp = new Compressor(0);
  // private Command dash = new DashboardData();

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    if (vacuumMode) {
      vacuum = new Vacuum();
    } else {
      claw = new Claw();
      claw.setGrip(false);
    }
    Robot.oi = new OI();
    Robot.comp.setClosedLoopControl(true);
    // this.chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", this.chooser); // this is the ONLY smart dashboard put that isn't a part of
                                                       // Dashboard Data

    // this.dash.start();
    // Scheduler.getInstance().add(new DashboardData());
    // CameraServer.getInstance().startAutomaticCapture();

    (new CameraThread()).start();

    this.initSmartDashboard();

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    updateSmartDashboard();
    /*
     * if(SmartDashboard.getBoolean("Compressor", false)){
     * if(!Robot.comp.enabled()){ Robot.comp.start(); } }else{
     * if(Robot.comp.enabled()){ Robot.comp.stop(); } }
     */
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
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
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    this.autonomousCommand = chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
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
    updateSmartDashboard();
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

  public void initSmartDashboard() {
    SmartDashboard.putNumber("P", Robot.elevator.getP());
    SmartDashboard.putNumber("I", Robot.elevator.getI());
    SmartDashboard.putNumber("D", Robot.elevator.getD());

    SmartDashboard.putNumber("Front climber position", Robot.climber.getFront());
    SmartDashboard.putNumber("Front climber setpoint", Robot.climber.getFrontSetpoint());
    SmartDashboard.putNumber("Front raw position", Robot.climber.getFrontRaw());
    SmartDashboard.putNumber("Front raw setpoint", Robot.climber.getFrontRawSetpoint());
    SmartDashboard.putNumber("Front applied output", Robot.climber.getAppliedOutput(Dart.FRONT));
    SmartDashboard.putNumber("Back climber position", Robot.climber.getBack());
    SmartDashboard.putNumber("Back climber setpoint", Robot.climber.getBackSetpoint());
    SmartDashboard.putNumber("Back raw position", Robot.climber.getBackRaw());
    SmartDashboard.putNumber("Back raw setpoint", Robot.climber.getBackRawSetpoint());
    SmartDashboard.putNumber("Back applied output", Robot.climber.getAppliedOutput(Dart.BACK));

    if (vacuumMode) {
      SmartDashboard.putNumber("wrist angle", Robot.vacuum.getPosition());
      SmartDashboard.putNumber("wrist ticks", Robot.vacuum.getTicks());
      SmartDashboard.putNumber("wrist setpoint", Robot.vacuum.getSetpoint());
      SmartDashboard.putNumber("Vacuum motor current", Robot.vacuum.getVacCurrent());
      SmartDashboard.putNumber("Vacuum motor current average", 0);
    } else {

    }

    SmartDashboard.putNumber("elevator position", Robot.elevator.getPosition());
    SmartDashboard.putNumber("elevator setpoint", Robot.elevator.getCurrentSetpoint());
    SmartDashboard.putNumber("elevator output value", Robot.elevator.getAppliedOutput());

    // SmartDashboard.putBoolean("Compressor", false);
    SmartDashboard.putNumber("set point", 10);

    SmartDashboard.putString("operator name", DriverStation.getInstance().getJoystickName(1));
    SmartDashboard.putBoolean("operator is box", DriverStation.getInstance().getJoystickIsXbox(1));
    SmartDashboard.putNumber("operator type", DriverStation.getInstance().getJoystickType(1));
  }

  public void updateSmartDashboard() {
    if (vacuumMode) {
      SmartDashboard.putNumber("wrist angle", Robot.vacuum.getPosition());
      SmartDashboard.putNumber("wrist ticks", Robot.vacuum.getTicks());
      SmartDashboard.putNumber("wrist setpoint", Robot.vacuum.getSetpoint());
      SmartDashboard.putNumber("Vacuum motor current", Robot.vacuum.getVacCurrent());
    } else {

    }

    SmartDashboard.putNumber("Front climber position", Robot.climber.getFront());
    SmartDashboard.putNumber("Front climber setpoint", Robot.climber.getFrontSetpoint());
    SmartDashboard.putNumber("Front raw position", Robot.climber.getFrontRaw());
    SmartDashboard.putNumber("Front raw setpoint", Robot.climber.getFrontRawSetpoint());
    SmartDashboard.putNumber("Front applied output", Robot.climber.getAppliedOutput(Dart.FRONT));
    SmartDashboard.putNumber("Back climber position", Robot.climber.getBack());
    SmartDashboard.putNumber("Back climber setpoint", Robot.climber.getBackSetpoint());
    SmartDashboard.putNumber("Back raw position", Robot.climber.getBackRaw());
    SmartDashboard.putNumber("Back raw setpoint", Robot.climber.getBackRawSetpoint());
    SmartDashboard.putNumber("Back applied output", Robot.climber.getAppliedOutput(Dart.BACK));

    SmartDashboard.putNumber("elevator position", Robot.elevator.getPosition());
    SmartDashboard.putNumber("elevator setpoint", Robot.elevator.getCurrentSetpoint());
    SmartDashboard.putNumber("elevator output value", Robot.elevator.getAppliedOutput());

    SmartDashboard.putData(Robot.driveTrain);

    SmartDashboard.putString("operator name", DriverStation.getInstance().getJoystickName(1));
    SmartDashboard.putBoolean("operator is box", DriverStation.getInstance().getJoystickIsXbox(1));
    SmartDashboard.putNumber("operator type", DriverStation.getInstance().getJoystickType(1));
  }
}

class CameraThread extends Thread {

  @Override
  public void run() {

    CameraServer.getInstance().startAutomaticCapture();
    CameraServer.getInstance().removeServer("Camera0");

    CvSink cvSink = CameraServer.getInstance().getVideo();
    CvSource outputStream = CameraServer.getInstance().putVideo("the working one", 320, 240);

    Mat source = new Mat();
    Mat output = new Mat();

    while(!Thread.interrupted()){
      cvSink.grabFrame(source);
      Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
      outputStream.putFrame(output);
    }

  }
}