package frc.robot.Requesters;

import java.util.Scanner;

public class BallRequester extends Requester {

    private double distance, angle;

    public BallRequester(){
        super(3);
    }

    @Override
    protected void filterData(String data) {
        Scanner sc = new Scanner(data);
        for(int i = 0; i < 2; i++){ // repeat for both distance and angle
            sc.useDelimiter("\\d");
            String s = sc.next();
            sc.useDelimiter("[,}]");
            if(s.contains("Distance")){
                this.distance = sc.nextDouble();
                if(s.contains("-")){
                    this.distance *= -1;
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

    public double getDistance(){
        return this.distance;
    }

    public double getAngle(){
        return Math.toDegrees(this.angle);
    }

    @Override
    public String getRequestMessage() {
        return "BALL";
    }

    @Override
    public String getRequestType() {
        return "BALL";
    }
}
