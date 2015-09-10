package hawup.examples.rsa;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import hawup.core.Task;
import hawup.core.TasksProvider;

/**
 * 
 * @author roncytron
 * Edited by Sean McManus
 * Added Constructor to take in both an upper bound and lower bound
 * sean.mcmanus@wustl.edu
 * Lab Section G
 * Lab 4 Server
 */
public class RSABreaker implements TasksProvider<TwoInts> {
	
	final private BigInteger pubkey;
	final private BigInteger upperbound;
	final private BigInteger lowerbound;

	/**
	 * Search for a private key whose corresponding public
	 *    key matches the specified pubkey
	 * @param pubkey the public key
	 * @param upperbound the largest integer we should try as the private key
	 */
	public RSABreaker(BigInteger pubkey, BigInteger upperbound) {
		this(pubkey, BigInteger.ZERO, upperbound);
	}
	
	public RSABreaker(BigInteger pubkey, BigInteger lowerbound, BigInteger upperbound){
		this.pubkey = pubkey;
		this.upperbound = upperbound;
		this.lowerbound = lowerbound;
	}
	@Override
	public Set<Task<TwoInts>> getTasks(int numNodes) {
		int numTasks = numNodes * 16;
		Set<Task<TwoInts>> tasks = new HashSet<Task<TwoInts>>();
		BigInteger range = upperbound.subtract(lowerbound);
		BigInteger numsPerTask = range.divide(BigInteger.valueOf(numTasks));
		BigInteger priv = lowerbound;
		if(numsPerTask.equals(BigInteger.ZERO)){
			numsPerTask = BigInteger.ONE;
		}
		while (priv.compareTo(upperbound) < 0) {
			tasks.add(new LookForKeyBetweenLoHi(pubkey, priv, priv.add(numsPerTask)));
			priv = priv.add(numsPerTask);
		}
		
		return tasks;
	}

}
