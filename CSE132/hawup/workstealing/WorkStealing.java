package hawup.workstealing;

import hawup.core.Node;

import java.util.Set;

public class WorkStealing implements Runnable {
	
	final private Set<Node> cluster;
	final private int period;

	/**
	 * 
	 * @param cluster the set of nodes for this HaWUp
	 * @param period the number of milliseconds between stealing attempts
	 */
	public WorkStealing(Set<Node> cluster, int period) {
		this.cluster = cluster;
		this.period  = period;
	}

	/**
	  *  This method begins workstealing
	  *  it spawns a new thread per node that accomplishes
	  *  work stealing for that node
	 */
	@Override
	public void run() {
		for (Node n : cluster) {
			//
			// Spawn a work stealing thread for node n
			//
			Runnable r = new WorkStealer(n, cluster, period);
			new Thread(r).start();
		}
	}

}
