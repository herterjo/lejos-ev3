package lejos.robotics;

import lejos.robotics.filter.AbstractFilter;

public class AccelerometerAdapter extends AbstractFilter implements Accelerometer{
  float[] sample;
  

  public AccelerometerAdapter(SampleProvider source) {
    super(source);
    sample=new float[sampleSize];
  }
  
  protected int getElement(int index) throws Exception {
    fetchSample(sample,0);
    return (int) sample[index];
  }

  @Override
  public int getXAccel() throws Exception {
    return getElement(0);
  }

  @Override
  public int getYAccel() throws Exception {
    return getElement(1);
  }

  @Override
  public int getZAccel() throws Exception {
    return getElement(2);
  }

}
