package clock.timer;

import java.util.Calendar;
import java.util.GregorianCalendar;

import clock.analog.AnalogClock;
import java.util.Observable;
import java.util.logging.*;

/**
 * <code>ClockTimer</code> is a subject for storing and maintaining the time of 
 * day. It notifies its observers every second. The class provides an interface 
 * for retrieving individual time units such as hour, minute and second.
 * 
 * @author Andreas Ruppen
 */
public class ClockTimer extends Observable implements Runnable {
    private long time;
    
    private Calendar calendar;
    
    // delay in milliseconds
    private long delay = 1000;
    
    private Thread thread;

	private static Logger loggingService = Logger.getLogger("ClockTimer");
	
	/**
	 * 
	 * @uml.property name="analogClock"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	AnalogClock analogClock;

	
    /**
     * Creates a new instance of <code>ClockTimer</code>.
     */
/*    public ClockTimer(AnalogClock analogClock) {
		// TODO Remove the analog clock argument from the constructor, once the observer pattern is used.
		this.analogClock = analogClock;
        calendar = new GregorianCalendar();
    }
*/
      //TODO  haha
    public ClockTimer() {
	// TODO Remove the analog clock argument from the constructor, once the observer pattern is used.	
        calendar = new GregorianCalendar();
    }
        
    /**
     * Returns the current hour.
     */
    private int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Returns the current minute.
     */
    private int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * Returns the current second.
     */
    private int getSecond() {
        return calendar.get(Calendar.SECOND);
    }

    /**
     * Notifies the observers every second.
     */
    public void run() {
        while (thread != null) {
            try {
                time = System.currentTimeMillis();
                calendar.setTimeInMillis(time);
                // TODO Notify observers here, instead of setting the time direcly.
				// Program against the observer interface, instead of programming against the analog clock class!
				analogClock.update(getHour(), getMinute(), getSecond());
                 this.setChanged();
                 this.notifyObservers();
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            	loggingService.severe("Timer got interrupted");
            }
        }
    }

    /**
     * Starts the timer.
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        if (thread != null) {
            thread = null;
        }
    }
}
