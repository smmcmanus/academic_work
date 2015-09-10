package hawup.examples.rsa;

import java.math.BigInteger;

public class TwoInts {
	
	public static final TwoInts ZERO = new TwoInts(BigInteger.ZERO, BigInteger.ZERO);
	public final BigInteger p,q;
	
	/**
	 * WLOG store p < q
	 * @param p
	 * @param q
	 */
	public TwoInts(BigInteger p, BigInteger q) {
		boolean pLessThanQ = p.compareTo(q) < 0;
		this.p = pLessThanQ ? p : q;
		this.q = pLessThanQ ? q : p;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + ((q == null) ? 0 : q.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwoInts other = (TwoInts) obj;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (q == null) {
			if (other.q != null)
				return false;
		} else if (!q.equals(other.q))
			return false;
		return true;
	}
	
	public String toString() {
		if (this==ZERO) {
			return "ZERO";
		}
		else return "("+p+","+q+")";
	}

}
