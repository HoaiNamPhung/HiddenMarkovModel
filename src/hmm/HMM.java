package hmm;

import smile.math.matrix.Matrix;

public class HMM {
	
	// Lambda L = (A, B, pi)
	// Either generated by Problem 3 or given for Problem 1/2.
	double[][] initA = null;
	double[][] initB = null;
	double[] initPi = null;

	// Other notable variables distinct to HMMs.
	int[][] Os;
	int N;
	int M;
	int T;
	
	// Stored HMM post Baum-Welch.
	smile.sequence.HMM hmm = null;
	
	/**
	 * Constructor for the discrete HMM for Problem 3, which will train a stochastic model using the Baum-Welch algorithm. 
	 * Mainly a wrapper for outputting smile's HMM implementation with our own initialized model.
	 * @param observations    The array containing n-fold observation sequences O, which themselves are of form (O[0],O[1],...,O[T-1]).
	 * @param numStates       The number of states, N.
	 * @param numObservations The number of observation symbols per state, M.
	 */
	public HMM(int[][] observations, int numStates, int numObservations) {
		this.Os = observations;
		this.N = numStates;
		this.M = numObservations;
		
		// T is the length of the observation sequence.
		this.T = observations[0].length;

		// Adjustable parameters.
		final double PERCENT_RANGE = .01; // Max percent difference from the uniform origin value for any number in the matrix.
		final double SUM = 1;

		// Generating stochastic non-uniform values for each matrix in L = (A, B, pi)
		this.initA = generateStochasticMatrix(PERCENT_RANGE, N, N, SUM); // A = NxN matrix
		this.initB = generateStochasticMatrix(PERCENT_RANGE, N, M, SUM); // B = NxM matrix
		this.initPi = generateStochasticRow(PERCENT_RANGE, N, SUM); 	// pi = 1xN matrix
	}
	
	/**
	 * Generates a model L = (A, B, pi) that maximizes the probability for a given observation sequence.
	 * @param maxIters The maximum number of iterations before early stopping.
	 * @return Returns L as a Triplet containing 2D double array A, 2D double array B, and double array pi.
	 */
	public smile.sequence.HMM solveProblem3(int maxIters) {
		Matrix a = new Matrix(initA);
		Matrix b = new Matrix(initB);
		this.hmm = new smile.sequence.HMM(initPi, a, b);
		this.hmm.update(Os, maxIters);
		return hmm;
	}
	
	/**
	 * Tests log[probability of O | L] on the previously trained HMM model.
	 * @throws Exception Throws if there is no trained model to work with yet.
	 */
	public double solveProblem1(int[] observationSeq) throws Exception {
		if (this.hmm == null) {
			throw new Exception("Model has not yet been trained using Baum-Welch algorithm.");
		}
		double logProb = this.hmm.logp(observationSeq);
		return logProb;
	}
	
	/**
	 * Generates a stochastic row with non-uniform values.
	 * @param range       The upper limit on deviation from a uniform value for each of the row's values..
	 * @param numElements The size of the row.
	 * @param sum         The desired total for the row's values when added up.
	 * @return Returns a stochastic row with non-uniform values.
	 */
	private double[] generateStochasticRow(double range, int numElements, double sum) {
		double uniformVal = sum / numElements;
		double[] stochasticRow = new double[numElements];
		double sumSoFar = 0;
		// Generate a random value between [uniformVal - range : uniformVal + range] for
		// all but the last element.
		for (int i = 0; i < numElements - 1; i++) {
			double deviation = uniformVal * range * Math.random();
			// Decide whether the value will positively or negatively deviate from the
			// uniform value.
			if (Math.random() < 0.5) {
				deviation *= -1;
			}
			stochasticRow[i] = uniformVal + deviation;
			sumSoFar += stochasticRow[i];
		}
		// Generate the value for the last element, ensuring everything adds up to sum.
		// Note that it might not be w/in range.
		stochasticRow[numElements - 1] = sum - sumSoFar;
		return stochasticRow;
	}

	/**
	 * Generates a stochastic matrix with stochastic rows containing non-uniform values. Note that columns ARE NOT stochastic.
	 * @param range      The upper limit on deviation from a uniform value for each of the row's values.
	 * @param numRows    The number of rows.
	 * @param numColumns The number of columns.
	 * @param sum        The desired total for the row's values when added up.
	 * @return Returns a stochastic matrix with non-uniform values.
	 */
	private double[][] generateStochasticMatrix(double range, int numRows, int numColumns, double sum) {
		double[][] stochasticMatrix = new double[numRows][numColumns];
		// Generate stochastic rows for the matrix.
		for (int i = 0; i < numRows; i++) {
			stochasticMatrix[i] = generateStochasticRow(range, numColumns, sum);
		}
		return stochasticMatrix;
	}
}