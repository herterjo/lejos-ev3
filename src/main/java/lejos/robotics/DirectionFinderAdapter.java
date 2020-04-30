package lejos.robotics;

import lejos.robotics.filter.ModulusFilter;

public class DirectionFinderAdapter implements DirectionFinder {
	private Calibrate calibrator;
	private SampleProvider provider;
    private final SampleProvider initialProvider;
	private final float[] sample = new float[1];
	
	public DirectionFinderAdapter(SampleProvider provider) {
		this.provider = provider;
	    initialProvider = provider;
		if (provider instanceof Calibrate) this.calibrator = (Calibrate) provider;
	}
	
	@Override
	public float getDegreesCartesian() throws Exception {
		provider.fetchSample(sample, 0);
		return sample[0];
	}

	@Override
	public void startCalibration() {
		if (calibrator != null) calibrator.startCalibration();
	}

	@Override
	public void stopCalibration() {	
		if (calibrator != null) calibrator.stopCalibration();
	}

	@Override
	public void resetCartesianZero() throws Exception {
		float[] sample = new float[1];
		initialProvider.fetchSample(sample,0); 
		provider = new ModulusFilter(initialProvider, sample, 360);
	}

}
