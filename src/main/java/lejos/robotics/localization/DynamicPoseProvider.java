package lejos.robotics.localization;

import lejos.utility.Matrix;

public interface DynamicPoseProvider extends PoseProvider {
  
  /** Gives the X component of the robots position
   * @return
   */
  double getX();
  
  /** Gives the Y component of the robots position
   * @return
   */
  double getY();
  
  /** Gives the heading of the robot in degrees
   * @return
   */
  double getHeading();
  
  /** Gives the linear speed of the robot
   * @return
   */
  double getLinearSpeed();
  
  /** Gives the direction the robot is going to
   * @return
   */
  double getDirectionOfLinearSpeed();
  
  /** Gives the angular speed of the robot in degrees/second
   * @return
   */
  double getAngularSpeed();
  
  /** Gives a Matrix holding the robots speed components 
   * @return A 3 x 1 matrix (X speed, Y speed, angular speed)
   */
  Matrix getSpeed();
  
  /** Gives the linear acceleration of the robot
   * @return
   */
  double getLinearAcceleration();
  
  /** Gives the direction the robot is going to
   * @return
   */
  double getDirectionOfLinearAcceleration();

  /** Gives the angular acceleration of the robot
   * @return
   */
  double getAngularAcceleration();
  
  /**Gives a Matrix holding the robots acceleration components 
   * @return A 3 x 1 matrix (X acceleration, Y acceleration, angular acceleration)
   */
  Matrix getAcceleration();

}
