package org.greyfire.transcendancy.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import org.greyfire.transcendancy.Constants;
import org.greyfire.transcendancy.cosmos.Coord;
import org.greyfire.transcendancy.cosmos.Galaxy;
import org.greyfire.transcendancy.cosmos.Starlane;
import org.greyfire.transcendancy.cosmos.StellarLocation;

public class GalaxyPanel extends SecondaryPanel {

	private static final long serialVersionUID = 1L;
	private Galaxy content;
	private ArtCanvas canvas;

	public GalaxyPanel(Galaxy content) {
		super();
		this.content = content;
		this.onOpen();
	}

	public GalaxyPanel(Galaxy content, boolean doublebuffer) {
		super(doublebuffer);
		this.content = content;
		this.onOpen();
	}

	@Override
	public void onClose() {
		/* donothing */
	}

	@Override
	public void onOpen() {
		this.canvas = new ArtCanvas(Constants.GALAXY_VIEW_WIDTH, Constants.GALAXY_VIEW_HEIGHT);
        this.setLayout(new GridLayout(1, 1));
        this.add(this.canvas);
	}

	@Override
	public void redraw() {
		ArrayList<StellarLocation> anchors = content.anchors();
		ArrayList<Starlane>        lanes   = content.lanes();
		Coord c, c2;
		int clr, cclr;
		int u, v, u2, v2;
		int z, z2;
		int width  = Constants.GALAXY_VIEW_WIDTH;
		int height = Constants.GALAXY_VIEW_HEIGHT;
		double anglex = Constants.GALAXY_VIEW_CAM_ANGLE_X;
		double angley = Constants.GALAXY_VIEW_CAM_ANGLE_Y;
		int star_near = ArtCanvas.translated_colour(new Color(100, 200, 255));
		int star_far  = ArtCanvas.translated_colour(new Color(255, 200, 100));
		int starc_near = ArtCanvas.translated_colour(new Color(64, 127, 192));
		int starc_far  = ArtCanvas.translated_colour(new Color(192, 127, 64));
		int lane_near = ArtCanvas.translated_colour(new Color(127, 127, 255));
		int lane_far  = ArtCanvas.translated_colour(new Color(255, 127, 127));
		for(Starlane lane : lanes) {
			c  = lane.getAnchorA().getPosition();
			c2 = lane.getAnchorB().getPosition();
			z  = ((int)c.z())  + Constants.GALAXY_VIEW_CAM_DIST;
			z2 = ((int)c2.z()) + Constants.GALAXY_VIEW_CAM_DIST;
			u  = (int)(width  * (  (c.x()  * anglex / z)  + 0.5));
			v  = (int)(height * (1-(c.y()  * angley / z)  - 0.5));
			u2 = (int)(width  * (  (c2.x() * anglex / z2) + 0.5));
			v2 = (int)(height * (1-(c2.y() * angley / z2) - 0.5));
			clr = ((c.z()<0 || c2.z()<0) ? lane_near : lane_far);
			System.err.println(String.format("%s: <%d, %d>--<%d, %d>", lane.getLongTitle(), u, v, u2, v2));
			if(u>0 && v>0 && u2>0 && v2>0 && u<width && v<height && u2<width && v2<height) {
				this.canvas.line(u, v, u2, v2, clr);
			}
		}
		for(StellarLocation star : anchors) {
			c = star.getPosition();
			z = ((int)c.z()) + Constants.GALAXY_VIEW_CAM_DIST;
			u  = (int)(width  * (  (c.x()  * anglex / z) + 0.5));
			v  = (int)(height * (1-(c.y()  * angley / z) - 0.5));
			clr  = (c.z()<0 ? star_near  : star_far);
			cclr = (c.z()<0 ? starc_near : starc_far);
			System.err.println(String.format("%s: <%d, %d>", star.getLongTitle(), u, v));
			if(u>0 && v>0 && u<width-1 && v<height-1) {
				this.canvas.pset(u, v, clr);
				this.canvas.pset(u+1, v,   cclr);
				this.canvas.pset(u-1, v,   cclr);
				this.canvas.pset(u,   v+1, cclr);
				this.canvas.pset(u,   v-1, cclr);
			}
		}
		this.canvas.refresh();
	}
}
