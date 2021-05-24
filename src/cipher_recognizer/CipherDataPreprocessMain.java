package cipher_recognizer;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import hmm.*;

class CipherDataPreprocessMain {

	@Test
	void test() {
		CipherPreprocessor pp = CipherPreprocessor.getPreprocessor();
		Serializer s = Serializer.getSerializer();
				
		// Adjustable hyper-parameters
		int T = 54;				
		int numCiphers = 1000;
		
		// Filepaths
		String CSV_PATH = "./cipher_datasets/encrypted_datasets_" + numCiphers + ".csv";
		String SAVE_PATH = "./processed_cipher_datasets/encrypted_datasets_" + numCiphers + ".txt";
		
		// Preprocess.
		List<int[][]> cipherObservations = null;
		try {
			cipherObservations = pp.getObservationSequences(CSV_PATH, T);
			
			// Print observations.
			ArrayPrinter ap = new ArrayPrinter();
			for (int[][] arrO : cipherObservations) {
				ap.printString(arrO);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Serialize
		s.serializeDatasetList(cipherObservations, SAVE_PATH, false);
		System.out.println("Done.");
	}
}
