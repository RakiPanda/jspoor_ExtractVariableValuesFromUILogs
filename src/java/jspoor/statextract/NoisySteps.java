package jspoor.statextract;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * This class loads and hold the noisy steps for user while he has done a UI
 * task. It is used to remove these steps from further calculations.
 * 
 * @author arin ghazarian
 * 
 */
public class NoisySteps {
	Hashtable noisesTable;

	public NoisySteps(String fileName) {
		noisesTable = new Hashtable();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		String lineStr;
		try {
			while ((lineStr = in.readLine()) != null) {
				int spaceIndx = lineStr.indexOf(' ');
				String trialNo = lineStr.substring(0, spaceIndx);
				String noisySteps = lineStr.substring(spaceIndx + 1);

				StringTokenizer tokenizer = new StringTokenizer(noisySteps, ",");
				ArrayList noises = new ArrayList();
				while (tokenizer.hasMoreTokens()) {

					noises.add(Integer.parseInt(tokenizer.nextToken()));
				}
				noisesTable.put(Integer.parseInt(trialNo), noises);

			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * returns whether a given step in a specfic trial is noisy or not
	 * 
	 * @param trialNo
	 *            the trial number of the task performance
	 * @param stepNo
	 *            step number, each task is consisted of some steps
	 * @return a boolean indicating whether the given step is noisy or correct
	 */
	public boolean isNoisy(int trialNo, int stepNo) {

		ArrayList noises = (ArrayList) noisesTable.get(new Integer(trialNo));
		if (noises != null)
			return noises.contains(new Integer(stepNo + 1));
		else
			return false;

	}

}
