package frc.robot.Requesters;

import java.util.Scanner;

public class LightRequester extends Requester {

    private boolean state = false, requestedState = false;

    public LightRequester(){
        super(Requester.LIGHT, 0);
    }

    @Override
    protected void filterData(String data) {
        Scanner sc = new Scanner(data);
        for(int i = 0; i < 1; i++){
            sc.useDelimiter("\\d");
            String s = sc.next();
            sc.useDelimiter("[,}]");
            System.out.println(s);
            if(s.contains("True")){
                this.state = true;
            }else if(s.contains("False")){
                this.state = false;
            }
        } 

        sc.close();
    }

    @Override
    public void setRequesting(boolean requesting){
        synchronized(this){
            this.requestedState = requesting;
        }
    }

    public boolean getState(){
        return this.state;
    }

}
