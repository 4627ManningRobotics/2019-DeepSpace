/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.Requester;
import frc.robot.commands.Senses;

/**
 * Add your docs here.
 */
public class Sensors extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private final SerialPort RaspberryPi = null;//new SerialPort(9600, Port.kUSB);
  protected final PiSerialGetter getter = new PiSerialGetter(this.RaspberryPi);
  protected final PiSerialGetter sender = new PiSerialGetter(this.RaspberryPi);
  protected final Thread serial_in = new Thread(this.getter, "Pi get"); 
  protected final Thread serial_out = new Thread(this.sender, "Pi get"); 

  ArrayList<Requester> requests = new ArrayList<Requester>();
  
  public Sensors(){
     this.serial_in.setDaemon(true); // ENSURES THE THREAD CLOSES
     this.serial_out.setDaemon(true);

     this.serial_in.start();
     this.serial_out.start();
   }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    super.setDefaultCommand(new Senses());
  }

  public void addRequester(Requester req){
    this.requests.add(req);
  }

}

class PiSerialGetter implements Runnable{

  private final byte BN[] = "\n".getBytes();
  private final SerialPort serial_in;
  final Queue<String> inQueue = new SynchronousQueue<String>();

  public PiSerialGetter(SerialPort s){
    this.serial_in = s;
  }

  @Override
  public void run() {
    ArrayList<byte[]> buffer = new ArrayList<byte[]>();
    while(true){
      buffer.addAll(Arrays.asList(serial_in.read(1024)));
      int index = buffer.indexOf(this.BN);
        if(index != -1){
          String message = "";
          for(int i = 0; i < index + 1; i++){
            message += buffer.remove(0).toString(); //always remove 0 since removing the first will shift the rest
          }
          buffer.remove(0); //remove \n
          this.inQueue.add(message);
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
