package lejos.robotics;

import lejos.robotics.filter.AbstractFilter;

public class RangeFinderAdapter extends AbstractFilter implements RangeFinder{
	private final float[] buf;

	public RangeFinderAdapter(SampleProvider source) {
		super(source);
		buf=new float[sampleSize];
	}
	
	@Override
	public float getRange() throws Exception {
		fetchSample(buf,0);
		return buf[0] * 100;
	}

	@Override
	public float[] getRanges() throws Exception {
		float[] sample=new float[sampleSize];
		fetchSample(sample,0);
		return sample;
	}
}
