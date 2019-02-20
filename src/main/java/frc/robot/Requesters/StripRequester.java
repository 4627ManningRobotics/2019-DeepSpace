package frc.robot.Requesters;

import java.util.Scanner;

public class StripRequester extends Requester {

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