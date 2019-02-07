package frc.robot.commands;

import java.util.Scanner;

public abstract class Requester{
  
    public static final String BALL = "BALL";
    public static final String STRIP = "STRIP";

    public final String request;
    private boolean is_requesting = false;
    private String data;

    /*
    * Requester is designed to be used for use in retrieving serial information 
    * through the Sensors class and is only inteded to be used this way
    */
    protected Requester(String RequestType) {
        this.request = RequestType;
    }

    public void request(){
        this.is_requesting = true;
    }

    public void setData(String d){
        this.data = d;
        this.filterData(this.data);
        this.is_requesting = false;
    }

    public boolean isRequesting(){
        return this.is_requesting;
    }

    protected abstract void filterData(String data);

}

class BallRequester extends Requester{

    private double X,Y;

    public BallRequester(){
        super(Requester.BALL);
    }

    @Override
    protected void filterData(String data) {
        Scanner sc = new Scanner(data);
        for(int i = 0; i < 2; i++){ // repeat for both x and y
            sc.useDelimiter("[XY]"); //find only the charecters x and y 
            String s = sc.next();
            sc.useDelimiter("123..."); //find only the double value
            if(s.equals("X")){
                this.X = sc.nextDouble();
            }else if(s.equals("Y")){
                this.Y = sc.nextDouble();
            }
        }
        sc.close();
    }

    public double getX(){
        return this.X;
    }
    
    public double getY(){
        return this.Y;
    }
}

class StripRequester extends Requester{

    private double delta, angle;

    public StripRequester(){
        super(Requester.STRIP);
    }

    @Override
    protected void filterData(String data) {
        Scanner sc = new Scanner(data);
        for(int i = 0; i < 2; i++){ // repeat for both x and y
            sc.useDelimiter("(Delta | Angle)"); //find only the charecters x and y 
            String s = sc.next();
            sc.useDelimiter("123..."); //find only the double value
            if(s.equals("Delta")){
                this.delta = sc.nextDouble();
            }else if(s.equals("Angle")){
                this.angle = sc.nextDouble();
            }
        }
        sc.close();
    }

    public double getDelta(){
        return this.delta;
    }

    public double getAngle(){
        return this.angle;
    }
}