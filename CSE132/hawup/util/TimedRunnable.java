package hawup.util;

import java.time.Duration;
import java.time.Instant;

/**
 * You have to do work in this class
 * 
 * Runs a specified Runnable, keeping track of
 *   when it started and ended,
 *   so that the Duration of the Runnable can be returned
 *   
 * @author roncytron
 *
 */
public class TimedRunnable implements Runnable {

	final private Runnable runnable;
	private Instant start;
	private Instant end;

	/**
	 * Construct but do not yet run the Runnable
	 * @param r The Runnable that will be run
	 */
	public TimedRunnable(Runnable r) {
		this.runnable = r;
	}

	/**
	 * 1) Mark time (variable start), 
	 * 2) Run the Runnable, 
	 * 3) Mark time (variable end)
	 */
	public void run() {
		this.start = Instant.now();    // (1)
		try {
			runnable.run();            // (2)
		}
		finally {
			this.end = Instant.now();  // (3)
			Wrappers.notifyAll(this);
		}
	}

	/**
	 * If the Runnable as not yet completed execution, wait
	 * 
	 * @return the Duration of time taken by the Runnable
	 */
	public Duration getTime() {
		while(this.end == null){
			Wrappers.wait(this);
		}
		
		// FIXME so that the calling thread
		//   waits until the run() computation
		//   has finished.
		//
		// You can use the non-null value of this.end
		//   to know run() is over
		//
		return Duration.between(start, end);
	}

}
