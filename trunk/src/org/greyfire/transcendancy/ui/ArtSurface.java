package org.greyfire.transcendancy.ui;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface ArtSurface {

        public int width();
        public int height();
        
        public void refresh();
        public void refresh(int x, int y, int w, int h);
        public void clear(Color clr);
        public void pset(int x, int y, int c);
        public void pset(int x, int y, Color clr);
        public void pset_safe(int x, int y, int c);
        public void pset_safe(int x, int y, Color clr);
        public int  pget(int x, int y);
        public void line(int x0, int y0, int x1, int y1, int c);
        public void line(int x0, int y0, int x1, int y1, Color clr);
        public void line_safe(int x0, int y0, int x1, int y1, int c);
        public void line_safe(int x0, int y0, int x1, int y1, Color clr);
        public void circle(int x0, int y0, int radius, int c);
        public void circle(int x0, int y0, int radius, Color clr);
        public void circle_safe(int x0, int y0, int radius, int c);
        public void circle_safe(int x0, int y0, int radius, Color clr);
        public void blit(int x, int y, BufferedImage img);
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
