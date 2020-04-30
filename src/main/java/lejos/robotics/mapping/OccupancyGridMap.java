package lejos.robotics.mapping;

public class OccupancyGridMap {
	private final int width;
    private final int height;
	private final double freeThreshold;
    private final double occupiedThreshold;
	private final double resolution; // cells per meter

	private final byte[][] cells;
	
	public OccupancyGridMap(int width, int height, 
			double freeThreshold, double occupiedThreshold, 
			double resolution) {
		
		this.width = width;
		this.height = height;
		this.freeThreshold = freeThreshold;
		this.occupiedThreshold = occupiedThreshold;
		this.resolution = resolution;
		cells = new byte[width][height];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public double getResolution() {
		return resolution;
	}
	
	public double getFreeThreshold() {
		return freeThreshold;
	}
	
	public double getOccupiedThreshold() {
		return occupiedThreshold;
	}
	
	public void setOccupied(int x, int y, int occupied) {
		cells[x][y] = (byte) occupied;
	}
	
	public int getOccupied(int x, int y) {
		return cells[x][y];
	}
	
	public boolean isOccupied(int x, int y) {
		return(cells[x][y] >= occupiedThreshold);
	}
	
	public boolean isFree(int x, int y) {
		return(cells[x][y] <= freeThreshold);
	}
}