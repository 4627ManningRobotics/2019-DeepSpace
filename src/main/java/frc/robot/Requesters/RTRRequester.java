package frc.robot.Requesters;

import java.util.Scanner;

public class RTRRequester extends Requester {

    private double angle;

    public RTRRequester(){
        super(4);
    }

    @Override
    protected void filterData(String data) {
        System.out.println(data);
        Scanner sc = new Scanner(data);
        for(int i = 0; i < 1; i++){ // repeat for both x, y, and angle
            sc.useDelimiter("\\d");
            String s = sc.next();
            sc.useDelimiter("[,}]");
            if(s.contains("Angle")){
                this.angle = sc.nextDouble();
                if(s.contains("-")){
                    this.angle *= -1;
                }
            }
        } 
        sc.close();
    }

    public double getAngle(){
        return Math.toDegrees(this.angle);
    }

    @Override
    public String getRequestMessage() {
        return "RTR";
    }

    @Override
    public String getRequestType() {
        return "RTR";
    }
}