package org.greyfire.transcendancy;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

import org.greyfire.transcendancy.bio.Species;
import org.greyfire.transcendancy.cosmos.Star;
import org.greyfire.transcendancy.cosmos.StarClass;

public abstract class DataResources {
    private static String DEFAULT_DIRECTORY = "";
    public static ArrayList<String> starnames = new ArrayList<String>();
    public static ArrayList<String> speciesidents = new ArrayList<String>();
    public static HashMap<String, String> gameproperties = new HashMap<String, String>();
    public static HashMap<String, ArrayList<Color>> spectra = new HashMap<String, ArrayList<Color>>();
    
    public static InputStream openFile(String filename) throws FileNotFoundException {
        InputStream instream = null;
        try {
            instream = new FileInputStream(filename);
        } catch(FileNotFoundException e) {
            try {
                instream = DataResources.class.getResource(filename).openStream();
            } catch(IOException e2) {
                throw new FileNotFoundException(String.format("could not open archived file: \"%s\"", filename));
            } catch(NullPointerException e2) {
                throw new FileNotFoundException(String.format("could not open archived file: \"%s\"", filename));
            }
        }
        return instream;
    }
    
    public static Color parseColour(String spec) {
        int r, g, b;
        String s = spec;
        String[] components;
        if(!spec.matches("^<\\s*\\d+\\s*,\\s*\\d+\\s*,\\s*\\d+\\s*>$")) {
            throw new IllegalArgumentException("spec must be like <255, 255, 255>");
        }
        s = s.substring(1, s.length()-1);
        components = s.split(",");
        r = Integer.parseInt(components[0].trim());
        g = Integer.parseInt(components[1].trim());
        b = Integer.parseInt(components[2].trim());
        return new Color(r, g, b);
    }
    
    public static ArrayList<String> loadNames(String filename) throws FileNotFoundException, IOException {
        ArrayList<String> names = new ArrayList<String>();
        InputStream instream;
        BufferedReader inreader;
        String line;
        
        instream = openFile(filename);
        inreader = new BufferedReader(new InputStreamReader(instream));
        while((line = inreader.readLine())!=null) {
            line = line.trim();
            if(!(line.length()<1 || line.charAt(0)=='#')) { 
                names.add(line);
            }
        }
        instream.close();
        
        return names;
    }
    
    public static HashMap<String, String> loadMap(String filename) throws FileNotFoundException, IOException {
        HashMap<String, String> map = new HashMap<String, String>();
        InputStream instream;
        BufferedReader inreader;
        String line, key, value;
        int a;
        
        instream = openFile(filename);
        inreader = new BufferedReader(new InputStreamReader(instream));
        while((line = inreader.readLine())!=null) {
            line = line.trim();
            if(!(line=="" || line.charAt(0)=='#')) { 
                a = line.indexOf(':');
                if(a>0) {
                    key   = line.substring(0, a).trim();
                    value = line.substring(a+1).trim();
                    if(!key.equals("")) {
                        map.put(key, value);
                    }
                }
            }
        }
        instream.close();
        
        return map;
    }
    
    public static void load() {
        load(DEFAULT_DIRECTORY);
    }
        
    public static void load(String directory) {
        ArrayList<String> newnames;
        HashMap<String, String> newmap;
        String assumedname;
        String s;
        Color c;
        try {
            newnames = loadNames(String.format("%scosmos/starnames.txt", directory));
            starnames = newnames;
            Star.resetNames(starnames);
            
            newmap = loadMap(String.format("%scosmos/startypes/spectra.txt", directory));
            for(String key : newmap.keySet()) {
                s = newmap.get(key);
                ArrayList<Color> spectrum = new ArrayList<Color>();
                for(String spec : s.split(";")) {
                    c = parseColour(spec.trim());
                    spectrum.add(c);
                }
                spectra.put(key, spectrum);
            }
            
            ArrayList<Color> default_spectrum = new ArrayList<Color>();
            HashMap<String, ArrayList<String>> sizespectra = new HashMap<String, ArrayList<String>>();
            newnames = loadNames(String.format("%scosmos/startypes/index.txt", directory));
            newmap   = loadMap(String.format("%scosmos/startypes/sizespectra.txt", directory));
            default_spectrum.add(new Color(255, 255, 255));
            default_spectrum.add(new Color(127, 0, 0));
            default_spectrum.add(new Color(255, 192, 192));
            
            for(String size : newmap.keySet()) {
                ArrayList<String> valid_spectra = new ArrayList<String>();
                String[] splits = newmap.get(size).split(",");
                for(String split : splits) {
                    valid_spectra.add(split.trim());
                }
                sizespectra.put(size, valid_spectra);
            }
            for(String ident : newnames) {
                BufferedImage mini = ImageIO.read(openFile(String.format("%scosmos/startypes/%s/mini.png", directory, ident)));
                Integer maxplanets = null;
                Integer maxconnections = null;
                if(ident.charAt(0)=='_') {
                    /* this kind has spectral varieties */
                    for(String spectrum : sizespectra.get(ident)) {
                        String varident = String.format("%s%s", spectrum, ident.substring(1));
                        newmap = loadMap(String.format("%scosmos/startypes/%s/%s_definition.txt", directory, ident, spectrum));
                        maxplanets = Integer.valueOf(newmap.get("maxplanets"));
                        maxconnections = Integer.valueOf(newmap.get("maxlanes"));
                        if(maxplanets<0) throw new NumberFormatException(String.format("%s.maxplanets must be a non-negative number", ident));
                        if(maxconnections<1 || maxconnections % 2 != 0) throw new NumberFormatException(String.format("%s.maxlanes must be a positive even number", varident));
                        StarClass.resetInstance(varident, newmap.get("name"), spectra.get(spectrum), mini, maxplanets, maxconnections);
                    }
                } else {
                    newmap = loadMap(String.format("%scosmos/startypes/%s/definition.txt", directory, ident));
                    maxplanets = Integer.valueOf(newmap.get("maxplanets"));
                    maxconnections = Integer.valueOf(newmap.get("maxlanes"));
                    if(maxplanets<0) throw new NumberFormatException(String.format("%s.maxplanets must be a non-negative number", ident));
                    if(maxconnections<1 || maxconnections % 2 != 0) throw new NumberFormatException(String.format("%s.maxlanes must be a positive even number", ident));
                    StarClass.resetInstance(ident, newmap.get("name"), default_spectrum, mini, maxplanets, maxconnections);
                }
            }

            newnames = loadNames(String.format("%sbio/speciesindex.txt", directory));
            speciesidents = newnames;
            
            for(String ident : speciesidents) {
                assumedname = ident;
                try {
                    newmap = loadMap(String.format("%sbio/%s/terms.txt", directory, ident));
                    assumedname = newmap.get(Species.Term.COLLECTIVE.toString());
                    if(assumedname==null)  assumedname = newmap.get(Species.Term.PLURAL.toString());
                    if(assumedname==null)  assumedname = newmap.get(Species.Term.SINGULAR.toString());
                    if(assumedname==null)  assumedname = ident;
                    Species.Instance(assumedname, newmap);
                } catch(FileNotFoundException e) {
                    System.err.println(String.format("could not load %s species", assumedname));
                }
            }
            
        } catch(FileNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
            if(directory.equals(DEFAULT_DIRECTORY))  System.exit(1);
            load(DEFAULT_DIRECTORY);
        } catch(IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
