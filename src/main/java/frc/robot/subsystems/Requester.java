package frc.robot.subsystems;

import java.util.Scanner;

/*
 * Requester is designed to be used for use in retrieving serial information 
 * through the Sensors class and is only inteded to be used this way
 */
public abstract class Requester{
  
    // All known keys
    public static final String BALL = "BALL";
    public static final String STRIP = "STRIP";

    private final String request;
    private boolean is_requesting = false;
    private String data;

    /*
     * Store the key for use in retrieving/sending information
     */
    protected Requester(String RequestType) {
        this.request = RequestType;
    }

    public String getRequestType(){
        return this.request;
    }

    public synchronized void setRequesting(boolean reqesting){
        this.is_requesting = reqesting;
    }

    /*
     * Sets the data to be processed
     */
    public synchronized void setData(String d){
        this.data = d;
        this.filterData(this.data);
    }

    public synchronized boolean isRequesting(){
        return this.is_requesting;
    }

    // Filtering decided by extentions
    protected abstract void filterData(String data);

}

class BallRequester extends Requester{

    private double X, Y, angle;

    public BallRequester(){
        super(Requester.BALL);
    }

    @Override
    protected void filterData(String data) {
        Scanner sc = new Scanner(data);
        for(int i = 0; i < 3; i++){ // repeat for both x, y, and angle
            sc.useDelimiter("\\d");
            String s = sc.next();
            sc.useDelimiter("[,}]");
            if(s.contains("X")){
                this.X = sc.nextDouble();
                if(s.contains("-")){
                    this.X *= -1;
                }
            }else if(s.contains("Y")){
                this.Y = sc.nextDouble();
                if(s.contains("-")){
                    this.Y *= -1;
                }
            }else if(s.contains("Angle")){
                this.angle = sc.nextDouble();
                if(s.contains("-")){
                    this.angle *= -1;
                }
            }
        } 
        //SmartDashboard.putString("Strip data", this.X + ":" + this.Y + ":" + this.angle);

        sc.close();
    }

    public double getX(){
        return this.X;
    }
    
    public double getY(){
        return this.Y;
    }

    public double getAngle(){
        return this.angle;
    }
}

class StripRequester extends Requester{

    private double X, Y, angle;

    public StripRequester(){
        super(Requester.STRIP);
    }

    @Override
    protected void filterData(String data) {
        Scanner sc = new Scanner(data);
        for(int i = 0; i < 3; i++){ // repeat for both x, y, and angle
            sc.useDelimiter("\\d");
            String s = sc.next();
            sc.useDelimiter("[,}]");
            if(s.contains("X")){
                this.X = sc.nextDouble();
                if(s.contains("-")){
                    this.X *= -1;
                }
            }else if(s.contains("Y")){
                this.Y = sc.nextDouble();
                if(s.contains("-")){
                    this.Y *= -1;
                }
            }else if(s.contains("Angle")){
                this.angle = sc.nextDouble();
                if(s.contains("-")){
                    this.angle *= -1;
                }
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

    public double getAngle(){
        return this.angle;
    }
}