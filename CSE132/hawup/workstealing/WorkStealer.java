package hawup.workstealing;

import static studio4.misc.Sleeper.sleep;

import java.util.Set;

import studio4.locking.LockPub;
import hawup.core.Node;
import hawup.core.Task;
import hawup.util.Wrappers;

/**
 * Edited by Sean McManus
 * sean.mcmanus@wustl.edu
 * Lab Section G
 * Lab 3d Workstealing
 * Implemented work stealing to the HAWUP 
 * @author seanmcmanus
 *
 */
public class WorkStealer implements Runnable {

	final private Node node;
	final private Set<Node> cluster;
	final int     period;
	
	/**
	 * 
	 * @param node The node that will try to steal work
	 * @param cluster The set of all nodes
	 * @param period number of milliseconds between stealing attempts
	 */
	public WorkStealer(Node node, Set<Node> cluster, int period) {
		this.node = node;
		this.cluster = cluster;
		this.period = period;
	}

	/**
	 * Continually try to steal work, sleeping for this.period
	 *    milliseconds between attempts
	 */
	@Override
	public void run() {
		while (true) {
			//
			// for each node n in our cluster
			//    Obtain two locks:  one on node, one on n
			//    Be careful to do that in the proper order.
			//    For full credit you must use synchronized explicitly here to get locks
			//       Do not use the DoubleLock object we used in studio
			//       But you can study it to recall how we applied Havender's algorithm to avoid deadlock
			// my addition
			for(Node n : this.cluster){
				if(n.hashCode() < this.node.hashCode()){ //Havenders algorithm applied to avoid deadlock
					synchronized(this.node){
						synchronized(n){
							if(!this.node.isBusy() && (n.getNumWaiting() > 0)){ //Work is stolen when applicable
								Task<?> t = n.findAndRemoveTask();  // may need to make this method public
								node.addTask(t);
								node.publish("thief", t);
								n.publish("victim", t);
							}
						}
					}
				}else{ 
					synchronized(n){
						synchronized(this.node){
							if(!this.node.isBusy() && (n.getNumWaiting() > 0)){
								Task<?> t = n.findAndRemoveTask();  // may need to make this method public
								node.addTask(t);
								node.publish("thief", t);
								n.publish("victim", t);
							}
						}
					}
				}	
			}
			//    if node is not busy, and n has work in its queue
			//       Task<?> t = n.findAndRemoveTask()  // may need to make this method public
			//       node.addTask(t)
			//       node.publish("thief", t);
			//       n.publish("victim", t);
			//    end if
			//       The above statements appear to execute atomically
			//           because we have a lock on both nodes
			// end of our for loop -- all locks should be released at this point
			//
			//  Now sleep so we don't spin out of control trying to steal work
			//
			Wrappers.sleep(period);
		}

	}

	public String toString() {
		return "Work stealer for " + node;
	}

}
