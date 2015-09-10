package hawup.examples.rsa;

import hawup.core.PartialResult;
import hawup.core.Task;

import java.math.BigInteger;

/**
 * Try private keys between low and high, looking for one
 *    whose public key matches the one specified
 * @author roncytron
 *
 */
public class LookForKeyBetweenLoHi extends Task<TwoInts> {

	final private BigInteger low;
	final private BigInteger high;
	private TwoInts ans;
	final private BigInteger pubkey;

	/**
	 * 
	 * @param pubkey the public key we want to match
	 * @param low the lowest private key we will try, inclusively
	 * @param high the highet private key we will try, inclusively
	 */
	public LookForKeyBetweenLoHi(BigInteger pubkey, BigInteger low, BigInteger high) {
		this.low = low;
		this.high = high;
		this.pubkey = pubkey;
	}

	@Override
	public void taskWork() {
		ans = TwoInts.ZERO;
		BigInteger i = low;
		//
		// Keep iterating if i is in the interval [low, high]
		//   until we find a match
		// When we find a match, ans is set to something other than 0.
		//
		while (ans==TwoInts.ZERO && i.compareTo(high)<= 0) {
			BigInteger[] trial = pubkey.divideAndRemainder(i);
			if (trial[1].equals(BigInteger.ZERO)) {
				//
				// Found match!
				//
				ans = new TwoInts(i,trial[0]);
			}
			//
			// i = i + 1
			//
			i = i.add(BigInteger.ONE);
		}
	}


	@Override
	public PartialResult<TwoInts> getTaskPartialResult() {
		return new PrivateKey(ans);
	}

	public String toString() {
		return "RSA search from " + low + " to " + high; 
	}

}
