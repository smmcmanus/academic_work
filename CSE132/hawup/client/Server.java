package hawup.client;

import hawup.core.HaWUp;
import hawup.core.PartialResult;
import hawup.core.ResultRunnable;
import hawup.examples.rsa.RSABreaker;
import hawup.examples.sleeps.Sleepers;
import hawup.examples.sum.SumFrom1ToN;
import hawup.util.Publisher;
import hawup.util.TimedRunnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Server receives information from clients, processes submitted jobs in separate threads 
 * and outputs answers to clients. 
 * @author seanmcmanus
 * sean.mcmanus@wustl.edu
 * Lab Section G
 * Lab 4 Server
 *
 */
public class Server {
	final private int port;

	// works fine with client 4

	public Server(int port) {
		this.port = port;
	}

	public void run() throws IOException {
		ServerSocket ss = new ServerSocket(port);
		while (true) {

			final Socket s = ss.accept();
			Thread t = new Thread() {

				public void run() {
					try {
						HaWUp h = new HaWUp(15,16);
						InputStream is = s.getInputStream();
						DataInputStream dis = new DataInputStream(is);
						OutputStream os = s.getOutputStream();
						DataOutputStream dos = new DataOutputStream(os);
						dos.writeUTF("Sean's Server");
						dos.writeShort(h.getNumNodes());
						dos.writeShort(5);//
						String name = dis.readUTF();
						String pass = dis.readUTF();
						if(!pass.equals("xyzzy")){
							dos.writeUTF("bye");
							s.close();
						} else {
							dos.writeUTF("ok");
							String job = dis.readUTF();
							if(job.equals("sleep")){
								int numTasks = dis.readInt();
								int sleepTime = dis.readInt();
								dos.writeUTF("ok");
								ResultRunnable rr = h.genJob(new Sleepers(sleepTime, numTasks));
								rr.run();
								String result = "" + rr.getResult().getValue();
								dos.writeUTF("done");
								
							} else if(job.equals("sum")){
								int lo = dis.readShort();
								int hi = dis.readShort();
								dos.writeUTF("ok");
								final ResultRunnable rr = h.genJob(new SumFrom1ToN(lo, hi));
								rr.run();
								String result = "" + rr.getResult().getValue();
								dos.writeUTF(result);
								
							} else if(job.equals("rsa")){						
//								Long num = Long.valueOf(dis.readUTF());
//								Long lo = Long.valueOf(dis.readUTF());
//								Long hi = Long.valueOf(dis.readUTF());
								String num = dis.readUTF();
								String lo = dis.readUTF();
								String hi = dis.readUTF();
//								System.out.println("Num is " + num);
//								System.out.println("Num is " + BigInteger.valueOf(num));
								
								dos.writeUTF("ok");
								Publisher.LOGGING = true;
								ResultRunnable rr = h.genJob(new RSABreaker(new BigInteger(num), new BigInteger(lo), new BigInteger(hi)));
								rr.run();
								//System.out.println(rr.getResult().toString());
								String result = "Result: " + rr.getResult().getValue();
								System.out.println(result);
								dos.writeUTF(result);
								
							}
							
						}
						


					}catch(Throwable t) {
						throw new Error("saw error " + t);
					}
				}
			};
			t.start();
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server(3000);
		server.run();

	}
}

