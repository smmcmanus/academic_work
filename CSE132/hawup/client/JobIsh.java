package hawup.client;

import java.io.DataOutputStream;

public interface JobIsh {

	public abstract String getUserName();

	public abstract String getPassword();
	
	public abstract void sendJobPayload(DataOutputStream dos);

}