package hawup.client;

import hawup.network.DebugSockets;
import hawup.network.PrintStreamPanel;
import hawup.network.StreamsAndTranscriptViz;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Can send a job to a server
 * Launches a viz so you can see the streams in both directions
 *   and the transcript of the session
 * @author roncytron
 *
 */
public class SubmitsJob implements Runnable {
	
	private final String    serverIP;
	private final int       serverPort;
	private final JobIsh client;
	private final PrintStreamPanel fromServer, toServer, transcript;
	private final PrintStream ps;
	
	public SubmitsJob(String serverIP, int serverPort, JobIsh client) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.client     = client;
		this.fromServer = new PrintStreamPanel(Color.WHITE, "From Server",170);
		this.toServer   = new PrintStreamPanel(Color.YELLOW, "To Server",170);
		this.transcript = new PrintStreamPanel(Color.GREEN, "Transcript",380);
		this.ps       = transcript.getPrintStream();
		StreamsAndTranscriptViz viz = new StreamsAndTranscriptViz(
				"Client User: " + client.getUserName(),
				fromServer, toServer, transcript);
		viz.setVisible(true);
	}
	
	public void run() {
		try {
			Socket socket = new Socket(serverIP, serverPort);
			DataInputStream in = DebugSockets.genIn(socket, fromServer);
			DataOutputStream out = DebugSockets.genOut(socket, toServer);
			String serverName = in.readUTF();
			int    numNodes   = in.readShort();
			int    maxQsize   = in.readShort();
			message("Connected to Server " + serverName);
			message("  has " + numNodes + " nodes and queue size " + maxQsize);
			out.writeUTF(client.getUserName());
			out.writeUTF(client.getPassword());
			message("Response " + in.readUTF());
			message("Sending job payload");
			client.sendJobPayload(out);
			message("Response " + in.readUTF());
			String answer = in.readUTF();
			message("Answer " + answer);

			
		} catch(Throwable t) {
			throw new Error("Client problem " + t);
		}
		
	}

	private void message(String s) {
		ps.println(s);
	}

}
