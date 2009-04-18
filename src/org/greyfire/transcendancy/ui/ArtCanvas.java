package org.greyfire.transcendancy.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;

public class ArtCanvas extends Canvas implements ArtSurface {

    private static final long serialVersionUID = 1L;
    
    private Color background = Color.BLACK;
    private Graphics g;
    private MemoryImageSource mem;
    private Image screen;
    private int width;
    private int height;
    private int size;
    private int[] pixels;
    public static int WHITE = ArtCanvas.translated_colour(Color.WHITE);
    public static int BLACK = ArtCanvas.translated_colour(Color.BLACK);
    
    public ArtCanvas(int width, int height) {
        this.g = this.getGraphics();
        if(width<=0 || height<=0) throw new IllegalArgumentException("dimensions must be positive");
        this.width  = width;
        this.height = height;
        this.size   = width*height;
        this.pixels = new int[size];
        this.mem = new MemoryImageSource(width, height, pixels, 0, width);
        this.mem.setAnimated(true);
        this.mem.setFullBufferUpdates(true);
        this.screen = Toolkit.getDefaultToolkit().createImage(this.mem);
    }

    public int width()  { return this.width;  }
    public int height() { return this.height; }
    
    public void update(Graphics g) {
        this.paint(g);
    }
    
    public void refresh() {
        //this.paint(this.g);
        this.repaint();
    }
    
    public void refresh(int x, int y, int w, int h) {
        //this.paint(this.g, x, y, w, h);
        this.repaint();
    }
    
    public void paint(Graphics g) {
        Graphics graphics = this.g;
        if(graphics==null) graphics = g;
        if(graphics==null) return;
        this.mem.newPixels();
        //graphics.setColor(background);
        //graphics.fillRect(0, 0, width, height);
        graphics.drawImage(screen, 0, 0, null);
    }
    
    public void paint(Graphics g, int x, int y, int w, int h) {
        Graphics graphics = this.g;
        if(graphics==null) graphics = g;
        if(graphics==null) return;
        this.mem.newPixels(x, y, w, h);
        //graphics.setColor(background);
        //graphics.fillRect(x, y, w, h);
        this.g.drawImage(screen, 0, 0, null);
    }
    
    public static int translated_colour(Color clr) {
        int r = clr.getRed();
        int g = clr.getGreen();
        int b = clr.getBlue();
        int a = clr.getAlpha();
        return translated_colour(r, g, b, a);
    }
    public static int translated_colour(int r, int g, int b, int a) {
        return ((b << 0) | (g << 8) | (r << 16) | (a << 24));
    }
    public static int translated_colour(int r, int g, int b) {
        return translated_colour(r, g, b, 255);
    }
    public static int interpolate_colours(int c1, int c2, double proportion) {
        int r1 = (c1 & 0x00FF0000) >> 16;
        int g1 = (c1 & 0x0000FF00) >>  8;
        int b1 = (c1 & 0x000000FF);
        int r2 = (c2 & 0x00FF0000) >> 16;
        int g2 = (c2 & 0x0000FF00) >>  8;
        int b2 = (c2 & 0x000000FF);
        r1 = (int)(r1*proportion + r2*(1-proportion));
        g1 = (int)(g1*proportion + g2*(1-proportion));
        b1 = (int)(b1*proportion + b2*(1-proportion));
        return translated_colour(r1, g1, b1, 255);
    }
    public static int multiply_colours(int c1, int c2) {
        int a1 = (c1 & 0xFF000000) >> 24;
        int r1 = (c1 & 0x00FF0000) >> 16;
        int g1 = (c1 & 0x0000FF00) >>  8;
        int b1 = (c1 & 0x000000FF);
        int a2 = (c2 & 0xFF000000) >> 24;
        int r2 = (c2 & 0x00FF0000) >> 16;
        int g2 = (c2 & 0x0000FF00) >>  8;
        int b2 = (c2 & 0x000000FF);
        r1 = (r1*r2)/255;
        g1 = (g1*g2)/255;
        b1 = (b1*b2)/255;
        a1 = (a1*a2)/255;
        return translated_colour(r1, g1, b1, a1);
    }
    
    public void clear(Color clr) {
        int c = translated_colour(clr);
        for(int i=0; i<this.size; i++) {
            this.pixels[i] = c;
        }
    }
    
    public void pset(int x, int y, int c) {
        int point = y*width + x;
        this.pixels[point] = c;
    }
    
    public void pset(int x, int y, Color clr) {
        int point = y*width + x;
        pixels[point] = translated_colour(clr);
    }
    
    public void pset_safe(int x, int y, int c) {
        if(x<0 || y<0 || x>=width-1 || y>=height-1) return;
        int point = y*width + x;
        this.pixels[point] = c;
    }
    
    public void pset_safe(int x, int y, Color clr) {
        if(x<0 || y<0 || x>=width-1 || y>=height-1) return;
        int point = y*width + x;
        pixels[point] = translated_colour(clr);
    }
    
    public int pget(int x, int y) {
        int point = y*width + x;
        return this.pixels[point];
    }

