package hawup.client;

import hawup.network.DebugSockets;

import java.io.DataOutputStream;
import java.math.BigInteger;
import java.util.Random;

public class RSA extends JobSpec {

	private final String num, lo, hi;

	/**
	 * 
	 * @param username
	 * @param pw
	 * @param num The number to be factored
	 * @param lo The lowest factor to try
	 * @param hi The highest factor to try
	 */
	public RSA(String username, String pw, String num, String lo, String hi) {
		super(username, pw);
		this.num = num;
		this.lo  = lo;
		this.hi  = hi;
	}

	@Override
	public void sendJobPayload(DataOutputStream out) {
		try {
			out.writeUTF("rsa");
			out.writeUTF(num);
			out.writeUTF(lo);
			out.writeUTF(hi);
		} catch(Throwable t) {
			DebugSockets.error("Problem sending rsa " + t);
		}

	}

	public static void main(String[] args) {
		Random r = new Random();
		BigInteger p = BigInteger.probablePrime(25, r);
		BigInteger q = BigInteger.probablePrime(25, r);
		BigInteger num = p.multiply(q);
		BigInteger upper = p.multiply(BigInteger.ONE.add(BigInteger.ONE)).add(q);
		RSA s = new RSA("ron", "xyzzy", p.multiply(q).toString(), "0", upper.toString());
		SubmitsJob c = new SubmitsJob("localhost", 3000, s);
		c.run();
	}

}
