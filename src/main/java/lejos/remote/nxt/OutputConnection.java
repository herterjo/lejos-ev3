package lejos.remote.nxt;

import java.io.*;

public interface OutputConnection extends Connection {
	/**
	 * Open and return a data output stream for a connection.
	 * @return the data output stream
	 */
    DataOutputStream openDataOutputStream();
     
	/**
	 * Open and return an output stream for a connection.
	 * @return the output stream
	 */
    OutputStream openOutputStream();
     
}
