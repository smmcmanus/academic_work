package hawup.examples.rsa;

import hawup.core.PartialResult;

public class PrivateKey implements PartialResult<TwoInts> {

	final private TwoInts pq;

	public PrivateKey(TwoInts pq) {
		this.pq = pq;
	}
	
	/**
	 * 0 indicates that there is no match yet for the private key
	 * Thus, to reduce, we take either answer that is non-zero
	 */
	@Override
	public PartialResult<TwoInts> reduce(PartialResult<TwoInts> other) {
		if (this.getValue() != TwoInts.ZERO)
			return this;
		if (other.getValue() != TwoInts.ZERO)
			return other;
		return this;
	}

	@Override
	public TwoInts getValue() {
		return pq;
	}
	
	public String toString() {
		return "" + pq;
	}

}
