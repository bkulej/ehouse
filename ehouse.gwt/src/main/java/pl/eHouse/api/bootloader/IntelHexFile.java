/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.bootloader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Bartek
 */
public class IntelHexFile {
    
    private ArrayList<IntelHexLine> lines = new ArrayList<IntelHexLine>();
    private int dataLines =0;
    
    public IntelHexFile() {        
    }

    public IntelHexFile(File file) throws FileNotFoundException, IOException, IntelHexException {        
        BufferedReader reader = new BufferedReader(new FileReader(file));            
        try {
            String line = null;
            while(( line = reader.readLine()) != null) {
                IntelHexLine hex = new IntelHexLine(line);
                if(hex.getType() == 0) {
                    ++dataLines;
                }
                lines.add(hex);
            }
        } finally {
            reader.close();
        }
    }
    
    
    public IntelHexLine getLine(int i) {
        return lines.get(i);
    }
    
    
    public void setLine(int i, IntelHexLine line) {
        lines.set(i, line);
    }
    
    
    public void addLine(IntelHexLine line) {
        if(line.getType() == 0) {
            ++dataLines;
        }
        lines.add(line);
    }
    
    
    public int getLinesCount() {
        return lines.size();
    }

    public int getDataLines() {
        return dataLines;
    }    
    
    public void write(String fileName) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(fileName));
        try {
            for(IntelHexLine line: lines) {             
                file.write(line.getFormatedLine());
                file.newLine();
            }
        } finally {
            file.close();
        }
    }
        
    
}
