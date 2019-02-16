/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.StopBits;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Senses;

/**
 * The collection of all Sensors and information streams that aren't specific to any
 * subsystem or command. The main part of which being the serial input from the rasberry pi.
 * There are a few threads and a lot of code that can break the robot so be careful!!!
 */
public class Sensors extends Subsystem {

  public static final Requester ballReqester = new BallRequester();
  public static final Requester stripReqester = new StripRequester();

  private SerialPort RaspberryPi;
  public final Requester[] requests = new Requester[]{Sensors.ballReqester, Sensors.stripReqester};
  protected PiSerialGetter getter;
  protected PiSerialSender sender;
  protected Thread serial_in;
  protected Thread serial_out;

  private PigeonIMU gyro = new PigeonIMU(0);
  private double[] gyroRotation = new double[3];

  public Sensors(){ 

    this.RaspberryPi = null;
    this.getter = null;
    this.sender = null;
    this.serial_in = null;
    this.serial_out = null;
    try{
      this.RaspberryPi = new SerialPort(115200, Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
      this.getter = new PiSerialGetter(this.RaspberryPi);
      this.sender = new PiSerialSender(this.RaspberryPi, this.requests);
      this.serial_in = new Thread(this.getter, "Pi get");
      this.serial_out = new Thread(this.sender, "Pi get");

      this.serial_in.setDaemon(true); // ENSURES THE THREAD CLOSES
      this.serial_out.setDaemon(true);

      this.serial_in.start(); // creates the threads
      this.serial_out.start();
      
    }catch(Exception e){
      e.printStackTrace();
    }
    //this.serial_in.run(); // We cant run the serial yet because the robot needs
    //this.serial_out.run();// more time to start up, moved to the default command
   }

  @Override
  public void initDefaultCommand() {
    if(this.RaspberryPi != null){
      SmartDashboard.putBoolean("Serial", true);
      super.setDefaultCommand(new Senses(this.getter.inQueue));
    }else{
      SmartDashboard.putBoolean("Serial", false);
    }
  }

  public double getRotation(){
    this.gyro.getAccumGyro(this.gyroRotation);
    return this.gyroRotation[0];
  }

}

/*
 * This runs on the thread with the intention of only receiving and storing incoming information
 */
class PiSerialGetter implements Runnable{

  private final String DELIMITER = "\n";
  private final SerialPort serial_in;
  protected final Queue<String> inQueue = new LinkedBlockingQueue<String>();

  public PiSerialGetter(SerialPort s){
    this.serial_in = s;
  }

  // This is mostly just pure programming chaos but it works I swear!
  @Override
  public void run() {
    // Declare variables outside of loop
    String buffer = "";
    byte[] in;
    int index;
    String newBuff;
    String message;
    while(true){
        in = serial_in.read(1024); //get serial information
        buffer += new String(in); // convert serial data to string
        index = buffer.indexOf(this.DELIMITER); //find delimiter
          while(index != -1){ // Make sure delimiter is found
            newBuff = buffer.substring(index + 1); // Save the rest of the string discluding the delimiter
            message = buffer.substring(0, index); // Use the information up to the delimiter
            SmartDashboard.putString("Serial Message", message);
            buffer = newBuff; // reset the buffer to the new buffer
            this.inQueue.add(message); // add the chunk of information to the queue
            index = buffer.indexOf(this.DELIMITER); //find delimiter if one exists
        }
    }
  }
}
/*
 * Sends a key to the rasberry pi on a seperate thread
 */
class PiSerialSender implements Runnable{

  private final SerialPort serial;
  private final Requester[] requesters;

  public PiSerialSender(SerialPort s, Requester[] r ){
    this.serial = s;
    this.requesters = r;
  }

  @Override
  public void run() {
    int i;
    while(true){
      SmartDashboard.putBoolean("is requesting", Sensors.ballReqester.isRequesting());
      for(i = 0; i < this.requesters.length; i++){
        if(this.requesters[i].isRequesting() && !this.requesters[i].isRequesting()){
          this.serial.writeString(this.requesters[i].getRequesteType());
        }
      }
    }
  }
}
