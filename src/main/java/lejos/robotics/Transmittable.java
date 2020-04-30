package lejos.robotics;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Transmittable {
	void dumpObject(DataOutputStream dos) throws IOException;
	
	void loadObject(DataInputStream dis) throws IOException;
}
