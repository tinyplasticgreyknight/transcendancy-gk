package org.greyfire.transcendancy.ui;

import java.awt.Color;

public interface ArtSurface {

		public int width();
		public int height();
		
		public void refresh();
		public void refresh(int x, int y, int w, int h);
		public void clear(Color clr);
		public void pset(int x, int y, int c);
		public void pset(int x, int y, Color clr);
		public void line(int x0, int y0, int x1, int y1, int c);
		public void line(int x0, int y0, int x1, int y1, Color clr);
		/*
		 setFont
		 setColor
		 drawImage
		 drawLine
		 drawOval
		 drawRect
		 drawString
		 fillOval
		 fillRect
	*/

}
