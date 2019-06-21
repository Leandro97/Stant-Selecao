package main;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {	
	public static void main(String[] args) throws IOException {
		Solver solver = new Solver();
		solver.pd(0, 180);
		solver.track(0, 180);
		System.out.println(solver.getAux());
	}

}
