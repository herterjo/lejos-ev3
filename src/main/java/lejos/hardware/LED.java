package lejos.hardware;

import lejos.utility.ExceptionWrapper;

import java.util.concurrent.Future;

public interface LED {
	
	public Future<ExceptionWrapper> setPattern(int pattern);

}
