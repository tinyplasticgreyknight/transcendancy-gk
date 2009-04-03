package org.greyfire.transcendancy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.greyfire.transcendancy.bio.Species;

public class DataResources {
	private static String DEFAULT_DIRECTORY = "";
	public ArrayList<String> starnames = new ArrayList<String>();
	public ArrayList<String> speciesidents = new ArrayList<String>();
	
	public DataResources() {
		this.load(DEFAULT_DIRECTORY);
	}
	public DataResources(String directory) {
		this.load(directory);
	}
	
	public InputStream openFile(String filename) throws FileNotFoundException {
		InputStream instream = null;
		try {
			instream = new FileInputStream(filename);
		} catch(FileNotFoundException e) {
			try {
				instream = this.getClass().getResource(filename).openStream();
			} catch(IOException e2) {
				throw new FileNotFoundException(String.format("could not open archived file: \"%s\"", filename));
			} catch(NullPointerException e2) {
				throw new FileNotFoundException(String.format("could not open archived file: \"%s\"", filename));
			}
		}
		return instream;
	}
	
	public ArrayList<String> loadNames(String filename) throws FileNotFoundException, IOException {
		ArrayList<String> names = new ArrayList<String>();
		InputStream instream;
		BufferedReader inreader;
		String line;
		
		instream = this.openFile(filename);
		inreader = new BufferedReader(new InputStreamReader(instream));
		while((line = inreader.readLine())!=null) {
			line = line.trim();
			if(!(line=="" || line.charAt(0)=='#')) { 
				names.add(line);
			}
		}
		instream.close();
		
		return names;
	}
	
	public HashMap<String, String> loadMap(String filename) throws FileNotFoundException, IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		InputStream instream;
		BufferedReader inreader;
		String line, key, value;
		int a;
		
		instream = this.openFile(filename);
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
	
	public void load(String directory) {
		ArrayList<String> newnames;
		HashMap<String, String> newmap;
		String assumedname;
		try {
			newnames = this.loadNames(String.format("%scosmos/starnames.txt", directory));
			starnames = newnames;

			newnames = this.loadNames(String.format("%sbio/speciesindex.txt", directory));
			speciesidents = newnames;
			
			for(String ident : speciesidents) {
				assumedname = ident;
				try {
					newmap = this.loadMap(String.format("%sbio/%s/terms.txt", directory, ident));
					assumedname = newmap.get(Species.Term.COLLECTIVE.toString());
					if(assumedname==null)  assumedname = newmap.get(Species.Term.PLURAL.toString());
					if(assumedname==null)  assumedname = newmap.get(Species.Term.SINGULAR.toString());
					if(assumedname==null)  assumedname = ident;
					System.out.println(String.format("init %s species", assumedname));
					Species.Instance(assumedname, newmap);
				} catch(FileNotFoundException e) {
					System.err.println(String.format("could not load %s species", assumedname));
				}
			}
			
		} catch(FileNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			if(directory.equals(DEFAULT_DIRECTORY))  System.exit(1);
			this.load(DEFAULT_DIRECTORY);
		} catch(IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
}
