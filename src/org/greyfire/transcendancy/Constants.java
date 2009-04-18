package org.greyfire.transcendancy;

public abstract class Constants {
    public static int GALAXY_VIEW_WIDTH  = 300;
    public static int GALAXY_VIEW_HEIGHT = 300;
    public static int GALAXY_VIEW_CAM_DIST = 250;
    public static int GALAXY_MIN_CAM_DIST  = 100;
    public static int GALAXY_RADIUS        = 100;
    public static double GALAXY_VIEW_CAM_ANGLE_X = 1/Math.tan(Math.toRadians(45));
    public static double GALAXY_VIEW_CAM_ANGLE_Y = 1/Math.tan(Math.toRadians(45));
    
    public static double LANE_STRETCH_PROPORTION = 0.50; /** the proportion of the galaxy radius a lane can stretch without becoming a redlink */
    public static double LANE_STRECH_TOLERANCE_SMALL  = 2.20;
    public static double LANE_STRECH_TOLERANCE_MEDIUM = 1.70;
    public static double LANE_STRECH_TOLERANCE_LARGE  = 1.00;
    
    public static int GALAXY_DENSITY_SMALL  =  10; /** the number of stars in a Small galaxy */
    public static int GALAXY_DENSITY_MEDIUM =  25; /** the number of stars in a Medium galaxy */
    public static int GALAXY_DENSITY_LARGE  = 100; /** the number of stars in a Large galaxy */

    public static int MAX_PLAYERS = 7;
}
