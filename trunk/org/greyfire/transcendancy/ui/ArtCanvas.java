package org.greyfire.transcendancy.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class ArtCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	
	private Graphics g;
	private MemoryImageSource mem;
	private Image screen;
	private int width;
	private int height;
	private int size;
	private int[] pixels;
	
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
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, width, height);
		graphics.drawImage(screen, 0, 0, null);
	}
	
	public void paint(Graphics g, int x, int y, int w, int h) {
		Graphics graphics = this.g;
		if(graphics==null) graphics = g;
		if(graphics==null) return;
		this.mem.newPixels(x, y, w, h);
		graphics.setColor(Color.BLACK);
		graphics.fillRect(x, y, w, h);
		this.g.drawImage(screen, 0, 0, null);
	}
	
	public int translated_colour(Color clr) {
		int r = clr.getRed();
		int g = clr.getGreen();
		int b = clr.getBlue();
		int a = clr.getAlpha();
		return ((b << 0) | (g << 8) | (r << 16) | (a << 24));
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
