package hawup.examples.rsa;

import hawup.core.HaWUp;
import hawup.core.ResultRunnable;
import hawup.util.Publisher;

import java.math.BigInteger;
import java.util.Random;


public class Main {

	public static void main(String[] args) {
		HaWUp h = new HaWUp(15,10);
		Publisher.LOGGING = true;
		final int bitsInPriv = 16;
		
		//
		// Generate a random private key
		//
		Random r = new Random();
		BigInteger p = BigInteger.probablePrime(bitsInPriv, r);
		BigInteger q = BigInteger.probablePrime(bitsInPriv, r);
		TwoInts priv = new TwoInts(p,q);
		//
		//   and compute its public key
		//
		BigInteger pubkey = p.multiply(q);
		//
		// By trying *all* possible private keys, find a private key that corresponds
		//   to that random public key for priv
		//
		ResultRunnable rr = h.genJob(new RSABreaker(pubkey,BigInteger.valueOf(1),BigInteger.valueOf(2).pow(bitsInPriv)));
		//
		// Run on our Nodes
		//
		rr.run();
		//
		// Wait for the answer and report it
		//
		String got = ""+rr.getResult().getValue();
		System.out.println("HaWUp found   private key " + got);
		System.out.println("My secret was private key " + priv + " whose pub is " + pubkey);


	}

}
