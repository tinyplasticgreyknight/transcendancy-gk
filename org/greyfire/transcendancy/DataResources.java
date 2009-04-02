package org.greyfire.transcendancy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataResources {
	private static String DEFAULT_DIRECTORY = "resource";
	public ArrayList<String> starnames = new ArrayList<String>();
	
	public DataResources() {
		this.load(DEFAULT_DIRECTORY);
	}
	public DataResources(String directory) {
		this.load(directory);
	}
	
	public static ArrayList<String> loadNames(String filename) throws FileNotFoundException, IOException {
		ArrayList<String> names = new ArrayList<String>();
		FileInputStream instream;
		BufferedReader inreader;
		String line;
		
		instream = new FileInputStream(new File(
				ClassLoader.getSystemResource(filename).getFile()
		));
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
	
	public void load(String directory) {
		ArrayList<String> newnames;
		try {
			newnames = DataResources.loadNames(String.format("%s/names/stars.txt", directory));
			starnames = newnames;
		} catch(FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch(IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
