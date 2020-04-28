package lejos.robotics.filter;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

/**
 * PID filter. Reads the measurement variable from the source and outputs the
 * process variable. The filter can work with programmatically set set-points or
 * with set-points provided by a SampleProvider.
 * 
 * @author Aswin Bouwmeester
 *
 */
public class PidFilter extends AbstractFilter {

  private long           lastTime;        // timestamp for previous iteration
  private float[]        kp;              // Proportional coefficient
  private float[]        ki;              // Integral Coefficient
  private float[]        kd;              // Derivative coefficient
  private float[]        sp;              // set-point
  private float[]        cp;              // Proportional term
  private float[]        ci;              // Integral term
  private float[]        cd;              // Derivative term
  private float[]        ul;              // Maximum value for the process variable
  private float[]        ll;              // minimum value for the process variable
  private float[]        pv;              // process variable
  private float[]        ca;              // controllable area for the controller
  private SampleProvider setPointProvider;

  public PidFilter(SampleProvider source) {
    super(source);
    kp = new float[sampleSize];
    ki = new float[sampleSize];
    kd = new float[sampleSize];
    sp = new float[sampleSize];
    ci = new float[sampleSize];
    cd = new float[sampleSize];
    cp = new float[sampleSize];
    pv = new float[sampleSize];
    ul = createArray(sampleSize, Float.NaN);
    ll = createArray(sampleSize, Float.NaN);
    ca = createArray(sampleSize, Float.NaN);
  }

  /**
   * Sets the P, I and D coefficients for the filter
   * 
   * @param index
   *          Indicates the position in the sample
   * @param proportional
   * @param integral
   * @param derevative
   */
  public void setCoefficients(int index, float proportional, float integral, float derevative) {
    if (index > sampleSize || index < 0)
      throw new IndexOutOfBoundsException("Index must be within sample size");
    kp[index] = proportional;
    ki[index] = integral;
    kd[index] = derevative;
  }

  /**
   * Sets the setpoint for the filter
   * 
   * @param index
   *          Indicates the position in the sample
   * @param setpoint
   */
  public void setSetpoint(int index, float setpoint) {
    if (index > sampleSize || index < 0)
      throw new IndexOutOfBoundsException("Index must be within sample size");
    sp[index] = setpoint;
  }

  /**
   * Sets a sampleprovider that provides the filter with setpoints. <br>
   * When using a SampleProvider for the setpoints the setpoints will be
   * refreshed with every fetchSample by calling the fetchSample method of the
   * setpoint provider.
   * 
   * @param setPointProvider
   */
  public void setSetpoint(SampleProvider setPointProvider) {
    if (setPointProvider.sampleSize() != sampleSize)
      throw new IndexOutOfBoundsException("Sample Size must match");
    this.setPointProvider = setPointProvider;
  }

  /**
   * Sets boundaries for the process variable (the filter output). Useful for
   * limiting the range of the process variable. A value of Float.NaN means no
   * limit.
   * 
   * @param index
   * @param lowerBound
   * @param upperBound
   */
  public void setBoundaries(int index, float lowerBound, float upperBound) {
    if (index > sampleSize || index < 0)
      throw new IndexOutOfBoundsException("Index must be within sample size");
    ul[index] = upperBound;
    ll[index] = lowerBound;
  }

  /**
   * Sets the controllable area for the filter. The filter will only use
   * integral values when the process error (measurement variable - setpoint) is
   * within the controllable area (between -boundary and +boundary)
   * 
   * @param index
   * @param boundary
   */
  public void setControllableArea(int index, float boundary) {
    if (index > sampleSize || index < 0)
      throw new IndexOutOfBoundsException("Index must be within sample size");
    ca[index] = boundary;
  }

  /**
   * Resets the Integral and proportional value of the filter
   * 
   */
  public void reset() {
    ci = new float[sampleSize];
    cd = new float[sampleSize];
    cp = new float[sampleSize];
    lastTime = 0;
  }

  private float[] createArray(int size, float initialValue) {
    float[] a = new float[size];
    for (int i = 0; i < size; i++) {
      a[i] = initialValue;
    }
    return a;
  }

  @Override
  public void fetchSample(float[] sample, int offset) {
    // Process variable
    source.fetchSample(pv, 0);
    if (setPointProvider != null)
      setPointProvider.fetchSample(sp, 0);
    // for derivative
    float dt = getDt();
    for (int i = 0; i < sampleSize; i++) {
      // store previous error
      float previous = cp[i];
      // proportional or error
      cp[i] = sp[i] - pv[i];
      // integral
      if (Float.isNaN(ca[i]) || Math.abs(cp[i]) <= ca[i]) {
        ci[i] += cp[i] * dt;
      } else {
        ci[i] = 0;
      }
      // derivative
      if (dt != 0)
        cd[i] = (cp[i] - previous) / dt;
      else
        cd[i] = 0;
      // control variable
      sample[i + offset] = bound(kp[i] * cp[i] + ki[i] * ci[i] + kd[i] * cd[i], ll[i], ul[i]);
    }
  }

  private float getDt() {
    long now = System.currentTimeMillis();
    float dt = 0;
    if (lastTime != 0) {
      dt = (now - lastTime) / 1000f;
    }
    lastTime = now;
    return dt;
  }

  private float bound(float value, float min, float max) {
    if (!Float.isNaN(min))
      value = Math.max(min, value);
    if (!Float.isNaN(max))
      value = Math.min(max, value);
    return value;
  }

}
