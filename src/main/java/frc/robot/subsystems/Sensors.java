/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Queue;
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
 * The collection of all Sensors and information streams that aren't specific to any 
 * subsystem or command. The main part of which being the serial input from the rasberry pi.
 * There are a few threads and a lot of code that can break the robot so be careful!!!
 */
public class Sensors extends Subsystem {

  private ArrayList<Requester> requests = new ArrayList<Requester>();
  private final SerialPort RaspberryPi = new SerialPort(9600, Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
  protected final PiSerialGetter getter = new PiSerialGetter(this.RaspberryPi);
  protected final PiSerialSender sender = new PiSerialSender(this.RaspberryPi, this.requests);
  protected final Thread serial_in = new Thread(this.getter, "Pi get"); 
  protected final Thread serial_out = new Thread(this.sender, "Pi get"); 

  
  public Sensors(){
    this.serial_in.setDaemon(true); // ENSURES THE THREAD CLOSES
    this.serial_out.setDaemon(true);

    this.serial_in.start(); // creates the threads
    this.serial_out.start();
    //this.serial_in.run(); // We cant run the serial yet because the robot needs
    //this.serial_out.run();// more time to start up, moved to the default command
   }

   // Runs both threads
   public void run(){ 
    this.serial_in.run();
    this.serial_out.run();
   }
  
  @Override
  public void initDefaultCommand() {
    super.setDefaultCommand(new Senses(this.getter.inQueue));
  }

  public void addRequester(Requester req){
    this.requests.add(req);
  }

}

/*
 * This runs on the thread with the intention of only receiving and storing incoming information
 */
class PiSerialGetter implements Runnable{

  private final String DELIMITER = "@";
  private final SerialPort serial_in;
  protected final Queue<String> inQueue = new SynchronousQueue<String>();

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
      try{
        in = serial_in.read(1024); //get serial information
        buffer += new String(in, "UTF-8"); // convert serial data to string
        index = buffer.indexOf(this.DELIMITER); //find delimiter
          while(index != -1){ // Make sure delimiter is found 
            newBuff = buffer.substring(index + 1); // Save the rest of the string discluding the delimiter
            message = buffer.substring(0, index); // Use the information up to the delimiter
            SmartDashboard.putString("Serial Message", message);
            buffer = newBuff; // reset the buffer to the new buffer
            this.inQueue.add(message); // add the chunk of information to the queue
            index = buffer.indexOf(this.DELIMITER); //find delimiter if one exists
        }
      }catch(Exception e){
        e.printStackTrace();
      }
      try {
        super.wait(1); // Slow down the process to ensure the main process is still a higher priority
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
/*
 * Sends a key to the rasberry pi on a seperate thread
 */
class PiSerialSender implements Runnable{
  
  private final SerialPort serial;
  private final ArrayList<Requester> requesters;

  public PiSerialSender(SerialPort s, ArrayList<Requester> r ){
    this.serial = s;
    this.requesters = r;
  }

  @Override
  public void run() {
    while(true){ 
      for(Requester r: this.requesters){
        if(r.isRequesting() && !r.isRequesting()){
        this.serial.writeString(r.request);
        }
      }
      try {
        super.wait(1); // Slow down the process to ensure the main process is still a higher priority
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
