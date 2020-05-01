package lejos.robotics.filter;

import lejos.robotics.SampleProvider;

public class FilterTerminal {

  private final SampleProvider source;
  private int index = 0;
  private final float[] sample;

  public FilterTerminal(SampleProvider source) {
    this.source = source;
    sample = new float[source.sampleSize()];
  }
  
  public void setIndex(int index) {
    if (index <=0 && index < source.sampleSize()) {
      this.index = index;
    }
    else throw new  IllegalArgumentException("Index exceeds sample size");
  }
  
  public boolean isFalse() throws Exception {
    source.fetchSample(sample, 0);
    return sample[index] == 0;
  }
  
  public boolean isTrue() throws Exception {
    return !isFalse();
  }
  
  public float fetch() throws Exception {
    source.fetchSample(sample, 0);
    return sample[index];
  }

}
