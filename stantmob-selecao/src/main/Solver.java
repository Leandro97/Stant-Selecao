package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Solver {
	private Map values;
	private List keyIndex;
	private List<Integer> aux = new ArrayList<Integer>();
	private int currentTime = 9;
	private int remainingTime = 180;	
	private int activities;
	private Integer[][] record;

	public Solver() throws IOException {
		Init init = new Init();
		values = init.getMap();
		keyIndex = init.getKeyIndex();
		activities = keyIndex.size();
		record = new Integer[activities + 1][remainingTime + 1];
	}

	public int getValue(int index) {
		return (Integer) values.get(keyIndex.get(index));
	}

	public Integer[][] getRecord() {
		return record;
	}
	
	public int pd(int act, int remainingTime) {
		//TODO CONDIÇÕES DE PARADA

		if((remainingTime == 0) || (act == activities)) {
			record[act][remainingTime] = 0;
			return 0;
		}

		if(record[act][remainingTime] != null) {
			return record[act][remainingTime];
		}

		if(getValue(act) > remainingTime) {
			record[act][remainingTime] = pd(act + 1, remainingTime);
		} else {
			int put = 1 + pd(act + 1, remainingTime - getValue(act));
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
	
	public List track(int act, int remainingTime) {
		if (act == 8 || record[act + 1][remainingTime] == null) {
			return null;
		}
		
		if (record[act][remainingTime] > record[act + 1][remainingTime]) {
			aux.add(act);
			return track(act + 1, remainingTime - getValue(act));
		} else {
			return track(act + 1, remainingTime);
		}
	}
	
	public List getAux() {
		int total = 0;
		for (Integer i : aux) {
			total += getValue(i);
		}
		System.out.println(total);
		return aux;
	}
}
