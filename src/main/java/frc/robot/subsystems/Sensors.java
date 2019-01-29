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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
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

  private final BufferedReader RaspPi_in;
  private final BufferedWriter RaspPi_out;
  protected final PiGetter getter = new PiGetter(this.RaspPi_in);
  protected final PiSender sender = new PiSender(this.RaspPi_out);
  protected final Thread in = new Thread(this.getter, "Pi get"); 
  protected final Thread out = new Thread(this.sender, "Pi get"); 

  ArrayList<Requester> requests = new ArrayList<Requester>();
  
  public Sensors(){
     this.in.setDaemon(true); // ENSURES THE THREAD CLOSES
     this.out.setDaemon(true);

     this.in.start();
     this.out.start();

     try{
      URL oracle = new URL("http://10.41.91.109/a"); //URL of Raspberry Pi
      URLConnection yc = oracle.openConnection();
      this.RaspPi_in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
      this.RaspPi_out = new BufferedWriter(new OutputStreamWriter(yc.getOutputStream()));
      
     } catch(Exception e) {
       e.printStackTrace();
     }
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

class PiGetter implements Runnable{

  private final BufferedReader serial_in;
  protected final Queue<String> inQueue = new SynchronousQueue<String>();

  public PiGetter(BufferedReader r){
    this.serial_in = r;
  }

  @Override
  public void run() {
    while (true) {
      String s = "";
      try {
        s = this.serial_in.readLine();
      } catch (IOException e) {
        e.printStackTrace();
  }
  if(!s.equals("")){
      this.inQueue.add(s);
    }
  }
}

class PiSender implements Runnable{

  private final Queue<String> outQueue = new SynchronousQueue<String>();
  private final BufferedWriter writer;

  public PiSender(BufferedWriter w){
    this.writer = w;
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
