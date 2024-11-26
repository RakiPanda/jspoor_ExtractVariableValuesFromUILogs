package jspoor.statextract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class is used to verify whether a sequence of actions are correct
 * according to the sequence of actions given in the shelldata file.
 * 
 * @author arin ghazarian
 * 
 */
public class EventsStreamParser {
	PrintWriter outputWriter;
	int actionCounter;
	/**
	 * each element in this array contains the actions for an step of the UI
	 * task. element 0 is the first step and actions in each step are added in
	 * their happening order.
	 */
	ArrayList actionMatrix[];
	private int curStepNo = -1;
	/**
	 * no sequence checking will be done and always will return true
	 */
	private boolean dontCheck;

	public EventsStreamParser(String fileUrl, ArrayList[] actionMatrix,
			boolean dontCheck) {
		actionCounter = 0;
		this.actionMatrix = actionMatrix;
		this.dontCheck = dontCheck;

		try {

			FileWriter outFile = new FileWriter(fileUrl, false);
			outputWriter = new PrintWriter(outFile);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * compares the given action with its action matrix to seee if the action is
	 * allowed
	 * 
	 * @param stepNo
	 *            current step number of the UI task
	 * @param action
	 *            an string indicating the action happened
	 * @return true if the given action is the next correct action
	 */
	public boolean parseNextEvent(int stepNo, String action) {
		if (dontCheck)
			return true;
		curStepNo = stepNo;
		if (stepNo != ExtractVariableValuesFromUILogs.NOSTEP) {
			if (actionCounter < actionMatrix[stepNo].size()) {
				if (action.equals(actionMatrix[stepNo].get(actionCounter))) {
					actionCounter++;
					return true;
				} else {
					if (action.equals("Dragged")) {
						outputWriter.println("Extra Draggs!");
						return true;
					} else {

						// if (actionCounter<actionMatrix[curStepNo].size()){
						//outputWriter.println("Incomplete step actions at step "
						// +curStepNo);
						// }

						if (((stepNo + 1) < actionMatrix.length)
								&& (actionCounter == 0)
								&& action.equals(actionMatrix[stepNo + 1]
										.get(0))) {
							outputWriter.println("Step " + curStepNo
									+ " was  missed! ");
						} else {
							outputWriter.println("Wrong Action where "
									+ actionMatrix[stepNo].get(actionCounter)
									+ " was Expected!");
						}
						if (actionCounter != 0) {
							actionCounter = 0;
							parseNextEvent(stepNo, action);
						} else
							actionCounter = 0;
					}
					return false;
				}
			} else {
				outputWriter.println("extra Action:" + action);
				return false;
			}
		} else {
			return true;
		}
	}

	public void resetParserForNextStep() {

		actionCounter = 0;
	}

	public void eventsTerminate() {
		outputWriter.flush();
		outputWriter.close();
	}

}
