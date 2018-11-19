/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.bootloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Bartek
 */
public class CodeMemory {
    
    
    private HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();      
    
    public CodeMemory(IntelHexFile file) throws IntelHexException {        
        for(int lineNumber = 0; lineNumber < file.getLinesCount(); ++ lineNumber) {
            IntelHexLine line = file.getLine(lineNumber);
            if((line.getType() ==0) && (line.getCount() > 0)) {
                for(int i=0; i < line.getCount(); ++i) {
                    data.put(line.getAddress()+i, line.getData(i));
                }                                
            }            
        }
    } 

    
    public List<Integer> getPage(int address, int pageSize) {
        boolean hasData = false;
        ArrayList<Integer> page = new ArrayList<Integer>();
        for(int i = 0; i< pageSize; ++i ) {
            Integer value = data.get(address + i);
            if((value != null) && (value.intValue() != 0xFF) ) {
                page.add(value);
                hasData = true;
            } else {
                page.add(0xFF);
            }            
        }
        if(!hasData) {
            page.clear();
        }
        return page;        
    }
        
}
