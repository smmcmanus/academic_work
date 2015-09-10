package hawup.client;

import hawup.network.DebugSockets;

import java.io.DataOutputStream;

public class Sleep extends JobSpec {

	private final int numTasks, sleepTime;

	/**
	 * 
	 * @param username
	 * @param pw
	 * @param numTasks total number of tasks to run on the server
	 * @param sleepTime max sleep time in milliseconds for each task
	 */
	public Sleep(String username, String pw, int numTasks, int sleepTime) {
		super(username, pw);
		this.numTasks = numTasks;
		this.sleepTime = sleepTime;
	}

	@Override
	public void sendJobPayload(DataOutputStream out) {
		try {
			out.writeUTF("sleep");
			out.writeInt(numTasks);
			out.writeInt(sleepTime);
		} catch(Throwable t) {
			DebugSockets.error("Problem sending sleeper " + t);
		}

	}

	public static void main(String[] args) {
		Sleep s = new Sleep("ron", "xyzzy", 128, 5000);
		SubmitsJob c = new SubmitsJob("localhost", 3000, s);
		c.run();
	}

}
