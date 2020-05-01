package lejos.hardware;

public class BrickInfo {
	private final String name;
	private final String ipAddress;
	private final String type;
	
	public BrickInfo(String name, String ipAddress, String type) {
		this.name = name;
		this.ipAddress = ipAddress;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIPAddress() {
		return ipAddress;
	}
	
	public String getType() {
		return type;
	}
}
