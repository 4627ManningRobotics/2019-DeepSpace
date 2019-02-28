package frc.robot.Requesters;

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