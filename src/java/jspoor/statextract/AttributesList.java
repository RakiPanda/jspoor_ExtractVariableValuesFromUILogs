package jspoor.statextract;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * This class loads the list of attributes which should be included in weka arff
 * reports (such as stepArffAS) from a file named "attrsList.txt" located at the
 * root directory from where the application runs.
 * 
 * @author arin ghazarian
 * 
 */
public class AttributesList {

	/**
	 * this hashtable holds the attributes names
	 */
	private static HashSet attrsSet;
	/**
	 * singleton instance
	 */
	private static AttributesList instance;

	public static AttributesList getInstance(String path) {
		if (instance == null)
			instance = new AttributesList(path);
		return instance;
	}

	private AttributesList(String path) {
		attrsSet = new HashSet();
		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(path + "\\attrsList.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String attr = null;

		try {
			while ((attr = in.readLine()) != null) {
				attrsSet.add(attr);

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * returns whether a given attribute is in the attributes list loaded from
	 * disk
	 * 
	 * @param attrName
	 *            an attribute name
	 * @return return true if the given attribute is in the attributes list
	 *         loaded
	 */
	public boolean isInAttributesSet(String attrName) {
		return attrsSet.contains(attrName.trim());
	}

}
