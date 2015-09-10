package hawup.client;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Everything but the actual payload for a specific job
 * @author roncytron
 *
 */
abstract public class JobSpec implements JobIsh {
	
	private final String username, pw;

	public JobSpec(
			String username, 
			String pw) {
		this.username = username;
		this.pw       = pw;
	}
		
	/* (non-Javadoc)
	 * @see hawup.client.ClientIsh#getUserName()
	 */
	@Override
	public String getUserName() {
		return this.username;
	}
	
	/* (non-Javadoc)
	 * @see hawup.client.ClientIsh#getPassword()
	 */
	@Override
	public String getPassword() {
		return this.pw;
	}
	
}
