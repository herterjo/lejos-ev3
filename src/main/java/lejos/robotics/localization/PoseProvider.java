package lejos.robotics.localization;

import lejos.robotics.navigation.Pose;

/**
 * Provides the coordinate and heading info via a Pose object.
 * @author NXJ Team
 *
 */
public interface PoseProvider {
	
	Pose getPose() throws Exception;
    
	void setPose(Pose aPose);
}
