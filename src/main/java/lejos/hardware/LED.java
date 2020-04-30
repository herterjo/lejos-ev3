package lejos.hardware;

import lejos.utility.ExceptionWrapper;

import java.util.concurrent.Future;

public interface LED {
	
	Future<ExceptionWrapper> setPattern(int pattern);

}
