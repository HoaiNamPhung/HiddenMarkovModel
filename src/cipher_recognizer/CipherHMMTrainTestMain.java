package cipher_recognizer;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import hmm.*;

class CipherHMMTrainTestMain {

	@Test
	void test() throws Exception {
		
		/* =================== PARAMS ===================== */
		
		// Hyper-parameters
		int N = 7;
		int M = 27;		// 26 letters in alphabet + 1 (for 0)
		int T = 54;
		int maxIters = 200;
		int numCiphers = 1000;
		int numTestSets = 200;		
		final int TEST_SET_INDEX = 5;
		
		// Enciphering methods
		String METHOD_0 = "vary_key_ctc";
		String METHOD_1 = "vary_text_ctc";
		String METHOD_2 = "vary_key_ssc";
		String METHOD_3 = "vary_text_ssc";
		String METHOD_4 = "vary_key_cc";
		String METHOD_5 = "vary_text_cc";
		String[] methods = {METHOD_0, METHOD_1, METHOD_2, METHOD_3, METHOD_4, METHOD_5};
		
		/* ===================== TRAIN ====================== */
		
		// Load our dataset and dictionary.
		Serializer s = Serializer.getSerializer();
		String DATASET_PATH = "./processed_cipher_datasets/encrypted_datasets_" + numCiphers + ".txt";
		String PLAINTEXT_DATASET_PATH = "./processed_cipher_datasets/normaltext_dataset.txt";
		String TEST_RESULTS_PATH = "./cipher_test_outputs/" + numCiphers + "_testoutput_N" + N + "_M" + M + "_T" + T + "_" + methods[TEST_SET_INDEX] + ".csv";
		List<int[][]> osList = s.deserializeDatasetList(DATASET_PATH);
		
		// We can train our HMM model on non-enciphered plaintext.
		List<int[][]> plainOsList = s.deserializeDatasetList(PLAINTEXT_DATASET_PATH);
		int[][] plainOs = plainOsList.get(0);
		
		// Or we can train an HMM model based on our first enciphering method data-set.
		// Split observation sequence data-set into training and testing data.
		int[][] trainingData = Arrays.copyOfRange(osList.get(TEST_SET_INDEX), TEST_SET_INDEX, osList.get(TEST_SET_INDEX).length - numTestSets);
		int[][] testingData = Arrays.copyOfRange(osList.get(TEST_SET_INDEX), osList.get(TEST_SET_INDEX).length - numTestSets, osList.get(TEST_SET_INDEX).length);
		
		// Train model with HMM (Baum-Welch)
		HMM hmm = new HMM(trainingData, N, M);
		smile.sequence.HMM trainedHMM = hmm.solveProblem3(maxIters);
		System.out.println(trainedHMM.toString());
		
		/* =================== TEST DATA ===================== */
		// Test the model.
		System.out.println("");
		System.out.println("=== Testing ===");
		System.out.println("");
		
		// Testing set of base malware family.
		FileWriter csvWriter = new FileWriter(TEST_RESULTS_PATH, false);	
		for (int i = 0; i < osList.size(); i++) {
			int[][] Os = osList.get(i);
			if (i == TEST_SET_INDEX) {
				Os = testingData;
			}
			System.out.println(methods[i]);
			csvWriter.append(methods[i] + ",");
			for (int[] O : Os) {
				// Printing output.
				double logProbTest = hmm.solveProblem1(O);
				double standardizedLogProbTest = logProbTest / O.length;
				System.out.println("logProbTest: " + logProbTest + " | standardized: " + standardizedLogProbTest);
				// Each line contains comma-separated observation sequence logProbs for a given enciphering method.
				csvWriter.append(standardizedLogProbTest + "");
				csvWriter.append(",");
			}
			// We store the results for the next enciphering method on the next line.
			csvWriter.append("\n");
			System.out.println();
		}
		csvWriter.flush();
		csvWriter.close();
	}
}
