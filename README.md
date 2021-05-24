# HiddenMarkovModel
Implementation of a Hidden Markov Model and the Baum Welch Algorithm.

Can currently do the following:
* Train an HMM on an observation sequence
* Score a given observation sequence based on a trained HMM

There also exist main methods for preprocessing potential observations sequences as data. Currently can preprocess:
* 'Malware': Actually just .txt files with an opcode on each line
* 'Ciphers': Datasets in the form of a .csv file, containing ciphertexts for each column, different enciphering methods for each row

Main methods are already given for training and testing on an HMM with said malware and ciphertext observation sequences once they are serialized by the preprocessing main methods.
