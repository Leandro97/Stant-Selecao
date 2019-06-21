package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Init {
	private Map<String, Integer> map;
	private List<String> keyIndex = new ArrayList<String>();
	
	public int check(String line) {
		int time;
		if(line.equals("lightning")) {
			time =  5;
		} else {
			time = Integer.parseInt(line.split("m")[0]);
		}
		
		return time;
	}
	
	public Map getMap() throws IOException {
		URL url = Main.class.getClassLoader().getResource("files/proposals.txt");
		File file = new File(url.getPath());
		BufferedReader reader = new BufferedReader(new FileReader(file)); 
		
		String line;
		map = new LinkedHashMap<String, Integer>();
		
        while((line = reader.readLine()) != null) {
        	keyIndex.add(line);
        	
        	String[] aux = line.split(" ");
        	String last = aux[aux.length - 1];
        	
        	int duration = check(last);
        	map.put(line, duration);
        }
        
        return map;
	}
	
	public List getKeyIndex() {
		return keyIndex;
	}
	
	public int getTotalTime() {
		Iterator iterator = map.entrySet().iterator(); 
		int total = 0;
		
		while (iterator.hasNext()) { 
            Map.Entry entry = (Map.Entry)iterator.next(); 
            total += (Integer) entry.getValue();
        } 

		return total;
	}
}
