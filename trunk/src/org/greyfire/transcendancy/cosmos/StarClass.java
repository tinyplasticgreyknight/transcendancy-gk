package org.greyfire.transcendancy.cosmos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.greyfire.transcendancy.Util;
import org.greyfire.transcendancy.ui.ArtCanvas;


public class StarClass {
    private static HashMap<String, StarClass> classregistry = new HashMap<String, StarClass>();
    private static ArrayList<String> registry_keys = new ArrayList<String>();
    private static Random stream = new Random(Util.seed());
    private String name;
    private ArrayList<Color> spectrum;
    private BufferedImage mini;
    private Dimension mini_offset;
    private int maxplanets;
    private int maxconnections;
    private StarClass(String name, ArrayList<Color> spectrum, BufferedImage mini, int maxplanets, int maxconnections) {
        this.name = name;
        this.spectrum = spectrum;
        /* TODO: maxplanets and maxconnections should be loaded from startype's description.txt */
        this.maxplanets     = maxplanets;
        this.maxconnections = maxconnections;
        this.setMini(mini);
    }

    public static StarClass Random() {
        return classregistry.get(registry_keys.get(stream.nextInt(registry_keys.size())));
    }
    
    public static StarClass Instance(String key) {
        return classregistry.get(key);
    }
    
    public static void resetInstance(String key, String name, ArrayList<Color> spectrum, BufferedImage mini, int maxplanets, int maxconnections) {
        if(classregistry.get(key)==null) {
            classregistry.put(key, new StarClass(name, spectrum, mini, maxplanets, maxconnections));
            registry_keys.add(key);
        }
    }
    public String toString() { return this.name; }
    
    public int maxPlanets() { return this.maxplanets; }
    public int maxConnections() { return this.maxconnections; }
    
    public void setMini(BufferedImage mini) {
        int u, v, c, a;
        int w, h;
        int tint = ArtCanvas.translated_colour(this.spectrum.get(0));
        w = mini.getWidth();
        h = mini.getHeight();
        BufferedImage tinted = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for(v=0; v<h; v++) {
            for(u=0; u<w; u++) {
                c = mini.getRGB(u, v);
                a = (c & 0xFF000000);
                c = ArtCanvas.multiply_colours(tint, c);
                c = a | (c & 0x00FFFFFF);
                tinted.setRGB(u, v, c);
            }
        }
        this.mini = tinted;
        this.mini_offset = new Dimension(w/2, h/2);
    }
    
    public void painter(ArtCanvas canvas, int x, int y) {
        int clr_corona = ArtCanvas.translated_colour(spectrum.get(2));
        int clr_core   = ArtCanvas.translated_colour(spectrum.get(1));
        canvas.blit(x-mini_offset.width, y-mini_offset.height, this.mini);
        /*
        canvas.pset(x,   y,   clr_core);
        canvas.pset(x+1, y-1, clr_corona);
        canvas.pset(x+1, y,   clr_corona);
        canvas.pset(x+1, y+1, clr_corona);
        canvas.pset(x-1, y-1, clr_corona);
        canvas.pset(x-1, y,   clr_corona);
        canvas.pset(x-1, y+1, clr_corona);
        canvas.pset(x-1, y+1, clr_corona);
        canvas.pset(x,   y+1, clr_corona);
        canvas.pset(x+1, y+1, clr_corona);
        canvas.pset(x-1, y-1, clr_corona);
        canvas.pset(x,   y-1, clr_corona);
        canvas.pset(x+1, y-1, clr_corona);
        */
    }
}