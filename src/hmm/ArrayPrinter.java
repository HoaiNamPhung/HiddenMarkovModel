package hmm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringJoiner;

public class ArrayPrinter {
	
	BufferedWriter myWriter = null;

	public ArrayPrinter() {}
	
	public ArrayPrinter(String file) {
		try {
			this.myWriter = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToFile(String str) {
		try {
			myWriter.write(str);
			myWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToFile(double[] arr) {
		String str = Arrays.toString(arr);
		writeToFile(str);
	}
	
	public void writeToFile(double[][] arr) {
		String str = convertToString(arr);
		writeToFile(str);
	}
	
	public void close() {
		if (myWriter != null) {
			try {
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String convertToString(double[][] arr) {
		StringJoiner sj = new StringJoiner(System.lineSeparator());
		for (double[] row : arr) {
		    sj.add(Arrays.toString(row));
		}
		return sj.toString();
	}
	
	public String convertToString(int[][] arr) {
		StringJoiner sj = new StringJoiner(System.lineSeparator());
		for (int[] row : arr) {
		    sj.add(Arrays.toString(row));
		}
		return sj.toString();
	}
	
	public void printString(double[][] arr) {
		String str = convertToString(arr);
		System.out.println(str);
	}
	
	public void printString(int[][] arr) {
		String str = convertToString(arr);
		System.out.println(str);
	}
}
