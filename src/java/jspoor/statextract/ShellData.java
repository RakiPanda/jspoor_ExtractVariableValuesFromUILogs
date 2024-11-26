package jspoor.statextract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class loads and holds the shell data related to performing a task in a
 * software.
 * 
 * @author arin ghazarian
 * 
 */
public class ShellData {

	/**
	 * the singleton instance of this class
	 * 
	 */
	private static ShellData instance;

	public int numOfSteps;
	public int[] actionsCountInSteps;
	public int NOVICE_NOVICE_HIGH;
	public int NOVICE_MODERATE_LOW;
	public int NOVICE_MODERATE_HIGH;
	public int NOVICE_SKILLED_LOW;
	public int EXPERT_NOVICE_HIGH;
	public int EXPERT_MODERATE_LOW;
	public int EXPERT_MODERATE_HIGH;
	public int EXPERT_SKILLED_LOW;
	public int MIN_PAUSE_DURATION;
	public float stepsKlmTime[];
	public float[] minStepsDistance;
	public int numOfResponseTimeActions;
	public ResponsingAction[] responsingActionsList;
	ArrayList actionMatrix[];
	ActionIndicator[] stepsPerformanceIndictor;
	ActionIndicator startActionIndictor;

	/**
	 * loads the data from a text file located at the root folder where the
	 * application runs
	 */
	private ShellData() {
		File f = new File("shelldata.txt");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			StringTokenizer tokenizer;
			String line;
			String nexToken;

			line = in.readLine();
			tokenizer = new StringTokenizer(line, ",");
			nexToken = tokenizer.nextToken().trim();
			numOfSteps = Integer.parseInt(nexToken);

			actionsCountInSteps = new int[numOfSteps];
			tokenizer = new StringTokenizer(in.readLine(), ",");
			for (int i = 0; i < numOfSteps; i++) {
				actionsCountInSteps[i] = Integer.parseInt(tokenizer.nextToken()
						.trim());
			}

			tokenizer = new StringTokenizer(in.readLine(), ",");

			NOVICE_NOVICE_HIGH = Integer.parseInt(tokenizer.nextToken().trim());
			NOVICE_MODERATE_LOW = Integer
					.parseInt(tokenizer.nextToken().trim());
			NOVICE_MODERATE_HIGH = Integer.parseInt(tokenizer.nextToken()
					.trim());
			NOVICE_SKILLED_LOW = Integer.parseInt(tokenizer.nextToken().trim());

			tokenizer = new StringTokenizer(in.readLine(), ",");
			EXPERT_NOVICE_HIGH = Integer.parseInt(tokenizer.nextToken().trim());
			EXPERT_MODERATE_LOW = Integer
					.parseInt(tokenizer.nextToken().trim());
			EXPERT_MODERATE_HIGH = Integer.parseInt(tokenizer.nextToken()
					.trim());
			EXPERT_SKILLED_LOW = Integer.parseInt(tokenizer.nextToken().trim());

			tokenizer = new StringTokenizer(in.readLine(), ",");
			MIN_PAUSE_DURATION = Integer.parseInt(tokenizer.nextToken().trim());

			stepsKlmTime = new float[numOfSteps];
			tokenizer = new StringTokenizer(in.readLine(), ",");
			for (int i = 0; i < numOfSteps; i++) {
				String token = tokenizer.nextToken().trim();
				stepsKlmTime[i] = Float.parseFloat(token);
			}

			minStepsDistance = new float[numOfSteps];
			tokenizer = new StringTokenizer(in.readLine(), ",");
			for (int i = 0; i < numOfSteps; i++) {
				minStepsDistance[i] = Float.parseFloat(tokenizer.nextToken()
						.trim());
			}

			tokenizer = new StringTokenizer(in.readLine(), ",");
			numOfResponseTimeActions = Integer.parseInt(tokenizer.nextToken()
					.trim());

			responsingActionsList = new ResponsingAction[numOfResponseTimeActions];

			for (int i = 0; i < numOfResponseTimeActions; i++) {
				tokenizer = new StringTokenizer(in.readLine(), ",");
				int id = Integer.parseInt(tokenizer.nextToken().trim());
				long reponseTime = Long.parseLong(tokenizer.nextToken().trim());
				String condition = tokenizer.nextToken().trim();
				responsingActionsList[i] = new ResponsingAction(id, condition,
						reponseTime);
			}

			actionMatrix = new ArrayList[numOfSteps];
			for (int i = 0; i < numOfSteps; i++) {
				actionMatrix[i] = new ArrayList();
			}
			for (int i = 0; i < numOfSteps; i++) {
				tokenizer = new StringTokenizer(in.readLine(), ",");
				while (tokenizer.hasMoreElements()) {
					actionMatrix[i].add(tokenizer.nextToken().trim());
				}
			}

			stepsPerformanceIndictor = new ActionIndicator[numOfSteps];
			for (int i = 0; i < numOfSteps; i++) {
				String condition = in.readLine().trim();
				stepsPerformanceIndictor[i] = new ActionIndicator(condition, i);
			}

			String condition = in.readLine().trim();
			startActionIndictor = new ActionIndicator(condition, 0);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * returns the singleton instance of this class
	 * 
	 * @return the unique instance of <code>ShellData</Code> class
	 */
	public static ShellData getInstance() {
		if (instance == null)
			instance = new ShellData();

		return instance;

	}

}
