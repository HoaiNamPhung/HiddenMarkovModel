package hmm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Serializer {

	// Singleton instance for serializing files.
	private static Serializer s = null;
		
	// Private constructor to prevent re-instantiation
	private Serializer() {
	}
		
	// Get the existing instance.
	public static Serializer getSerializer() {
		if (s == null) {
			s = new Serializer();
		}
		return s;
	}
	
	/**
	 * Serializes the data-set, an array of integers, in a file of a given name.
	 * @param dataset The data-set to store into a file.
	 * @param filename The file to store the data-set into.
	 * @param appendFlag If false, overwrite current file.
	 */
	public void serializeDataset(int[][] dataset, String filename, boolean appendFlag) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, appendFlag);
			ObjectOutput savefile = new ObjectOutputStream(fos);
			savefile.writeObject(dataset);
			savefile.close();
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Serializes the data-set, a list of an array of integers, in a file of a given name.
	 * @param dataset The data-set to store into a file.
	 * @param filename The file to store the data-set into.
	 * @param appendFlag If false, overwrite current file.
	 */
	public void serializeDatasetList(List<int[][]> dataset, String filename, boolean appendFlag) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, appendFlag);
			ObjectOutput savefile = new ObjectOutputStream(fos);
			savefile.writeObject(dataset);
			savefile.close();
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Serializes the mapping of type <String, Integer> into a file of a given name.
	 * @param mapping The mapping to store into a file.
	 * @param filename The file to store the mapping into.
	 * @param appendFlag If false, overwrite current file.
	 */
	public void serializeMapping(Map<String, Integer> map, String filename, boolean appendFlag) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, appendFlag);
			ObjectOutput savefile = new ObjectOutputStream(fos);
			savefile.writeObject(map);
			savefile.close();
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves the data-set, an array of integers, from a file of a given name.
	 * @param filename The file to load the data-set from.
	 * @return Returns the retrieved data-set.
	 */
	public int[][] deserializeDataset(String filename) {
		int[][] dataset;
		try { 
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream savefile = new ObjectInputStream(fis);
			dataset = (int[][]) savefile.readObject();
			savefile.close();
			fis.close();
			return dataset;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retrieves the data-set, a list of an array of integers, from a file of a given name.
	 * @param filename The file to load the data-set from.
	 * @return Returns the retrieved data-set.
	 */
	@SuppressWarnings("unchecked")
	public List<int[][]> deserializeDatasetList(String filename) {
		List<int[][]> dataset;
		try { 
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream savefile = new ObjectInputStream(fis);
			dataset = (List<int[][]>) savefile.readObject();
			savefile.close();
			fis.close();
			return dataset;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retrieves the mapping of type <String, Integer> from a file of a given name.
	 * @param filename The file to load the mapping from.
	 * @return Returns the retrieved mapping.
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> deserializeMapping(String filename) {
		HashMap<String, Integer> map = new HashMap<>();
		try { 
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream savefile = new ObjectInputStream(fis);
			map = (HashMap<String, Integer>) savefile.readObject();
			savefile.close();
			fis.close();
			return map;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
