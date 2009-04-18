/*
 * Music.java
 *
 * Created on 09 April 2009, 23:59
 */

package org.greyfire.transcendancy.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.*;
import org.greyfire.transcendancy.DataResources;

/**
 *
 * @author rodge
 */
public abstract class Music {
    
    private static int EXTERNAL_BUFFER_SIZE = 128000;
    public static boolean active = false;
    
    public static void play(String filename) throws FileNotFoundException, IOException {
        if(!Music.active) return;
        AudioInputStream stream;
        SourceDataLine line = null;
        try {
            stream = AudioSystem.getAudioInputStream(DataResources.openFile(filename));
            
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            System.out.println("play()");
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            int nBytesRead = 0;
            byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
            while (Music.active && nBytesRead != -1) {
                try {
                    nBytesRead = stream.read(abData, 0, abData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nBytesRead >= 0) {
                    int nBytesWritten = line.write(abData, 0, nBytesRead);
                }
            }
        
        } catch (UnsupportedAudioFileException e1) {
            throw new IOException(e1);
        } catch (LineUnavailableException e2) {
            throw new IOException(e2);
        } finally {
            if(line==null) return;
            line.drain();
            line.close();
        }
    }
    public static void loop(String filename) throws FileNotFoundException, IOException {
        while(Music.active) {
            play(filename);
        }
    }
    public static void loop(String filename, int n) throws FileNotFoundException, IOException {
        for(int i=0; Music.active && i<n; i++) {
            play(filename);
        }
    }
}
