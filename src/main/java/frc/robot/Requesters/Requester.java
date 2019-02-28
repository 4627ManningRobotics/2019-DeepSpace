package frc.robot.Requesters;

import frc.robot.Utilities.Counter;

/*
 * Requester is designed to be used for use in retrieving serial information 
 * through the Sensors class and is only inteded to be used this way
 */
public abstract class Requester{
  
    // All known keys
    public static final String BALL = "BALL";
    public static final String STRIP = "STRIP";
    public static final String MOUSE = "MOUSE";

    private final String request;
    private boolean is_requesting = false;
    private String data;
    private Counter counter;

    /*
     * Store the key for use in retrieving/sending information
     */
    protected Requester(String RequestType) {
        this(RequestType, 0);
    }

    protected Requester(String RequestType, int count){
        this.request = RequestType;
        this.counter = new Counter(count);
    }

    public String getRequestType(){
        return this.request;
    }

    public synchronized void setRequesting(boolean reqesting){
        if(this.counter.getLimit() < 1){
            this.counter.count();
            this.is_requesting = this.counter.isDoneCounting();
            if(this.is_requesting){
                this.counter.reset();
            }
        }else{
            this.is_requesting = reqesting;
        }
    }

    /*
     * Sets the data to be processed
     */
    public synchronized void setData(String d){
        if(this.counter.getLimit() < 1){
            this.is_requesting = false;
        }
        this.data = d;
        this.filterData(this.data);
    }

    public synchronized boolean isRequesting(){
        return this.is_requesting;
    }

    // Filtering decided by extentions
    protected abstract void filterData(String data);

}