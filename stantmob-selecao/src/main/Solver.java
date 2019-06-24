package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
	private List<String> talkList;
	private List<Integer> timeList;
	private List<Integer> aux = new ArrayList<Integer>();
	private List<String> answer = new ArrayList<String>();
	private int currentTime = 540;
	private int remainingTime = 240;	
	private int activities = 0;
	private double tracks;
	private Integer[][] record;

	public Solver() throws IOException {
		double total = 0;
		//URL url = Main.class.getClassLoader().getResource("files/proposals.txt");
		//URL url = Main.class.getClassLoader().getResource("files/proposals2.txt");
		URL url = Main.class.getClassLoader().getResource("files/proposals3.txt");

		
		File file = new File(url.getPath());
		BufferedReader reader = new BufferedReader(new FileReader(file)); 

		String line;
		talkList = new ArrayList<String>();
		timeList = new ArrayList<Integer>();

		while((line = reader.readLine()) != null) {
			activities++;
			String last = line.replaceAll("\\D+","");
			
			int time;
			if(last.equals("")) {
				time =  5;
			} else {
				time = Integer.parseInt(last.split("m")[0]);
			}

			int duration = time;
			total += time;
			
			talkList.add(line);
			timeList.add(duration);
		}
		
		reader.close();
		tracks = Math.ceil(total/420);
		record = new Integer[activities + 1][remainingTime + 1];
	}

	public Integer[][] getRecord() {
		return record;
	}

	public int pd(int act, int remainingTime) {
		if((remainingTime == 0) || (act == activities)) {
			record[act][remainingTime] = 0;
			return 0;
		}

		if(record[act][remainingTime] != null) {
			return record[act][remainingTime];
		}

		if(timeList.get(act) > remainingTime) {
			record[act][remainingTime] = pd(act + 1, remainingTime);
		} else {
			int put = 1 + pd(act + 1, remainingTime - timeList.get(act));
			int notPut = pd(act + 1, remainingTime);

			if(put > notPut) {
				record[act][remainingTime] = put;

			}else {
				record[act][remainingTime] = notPut;
			}
		}
		//System.out.println(getValue(act) + " " + remainingTime + " " + record[act][remainingTime]);
		return record[act][remainingTime];
	}

	public List<Integer> track(int act, int remainingTime) {
		if (act == activities || record[act + 1][remainingTime] == null) {
			return null;
		}

		if (record[act][remainingTime] > record[act + 1][remainingTime]) {
			aux.add(act);
			
			return track(act + 1, remainingTime - timeList.get(act));
		} else {
			return track(act + 1, remainingTime);
		}
	}
	
	public void reset(List<Integer> indexes) {
		record = new Integer[activities + 1][remainingTime + 1];
		aux = new ArrayList<Integer>();
		
		for (int index : indexes) {
			String str = formatting(currentTime) + " " + talkList.get(index);
			answer.add(str);
			currentTime += timeList.get(index);
		}
		
		Collections.reverse(indexes);
		for (int index : indexes) {
			activities--;
			talkList.remove(index);
			timeList.remove(index);
		}
		
	}
	
	public String formatting(int time) {
		int hour = time / 60;
		int minute = time % 60;
		String str = String.format("%02d" , hour) + ":" + String.format("%02d" , minute);
		return str;
	}

	public List<String> getAnswer() {
		return answer;
	}
	
	public List<Integer> getAux() {
		return aux;
	}
	
	public double getTracks() {
		return tracks;
	}
	
	public void setTask(String task, int time) {
		answer.add(task);
		currentTime = time;
	}
	
	public void setCurrentTime(int time) {
		currentTime = time;
	}
}
