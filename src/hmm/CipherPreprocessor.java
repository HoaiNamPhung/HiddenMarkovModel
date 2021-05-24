package hmm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CipherPreprocessor {
	
	// Singleton instance for pre-processing.
	private static CipherPreprocessor pp = null;
	
	// Private constructor to prevent re-instantiation
	private CipherPreprocessor() {	
	}
	
	// Get the existing instance.
	public static CipherPreprocessor getPreprocessor() {
		if (pp == null) {
			pp = new CipherPreprocessor();
		}
		return pp;
	}
	
	/**
	 * Converts a .csv of ciphertexts to an array of observation sequences, stored in a list separated by enciphering method.
	 * All alphabetical characters are converted to their equivalent numerical values.
	 * Assumes that each column of the given .csv is a different method of enciphering.
	 * T determines the length of each observation sequence, truncating and 0-filling as necessary. 0 represents no alphabetical character.
	 * @param dirPath The path to the directory of files.
	 * @return Returns an array of observation sequences.	
	 * @throws IOException There was an error in reading the file line-by-line.
	 * @throws FileNotFoundException The specified file could not be found or accessed.
	 */
	public List<int[][]> getObservationSequences(String dirPath, int T) throws FileNotFoundException, IOException {
		
		List<int[][]> Os = new ArrayList<>();
		    
		// Break .csv into a list of different enciphering methods on a line-by-line (column-by-column) basis.
		BufferedReader br = new BufferedReader(new FileReader(dirPath));
		String line;
		while ((line = br.readLine()) != null) {
			int i = 0;
			String[] ciphertexts = line.split(",");
		    int[][] arrO = new int[ciphertexts.length][T];
		        
		    // Create an array of observation sequences from the current line (column).
		    for (String ciphertext : ciphertexts) {
		    	
		    	// If current ciphertext obtained from .csv is "0", then it and the rest of the line is empty. Stop here.
		    	if (ciphertext.equals("0")) {
		    		break;
		    	}
		    	
		    	// Create observation sequence of length T from ciphertext.
		    	int t = 0;
		    	char[] charArr = ciphertext.toCharArray();
		    	for (char c : charArr) {
		    		if (t >= T) {
		    			break;
		    		}
		    		arrO[i][t] = getNumericalValue(c);
		    		t++;
		        }
		        i++;
		    }
		    Os.add(arrO);
		}
		br.close();
		return Os;
	}
	
	/**
	 * Returns the numerical value for an alphabetical character; A~Z -> 1~26.
	 * @param ch The alphabetical character to convert.
	 * @return The numerical value of the given character.
	 */
	private int getNumericalValue(char ch) {
		return Character.getNumericValue(ch) - 9;
	}
}
