package frc.robot.Requesters;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.StopBits;
import frc.robot.Utilities.Counter;

/*
 * Requester is designed to be used for use in retrieving serial information 
 * through the Sensors class and is only inteded to be used this way
 */
public abstract class Requester {

    private static List<Requester> allRequesters = new ArrayList<>();

    private String data;
    private Counter counter;

    private static PiSerialGetter serial_in;
    public static final Queue<String> inQueue = new LinkedBlockingQueue<String>();

    /*
     * Store the key for use in retrieving/sending information
     */
    protected Requester() {
        this(0);
    }

    protected Requester(int count) {
        this.counter = new Counter(count, () -> {
            this.request();
        });
        Requester.allRequesters.add(this);
    }

    public static void initRequesters() {
        Requester.serial_in = new PiSerialGetter(Requester.inQueue);

        Requester.serial_in.setDaemon(true); // ENSURES THE THREAD CLOSES

        Requester.serial_in.start(); // creates the threads
    }

    public void request(boolean request) {
        if (request) {
            this.counter.count();
        } else {
            this.counter.reset();
        }
    }

    /**
     * DO NOT TOUCH!!! This sends the specified message of the requester subclass to
     * the raspberry pi.
     */
    public void request() {
        Requester.serial_in.RaspberryPi.writeString(this.getRequestMessage() + "\n");
    }

    /*
     * Sets the data to be processed
     */
    public void setData(String d) {
        this.data = d;
        this.filterData(this.data);
    }

    // Filtering decided by extentions
    protected abstract void filterData(String data);

    public abstract String getRequestMessage();

    public abstract String getRequestType();

    public static Requester getInstance(Class<? extends Requester> c) {
        for (Requester r : Requester.allRequesters) {
            if (r.getClass() == c) {
                return r;
            }
        }
        return null;
    }

    public static Requester[] getAllRequesters() {
        return (Requester[]) Requester.allRequesters.toArray();
    }

    /*
     * This runs on the thread with the intention of only receiving and storing
     * incoming information
     */
    private static class PiSerialGetter extends Thread {

        private final String DELIMITER = "\n";
        protected final Queue<String> inQueue;
        private Runnable runner;
        private SerialPort RaspberryPi;

        public PiSerialGetter(Queue<String> inQueue) {
            this.inQueue = inQueue;
            this.RaspberryPi = null;
            this.runner = () -> this.findSerialPort();
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    this.runner.run();
                } catch (Exception ex) {
                    this.runner = () -> this.findSerialPort();
                }
            }
        }

        private void findSerialPort() {
            try {
                Thread.sleep(1000);
                this.RaspberryPi = new SerialPort(115200, Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
                this.runner = () -> this.serialIn();
            } catch (Exception ex) {
            }
        }

        // This is mostly just pure programming chaos but it works I swear!
        private void serialIn() {
            // Declare variables outside of loop
            String buffer = "";
            byte[] in;
            int index;
            String newBuff, message;
            while (!Thread.interrupted()) {
                in = this.RaspberryPi.read(this.RaspberryPi.getBytesReceived()); // get serial information
                buffer += new String(in); // convert serial data to string
                index = buffer.indexOf(this.DELIMITER); // find delimiter
                while (index != -1) { // Make sure delimiter is found
                    newBuff = buffer.substring(index + 1); // Save the rest of the string discluding the delimiter
                    message = buffer.substring(0, index); // Use the information up to the delimiter
                    // SmartDashboard.putString("Serial Message", message);
                    buffer = newBuff; // reset the buffer to the new buffer
                    this.inQueue.add(message); // add the chunk of information to the queue
                    index = buffer.indexOf(this.DELIMITER); // find delimiter if one exists
                }
            }
        }

    }

}