    public void line(int x0, int y0, int x1, int y1, int c) {
        int ax0 = x0;
        int ax1 = x1;
        int ay0 = y0;
        int ay1 = y1;
        int dx = ax1 - ax0;
        int dy = ay1 - ay0;
        boolean steep = (Math.abs(dy) > Math.abs(dx));
        if(steep) {
            int tmp = ax0;
            ax0 = ay0;
            ay0 = tmp;
            tmp = ax1;
            ax1 = ay1;
            ay1 = tmp;
        }
        if(ax0 > ax1) {
            int tmp = ax1;
            ax1 = ax0;
            ax0 = tmp;
            tmp = ay1;
            ay1 = ay0;
            ay0 = tmp;
        }
        dx = ax1 - ax0;
        dy = Math.abs(ay1 - ay0); 
        int err = dx / 2;
        int iy = ay0;
        int ystep = (ay0 < ay1 ? 1 : -1);
        for(int ix=ax0; ix<=ax1; ix++) {
            if(steep) {
                this.pset(iy, ix, c);
            } else {
                this.pset(ix, iy, c);
            }
            err -= dy;
            if(err<0) {
                iy += ystep;
                err += dx;
            }
        }
    }

    public void line_safe(int x0, int y0, int x1, int y1, int c) {
        int ax0 = x0;
        int ax1 = x1;
        int ay0 = y0;
        int ay1 = y1;
        int dx = ax1 - ax0;
        int dy = ay1 - ay0;
        boolean steep = (Math.abs(dy) > Math.abs(dx));
        if(steep) {
            int tmp = ax0;
            ax0 = ay0;
            ay0 = tmp;
            tmp = ax1;
            ax1 = ay1;
            ay1 = tmp;
        }
        if(ax0 > ax1) {
            int tmp = ax1;
            ax1 = ax0;
            ax0 = tmp;
            tmp = ay1;
            ay1 = ay0;
            ay0 = tmp;
        }
        dx = ax1 - ax0;
        dy = Math.abs(ay1 - ay0); 
        int err = dx / 2;
        int iy = ay0;
        int ystep = (ay0 < ay1 ? 1 : -1);
        for(int ix=ax0; ix<=ax1; ix++) {
            if(steep) {
                this.pset_safe(iy, ix, c);
            } else {
                this.pset_safe(ix, iy, c);
            }
            err -= dy;
            if(err<0) {
                iy += ystep;
                err += dx;
            }
        }
    }
    
    public void line(int x0, int y0, int x1, int y1, Color clr) {
        this.line(x0, y0, x1, y1, translated_colour(clr));
    }

    public void line_safe(int x0, int y0, int x1, int y1, Color clr) {
        this.line_safe(x0, y0, x1, y1, translated_colour(clr));
    }
    
    public void blit(int x, int y, BufferedImage img) {
        int u, v;
        int X;
        int Y;
        int w = img.getWidth();
        int h = img.getHeight();
        ColorModel palette = img.getColorModel();
        int c;
        long ia;
        double a;
        for(v=0; v<h; v++) {
            Y = y+v;
            for(u=0; u<w; u++) {
                X = x+u;
                c = img.getRGB(u, v);
                ia = (c&0xFF000000)>>24;
                if(ia<0) ia += 256;
                a = ia/255.0;
                c = ArtCanvas.interpolate_colours(c, this.pget(X, Y), a);
                this.pset(X, Y, c);
            }
        }
    }
    
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

    public void circle(int x0, int y0, int radius, int c) {
        /* TODO: change this to Bresenham's circle instead of this awful sqrt() approach */
        if(radius<0) throw new IllegalArgumentException("radius must be non-negative");
        if(radius==0) { this.pset(x0, y0, c); return; }
        int u, v;
        int r2 = radius*radius;
        int last = radius;
        for(u=0; u<=radius; u++) {
            v = (int)Math.round(Math.sqrt(r2 - u*u));
            if(v<last-1) {
                this.line(x0+u, y0+last-1, x0+u, y0+v, c);
                this.line(x0-u, y0+last-1, x0-u, y0+v, c);
                this.line(x0+u, y0-last+1, x0+u, y0-v, c);
                this.line(x0-u, y0-last+1, x0-u, y0-v, c);
            } else {
                this.pset(x0+u, y0+v, c);
                this.pset(x0-u, y0+v, c);
                this.pset(x0+u, y0-v, c);
                this.pset(x0-u, y0-v, c);
            }
            last = v;
        }
    }

    public void circle_safe(int x0, int y0, int radius, int c) {
        /* TODO: change this to Bresenham's circle instead of this awful sqrt() approach */
        if(radius<0) throw new IllegalArgumentException("radius must be non-negative");
        if(radius==0) { this.pset_safe(x0, y0, c); return; }
        int u, v;
        int r2 = radius*radius;
        int last = radius;
        for(u=0; u<=radius; u++) {
            v = (int)Math.round(Math.sqrt(r2 - u*u));
            if(v<last-1) {
                this.line_safe(x0+u, y0+last-1, x0+u, y0+v, c);
                this.line_safe(x0-u, y0+last-1, x0-u, y0+v, c);
                this.line_safe(x0+u, y0-last+1, x0+u, y0-v, c);
                this.line_safe(x0-u, y0-last+1, x0-u, y0-v, c);
            } else {
                this.pset_safe(x0+u, y0+v, c);
                this.pset_safe(x0-u, y0+v, c);
                this.pset_safe(x0+u, y0-v, c);
                this.pset_safe(x0-u, y0-v, c);
            }
            last = v;
        }
    }

    public void circle(int x0, int y0, int radius, Color clr) {
        this.circle(x0, y0, radius, ArtCanvas.translated_colour(clr));
    }

    public void circle_safe(int x0, int y0, int radius, Color clr) {
        this.circle_safe(x0, y0, radius, ArtCanvas.translated_colour(clr));
    }

}
