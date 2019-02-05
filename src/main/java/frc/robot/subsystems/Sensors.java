/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Arrays;
import java.util.Queue;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.StopBits;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Requester;
import frc.robot.commands.Senses;

/**
 * Add your docs here.
 */
public class Sensors extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private final SerialPort RaspberryPi = new SerialPort(9600, Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
  protected final PiSerialGetter getter = new PiSerialGetter(this.RaspberryPi);
  protected final PiSerialSender sender = new PiSerialSender(this.RaspberryPi);
  protected final Thread serial_in = new Thread(this.getter, "Pi get"); 
  protected final Thread serial_out = new Thread(this.sender, "Pi get"); 

  ArrayList<Requester> requests = new ArrayList<Requester>();
  
  public Sensors(){
    this.serial_in.setDaemon(true); // ENSURES THE THREAD CLOSES
    this.serial_out.setDaemon(true);

    this.serial_in.start();
    this.serial_out.start();
    //this.serial_in.run();
    //this.serial_out.run();
   }

   public void run(){
    this.serial_in.run();
    this.serial_out.run();
   }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    super.setDefaultCommand(new Senses(this.getter.inQueue));
  }

  public void addRequester(Requester req){
    this.requests.add(req);
  }

}

class PiSerialGetter implements Runnable{

  private final String DELIMITER = "@";
  private final SerialPort serial_in;
  protected final Queue<String> inQueue = new SynchronousQueue<String>();

  public PiSerialGetter(SerialPort s){
    this.serial_in = s;
  }

  @Override
  public void run() {
    String buffer = "";
    while(true){
      try{
        byte[] in = serial_in.read(1024);
        buffer += new String(in, "UTF-8"); // convert serial data to string
        int index = buffer.indexOf(this.DELIMITER); //find delimiter
          while(index != -1){
            String newBuff = buffer.substring(index + 1);
            String message = buffer.substring(0, index);
            SmartDashboard.putString("Serial Message", message);
            buffer = newBuff;
            this.inQueue.add(message);
            index = buffer.indexOf(this.DELIMITER); //find delimiter
        }
      }catch(Exception e){
        e.printStackTrace();
      }

    }
  }

}

class PiSerialSender implements Runnable{

  private final Queue<String> outQueue = new SynchronousQueue<String>();
  private final SerialPort serial;

  public PiSerialSender(SerialPort s){
    this.serial = s;
  }

  @Override
  public void run() {
    while(true){ 
      if(!this.outQueue.isEmpty()){ //if there is something
          this.serial.writeString(this.outQueue.remove());
      }
    }
  }

  void request(String s){
    this.outQueue.add(s);
  }

}
