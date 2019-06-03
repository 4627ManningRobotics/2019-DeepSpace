package frc.robot.Requesters;

import java.util.Scanner;

public class MouseRequester extends Requester {

    private double distance, offset;

    public MouseRequester(){
        super(5);
    }

    @Override
    protected void filterData(String data) {
        Scanner sc = new Scanner(data);
        for(int i = 0; i < 1; i++){ // repeat for both distance and angle
            sc.useDelimiter("\\d");
            String s = sc.next();
            sc.useDelimiter("[,}]");
            if(s.contains("Distance")){
                this.distance = sc.nextDouble();
                //distance will never be - because it's actually displacment
            }
        } 
        sc.close();
    }

    public double getDistance(){
        return this.distance - this.offset;
    }

    public void zero_distance(){
        this.offset = this.distance;
    }

    @Override
    public String getRequestMessage() {
        return "Mouse";
    }

    @Override
    public String getRequestType() {
        return "Mouse";
    }

}
