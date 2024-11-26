package jspoor.statextract;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * The main class of the application
 * 
 * @author arin ghazarian
 * 
 */
public class ExtractVariableValuesFromUILogs {
	long afterResponseEventTime=0;
	ShellData shellData;
	AttributesList attrslist;
	int lastBtnPrsX = -1;
	int lastBtnPrsY = -1;
	int helpCount = 0;
	int undoCount = 0;
	int canceledOrClosedCount = 0;
	int ButtonClickedCount = 0;
	int menuItemClickedCount = 0;
	int menuNavigationsCount = 0;
	int keyboardPressesCount = 0;
	int mousMovesCount = 0;
	long pauseTimesSum = 0;
	int mouseKeyboardSwitchCounts = 0;
	int numOfActions = 0;
	long typeTimeSum = 0;
	float avgMouseMovesBeforActions = 0;
	long numOfPopupMenuOpened = 0;
	long keyTypePauseTime = 0;
	long pauseTime = 0;
	long eventTimeInMilis = 0;
	long lastTypedTime = 0;
	String prevEventType = "";
	String eventType = "";
	String secondParameter = "";
	String thirdParameter = "";
	String forthParameter = "";
	String fifthParameter = "";
	String eventSource = "";
	String focusedComponent = "";
	int shortcutKeyUsagesCount = 0;
	long tooltipShowTime = 0;
	int actionKeyPresses = 0;
	int toolipViewedCount = 0;
	long toolTipViewsTime = 0;
	int mouseButtonPressedCounts = 0;
	long focusgainedTime = 0;
	long focusOverTime = 0;
	int comboSlectionsCount = 0;
	long ComboSelectionsTimeSum = 0;
	int textComponentKeyTypedCounts = 0;
	int tooltipViewingSessions = 0;
	long prevMouseKeyEventTimeInMilis = 0;
	int mouseOrKeyEventsCount = 1;
	boolean inMenuSearchSession = false;
	long menuNavigationTime = 0;
	long menuNavigationStartTime = 0;
	int menuNavigationSessionsCount = 0;
	long menuNavigationsTimeSum = 0;
	int numOfItemsNavigatedInSession = 0;
	int numOfItemsNavigatedInSessionsSum = 0;
	long taskCompletionTime;
	long avgTooltipViewTime = 0;
	double avgItemsnavigatediInEachSession = 0;
	long avgMenuNavigationTime = 0;
	long avgMenuItemNavigataionTime = 0;
	long avgComboSelectionTime = 0;
	long avgKeyTypedPause = 0;
	String prevNonMouseMoveEventType = "";
	String prevNonMouseEventType = "";
	String prevNonMouseEventSource = "";
	boolean inTooltipSearchSession = false;
	long tooltipViewStartTime = 0;
	int numOfTooltipsViewedInSession;
	long tooltipSessionsTimeSum = 0;
	long numOfTooltipsViewedInSessionsSum = 0;
	long avgTooltipSessionTime = 0;
	float avgTooltipsViewedCountInSession = 0;
	long prevStepTimeInMillis = 0;
	long taskStartTime = 0;
	long prevStepEndTimeInMillis = 0;
	ArrayList mouseMoveEpisodes = new ArrayList();
	ArrayList mouseDraggEpisodes = new ArrayList();
	long prevMouseMoveTime = 0;
	int lastMouseX = 0;
	int lastMouseY = 0;
	boolean pausedMouseMove = false;
	ArrayList pauses = new ArrayList();
	long probableMenuNavigationEndTime = 0;
	long lastMenuItemNavigationTime = 0;
	long selectedMenuItemsDwellTimesSum = 0;
	long avgSelectedMenuItemsDwellTime = 0;
	ArrayList comboMouseMoveEpisodes = new ArrayList();
	ArrayList menuItemsNavigated = new ArrayList();
	BufferedImage image = null;
	double velocityBeforeButtonClickSum = 0;
	double avgVelocityBeforeButtonClicks = 0;

	long purePausesSumDuringResponseTimes = 0;
	int responseTimesCountInStep = 0;
	long avgPurePauseDuringResponseTimes = 0;
	ArrayList mouseMovesDuringResponseTimes = new ArrayList();

	public final static int NOSTEP = -1;
	boolean inResponse = false;
	int responsingCommand = 0;
	long responseStart = 0;
	// MouseMoves curMouseMovesDuringResponseTime=new MouseMoves();
	int eventsAfterResponseCount = 0;
	boolean afterResponse = false;
	int beforeResponseTimeX = 0;
	int beforeResponseTimeY = 0;
	int afterResponseTimeX = 0;
	int afterResponseTimeY = 0;
	float responseTimeAdaptiveMouseMovesRankSum = 0;
	float responseTimeAdaptiveMouseMovesRankSumNormalized = 0;
	float avgResponseTimeAdaptiveMouseMovesRank = 0;
	int currentStep = NOSTEP;
	String lastAction = "";
	String lastActionSource = "";

	PrintWriter[] stepReportFiles;

	PrintWriter[] wekaOutFiles;

	PrintWriter[] wekaOutFilesForNoviceGS;
	PrintWriter[] wekaOutFilesForModerateGS;
	PrintWriter[] wekaOutFilesForSkilledGS;

	float minDistanceForStep = 0;
	ArrayList timeLine = new ArrayList();
	ArrayList stepTimeLine = new ArrayList();
	int pauseCount = 0;
	long pauseSum = 0;
	long pauseSumForStep = 0;
	int pauseCountForStep = 0;
	boolean stepFinishedJustNow = false;
	long beforeStepPauseSum = 0;
	int numOfStepsDone = 0;
	float menuVibrationRadiusSum = 0;
	float menuVibrationCountSum = 0;
	ArrayList lastThreeMoves = new ArrayList();
	boolean stepDoneWithoutError = true;
	int numOfErrorfulSteps = 0;
	long beforeStepPause = 0;
	String experienceLevel;
	String path;
	boolean isNoisyStep = false;
	NoisySteps noisySteps;
	boolean normalized;
	boolean areOldLogs;
	StepParams[] prevTrialStepsParams;
	static int NOVICE_HIGH;
	static int MODERATE_LOW;
	static int MODERATE_HIGH;
	static int SKILLED_LOW;

	boolean isAStepSelected;
	int selectedStepNo;
	
	/**
	 * generates the specified report from the given ui log file
	 * 
	 * @param reportType
	 *            report type
	 * @param normalizd
	 *            calculate normalized values or task-dependent values
	 * @param logFilePath
	 *            the path to UI actions log file
	 * @param skillLevel
	 *            user's general or system skill level
	 * @param trialNo
	 *            performance trial number
	 * @param dontCheck
	 *            if set to true then the sequence of actions done in log file
	 *            is not compared and cheked against the sequence indicated in
	 *            shelldata file
	 */
	public void extractAttributeValues(String reportType, boolean normalizd,
			String logFilePath, String skillLevel, int trialNo,
			boolean dontCheck,boolean areOldLogs) {
		this.areOldLogs=areOldLogs;
		File f2 = new File(logFilePath);
		path = f2.getParent();

		if (normalizd)
			normalized = true;

		if (reportType.startsWith("stepArff"))
			openWekaFiles(path, reportType, normalized);
		else
			openStepReportFiles(path, reportType);
		if (skillLevel.equals("novice")) {

			NOVICE_HIGH = shellData.NOVICE_NOVICE_HIGH;
			MODERATE_LOW = shellData.NOVICE_MODERATE_LOW;
			MODERATE_HIGH = shellData.NOVICE_MODERATE_HIGH;
			SKILLED_LOW = shellData.NOVICE_SKILLED_LOW;
		} else if (skillLevel.equals("skilled")) {
			NOVICE_HIGH = shellData.EXPERT_NOVICE_HIGH;
			MODERATE_LOW = shellData.EXPERT_MODERATE_LOW;
			MODERATE_HIGH = shellData.EXPERT_MODERATE_HIGH;
			SKILLED_LOW = shellData.EXPERT_SKILLED_LOW;
		} else {
			System.out
					.println("error:wrong user skill level in user-info.txt!");
		}

		noisySteps = new NoisySteps("noises.txt");
		 this.experienceLevel=skillLevel;
		prevTrialStepsParams = new StepParams[7];
		for (int j = 0; j < 7; j++) {
			prevTrialStepsParams[j] = new StepParams();
		}
		resetToInitial();
		extractStatistics(f2, trialNo, skillLevel, reportType, dontCheck);

		if (reportType.startsWith("stepArff"))
			closeWekaFiles(reportType, normalized);
		else
			closeStepReportFiles();

	}

	public static void main(String[] args) {
		ExtractVariableValuesFromUILogs instance = new ExtractVariableValuesFromUILogs();
		 
		 if ((args.length >2) &&
				 (args[2].equals("-normalized")))instance.normalized=true;
		 if (args.length >3){
			 instance.selectedStepNo=Integer.parseInt(args[3]);
			 instance.isAStepSelected=true;
			 }
		
		instance.extractStatisticsBatchMode(args[0],instance.normalized,args[1
		 ],false);
		
		
		
		
		//instance.extractAttributeValues("stepArffAS", true,
			//	"C:\\columbauser\\ui-log_Sat_11-Apr-2009_16-36-39.txt",
			//	"skilled", 10, true);
	}

	/**
	 * generate the specified type of report for all UI log files available at
	 * the logs directory of all directories specified at the userDirList
	 * parameter.
	 * 
	 * @param reportType
	 *            type of the report to be generated
	 * @param normalized
	 *            extract task-dependent or normal values
	 * @param userDirList
	 *            path to a text file in which each line is a path to a
	 *            directory where log files for a user is available
	 * @param dontCheck
	 */
	public void extractStatisticsBatchMode(String reportType,
			boolean normalized, String userDirList, boolean dontCheck) {
		System.out.println("Generating report of type: "+ reportType);

		this.normalized = normalized;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(userDirList));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (reportType.startsWith("stepArff"))
			openWekaFiles( ".\\" + reportType, reportType,
					normalized);
		String lineStr;
		try {
			while ((lineStr = in.readLine()) != null) {
				this.path = lineStr;
				File dir = new File(path + "\\logs");

				System.out.println("Processing UI log files at "+lineStr);
				
				
				if (!reportType.startsWith("stepArff"))
					openStepReportFiles(path, reportType);
				noisySteps = new NoisySteps(path + "\\noises.txt");
				// this.experienceLevel=experienceLevel;
				prevTrialStepsParams = new StepParams[7];
				for (int j = 0; j < 7; j++) {
					prevTrialStepsParams[j] = new StepParams();
				}

				BufferedReader userInfoFile = new BufferedReader(
						new FileReader(path + "\\user-info.txt"));
				String userName = userInfoFile.readLine();
				String sexuality = userInfoFile.readLine();
				String skillLevel = userInfoFile.readLine();
				String logType="absolutePos";
				 logType = userInfoFile.readLine();
				
				if (logType.equalsIgnoreCase("relativePos")){
					this.areOldLogs=true;
				}else{
					this.areOldLogs=false;
				}


				if (skillLevel.equals("novice")) {

					NOVICE_HIGH = shellData.NOVICE_NOVICE_HIGH;
					MODERATE_LOW = shellData.NOVICE_MODERATE_LOW;
					MODERATE_HIGH = shellData.NOVICE_MODERATE_HIGH;
					SKILLED_LOW = shellData.NOVICE_SKILLED_LOW;
				} else if (skillLevel.equals("skilled")) {
					NOVICE_HIGH = shellData.EXPERT_NOVICE_HIGH;
					MODERATE_LOW = shellData.EXPERT_MODERATE_LOW;
					MODERATE_HIGH = shellData.EXPERT_MODERATE_HIGH;
					SKILLED_LOW = shellData.EXPERT_SKILLED_LOW;
				} else {
					System.out
							.println("error:wrong user skill level in user-info.txt!");
				}
				this.experienceLevel=skillLevel;
				userInfoFile.close();

				// FileWriter outFile = new FileWriter(path+"\\"+args[5]+".txt",
				// false);
				// PrintWriter reportOutFile = new PrintWriter(outFile);
				int trialNo = 0;

				String[] files = dir.list();
				Arrays.sort(files);
				for (int i = 0; i < files.length; i++) {
					File f2 = new File(path + "\\logs", files[i]);
					if (f2.isFile() && f2.getName().startsWith("ui-log")) {
						trialNo++;
						resetToInitial();
						extractStatistics(f2, trialNo, skillLevel, reportType,
								dontCheck);

					}
				}
				if (!reportType.startsWith("stepArff"))closeStepReportFiles();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (reportType.startsWith("stepArff"))
			closeWekaFiles(reportType, normalized);
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Finished!");
		// sampleInstance.splitLowLevelActionsFromSemantic(args[0]);
	}

	public void openWekaFiles(String path, String reportType, boolean normalized) {
		
		
		File outDir = new File(path);
		boolean res=outDir.mkdir();
		//System.out.println("res: "+ res);
		//System.out.println("outDir "+ outDir.getAbsolutePath());
		
		if (reportType.equals("stepArffAS")
				|| reportType.equals("stepArffGSAS")
				|| (reportType.equals("stepArffGSasParamAS"))
				|| (reportType.equals("stepArffASDif"))
				|| (reportType.equals("stepArffGSASDif"))) {
			wekaOutFiles = new PrintWriter[shellData.numOfSteps];
			try {
				// String
				// path=inputLogFile.getAbsolutePath().substring(0,inputLogFile
				// .getAbsolutePath().lastIndexOf("\\"));
				for (int i = 0; i < wekaOutFiles.length; i++) {
					FileWriter outFile = new FileWriter(path + "\\"
							+ reportType + (i + 1) + ".arff", false);
				//	System.out.println("outDir2 "+ path + "\\"
				//			+ reportType + (i + 1) + ".arff");
					wekaOutFiles[i] = new PrintWriter(outFile);
					if (normalized)
						printArffHeaderNormalized(wekaOutFiles[i], reportType);
					else
						printArffHeader(wekaOutFiles[i], reportType);
					if (normalized && (i == 0))
						break;
				}

			} catch (IOException exception) {
				exception.printStackTrace();

			}
		} else if (reportType.equals("stepArffGS")
				|| reportType.equals("stepArffGSDif")) {

			wekaOutFilesForNoviceGS = new PrintWriter[shellData.numOfSteps];
			wekaOutFilesForModerateGS = new PrintWriter[shellData.numOfSteps];
			wekaOutFilesForSkilledGS = new PrintWriter[shellData.numOfSteps];
			try {
				for (int i = 0; i < shellData.numOfSteps; i++) {
					FileWriter outFile = new FileWriter(path + "\\"
							+ reportType + (i + 1) + "Novice.arff", false);
					wekaOutFilesForNoviceGS[i] = new PrintWriter(outFile);
					if (normalized)
						printArffHeaderNormalized(wekaOutFilesForNoviceGS[i],
								reportType);
					else
						printArffHeader(wekaOutFilesForNoviceGS[i], reportType);

					outFile = new FileWriter(path + "\\" + reportType + (i + 1)
							+ "Moderate.arff", false);
					wekaOutFilesForModerateGS[i] = new PrintWriter(outFile);
					if (normalized)
						printArffHeaderNormalized(wekaOutFilesForModerateGS[i],
								reportType);
					else
						printArffHeader(wekaOutFilesForModerateGS[i],
								reportType);

					outFile = new FileWriter(path + "\\" + reportType + (i + 1)
							+ "Skilled.arff", false);
					wekaOutFilesForSkilledGS[i] = new PrintWriter(outFile);
					if (normalized)
						printArffHeaderNormalized(wekaOutFilesForSkilledGS[i],
								reportType);
					else
						printArffHeader(wekaOutFilesForSkilledGS[i], reportType);

					if (normalized && (i == 0))
						break;

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void closeStepReportFiles() {
		for (int i = 0; i < shellData.numOfSteps; i++) {
			stepReportFiles[i].close();
		}

	}

	private void openStepReportFiles(String path, String reportType) {
		stepReportFiles=new PrintWriter[shellData.numOfSteps];
		File outDir = new File(path + "\\" + reportType );
		boolean res=outDir.mkdir();
		

		for (int i = 0; i < shellData.numOfSteps; i++) {
			FileWriter fw = null;
			try {

				fw = new FileWriter(outDir.getPath() + "\\"
						+ reportType + (i + 1) + ".txt", false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			stepReportFiles[i] = new PrintWriter(fw);

		}

	}

	void closeWekaFiles(String reportType, boolean normalized) {

		if (reportType.equals("stepArffAS")
				|| reportType.equals("stepArffGSAS")
				|| (reportType.equals("stepArffGSasParamAS"))
				|| (reportType.equals("stepArffASDif"))
				|| (reportType.equals("stepArffGSASDif"))) {
			for (int i = 0; i < shellData.numOfSteps; i++) {
				wekaOutFiles[i].close();
				if (normalized && (i == 0))
					break;

			}

		} else if (reportType.equals("stepArffGS")
				|| reportType.equals("stepArffGSDif")) {

			for (int i = 0; i < shellData.numOfSteps; i++) {
				wekaOutFilesForNoviceGS[i].close();
				wekaOutFilesForModerateGS[i].close();
				wekaOutFilesForSkilledGS[i].close();
				if (normalized && (i == 0))
					break;

			}

		}

	}

	private void resetToInitial() {
		resetValues();
		responsingCommand=0;
		prevEventType = "";
		prevMouseKeyEventTimeInMilis = 0;
		prevMouseMoveTime = 0;
		prevNonMouseEventSource = "";
		prevNonMouseEventType = "";
		prevNonMouseMoveEventType = "";
		prevStepEndTimeInMillis = 0;
		prevStepTimeInMillis = 0;
		lastAction = "";
		lastActionSource = "";
		lastMouseX = 0;
		lastMouseY = 0;
		lastBtnPrsX = -1;
		lastBtnPrsY = -1;
		lastTypedTime = 0;
		timeLine = new ArrayList();
		pauseCount = 0;
		pauseSum = 0;
		stepFinishedJustNow = false;
		beforeStepPauseSum = 0;
		numOfStepsDone = 0;
		lastThreeMoves = new ArrayList();
		numOfErrorfulSteps = 0;

	}

	private void resetValues() {
		
		minDistanceForStep = 0;
		helpCount = 0;
		undoCount = 0;
		canceledOrClosedCount = 0;
		ButtonClickedCount = 0;
		menuItemClickedCount = 0;
		menuNavigationsCount = 0;
		keyboardPressesCount = 0;
		mousMovesCount = 0;
		pauseTimesSum = 0;
		mouseKeyboardSwitchCounts = 0;
		typeTimeSum = 0;
		avgMouseMovesBeforActions = 0;
		numOfPopupMenuOpened = 0;
		keyTypePauseTime = 0;
		pauseTime = 0;
		eventTimeInMilis = 0;
		lastTypedTime = 0;
		prevEventType = "";
		eventType = "";
		secondParameter = "";
		thirdParameter = "";
		forthParameter = "";
		fifthParameter = "";
		eventSource = "";
		focusedComponent = "";
		shortcutKeyUsagesCount = 0;
		tooltipShowTime = 0;
		actionKeyPresses = 0;
		toolipViewedCount = 0;
		toolTipViewsTime = 0;
		mouseButtonPressedCounts = 0;
		focusgainedTime = 0;
		focusOverTime = 0;
		comboSlectionsCount = 0;
		ComboSelectionsTimeSum = 0;
		textComponentKeyTypedCounts = 0;
		tooltipViewingSessions = 0;
		mouseOrKeyEventsCount = 1;
		inMenuSearchSession = false;
		menuNavigationTime = 0;
		menuNavigationStartTime = 0;
		menuNavigationSessionsCount = 0;
		menuNavigationsTimeSum = 0;
		numOfItemsNavigatedInSession = 0;
		numOfItemsNavigatedInSessionsSum = 0;
		taskCompletionTime = 0;
		avgTooltipViewTime = 0;
		avgItemsnavigatediInEachSession = 0;
		avgMenuNavigationTime = 0;
		avgMenuItemNavigataionTime = 0;
		avgComboSelectionTime = 0;
		avgKeyTypedPause = 0;
		prevNonMouseMoveEventType = "";
		prevNonMouseEventType = "";
		prevNonMouseEventSource = "";
		inTooltipSearchSession = false;
		tooltipViewStartTime = 0;
		tooltipSessionsTimeSum = 0;
		numOfTooltipsViewedInSessionsSum = 0;

		// prevMouseMoveTime=0;
		mouseMoveEpisodes = new ArrayList();
		mouseDraggEpisodes = new ArrayList();
		pauses = new ArrayList();
		probableMenuNavigationEndTime = 0;
		lastMenuItemNavigationTime = 0;
		selectedMenuItemsDwellTimesSum = 0;
		avgSelectedMenuItemsDwellTime = 0;
		comboMouseMoveEpisodes = new ArrayList();
		menuItemsNavigated = new ArrayList();
		velocityBeforeButtonClickSum = 0;
		avgVelocityBeforeButtonClicks = 0;
		purePausesSumDuringResponseTimes = 0;
		responseTimesCountInStep = 0;
		mouseMovesDuringResponseTimes = new ArrayList();
		avgPurePauseDuringResponseTimes = 0;
		eventsAfterResponseCount = 0;
		responseTimeAdaptiveMouseMovesRankSum = 0;
		responseTimeAdaptiveMouseMovesRankSumNormalized = 0;
		avgResponseTimeAdaptiveMouseMovesRank = 0;
		menuVibrationCountSum = 0;
		menuVibrationRadiusSum = 0;
		avgTooltipsViewedCountInSession = 0;

		toolipViewedCount = 0;
		tooltipViewingSessions = 0;
		avgTooltipsViewedCountInSession = 0;
		avgTooltipViewTime = 0;
		avgTooltipSessionTime = 0;
		stepDoneWithoutError = true;
		beforeStepPause = 0;
		stepTimeLine = new ArrayList();
		pauseSumForStep = 0;
		pauseCountForStep = 0;
		isNoisyStep = false;
	}

	public ExtractVariableValuesFromUILogs() {
		attrslist = AttributesList.getInstance(".");
		shellData = ShellData.getInstance();
	}

	public void extractStatistics(File inputLogFile, int trialNo,
			String experienceLevel, String reportType, boolean dontCheck) {
		int mouseX = 0;
		int mouseY = 0;
		MouseMoveInfo prevMouseMoveInfo = null;
		MouseMoves curMouseMoves = null;

		PrintWriter out = null;
		BufferedReader in = null;

		try {

			// inputLogFile.getAbsolutePath().substring(0,inputLogFile.
			// getAbsolutePath().indexOf(".txt"));
			// TODO this was commeneted for runtime skilladaptation
			FileWriter outFile = new FileWriter(path + "attribute_values.arff",
					false);
			out = new PrintWriter(outFile);
			in = new BufferedReader(new FileReader(inputLogFile));
		} catch (IOException e) {
		}

		String str = null;
		boolean startTimeSet = false;

		long prevEventTimeInMilis = 0;
		try {
			str = in.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		EventsStreamParser eventsStreamParser = new EventsStreamParser(path
				+ "eventsParsingResults.txt", shellData.actionMatrix, dontCheck);
		currentStep = NOSTEP;
		while (str != null) {
			if (currentStep>=shellData.numOfSteps) {
				break;
			}
			StringTokenizer tokenizer = new StringTokenizer(str, ":");
			numOfActions++;
			forthParameter = "";
			fifthParameter = "";
			if (tokenizer.hasMoreElements())
				eventType = tokenizer.nextToken().trim();
			if (tokenizer.hasMoreElements())
				secondParameter = tokenizer.nextToken().trim();
			if (tokenizer.hasMoreElements())
				thirdParameter = tokenizer.nextToken().trim();
			if (tokenizer.hasMoreElements())
				forthParameter = tokenizer.nextToken().trim();
			if (tokenizer.hasMoreElements())
				fifthParameter = tokenizer.nextToken().trim();

			eventSource = secondParameter;
			if (eventSource.equals("HeavyWeightWindow")) {
				eventSource += (comboSlectionsCount + 1);
			}
			eventTimeInMilis = Long.valueOf(thirdParameter).longValue();

			if (eventType.equals("BUTTON_PRESSED")
					|| eventType.equals("MENU_ITEM_SELECTED")
					|| (eventType.equals("COMPONENT_FOCUS_LOST")
							&& forthParameter.equals("JTextField") && prevNonMouseEventType
							.equals("KEY_PRESSED"))
					|| (eventType.equals("COMBOBOX_SELECTED") && (focusedComponent
							.equals("JComboBox")))
					|| (prevEventType.equals("MOUSE_DRAGGED") && eventType
							.equals("MOUSE_BUTTON_RELEASED"))) {
				String action;

				if (prevEventType.equals("MOUSE_DRAGGED")) {
					action = "Dragged";
				} else if (eventType.equals("COMPONENT_FOCUS_LOST")) {
					// action="KEY_PRESSED"+textComponentKeyTypedCounts;
					action = "KEY_PRESSED";
				} else if (eventType.equals("COMBOBOX_SELECTED")) {
					action = eventType + " " + forthParameter;
					action = eventType;
					lastAction = eventType;
					lastActionSource = forthParameter;
				} else {

					action = eventType + " " + eventSource;
					lastAction = eventType;
					lastActionSource = eventSource;
				}

				boolean correctAction = eventsStreamParser.parseNextEvent(
						currentStep, action);
				stepDoneWithoutError = stepDoneWithoutError && correctAction;
				if (correctAction) {

					long time;
					if (prevEventType.equals("KEY_PRESSED")) {
						time = prevEventTimeInMilis - taskStartTime;
					} else {
						time = prevMouseKeyEventTimeInMilis - taskStartTime;
					}

					timeLine.add(new ActionTime(action, time));
					stepTimeLine.add(new ActionTime(action, time));

				}
			}
			if ((eventType.startsWith("MOUSE") || eventType.startsWith("KEY"))) {
				if (numOfActions > 1) {

					pauseTime = eventTimeInMilis - prevMouseKeyEventTimeInMilis;
					if (stepFinishedJustNow) {
						beforeStepPauseSum += pauseTime;
						beforeStepPause = pauseTime;
						numOfStepsDone++;
						stepFinishedJustNow = false;
					}
					if (pauseTime >= shellData.MIN_PAUSE_DURATION) {
						pauseCount++;
						pauseSum += pauseTime;
						pauseSumForStep += pauseTime;
						pauseCountForStep++;
						Pause pause = new Pause(prevMouseKeyEventTimeInMilis
								- taskStartTime, pauseTime);
						pauses.add(pause);
						timeLine.add(pause);
						stepTimeLine.add(pause);
					}

				}
				prevMouseKeyEventTimeInMilis = eventTimeInMilis;
				mouseOrKeyEventsCount++;
			}

			if (eventType.startsWith("MOUSE_MOVED")) {
				mousMovesCount++;

			}

			if (eventType.equals("BUTTON_PRESSED")
					&& eventSource.equalsIgnoreCase("cancel")) {
				canceledOrClosedCount++;
			}
			if ((eventType.equals("BUTTON_PRESSED") || eventType
					.equals("MENU_ITEM_SELECTED"))
					&& eventSource.equalsIgnoreCase("undo")) {
				undoCount++;
			}
			if (eventType.equals("WINDOW_CLOSED")) {
				canceledOrClosedCount++;
			}
			if (eventType.equals("MENU_ITEM_SELECTED")) {
				menuItemClickedCount++;
			}
			if (eventType.equals("BUTTON_PRESSED")) {
				ButtonClickedCount++;

				float beforeClickMovesDistanceTravelled = 0;
				long beforeClickMovesDuration = 0;
				for (int j = 0; j < lastThreeMoves.size(); j++) {
					MouseMoveInfo mouseMoveInfo = (MouseMoveInfo) lastThreeMoves
							.get(j);
					beforeClickMovesDistanceTravelled += (mouseMoveInfo
							.getMouseVelocity() * mouseMoveInfo
							.getMovementDurationInMillis());
					beforeClickMovesDuration += mouseMoveInfo
							.getMovementDurationInMillis();
				}
				if (beforeClickMovesDuration == 0)
					beforeClickMovesDuration = 1;
				velocityBeforeButtonClickSum += (beforeClickMovesDistanceTravelled / (beforeClickMovesDuration / 1000f));

			}

			if (afterResponse) {

				eventsAfterResponseCount++;
				if (eventsAfterResponseCount == 1) {
					responseTimesCountInStep++;
					if ((eventType.startsWith("MOUSE_MOVED"))
							&& ((eventTimeInMilis - prevEventTimeInMilis) > shellData.responsingActionsList[responsingCommand - 1].responseTime)) {
						purePausesSumDuringResponseTimes += (((eventTimeInMilis - prevEventTimeInMilis) - shellData.responsingActionsList[responsingCommand - 1].responseTime));
					}
				}
			}

			if (afterResponse && eventType.equals("MOUSE_BUTTON_PRESSED")) {
				float beforeResponseTimeDistance = (float) MouseMoveInfo
						.calcDistance(beforeResponseTimeX, lastMouseX,
								beforeResponseTimeY, lastMouseY);
				float afterResponseTimeDistance = (float) MouseMoveInfo
						.calcDistance(afterResponseTimeX, lastMouseX,
								afterResponseTimeY, lastMouseY);
				if (beforeResponseTimeDistance == 0)
					beforeResponseTimeDistance = 1;
				responseTimeAdaptiveMouseMovesRankSum += (beforeResponseTimeDistance - afterResponseTimeDistance);
				// this line was replaced with thefollowing in 20 july 2009
				//responseTimeAdaptiveMouseMovesRankSumNormalized += ((beforeResponseTimeDistance - afterResponseTimeDistance) / shellData.responsingActionsList[responsingCommand - 1].responseTime);
				responseTimeAdaptiveMouseMovesRankSumNormalized += ((beforeResponseTimeDistance - afterResponseTimeDistance) / (afterResponseEventTime-responseStart));
				afterResponse = false;
				

			}

			// if (inResponse &&
			// !(eventTimeInMilis<=(responseTimes[responsingCommand
			// ]+responseStart) ) )inResponse=false;

			if (eventType.equals("KEY_PRESSED")) {
				if (focusedComponent.equals("JTextField")
						|| focusedComponent.equals("JTextArea")) {
					textComponentKeyTypedCounts++;
					lastTypedTime = eventTimeInMilis;
				}
				keyboardPressesCount++;
				if (secondParameter.indexOf("ActionKey") != -1)
					actionKeyPresses++;
				int plusIndx = secondParameter.indexOf('+');
				if (plusIndx != -1) {
					if (!(secondParameter.substring(plusIndx + 1).trim()
							.equals("Shift") && (Character
							.isLetterOrDigit(secondParameter.substring(0,
									plusIndx).trim().charAt(0)))))
						shortcutKeyUsagesCount++;
				}

				// if (!prevEventType.equals(eventType))
				// mouseKeyboardSwitchCounts++;
			}
			if (eventType.equals("MENU_ITEM_NAVIGATED")) {
				if (!inMenuSearchSession) {
					inMenuSearchSession = true;
					menuNavigationStartTime = eventTimeInMilis;
					menuNavigationSessionsCount++;
				}
				menuNavigationsCount++;
			}
			if (inMenuSearchSession) {
				if (eventType.equals("MOUSE_BUTTON_RELEASED")) {
					// \\\\\\\\\\\\
					probableMenuNavigationEndTime = eventTimeInMilis;

				} else if (eventType.equals("MENU_ITEM_NAVIGATED")) {
					menuItemsNavigated.add(eventSource);
					numOfItemsNavigatedInSession++;
					lastMenuItemNavigationTime = eventTimeInMilis;

				} else if (eventType.equals("MENU_ITEM_SELECTED")
						|| (!eventType.equals("MOUSE_BUTTON_PRESSED")
								&& !eventType.equals("MOUSE_MOVED") && !eventType
								.equals("MOUSE_DRAGGED"))) {
					inMenuSearchSession = false;
					if (probableMenuNavigationEndTime == 0)
						probableMenuNavigationEndTime = lastMenuItemNavigationTime;
					menuNavigationTime = probableMenuNavigationEndTime
							- menuNavigationStartTime;
					if (menuNavigationTime < 0)
						menuNavigationTime = 0;

					menuNavigationsTimeSum += menuNavigationTime;
					numOfItemsNavigatedInSessionsSum += numOfItemsNavigatedInSession;
					numOfItemsNavigatedInSession = 0;
				}
			}

			if (eventType.equals("MENU_ITEM_SELECTED")) {
				selectedMenuItemsDwellTimesSum += (probableMenuNavigationEndTime - lastMenuItemNavigationTime);
				if ((probableMenuNavigationEndTime - lastMenuItemNavigationTime) < 0) {
					int x = 0;
				}

				float a[] = MenuNavigation.calcMenuItemVibrationFeatures(
						menuItemsNavigated, eventSource);
				menuVibrationRadiusSum += a[1];
				menuVibrationCountSum += a[0];
				menuItemsNavigated = new ArrayList();

			}
			if ((eventType.equals("BUTTON_PRESSED") || eventType
					.equals("MENU_ITEM_SELECTED"))
					&& eventSource.equalsIgnoreCase("Help")) {
				helpCount++;
			}
			// if (eventType.equals("MOUSE_MOVES_COUNT")) {
			// mousMovesCount += Long.valueOf(secondParameter).longValue();
			// }
			if (eventType.equals("TOOLTIP_SHOWED")) {
				if (!inTooltipSearchSession) {
					inTooltipSearchSession = true;
					tooltipViewingSessions++;
					tooltipViewStartTime = eventTimeInMilis;
				}

				tooltipShowTime = eventTimeInMilis;
				toolipViewedCount++;
				numOfTooltipsViewedInSession++;
			}
			if (eventType.equals("TOOLTIP_HIDED")) {
				toolTipViewsTime += (eventTimeInMilis - tooltipShowTime);
			}
			if (inTooltipSearchSession) {
				if (!prevNonMouseMoveEventType.equals("TOOLTIP_SHOWED")
						&& !prevNonMouseMoveEventType.equals("TOOLTIP_HIDED")) {
					inTooltipSearchSession = false;
					long tooltipSessionTime = eventTimeInMilis
							- tooltipViewStartTime;
					tooltipSessionsTimeSum += tooltipSessionTime;
					numOfTooltipsViewedInSessionsSum += numOfTooltipsViewedInSession;
					numOfTooltipsViewedInSession = 0;
				}
			}

			if (eventType.equals("MOUSE_BUTTON_PRESSED")) {
				mouseButtonPressedCounts++;
			}

			if (eventType.equals("MOUSE_BUTTON_PRESSED")
					|| eventType.equals("MOUSE_BUTTON_RELEASED")) {
				if (lastBtnPrsX != -1)
					minDistanceForStep += (calcDistance(lastMouseY,
							lastBtnPrsY, lastMouseX, lastBtnPrsX));

				lastBtnPrsX = lastMouseX;
				lastBtnPrsY = lastMouseY;
			}

			if (eventType.equals("COMPONENT_FOCUS_GAINED")) {
				focusedComponent = forthParameter;
				focusgainedTime = Long.valueOf(thirdParameter).longValue();

			}
			if (eventType.equals("COMPONENT_FOCUS_LOST")) {
				focusOverTime = Long.valueOf(thirdParameter).longValue()
						- focusgainedTime;
				if (focusedComponent.equals("JTextField")
						|| focusedComponent.equals("JTextArea")) {
					long typeTime = lastTypedTime - focusgainedTime;
					typeTimeSum += typeTime;
				}

				focusedComponent = "";

			}
			if (eventType.equals("POPUP_MENU_SHOWED")) {
				numOfPopupMenuOpened++;
				// mousMovesCount += Long.valueOf(secondParameter).longValue();
			}

			if ((eventType.equals("MOUSE_MOVED") && (prevEventType
					.equals("MOUSE_MOVED") || (prevEventType
					.equals("MENU_ITEM_NAVIGATED")
					|| prevEventType.equals("TOOLTIP_HIDED") || prevEventType
					.equals("TOOLTIP_SHOWED"))))
					|| (eventType.equals("MOUSE_DRAGGED") && prevEventType
							.equals("MOUSE_DRAGGED"))) {
				int[] x = returnCoords(forthParameter, eventSource,areOldLogs);
				mouseX = x[0];
				mouseY = x[1];
				MouseMoveInfo moveInfo = new MouseMoveInfo(lastMouseX, mouseX,
						lastMouseY, mouseY, prevMouseMoveTime,
						eventTimeInMilis, prevMouseMoveInfo);

				if (eventType.equals("MOUSE_MOVED")) {
					if ((eventTimeInMilis - prevMouseMoveTime) < shellData.MIN_PAUSE_DURATION) {
						if (pausedMouseMove) {
							curMouseMoves = new MouseMoves();
							mouseMoveEpisodes.add(curMouseMoves);
							if (focusedComponent.equals("JComboBox")
									&& !prevNonMouseEventType
											.equals("COMBOBOX_SELECTED")) {
								comboMouseMoveEpisodes.add(curMouseMoves);
							}

							curMouseMoves.addMove(prevMouseMoveInfo);
						}

						curMouseMoves.addMove(moveInfo);
						pausedMouseMove = false;
					} else {
						moveInfo = new MouseMoveInfo(lastMouseX, mouseX,
								lastMouseY, mouseY, eventTimeInMilis);
						pausedMouseMove = true;
					}
				} else if (eventType.equals("MOUSE_DRAGGED")) {
					if ((eventTimeInMilis - prevEventTimeInMilis) < shellData.MIN_PAUSE_DURATION) {
						if (pausedMouseMove) {
							curMouseMoves = new MouseMoves();
							mouseDraggEpisodes.add(curMouseMoves);
						}
						curMouseMoves.addMove(moveInfo);
						pausedMouseMove = false;
					} else {
						pausedMouseMove = true;
					}

				}

				prevMouseMoveInfo = moveInfo;
				prevMouseMoveTime = eventTimeInMilis;

			} else if ((eventType.equals("MOUSE_MOVED")
					&& !prevEventType.equals("MENU_ITEM_NAVIGATED")
					&& !prevEventType.equals("TOOLTIP_HIDED") && !prevEventType
					.equals("TOOLTIP_SHOWED"))
					|| eventType.equals("MOUSE_DRAGGED")) {
				int[] x = returnCoords(forthParameter, eventSource,areOldLogs);
				mouseX = x[0];
				mouseY = x[1];
				MouseMoveInfo moveInfo;
				// if (prevMouseMoveInfo==null)
				moveInfo = new MouseMoveInfo(lastMouseX, mouseX, lastMouseY,
						mouseY, eventTimeInMilis);
				// else moveInfo=new
				// MouseMoveInfo(lastMouseX,mouseX,lastMouseY,mouseY,
				// prevMouseMoveTime,eventTimeInMilis,prevMouseMoveInfo);
				curMouseMoves = new MouseMoves();
				curMouseMoves.addMove(moveInfo);
				prevMouseMoveTime = eventTimeInMilis;
				prevMouseMoveInfo = moveInfo;

				if (eventType.equals("MOUSE_DRAGGED")) {
					mouseDraggEpisodes.add(curMouseMoves);
				} else {
					mouseMoveEpisodes.add(curMouseMoves);
					if (focusedComponent.equals("JComboBox")
							&& !prevNonMouseEventType
									.equals("COMBOBOX_SELECTED")) {
						comboMouseMoveEpisodes.add(curMouseMoves);
					}
				}

				if (afterResponse) {
					afterResponseTimeX = mouseX;
					afterResponseTimeY = mouseY;
					afterResponseEventTime=eventTimeInMilis;

				}

				pausedMouseMove = false;

			}

			if (eventType.startsWith("MOUSE_MOVED")) {
				mousMovesCount++;
				if (lastThreeMoves.size() < 2)
					lastThreeMoves.add(prevMouseMoveInfo);
				else {
					lastThreeMoves.remove(0);
					lastThreeMoves.add(prevMouseMoveInfo);
				}
			}

			if (eventType.equals("COMBOBOX_SELECTED")) {

				long selectionTime = 0;
				if (focusedComponent.equals("JComboBox")) {
					comboSlectionsCount++;
					selectionTime = eventTimeInMilis - focusgainedTime;
					ComboSelectionsTimeSum += selectionTime;

				}
			}
			pauseTimesSum = pauseTimesSum + pauseTime;
			pauseTime = 0;
			prevEventTimeInMilis = eventTimeInMilis;
			prevEventType = eventType;

			if (!startTimeSet
					&& shellData.startActionIndictor.isConditionSaticfied(
							eventType, eventSource, prevNonMouseEventType,
							prevNonMouseEventSource, lastAction,
							lastActionSource, prevNonMouseMoveEventType)) {
				taskStartTime = eventTimeInMilis;
				prevStepEndTimeInMillis = taskStartTime;
				startTimeSet = true;
				currentStep = 0;
				resetValues();
				inMenuSearchSession = true;
				menuNavigationStartTime = taskStartTime;
				menuNavigationSessionsCount++;
				numOfItemsNavigatedInSession++;
				menuNavigationsCount++;
			}

			for (int m = 0; m < shellData.responsingActionsList.length; m++) {
				if (m<responsingCommand) continue;
				if (shellData.responsingActionsList[m].isConditionSaticfied(
						eventType, eventSource, prevNonMouseEventType,
						prevNonMouseEventSource, lastAction, lastActionSource,
						prevNonMouseMoveEventType)) {
					responsingCommand = shellData.responsingActionsList[m].id;
					responseStart = eventTimeInMilis;
					afterResponse = true;
					beforeResponseTimeX = lastMouseX;
					beforeResponseTimeY = lastMouseY;
					eventsAfterResponseCount = 0;
					break;
				}

			}

			// steps

			for (int m = 0; m < shellData.stepsPerformanceIndictor.length; m++) {
					if (m<currentStep) continue;
				if (shellData.stepsPerformanceIndictor[m].isConditionSaticfied(
						eventType, eventSource, prevNonMouseEventType,
						prevNonMouseEventSource, lastAction, lastActionSource,
						prevNonMouseMoveEventType)) {
					prevStepTimeInMillis = eventTimeInMilis
							- prevStepEndTimeInMillis;
					prevStepEndTimeInMillis = eventTimeInMilis;
					isNoisyStep = noisySteps.isNoisy(trialNo, currentStep);
					// image=openJpegImage("paint-screen.jpg");
					// printVariablesStatsToFile("Open",out);
					printReport(trialNo, "", reportType);

					// MenuNavigation.printMenuItemVibrationFeatures(out,
					// MenuNavigation
					// .calcMenuItemVibrationFeatures(menuItemsNavigated
					// ,"open"));
					// writeJpegImageToFile("output1.jpg",image);
					currentStep = shellData.stepsPerformanceIndictor[m].id + 1;
					stepFinishedJustNow = true;
					CheckForErrorFulStep();
					resetValues();
					eventsStreamParser.resetParserForNextStep();
					break;
				}

			}

			if (!eventType.startsWith("MOUSE_MOVED")) {
				prevNonMouseMoveEventType = eventType;
			}
			if (!eventType.startsWith("MOUSE")) {
				prevNonMouseEventType = eventType;
				prevNonMouseEventSource = eventSource;
			}
			if (eventType.equals("MOUSE_MOVED")
					|| eventType.equals("MOUSE_DRAGGED")) {
				int[] x = returnCoords(forthParameter, eventSource,areOldLogs);
				lastMouseX = x[0];
				lastMouseY = x[1];
			}

			try {
				str = in.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			// noisedLogPrintWriter.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(pauseTimesSum);
		// System.out.println(numOfActions);

		taskCompletionTime = eventTimeInMilis - taskStartTime;
		numOfActions--;

		// printTrialReport(reportType,trialNo,"",reportOutFile);
		//printTaskTotalResults(out,taskCompletionTime,success,experienceLevel);
		// printReport(trialNo,"",reportOutFile,reportType);

		eventsStreamParser.eventsTerminate();

	}

	public void CheckForErrorFulStep() {
		if (!stepDoneWithoutError)
			numOfErrorfulSteps++;
	}

	public static BufferedImage openJpegImage(String fileUrl) {
		BufferedImage image = null;
		try {
			File imgFile = new File(fileUrl);
			// load file from disk using Sun's JPEGIMageDecoder
			FileInputStream fis = new FileInputStream(imgFile);
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);
			image = decoder.decodeAsBufferedImage();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;

	}

	public void writeJpegImageToFile(String fileUrl, BufferedImage image) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileUrl);
		} catch (java.io.FileNotFoundException fnf) {
			System.out.println("File Not Found");
		}

		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
		param.setQuality(0.75f, false);

		try {
			encoder.encode(image);
			out.close();
		} catch (java.io.IOException io) {
			System.out.println(io);
		}
	}

	public static int[] returnCoords(String cartesPair, String source,boolean oldLogs) {
		int[] a = new int[2];
		
			
		int commaIndx = cartesPair.indexOf(',');
		a[0] = Integer.parseInt(cartesPair.substring(1, commaIndx));
		a[1] = Integer.parseInt(cartesPair.substring(commaIndx + 1, cartesPair
				.indexOf(')')));
		if (oldLogs){
		// x and y locations were recorded relative to each component and dialog in 
			//our experiment logs

		 if (source.equalsIgnoreCase("Text")) {
		 a[0] += 162;
		 a[1] += 310;
		 } else if (source.equalsIgnoreCase("HeavyWeightWindow1")) {
		 a[0] += 176;
		 a[1] += 455;
		 } else if (source.equalsIgnoreCase("HeavyWeightWindow2")) {
		 a[0] += 394;
		 a[1] += 455;
		 }
		}

		return a;
	}

	private void printVariablesStatsToFile(String annotaion, PrintWriter out) {
		avgMouseMovesBeforActions = (float) mousMovesCount
				/ mouseButtonPressedCounts;

		if (toolipViewedCount != 0)
			avgTooltipViewTime = toolTipViewsTime / toolipViewedCount;
		if (menuNavigationSessionsCount != 0)
			avgItemsnavigatediInEachSession = (float) numOfItemsNavigatedInSessionsSum
					/ menuNavigationSessionsCount;
		if (menuNavigationSessionsCount != 0)
			avgMenuNavigationTime = menuNavigationsTimeSum
					/ menuNavigationSessionsCount;
		if (menuNavigationsCount != 0)
			avgMenuItemNavigataionTime = menuNavigationsTimeSum
					/ menuNavigationsCount;
		if (comboSlectionsCount != 0)
			avgComboSelectionTime = ComboSelectionsTimeSum
					/ comboSlectionsCount;
		if (textComponentKeyTypedCounts != 0)
			avgKeyTypedPause = typeTimeSum / textComponentKeyTypedCounts;
		if (tooltipViewingSessions != 0)
			avgTooltipSessionTime = tooltipSessionsTimeSum
					/ tooltipViewingSessions;
		if (tooltipViewingSessions != 0)
			avgTooltipsViewedCountInSession = (float) toolipViewedCount
					/ tooltipViewingSessions;
		if (menuItemClickedCount != 0)
			avgSelectedMenuItemsDwellTime = selectedMenuItemsDwellTimesSum
					/ menuItemClickedCount;
		if (ButtonClickedCount != 0)
			avgVelocityBeforeButtonClicks = velocityBeforeButtonClickSum
					/ ButtonClickedCount;
		if (responseTimesCountInStep != 0)
			avgPurePauseDuringResponseTimes = purePausesSumDuringResponseTimes
					/ responseTimesCountInStep;
		if (responseTimesCountInStep != 0)
			avgResponseTimeAdaptiveMouseMovesRank = responseTimeAdaptiveMouseMovesRankSum
					/ responseTimesCountInStep;

		// out.println("@relation UsersSkillLevel");
		// out.println("");

		out.println(annotaion);

		out.print("@attribute stepTimeInMillis numeric");
		out.println(prevStepTimeInMillis + ",");
		out.print("@attribute avgTooltipViewTime numeric");
		out.println(avgTooltipViewTime + ",");
		out.print("@attribute tooltipViewingSessionsCount numeric");
		out.println(tooltipViewingSessions + ",");
		out.print("@attribute avgTooltipsViewedCountInSession numeric");
		out.println(avgTooltipsViewedCountInSession + ",");
		out.print("@attribute avgTooltipSessionTime numeric");
		out.println(avgTooltipSessionTime + ",");
		out.print("@attribute avgItemsnavigatediInEachSession numeric");
		out.println(avgItemsnavigatediInEachSession + ",");
		out.print("@attribute avgMenuNavigationTime numeric");
		out.println(avgMenuNavigationTime + ",");
		out.print("@attribute avgMenuItemNavigataionTime numeric");
		out.println(avgMenuItemNavigataionTime + ",");
		out.print("@attribute avgComboSelectionTime numeric");
		out.println(avgComboSelectionTime + ",");
		out.print("@attribute actionKeyPresses numeric");
		out.println(actionKeyPresses + ",");
		out.print("@attribute shortcutKeyUsagesCount numeric");
		out.println(shortcutKeyUsagesCount + ",");
		out.print("@attribute textComponentKeyTypedCounts numeric");
		out.println(textComponentKeyTypedCounts + ",");
		out.print("@attribute avgMouseMovesBeforActions numeric");
		out.println(avgMouseMovesBeforActions + ",");
		out.print("@attribute toolipViewedCount numeric");
		out.println(toolipViewedCount + ",");
		out.print("@attribute helpCount numeric ");
		out.println(helpCount + ",");
		out.print("@attribute undoCount numeric ");
		out.println(undoCount + ",");
		out.print("@attribute canceledOrClosedCount numeric ");
		out.println(canceledOrClosedCount + ",");
		out.print("@attribute ButtonClickedCount numeric ");
		out.println(ButtonClickedCount + ",");
		out.print("@attribute avgVelocityBeforeButtonClicks numeric ");
		out.println(avgVelocityBeforeButtonClicks + ",");
		out.print("@attribute menuItemClickedCount numeric ");
		out.println(menuItemClickedCount + ",");
		out.print("@attribute menuNavigationsCount numeric ");
		out.println(menuNavigationsCount + ",");
		out.print("@attribute keyboardPressesCount numeric ");
		out.println(keyboardPressesCount + ",");
		out.print("@attribute MousMovesCount numeric ");
		out.println(mousMovesCount + ",");
		out.print("@attribute pauseTimesAvg numeric");
		out.println(pauseTimesSum / mouseOrKeyEventsCount + ",");
		out.print("@attribute mouseKeyboardSwitchCounts numeric");
		out.println(mouseKeyboardSwitchCounts + ",");
		out.print("@attribute avgKeyTypedPause numeric");
		out.println(avgKeyTypedPause + ",");
		out.print("@attribute avgSelectedMenuItemsDwellTime numeric");
		out.println(avgSelectedMenuItemsDwellTime + ",");
		out.print("@attribute mouseMovesEpisodesCount numeric");
		out.println(mouseMoveEpisodes.size() + ",");
		out.print("@attribute mouseDraggEpisodesCount numeric");
		out.println(mouseDraggEpisodes.size() + ",");
		out.print("@attribute avgPurePauseDuringResponseTimes numeric");
		out.println(avgPurePauseDuringResponseTimes + ",");
		out.print("@attribute avgResponseTimeAdaptiveMouseMovesRank numeric");
		out.println(avgResponseTimeAdaptiveMouseMovesRank + ",");

		Iterator itr = mouseMoveEpisodes.iterator();
		int c = 1;

		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();
			out.println("#Mouse Move episode " + c++);
			mouseMoveEpisode.printMouseMovesFeatures(out);

			mouseMoveEpisode.drawMouseMovesPaths(image, Color.BLUE);
		}

		itr = mouseDraggEpisodes.iterator();
		c = 1;
		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();
			out.println("#Drag episode " + c++);
			mouseMoveEpisode.printMouseMovesFeatures(out);
			mouseMoveEpisode.drawMouseMovesPaths(image, Color.RED);
		}

		Iterator itr2 = pauses.iterator();
		c = 1;
		out.println("#pasues");
		while (itr2.hasNext()) {
			Pause pause = (Pause) itr2.next();
			out.println(pause.getTime() + " " + pause.getDuration() + ",");
		}

		Iterator itr3 = comboMouseMoveEpisodes.iterator();
		c = 1;
		while (itr3.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr3.next();
			mouseMoveEpisode.calcFeaturesValues();
			out.println("#Combobox mouse moves episode " + c++);
			mouseMoveEpisode.printMouseMovesFeatures(out);
		}

		itr = mouseMovesDuringResponseTimes.iterator();
		c = 1;

		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();
			out.println("#mouse moves during response times" + c++);
			mouseMoveEpisode.printMouseMovesFeatures(out);

		}

	}

	public void printReport(int trialNo, String annotaion, String reportType) {
		avgMouseMovesBeforActions = (float) mousMovesCount
				/ mouseButtonPressedCounts;

		if (toolipViewedCount != 0)
			avgTooltipViewTime = toolTipViewsTime / toolipViewedCount;
		if (menuNavigationSessionsCount != 0)
			avgItemsnavigatediInEachSession = (float) numOfItemsNavigatedInSessionsSum
					/ menuNavigationSessionsCount;
		if (menuNavigationSessionsCount != 0)
			avgMenuNavigationTime = menuNavigationsTimeSum
					/ menuNavigationSessionsCount;
		if (menuNavigationsCount != 0)
			avgMenuItemNavigataionTime = menuNavigationsTimeSum
					/ menuNavigationsCount;
		if (comboSlectionsCount != 0)
			avgComboSelectionTime = ComboSelectionsTimeSum
					/ comboSlectionsCount;
		if (textComponentKeyTypedCounts != 0)
			avgKeyTypedPause = typeTimeSum / textComponentKeyTypedCounts;
		if (tooltipViewingSessions != 0)
			avgTooltipSessionTime = tooltipSessionsTimeSum
					/ tooltipViewingSessions;
		if (tooltipViewingSessions != 0)
			avgTooltipsViewedCountInSession = (float) toolipViewedCount
					/ tooltipViewingSessions;
		if (menuItemClickedCount != 0)
			avgSelectedMenuItemsDwellTime = selectedMenuItemsDwellTimesSum
					/ menuItemClickedCount;
		if (ButtonClickedCount != 0)
			avgVelocityBeforeButtonClicks = velocityBeforeButtonClickSum
					/ ButtonClickedCount;
		if (responseTimesCountInStep != 0)
			avgPurePauseDuringResponseTimes = purePausesSumDuringResponseTimes
					/ responseTimesCountInStep;
		if (responseTimesCountInStep != 0)
			avgResponseTimeAdaptiveMouseMovesRank = responseTimeAdaptiveMouseMovesRankSum
					/ responseTimesCountInStep;

		Iterator itr = mouseMoveEpisodes.iterator();
		int c = 1;

		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();

			// mouseMoveEpisode.drawMouseMovesPaths(image, Color.BLUE);
		}

		itr = mouseDraggEpisodes.iterator();
		c = 1;
		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();

			// mouseMoveEpisode.drawMouseMovesPaths(image, Color.RED);
		}

		Iterator itr2 = pauses.iterator();
		c = 1;

		while (itr2.hasNext()) {
			Pause pause = (Pause) itr2.next();

		}

		Iterator itr3 = comboMouseMoveEpisodes.iterator();
		c = 1;
		while (itr3.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr3.next();
			mouseMoveEpisode.calcFeaturesValues();

		}

		itr = mouseMovesDuringResponseTimes.iterator();
		c = 1;

		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();

		}

		
		if (MODERATE_HIGH<0){
			if ( (trialNo>=Math.abs(MODERATE_LOW)) && (trialNo<=Math.abs(MODERATE_HIGH)) ){
				return;
			}
		}
		
//		if ( (trialNo>=5) && (trialNo<=9) ){
//			return;
//		}
		
//		if (trialNo==4){
//			return;
//		}
//		if (trialNo==5){
//			return;
//		}
		// if (!( ((trialNo>=2) &&(trialNo<=10))
		// &&(experienceLevel.equals("skilled"))) ){
		if ((isAStepSelected)&&(selectedStepNo!=currentStep)){
			return;
		}
		if (stepDoneWithoutError && !isNoisyStep) {
			if (reportType.equals("taskTime")) {
				reportTaskTime(trialNo, annotaion, stepReportFiles[currentStep]);
			} else if (reportType.equals("stepsTime")) {
				stepReportFiles[currentStep].println(trialNo + " "
						+ prevStepTimeInMillis);
			} else if (reportType.equals("klmRatio")) {
				stepReportFiles[currentStep]
						.println(trialNo
								+ " "
								+ (prevStepTimeInMillis / (shellData.stepsKlmTime[currentStep] * 1000)));
			} else if (reportType.equals("velocityInfo")) {
				printVelocityInfos(trialNo, annotaion,
						stepReportFiles[currentStep]);
			} else if (reportType.equals("velocityInfo2")) {
				printVelocityInfos2(mouseMoveEpisodes, trialNo, annotaion,
						stepReportFiles[currentStep]);
			} else if (reportType.equals("comboVelocityInfo2")) {
				printVelocityInfos2(comboMouseMoveEpisodes, trialNo, annotaion,
						stepReportFiles[currentStep]);
			} else if (reportType.equals("dragVelocityInfo2")) {
				printVelocityInfos2(mouseDraggEpisodes, trialNo, annotaion,
						stepReportFiles[currentStep]);
			} else if (reportType.equals("comboTimes")) {
				stepReportFiles[currentStep].println(trialNo + " "
						+ avgComboSelectionTime);
			} else if (reportType.equals("menuReport")) {
				printMenuReport(trialNo, annotaion,
						stepReportFiles[currentStep]);
			} else if (reportType.equals("responseTimeBehavior")) {
				stepReportFiles[currentStep].println(trialNo + " "
						+ avgResponseTimeAdaptiveMouseMovesRank);
			} else if (reportType.equals("minDistanceReport")) {
				stepReportFiles[currentStep].println(trialNo + " "
						+ minDistanceForStep);
			} else if (reportType.equals("purePauses")) {
				stepReportFiles[currentStep].println(trialNo + " "
						+ avgPurePauseDuringResponseTimes);
			} else if (reportType.equals("typePause")) {
				stepReportFiles[currentStep].println(trialNo + " "
						+ avgKeyTypedPause);
			} else if (reportType.equals("tooltipReport")) {
				printTooltipReport(trialNo, annotaion,
						stepReportFiles[currentStep]);
			} else if (reportType.equals("pausesReport")) {
				long avgPauseTime;
				if (pauseCountForStep != 0)
					avgPauseTime = (pauseSumForStep / pauseCountForStep);
				else
					avgPauseTime = 0;
				Collections.sort(stepTimeLine);
				itr = stepTimeLine.iterator();
				long actionPauseSum = 0;
				c = 0;
				Object prevOBj = null;
				while (itr.hasNext()) {
					Object o = itr.next();
					if (prevOBj instanceof ActionTime) {
						ActionTime actionTime = (ActionTime) prevOBj;

						if (o instanceof Pause) {
							Pause pause = (Pause) o;

							if (pause.getTime() == actionTime.getTime()) {
								actionPauseSum += pause.getDuration();
								c++;
							}

						}

					}
					prevOBj = o;
				}

				long avgActionPause;
				if (c != 0)
					avgActionPause = (actionPauseSum / c);
				else
					avgActionPause = 0;

				long avgAllPauses = pauseTimesSum / mouseOrKeyEventsCount;
				stepReportFiles[currentStep].println(trialNo + " "
						+ pauseCountForStep + " " + avgPauseTime + " "
						+ beforeStepPause + " " + avgActionPause + " "
						+ avgAllPauses);
			} else if (reportType.startsWith("stepArff")) {
				PrintWriter wekaOutFileStep = null;

				if (reportType.equals("stepArffAS")
						|| reportType.equals("stepArffGSAS")
						|| (reportType.equals("stepArffGSasParamAS"))
						|| reportType.equals("stepArffASDif")
						|| reportType.equals("stepArffGSASDif")) {
					if (normalized) {
						//******************************//
						//RETURN TO PREVIOUS State
						//*********************************//
						wekaOutFileStep = wekaOutFiles[0];
//						if (currentStep != 0)
//							wekaOutFileStep = wekaOutFiles[0];
//						else
//							return;// wekaOutFileStep=wekaOutFileStep2;
					} else {
						wekaOutFileStep = wekaOutFiles[currentStep];

					}
				} else if (reportType.equals("stepArffGS")) {

					if (normalized) {
						//******************************//
						//RETURN TO PREVIOUS State
						//*********************************//
					//	if (currentStep != 0) {
							
							
							if (trialNo <= NOVICE_HIGH)
								wekaOutFileStep = wekaOutFilesForNoviceGS[0];
							else if ((trialNo >= MODERATE_LOW)
									&& (trialNo <= MODERATE_HIGH))
								wekaOutFileStep = wekaOutFilesForModerateGS[0];
							else
								wekaOutFileStep = wekaOutFilesForSkilledGS[0];
					//	} else {
						//	return;
					//	}

					} else {

						if (trialNo <= NOVICE_HIGH)
							wekaOutFileStep = wekaOutFileStep = wekaOutFilesForNoviceGS[currentStep];
						else if ((trialNo >= MODERATE_LOW)
								&& (trialNo <= MODERATE_HIGH))
							wekaOutFileStep = wekaOutFilesForModerateGS[currentStep];
						else
							wekaOutFileStep = wekaOutFilesForSkilledGS[currentStep];

					}
				} else if (reportType.equals("stepArffGSDif")) {

					if (trialNo <= MODERATE_LOW)
						wekaOutFileStep = wekaOutFileStep = wekaOutFilesForNoviceGS[currentStep];
					else if ((trialNo > MODERATE_LOW)
							&& (trialNo <= (MODERATE_HIGH + 1)))
						wekaOutFileStep = wekaOutFilesForModerateGS[currentStep];
					else
						wekaOutFileStep = wekaOutFilesForSkilledGS[currentStep];

				}

				if (reportType.equals("stepArffASDif")
						|| reportType.equals("stepArffGSDif")
						|| reportType.equals("stepArffGSASDif")) {
					// prevTrialStepsParams[currentStep]=new StepParams();
					printStepArffDif(trialNo, annotaion, wekaOutFileStep,
							reportType, prevTrialStepsParams[currentStep]);
				} else {
					if (normalized)
						printStepArffNormalized(trialNo, annotaion,
								wekaOutFileStep, reportType);
					else
						printStepArff(trialNo, annotaion, wekaOutFileStep,
								reportType);
				}
			}
		}
		// }
		// else if (reportType.equals("stepArffASDif") ||
		// reportType.equals("stepArffGSDif")){
		//    		
		// prevTrialStepsParams[currentStep]=new StepParams();
		//        	
		// }
	}

	public void printArffHeader(PrintWriter out, String reportType) {
		out.println("@relation SkillLevel\n");
		if (attrslist.isInAttributesSet("stepTime"))
			out.println("@attribute stepTimeInMillis numeric");
		if (attrslist.isInAttributesSet("avgTooltipViewTime"))
			out.println("@attribute avgTooltipViewTime numeric");
		if (attrslist.isInAttributesSet("tooltipViewingSessions"))
			out.println("@attribute tooltipViewingSessionsCount numeric");
		if (attrslist.isInAttributesSet("avgTooltipsViewedCountInSession"))
			out.println("@attribute avgTooltipsViewedCountInSession numeric");
		if (attrslist.isInAttributesSet("avgTooltipSessionTime"))
			out.println("@attribute avgTooltipSessionTime numeric");
		if (attrslist.isInAttributesSet("avgItemsnavigatediInEachSession"))
			out.println("@attribute avgItemsnavigatediInEachSession numeric");
		if (attrslist.isInAttributesSet("avgMenuNavigationTime"))
			out.println("@attribute avgMenuNavigationTime numeric");
		if (attrslist.isInAttributesSet("avgMenuItemNavigataionTime"))
			out.println("@attribute avgMenuItemNavigataionTime numeric");
		if (attrslist.isInAttributesSet("avgComboSelectionTime"))
			out.println("@attribute avgComboSelectionTime numeric");
		if (attrslist.isInAttributesSet("avgMouseMovesBeforActions"))
			out.println("@attribute avgMouseMovesBeforActions numeric");
		if (attrslist.isInAttributesSet("toolipViewedCount"))
			out.println("@attribute toolipViewedCount numeric");
		if (attrslist.isInAttributesSet("avgVelocityBeforeButtonClicks"))
			out.println("@attribute avgVelocityBeforeButtonClicks numeric ");
		if (attrslist.isInAttributesSet("menuNavigationsCount"))
			out.println("@attribute menuNavigationsCount numeric ");

		if (attrslist.isInAttributesSet("avgOfAllPauses"))
			out.println("@attribute allPausesAvg numeric");
		if (attrslist.isInAttributesSet("avgKeyTypedPause"))
			out.println("@attribute avgKeyTypedPause numeric");
		if (attrslist.isInAttributesSet("avgSelectedMenuItemsDwellTime"))
			out.println("@attribute avgSelectedMenuItemsDwellTime numeric");
		// out.println("@attribute mouseMovesEpisodesCount numeric");
		if (attrslist.isInAttributesSet("mouseDraggEpisodesCount"))
			out.println("@attribute mouseDraggEpisodesCount numeric");
		if (attrslist.isInAttributesSet("avgPurePauseDuringResponseTimes"))
			out.println("@attribute avgPurePauseDuringResponseTimes numeric");
		if (attrslist
				.isInAttributesSet("avgResponseTimeAdaptiveMouseMovesRank"))
			out
					.println("@attribute avgResponseTimeAdaptiveMouseMovesRank numeric");

		if (attrslist.isInAttributesSet("episodesCount"))
			out.println("@attribute episodesCount numeric");
		if (attrslist.isInAttributesSet("distanceTravelledSum"))
			out.println("@attribute distanceTravelled numeric");
		if (attrslist.isInAttributesSet("directionChangesCountSum"))
			out.println("@attribute directionChangesCount numeric");
		if (attrslist.isInAttributesSet("totalAngelChangesSum"))
			out.println("@attribute TotalAngelChanges numeric");
		if (attrslist.isInAttributesSet("moveDurationSum"))
			out.println("@attribute moveDuration numeric");
		if (attrslist.isInAttributesSet("avgMouseXVelocity"))
			out.println("@attribute avgMouseXVelocity numeric");
		if (attrslist.isInAttributesSet("avgMouseYVelocity"))
			out.println("@attribute avgMouseYVelocity numeric");
		if (attrslist.isInAttributesSet("avgMouseVelocity"))
			out.println("@attribute avgMouseVelocity numeric");
		if (attrslist.isInAttributesSet("avgMouseXAccel"))
			out.println("@attribute avgMouseXAccel numeric");
		if (attrslist.isInAttributesSet("avgMouseYAccel"))
			out.println("@attribute avgMouseYAccel numeric");
		if (attrslist.isInAttributesSet("avgMouseAccel"))
			out.println("@attribute avgMouseAccel numeric");
		if (attrslist.isInAttributesSet("maxXVelocity"))
			out.println("@attribute maxXVelocity numeric");
		if (attrslist.isInAttributesSet("maxYVelocity"))
			out.println("@attribute maxYVelocity numeric");
		if (attrslist.isInAttributesSet("maxVelocity"))
			out.println("@attribute maxVelocity numeric");
		if (attrslist.isInAttributesSet("maxXAccel"))
			out.println("@attribute maxXAccel numeric");
		if (attrslist.isInAttributesSet("maxYAccel"))
			out.println("@attribute maxYAccel numeric");
		if (attrslist.isInAttributesSet("maxAccel"))
			out.println("@attribute maxAccel numeric");
		if (attrslist.isInAttributesSet("mouseMovesCount"))
			out.println("@attribute mouseMovesCount numeric");

		if (attrslist.isInAttributesSet("pauseCount"))
			out.println("@attribute pauseCount numeric");
		if (attrslist.isInAttributesSet("avgPauseTime"))
			out.println("@attribute avgPauseTime numeric");
		if (attrslist.isInAttributesSet("avgActionPause"))
			out.println("@attribute avgActionPause numeric");
		if (attrslist.isInAttributesSet("beforeStepPause "))
			out.println("@attribute beforeStepPause numeric");

		if (attrslist.isInAttributesSet("klmDiff"))
			out.println("@attribute klmDiff numeric ");
		if (attrslist.isInAttributesSet("klmRatio"))
			out.println("@attribute klmRatio numeric ");
		if (attrslist.isInAttributesSet("vibrationsCount"))
			out.println("@attribute vibrationsCount numeric");
		if (attrslist.isInAttributesSet("avgVibrationsRadius"))
			out.println("@attribute avgVibrationsRadius numeric");

		if (reportType.equals("stepArffGSasParamAS"))
			out.println("@attribute GeneralSkill {novice,skilled}");

		if (reportType.equals("stepArffGSAS")
				|| reportType.equals("stepArffGSASDif")) {
			out
					.println("@attribute class {novice/novice,novice/moderate,novice/skilled,"
							+ "skilled/novice,skilled/moderate, skilled/skilled} ");
		} else if (reportType.equals("stepArffGSDif")
				|| reportType.equals("stepArffGS"))
			out.println("@attribute class {novice,skilled}");
		else
			out.println("@attribute class {novice,moderate,skilled}");

		out.println("\n\n@data");

	}

	public  void printArffHeaderNormalized(PrintWriter out,
			String reportType) {
		
	
				out.println("@relation SkillLevel\n");
		if (attrslist.isInAttributesSet("klmRatio"))
		out.println("@attribute klmRatio numeric ");
		// out.println("@attribute avgTooltipViewTime numeric");
		// out.println("@attribute avgTooltipsViewedCountInSession numeric");
		if (attrslist.isInAttributesSet("avgMenuItemNavigataionTime"))
		out.println("@attribute avgMenuItemNavigataionTime numeric");
		if (attrslist.isInAttributesSet("avgSelectedMenuItemsDwellTime"))
			out.println("@attribute avgSelectedMenuItemsDwellTime numeric");
		if (attrslist.isInAttributesSet("avgVelocityBeforeButtonClicks"))
		out.println("@attribute avgVelocityBeforeButtonClicks numeric ");
		if (attrslist.isInAttributesSet("avgOfAllPauses"))
		out.println("@attribute allPausesAvg numeric");
		if (attrslist.isInAttributesSet("avgKeyTypedPause"))
		out.println("@attribute avgKeyTypedPause numeric");
		// out.println("@attribute avgPurePauseDuringResponseTimes numeric");
		if (attrslist.isInAttributesSet("avgResponseTimeAdaptiveMouseMovesRank"))
		out.println("@attribute avgResponseTimeAdaptiveMouseMovesRank numeric");
		if (attrslist.isInAttributesSet("episodesCount"))
		out.println("@attribute episodesCount numeric");// /
		if (attrslist.isInAttributesSet("distanceTravelledSum"))
		out.println("@attribute distanceTravelledSum numeric");
		if (attrslist.isInAttributesSet("avgMouseVelocity"))
		out.println("@attribute avgMouseVelocity numeric");
		if (attrslist.isInAttributesSet("avgMouseAccel"))
		out.println("@attribute avgMouseAccel numeric");
		if (attrslist.isInAttributesSet("maxVelocity"))
		out.println("@attribute maxVelocity numeric");
		if (attrslist.isInAttributesSet("maxAccel"))
		out.println("@attribute maxAccel numeric");
		if (attrslist.isInAttributesSet("pauseCount"))
		out.println("@attribute pauseCount numeric");
		if (attrslist.isInAttributesSet("avgPauseTime"))
		out.println("@attribute avgPauseTime numeric");
		if (attrslist.isInAttributesSet("avgActionPause"))
		out.println("@attribute avgActionPause numeric");
		if (attrslist.isInAttributesSet("beforeStepPause"))
		out.println("@attribute beforeStepPause numeric");

		if (reportType.equals("stepArffGSasParamAS"))
			out.println("@attribute GeneralSkill {novice,skilled}");

		
		//*********************************************//
		//	RETURN TP PREVIOUS STATE
		//***********************************************//
//		if (reportType.equals("stepArffGSAS")
//				|| reportType.equals("stepArffGSASDif")) {
//			out
//					.println("@attribute class {novice/novice,novice/moderate,novice/skilled,"
//							+ "skilled/novice,skilled/moderate, skilled/skilled} ");
//		} else if (reportType.equals("stepArffGSDif")
//				|| reportType.equals("stepArffGS"))
//			out.println("@attribute class {novice,skilled}");
//		else
//			out.println("@attribute class {novice,moderate,skilled}");
		
		
		if (reportType.equals("stepArffGSAS")
				|| reportType.equals("stepArffGSASDif")) {
			out
					.println("@attribute class {novice/novice,novice/skilled,"
							+ "skilled/novice, skilled/skilled} ");
		} else if (reportType.equals("stepArffGSDif")
				|| reportType.equals("stepArffGS"))
			out.println("@attribute class {novice,skilled}");
		else
			out.println("@attribute class {novice,skilled}");

		out.println("\n\n@data");

	}

	public void printStepArffDif(int trialNo, String annotaion,
			PrintWriter out, String reportType, StepParams prevTrialStepParams) {
		StepParams stepParams = new StepParams();
		// if (((trialNo>=MODERATE_LOW) && (trialNo<=MODERATE_HIGH) )){
		stepParams.isNosiyOrWitjErrors = isNoisyStep || !stepDoneWithoutError;
		// if (stepParams.isNosiyOrWitjErrors) {
		// prevTrialStepsParams[currentStep]=stepParams;
		// return;
		// }
		stepParams.stepTimeInMillis = prevStepTimeInMillis;
		stepParams.avgTooltipViewTime = avgTooltipViewTime;
		stepParams.tooltipViewingSessions = tooltipViewingSessions;
		stepParams.avgTooltipsViewedCountInSession = avgTooltipsViewedCountInSession;
		stepParams.avgTooltipSessionTime = avgTooltipSessionTime;
		stepParams.avgItemsnavigatediInEachSession = avgItemsnavigatediInEachSession;
		stepParams.avgMenuNavigationTime = avgMenuNavigationTime;
		stepParams.avgMenuItemNavigataionTime = avgMenuItemNavigataionTime;
		stepParams.avgComboSelectionTime = avgComboSelectionTime;
		stepParams.avgMouseMovesBeforActions = avgMouseMovesBeforActions;
		stepParams.toolipViewedCount = toolipViewedCount;
		stepParams.avgVelocityBeforeButtonClicks = avgVelocityBeforeButtonClicks;
		stepParams.menuNavigationsCount = menuNavigationsCount;
		stepParams.avgAllPauses = (pauseTimesSum / mouseOrKeyEventsCount);
		stepParams.avgKeyTypedPause = avgKeyTypedPause;
		stepParams.avgSelectedMenuItemsDwellTime = avgSelectedMenuItemsDwellTime;
		// stepParams.mouseMoveEpisodesSize=mouseMoveEpisodes.size();
		stepParams.mouseDraggEpisodesSize = mouseDraggEpisodes.size();
		stepParams.avgPurePauseDuringResponseTimes = avgPurePauseDuringResponseTimes;
		stepParams.avgResponseTimeAdaptiveMouseMovesRank = avgResponseTimeAdaptiveMouseMovesRank;

		stepParams.pauseCount = pauseCountForStep;
		if (pauseCountForStep != 0)
			stepParams.avgPauseTime = (pauseSumForStep / pauseCountForStep);
		else
			stepParams.avgPauseTime = 0;
		Collections.sort(stepTimeLine);
		Iterator itr = stepTimeLine.iterator();
		long actionPauseSum = 0;
		int c = 0;
		Object prevOBj = null;
		while (itr.hasNext()) {
			Object o = itr.next();
			if (prevOBj instanceof ActionTime) {
				ActionTime actionTime = (ActionTime) prevOBj;

				if (o instanceof Pause) {
					Pause pause = (Pause) o;

					if (pause.getTime() == actionTime.getTime()) {
						actionPauseSum += pause.getDuration();
						c++;
					}

				}

			}
			prevOBj = o;
		}
		stepParams.beforeStepPause = beforeStepPause;

		if (c != 0)
			stepParams.avgActionPause = (actionPauseSum / c);
		else
			stepParams.avgActionPause = 0;
		float klmDiff = ((shellData.stepsKlmTime[currentStep] * 1000) - prevStepTimeInMillis);
		stepParams.klmDiff = klmDiff;
		stepParams.klmRatio = (prevStepTimeInMillis / (shellData.stepsKlmTime[currentStep] * 1000));
		float[] res = MenuNavigation.calcMenuItemVibrationFeatures(
				menuItemsNavigated, "save");
		stepParams.vibrationsCount = res[0];
		stepParams.avgVibrationsRadius = res[1];

		VelocityParams velParams = getVelocityParams(mouseMoveEpisodes);
		stepParams.velocityParams = velParams;

		prevTrialStepsParams[currentStep] = stepParams;

		if ((prevTrialStepParams.isNosiyOrWitjErrors) || (trialNo == 1))
			return;

		out.println(annotaion);

		if (attrslist.isInAttributesSet("stepTime"))
			out.printf("%d", prevStepTimeInMillis
					- prevTrialStepParams.stepTimeInMillis);
		if (attrslist.isInAttributesSet("avgTooltipViewTime"))
			out.printf(" ,%d", avgTooltipViewTime
					- prevTrialStepParams.avgTooltipViewTime);
		if (attrslist.isInAttributesSet("tooltipViewingSessions"))
			out.printf(",%d", tooltipViewingSessions
					- prevTrialStepParams.tooltipViewingSessions);
		if (attrslist.isInAttributesSet("avgTooltipsViewedCountInSession"))
			out.printf(",%.3f", avgTooltipsViewedCountInSession
					- prevTrialStepParams.avgTooltipsViewedCountInSession);
		if (attrslist.isInAttributesSet("avgTooltipSessionTime"))
			out.printf(",%d", avgTooltipSessionTime
					- prevTrialStepParams.avgTooltipSessionTime);
		if (attrslist.isInAttributesSet("avgItemsnavigatediInEachSession"))
			out.printf(",%.3f", avgItemsnavigatediInEachSession
					- prevTrialStepParams.avgItemsnavigatediInEachSession);
		if (attrslist.isInAttributesSet("avgMenuNavigationTime"))
			out.printf(",%d,", avgMenuNavigationTime
					- prevTrialStepParams.avgMenuNavigationTime);
		if (attrslist.isInAttributesSet("avgMenuItemNavigataionTime"))
			out.printf(",%d", avgMenuItemNavigataionTime
					- prevTrialStepParams.avgMenuItemNavigataionTime);
		if (attrslist.isInAttributesSet("avgComboSelectionTime"))
			out.printf(",%d", avgComboSelectionTime
					- prevTrialStepParams.avgComboSelectionTime);
		if (attrslist.isInAttributesSet("avgMouseMovesBeforActions"))
			out.printf(",%.3f", avgMouseMovesBeforActions
					- prevTrialStepParams.avgMouseMovesBeforActions);
		if (attrslist.isInAttributesSet("toolipViewedCount"))
			out.printf(",%d,", toolipViewedCount
					- prevTrialStepParams.toolipViewedCount);
		if (attrslist.isInAttributesSet("avgVelocityBeforeButtonClicks"))
			out.printf(",%.3f", avgVelocityBeforeButtonClicks
					- prevTrialStepParams.avgVelocityBeforeButtonClicks);
		if (attrslist.isInAttributesSet("menuNavigationsCount"))
			out.printf(",%d", menuNavigationsCount
					- prevTrialStepParams.menuNavigationsCount);
		if (attrslist.isInAttributesSet("avgOfAllPauses"))
			out.printf(",%d", (pauseTimesSum / mouseOrKeyEventsCount)
					- prevTrialStepParams.avgAllPauses);
		if (attrslist.isInAttributesSet("avgKeyTypedPause"))
			out.printf(",%d", avgKeyTypedPause
					- prevTrialStepParams.avgKeyTypedPause);
		if (attrslist.isInAttributesSet("avgSelectedMenuItemsDwellTime"))
			out.printf(",%d", avgSelectedMenuItemsDwellTime
					- prevTrialStepParams.avgSelectedMenuItemsDwellTime);
		// out.printf("%d,",mouseMoveEpisodes.size()-prevTrialStepParams.
		// mouseMoveEpisodesSize);
		if (attrslist.isInAttributesSet("mouseDraggEpisodesCount"))
			out.printf(",%d", mouseDraggEpisodes.size()
					- prevTrialStepParams.mouseDraggEpisodesSize);
		if (attrslist.isInAttributesSet("avgPurePauseDuringResponseTimes"))
			out.printf(",%d", avgPurePauseDuringResponseTimes
					- prevTrialStepParams.avgPurePauseDuringResponseTimes);
		if (attrslist
				.isInAttributesSet("avgResponseTimeAdaptiveMouseMovesRank"))
			out
					.printf(
							",%.3f",
							avgResponseTimeAdaptiveMouseMovesRank
									- prevTrialStepParams.avgResponseTimeAdaptiveMouseMovesRank);

		printVelocityArffsDif(mouseMoveEpisodes, null, out,
				prevTrialStepParams.velocityParams);

		// printVelocityArffs(mouseDraggEpisodes,null,out);
		// printVelocityArffs(comboMouseMoveEpisodes,null,out);

		if (attrslist.isInAttributesSet("pauseCount"))
			out.print(", "
					+ (pauseCountForStep - prevTrialStepParams.pauseCount));
		if (attrslist.isInAttributesSet("avgPauseTime")) {
			if (pauseCountForStep != 0)
				out.print(", " + ((pauseSumForStep / pauseCountForStep)));
			else
				out.print(", " + (-prevTrialStepParams.avgPauseTime));
		}

		if (attrslist.isInAttributesSet("avgActionPause")) {
			out
					.print(", "
							+ (stepParams.avgActionPause - prevTrialStepParams.avgActionPause));
		}

		if (attrslist.isInAttributesSet("beforeStepPause"))
			out.print(", "
					+ (beforeStepPause - prevTrialStepParams.beforeStepPause));

		if (attrslist.isInAttributesSet("klmDiff"))
			out.printf(",%.3f", stepParams.klmDiff
					- prevTrialStepParams.klmDiff);
		if (attrslist.isInAttributesSet("klmRatio"))
			out
					.printf(
							",%.3f",
							(prevStepTimeInMillis / (shellData.stepsKlmTime[currentStep] * 1000))
									- prevTrialStepParams.klmRatio);

		if (attrslist.isInAttributesSet("vibrationsCount"))
			out.printf(",%.3f", stepParams.vibrationsCount
					- prevTrialStepParams.vibrationsCount);
		if (attrslist.isInAttributesSet("avgVibrationsRadius"))
			out.printf(",%.3f", stepParams.avgVibrationsRadius
					- prevTrialStepParams.avgVibrationsRadius);

		String applicationSkillLevel = "";
		if (trialNo <= MODERATE_LOW)
			applicationSkillLevel = "novice";
		else if ((trialNo > MODERATE_LOW) && (trialNo <= (MODERATE_HIGH + 1)))
			applicationSkillLevel = "moderate";
		else if ((trialNo >= (SKILLED_LOW + 1)))
			applicationSkillLevel = "skilled";
		else
			applicationSkillLevel = "else";

		if (reportType.equals("stepArffASDif")) {
			out.print("," + applicationSkillLevel);
		} else if (reportType.equals("stepArffGSDif")) {
			out.print("," + experienceLevel);
		} else if (reportType.equals("stepArffGSASDif"))
			out.print(", " + experienceLevel + "/" + applicationSkillLevel);

	}

	public void printStepArff(int trialNo, String annotaion, PrintWriter out,
			String reportType) {
		out.println(annotaion);

		if (attrslist.isInAttributesSet("stepTime"))
			out.printf("%d,", prevStepTimeInMillis);
		if (attrslist.isInAttributesSet("avgTooltipViewTime"))
			out.printf("%d,", avgTooltipViewTime);
		if (attrslist.isInAttributesSet("tooltipViewingSessions"))
			out.printf("%d,", tooltipViewingSessions);
		if (attrslist.isInAttributesSet("avgTooltipsViewedCountInSession"))
			out.printf("%.3f,", avgTooltipsViewedCountInSession);
		if (attrslist.isInAttributesSet("avgTooltipSessionTime"))
			out.printf("%d,", avgTooltipSessionTime);
		if (attrslist.isInAttributesSet("avgItemsnavigatediInEachSession"))
			out.printf("%.3f,", avgItemsnavigatediInEachSession);
		if (attrslist.isInAttributesSet("avgMenuNavigationTime"))
			out.printf("%d,", avgMenuNavigationTime);
		if (attrslist.isInAttributesSet("avgMenuItemNavigataionTime"))
			out.printf("%d,", avgMenuItemNavigataionTime);
		if (attrslist.isInAttributesSet("avgComboSelectionTime"))
			out.printf("%d,", avgComboSelectionTime);
		if (attrslist.isInAttributesSet("avgMouseMovesBeforActions"))
			out.printf("%.3f,", avgMouseMovesBeforActions);
		if (attrslist.isInAttributesSet("toolipViewedCount"))
			out.printf("%d,", toolipViewedCount);
		if (attrslist.isInAttributesSet("avgVelocityBeforeButtonClicks"))
			out.printf("%.3f,", avgVelocityBeforeButtonClicks);
		if (attrslist.isInAttributesSet("menuNavigationsCount"))
			out.printf("%d,", menuNavigationsCount);
		if (attrslist.isInAttributesSet("avgOfAllPauses"))
			out.printf("%d,", pauseTimesSum / mouseOrKeyEventsCount);
		if (attrslist.isInAttributesSet("avgKeyTypedPause"))
			out.printf("%d,", avgKeyTypedPause);
		if (attrslist.isInAttributesSet("avgSelectedMenuItemsDwellTime"))
			out.printf("%d,", avgSelectedMenuItemsDwellTime);
		// if ( attrslist.isInAttributesSet("mouseMoveEpisodesCount"))
		// out.printf("%d,",mouseMoveEpisodes.size());
		if (attrslist.isInAttributesSet("mouseDraggEpisodesCount"))
			out.printf("%d,", mouseDraggEpisodes.size());
		if (attrslist.isInAttributesSet("avgPurePauseDuringResponseTimes"))
			out.printf("%d,", avgPurePauseDuringResponseTimes);
		if (attrslist
				.isInAttributesSet("avgResponseTimeAdaptiveMouseMovesRank"))
			out.printf("%.3f", avgResponseTimeAdaptiveMouseMovesRank);

		printVelocityArffs(mouseMoveEpisodes, null, out);
		// printVelocityArffs(mouseDraggEpisodes,null,out);
		// printVelocityArffs(comboMouseMoveEpisodes,null,out);

		if (attrslist.isInAttributesSet("pauseCount"))
			out.print(", " + pauseCountForStep);
		if (attrslist.isInAttributesSet("avgPauseTime")) {
			if (pauseCountForStep != 0)
				out.print(", " + pauseSumForStep / pauseCountForStep);
			else
				out.print(", " + 0);
		}

		Collections.sort(stepTimeLine);
		Iterator itr = stepTimeLine.iterator();
		long actionPauseSum = 0;
		int c = 0;
		Object prevOBj = null;
		while (itr.hasNext()) {
			Object o = itr.next();
			if (prevOBj instanceof ActionTime) {
				ActionTime actionTime = (ActionTime) prevOBj;

				if (o instanceof Pause) {
					Pause pause = (Pause) o;

					if (pause.getTime() == actionTime.getTime()) {
						actionPauseSum += pause.getDuration();
						c++;
					}

				}

			}
			prevOBj = o;
		}

		if (attrslist.isInAttributesSet("avgActionPause")) {
			if (c != 0)
				out.print(", " + actionPauseSum / c);
			else
				out.print(", " + 0);
		}

		if (attrslist.isInAttributesSet("beforeStepPause"))
			out.print(", " + beforeStepPause);

		// out.println(", "+stepDoneWithoutError);

		// out.print(","+taskCompletionTime );
		float klmDiff = ((shellData.stepsKlmTime[currentStep] * 1000) - prevStepTimeInMillis);
		if (attrslist.isInAttributesSet("klmDiff"))
			out.printf(",%.3f", klmDiff);
		if (attrslist.isInAttributesSet("klmRatio"))
			out
					.printf(
							",%.3f",
							(prevStepTimeInMillis / (shellData.stepsKlmTime[currentStep] * 1000)));

		float[] res = MenuNavigation.calcMenuItemVibrationFeatures(
				menuItemsNavigated, "save");
		if (attrslist.isInAttributesSet("vibrationsCount"))
			out.printf(",%.3f", res[0]);
		if (attrslist.isInAttributesSet("avgVibrationsRadius"))
			out.printf(",%.3f", res[1]);

		String applicationSkillLevel = "";
		if (trialNo <= NOVICE_HIGH)
			applicationSkillLevel = "novice";
		else if ((trialNo >= MODERATE_LOW) && (trialNo <= MODERATE_HIGH))
			applicationSkillLevel = "moderate";
		else if ((trialNo >= SKILLED_LOW))
			applicationSkillLevel = "skilled";

		if (reportType.equals("stepArffAS")
				|| reportType.equals("stepArffGSasParamAS")) {
			if (reportType.equals("stepArffGSasParamAS"))
				out.print(", " + experienceLevel);
			out.print(", " + applicationSkillLevel);
		} else if (reportType.equals("stepArffGS"))
			out.print(", " + experienceLevel);
		else if (reportType.equals("stepArffGSAS"))
			out.print(", " + experienceLevel + "/" + applicationSkillLevel);

	}

	public void printStepArffNormalized(int trialNo, String annotaion,
			PrintWriter out, String reportType) {

		// if (((trialNo>=MODERATE_LOW) && (trialNo<=MODERATE_HIGH) )){

		out.println(annotaion);

		
		
		
		if (attrslist.isInAttributesSet("klmRatio"))
		out
				.printf(
						"%.3f,",
						(prevStepTimeInMillis / (shellData.stepsKlmTime[currentStep] * 1000)));
		
		if (attrslist.isInAttributesSet("avgMenuItemNavigataionTime"))
		{if (menuNavigationsCount != 0)
			out.printf("%d,", avgMenuItemNavigataionTime);
		else
			out.printf("?,");
		}
		
		if (attrslist.isInAttributesSet("avgSelectedMenuItemsDwellTime"))
			{
		if (menuNavigationsCount != 0)
			out.printf("%d,", avgSelectedMenuItemsDwellTime);
		else
			out.printf("?,");
		}
		// out.printf("%d,",avgComboSelectionTime );

		if (attrslist.isInAttributesSet("avgVelocityBeforeButtonClicks")){
			if (ButtonClickedCount != 0)
			out.printf("%.3f,", avgVelocityBeforeButtonClicks);
		else
			out.printf("?,");
		}
		
		
		if (attrslist.isInAttributesSet("avgOfAllPauses"))
		out.printf("%d,", pauseTimesSum / mouseOrKeyEventsCount);
		
		if (attrslist.isInAttributesSet("avgKeyTypedPause")){	
			if (textComponentKeyTypedCounts != 0)
			out.printf("%d,", avgKeyTypedPause);
		else
			out.printf("?,");
		}
		
		if (attrslist.isInAttributesSet("avgResponseTimeAdaptiveMouseMovesRank")){
			float avgResponseTimeAdaptiveMouseMovesRankNormalized = 0;
		
		if (responseTimesCountInStep != 0)
			avgResponseTimeAdaptiveMouseMovesRankNormalized = responseTimeAdaptiveMouseMovesRankSumNormalized
					/ responseTimesCountInStep;
		if (responseTimesCountInStep != 0)
			out.printf("%.3f", avgResponseTimeAdaptiveMouseMovesRankNormalized);
		else
			out.printf("?");
		}
		
		Collections.sort(stepTimeLine);
		Iterator itr = stepTimeLine.iterator();
		long actionPauseSum = 0;
		int c = 0;

		Object prevOBj = null;
		while (itr.hasNext()) {
			Object o = itr.next();
			// if (o instanceof ActionTime)actionCount++;
			if (prevOBj instanceof ActionTime) {
				ActionTime actionTime = (ActionTime) prevOBj;

				if (o instanceof Pause) {
					Pause pause = (Pause) o;

					if (pause.getTime() == actionTime.getTime()) {
						actionPauseSum += pause.getDuration();
						c++;
					}

				}

			}
			prevOBj = o;
		}

		float moveDuration = printVelocityArffsNormalized(mouseMoveEpisodes,
				null, out, shellData.actionsCountInSteps[currentStep]);

		if (attrslist.isInAttributesSet("pauseCount")){
			if (pauseCountForStep != 0)
			out
					.print(", "
							+ (pauseCountForStep / shellData.actionsCountInSteps[currentStep]));
		else
			out.print(", " + 0 );
		}
		if (attrslist.isInAttributesSet("avgPauseTime")){
			if (pauseCountForStep != 0)
				out
						.print( ", " + pauseSumForStep / pauseCountForStep);
			else
				out.print( ", " + 0);
		}
		
		
		
		if (attrslist.isInAttributesSet("avgActionPause")){
			if (c != 0)
			out.print(", " + actionPauseSum / c);
		else
			out.print(",?");
		
		}
		if (attrslist.isInAttributesSet("beforeStepPause"))
		out.print(", " + beforeStepPause);
		
		String applicationSkillLevel = "";
		if (trialNo <= NOVICE_HIGH)
			applicationSkillLevel = "novice";
		else if ((trialNo >= MODERATE_LOW) && (trialNo <= MODERATE_HIGH))
			applicationSkillLevel = "moderate";
		else if ((trialNo >= SKILLED_LOW))
			applicationSkillLevel = "skilled";

		if (reportType.equals("stepArffAS")
				|| reportType.equals("stepArffGSasParamAS")) {
			if (reportType.equals("stepArffGSasParamAS"))
				out.print(", " + experienceLevel);
			out.print(", " + applicationSkillLevel);
		} else if (reportType.equals("stepArffGS"))
			out.print(", " + experienceLevel);
		else if (reportType.equals("stepArffGSAS"))
			out.print(", " + experienceLevel + "/" + applicationSkillLevel);

	}

	public void printTooltipReport(int trialNo, String annotaion,
			PrintWriter reportOutFile) {
		reportOutFile.println(trialNo + " " + toolipViewedCount + " "
				+ tooltipViewingSessions + " "
				+ avgTooltipsViewedCountInSession + " " + avgTooltipViewTime
				+ " " + avgTooltipSessionTime);

	}

	public void printMenuReport(int trialNo, String annotaion,
			PrintWriter reportOutFile) {
		float avgVibrationRadius = 0;
		float avgVibrationCount = 0;
		if (menuItemClickedCount != 0) {
			avgVibrationCount = menuVibrationCountSum / menuItemClickedCount;
			avgVibrationRadius = menuVibrationRadiusSum / menuItemClickedCount;
		}
		reportOutFile.println(trialNo + " " + avgItemsnavigatediInEachSession
				+ " " + avgMenuNavigationTime + " "
				+ avgMenuItemNavigataionTime + " " + menuNavigationsCount + " "
				+ avgSelectedMenuItemsDwellTime + " " + avgVibrationRadius
				+ " " + avgVibrationCount);

	}

	public void printVelocityInfos(int trialNo, String annotaion,
			PrintWriter reportOutFile) {
		Iterator itr = mouseMoveEpisodes.iterator();
		int episodesCount = mouseMoveEpisodes.size();

		float avgMouseXVelocitySum = 0;
		float avgMouseYVelocitySum = 0;
		float avgMouseVelocitySum = 0;
		float avgMouseXAccelSum = 0;
		float avgMouseYAccelSum = 0;
		float avgMouseAccelSum = 0;
		float maxXVelocitySum = 0;
		float maxYVelocitySum = 0;
		float maxVelocitySum = 0;
		float maxXAccelSum = 0;
		float maxYAccelSum = 0;
		float maxAccelSum = 0;
		float distanceTravelledSum = 0;
		float moveDurationSum = 0;
		double totalAngelChangesSum = 0;
		int directionChangesCountSum = 0;
		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();
			avgMouseXVelocitySum += mouseMoveEpisode.getAvgMouseXVelocity();
			avgMouseYVelocitySum += mouseMoveEpisode.getAvgMouseYVelocity();
			avgMouseVelocitySum += mouseMoveEpisode.getAvgMouseVelocity();
			avgMouseXAccelSum += mouseMoveEpisode.getAvgMouseXAccel();
			avgMouseYAccelSum += mouseMoveEpisode.getAvgMouseYAccel();
			avgMouseAccelSum += mouseMoveEpisode.getAvgMouseAccel();
			maxXVelocitySum += mouseMoveEpisode.getMaxXVelocity();
			maxYVelocitySum += mouseMoveEpisode.getMaxYVelocity();
			maxVelocitySum += mouseMoveEpisode.getMaxVelocity();
			maxXAccelSum += mouseMoveEpisode.getMaxXAccel();
			maxYAccelSum += mouseMoveEpisode.getMaxYAccel();
			maxAccelSum += mouseMoveEpisode.getMaxAccel();
			distanceTravelledSum += mouseMoveEpisode.getDistanceTravelled();
			moveDurationSum += mouseMoveEpisode.getMoveDuration();
			totalAngelChangesSum += mouseMoveEpisode.getTotalAngelChanges();
			directionChangesCountSum += mouseMoveEpisode
					.getDirectionChangesCount();

		}
		if (episodesCount == 0) {
			reportOutFile.println(trialNo + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0);
			return;
		}

		moveDurationSum = moveDurationSum / 1000f;
		reportOutFile.println(trialNo + " " + episodesCount + " "
				+ distanceTravelledSum + " " + directionChangesCountSum + " "
				+ totalAngelChangesSum + " " + moveDurationSum + " "
				+ avgMouseXVelocitySum / episodesCount + " "
				+ avgMouseYVelocitySum / episodesCount + " "
				+ avgMouseVelocitySum / episodesCount + " "
				+ avgMouseXAccelSum / episodesCount + " "
				+ avgMouseYAccelSum / episodesCount + " " + avgMouseAccelSum
				/ episodesCount + " " + maxXVelocitySum / episodesCount + " "
				+ maxYVelocitySum / episodesCount + " " + maxVelocitySum
				/ episodesCount + " " + maxXAccelSum / episodesCount + " "
				+ maxYAccelSum / episodesCount + " " + maxAccelSum
				/ episodesCount + " " + avgVelocityBeforeButtonClicks);

	}

	public void printVelocityInfos2(ArrayList mouseMoveEpisodes, int trialNo,
			String annotaion, PrintWriter reportOutFile) {
		Iterator itr = mouseMoveEpisodes.iterator();
		int episodesCount = mouseMoveEpisodes.size();

		float xDistanceTravelledSum = 0;
		float yDistanceTravelledSum = 0;
		float yVelocityChangesSum = 0;
		float xVelocityChangesSum = 0;
		float velocityChangesSum = 0;
		float maxXVelocity = 0;
		float maxYVelocity = 0;
		float maxVelocity = 0;
		float maxXAccel = 0;
		float maxYAccel = 0;
		float maxAccel = 0;
		float distanceTravelledSum = 0;
		float moveDurationSum = 0;
		double totalAngelChangesSum = 0;
		int directionChangesCountSum = 0;
		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();
			distanceTravelledSum += mouseMoveEpisode.getDistanceTravelled();
			xDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));

			velocityChangesSum += Math.abs(mouseMoveEpisode.getAvgMouseAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			xVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));

			if (mouseMoveEpisode.getMaxXVelocity() > maxXVelocity) {
				maxXVelocity = mouseMoveEpisode.getMaxXVelocity();
			}
			if (mouseMoveEpisode.getMaxYVelocity() > maxYVelocity) {
				maxYVelocity = mouseMoveEpisode.getMaxYVelocity();
			}
			if (mouseMoveEpisode.getMaxVelocity() > maxVelocity) {
				maxVelocity = mouseMoveEpisode.getMaxVelocity();
			}
			if (mouseMoveEpisode.getMaxXAccel() > maxXAccel) {
				maxXAccel = mouseMoveEpisode.getMaxXAccel();
			}
			if (mouseMoveEpisode.getMaxYAccel() > maxYAccel) {
				maxYAccel = mouseMoveEpisode.getMaxYAccel();
			}
			if (mouseMoveEpisode.getMaxAccel() > maxAccel) {
				maxAccel = mouseMoveEpisode.getMaxAccel();
			}

			moveDurationSum += mouseMoveEpisode.getMoveDuration();
			totalAngelChangesSum += mouseMoveEpisode.getTotalAngelChanges();
			directionChangesCountSum += mouseMoveEpisode
					.getDirectionChangesCount();

		}
		if (episodesCount == 0) {
			reportOutFile.println(trialNo + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0);
			return;
		}

		moveDurationSum = moveDurationSum / 1000f;
		reportOutFile.println(trialNo + " " + episodesCount + " "
				+ distanceTravelledSum + " " + directionChangesCountSum + " "
				+ totalAngelChangesSum + " " + moveDurationSum + " "
				+ xDistanceTravelledSum / moveDurationSum + " "
				+ yDistanceTravelledSum / moveDurationSum + " "
				+ distanceTravelledSum / moveDurationSum + " "
				+ xVelocityChangesSum / moveDurationSum + " "
				+ yVelocityChangesSum / moveDurationSum + " "
				+ velocityChangesSum / moveDurationSum + " " + maxXVelocity
				+ " " + maxYVelocity + " " + maxVelocity + " " + maxXAccel
				+ " " + maxYAccel + " " + maxAccel + " "
				+ avgVelocityBeforeButtonClicks);

	}

	public void printVelocityArffs(ArrayList mouseMoveEpisodes,
			String annotaion, PrintWriter reportOutFile) {
		Iterator itr = mouseMoveEpisodes.iterator();
		int episodesCount = mouseMoveEpisodes.size();

		float xDistanceTravelledSum = 0;
		float yDistanceTravelledSum = 0;
		float yVelocityChangesSum = 0;
		float xVelocityChangesSum = 0;
		float velocityChangesSum = 0;
		float maxXVelocity = 0;
		float maxYVelocity = 0;
		float maxVelocity = 0;
		float maxXAccel = 0;
		float maxYAccel = 0;
		float maxAccel = 0;
		float distanceTravelledSum = 0;
		float moveDurationSum = 0;
		double totalAngelChangesSum = 0;
		int directionChangesCountSum = 0;
		int mouseMovesCount = 0;
		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();
			distanceTravelledSum += mouseMoveEpisode.getDistanceTravelled();
			xDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));

			velocityChangesSum += Math.abs(mouseMoveEpisode.getAvgMouseAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			xVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			mouseMovesCount += mouseMoveEpisode.getMovesCount();
			if (mouseMoveEpisode.getMaxXVelocity() > maxXVelocity) {
				maxXVelocity = mouseMoveEpisode.getMaxXVelocity();
			}
			if (mouseMoveEpisode.getMaxYVelocity() > maxYVelocity) {
				maxYVelocity = mouseMoveEpisode.getMaxYVelocity();
			}
			if (mouseMoveEpisode.getMaxVelocity() > maxVelocity) {
				maxVelocity = mouseMoveEpisode.getMaxVelocity();
			}
			if (mouseMoveEpisode.getMaxXAccel() > maxXAccel) {
				maxXAccel = mouseMoveEpisode.getMaxXAccel();
			}
			if (mouseMoveEpisode.getMaxYAccel() > maxYAccel) {
				maxYAccel = mouseMoveEpisode.getMaxYAccel();
			}
			if (mouseMoveEpisode.getMaxAccel() > maxAccel) {
				maxAccel = mouseMoveEpisode.getMaxAccel();
			}

			moveDurationSum += mouseMoveEpisode.getMoveDuration();
			totalAngelChangesSum += mouseMoveEpisode.getTotalAngelChanges();
			directionChangesCountSum += mouseMoveEpisode
					.getDirectionChangesCount();

		}
		if (episodesCount == 0) {
			reportOutFile.println(" " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0);
			return;
		}

		moveDurationSum = moveDurationSum / 1000f;
		if (attrslist.isInAttributesSet("episodesCount"))
			reportOutFile.printf(",%d", episodesCount);
		if (attrslist.isInAttributesSet("distanceTravelledSum"))
			reportOutFile.printf(",%.3f", distanceTravelledSum);
		if (attrslist.isInAttributesSet("directionChangesCountSum"))
			reportOutFile.printf(",%d", directionChangesCountSum);
		if (attrslist.isInAttributesSet("totalAngelChangesSum"))
			reportOutFile.printf(",%.3f", totalAngelChangesSum);
		if (attrslist.isInAttributesSet("moveDurationSum"))
			reportOutFile.printf(",%d", moveDurationSum);
		if (attrslist.isInAttributesSet("avgMouseXVelocity"))
			reportOutFile.printf(",%.3f", xDistanceTravelledSum
					/ moveDurationSum);
		if (attrslist.isInAttributesSet("avgMouseYVelocity"))
			reportOutFile.printf(",%.3f", yDistanceTravelledSum
					/ moveDurationSum);
		if (attrslist.isInAttributesSet("avgMouseVelocity"))
			reportOutFile.printf(",%.3f", distanceTravelledSum
					/ moveDurationSum);
		if (attrslist.isInAttributesSet("avgMouseXAccel"))
			reportOutFile
					.printf(",%.3f", xVelocityChangesSum / moveDurationSum);
		if (attrslist.isInAttributesSet("avgMouseYAccel"))
			reportOutFile
					.printf(",%.3f", yVelocityChangesSum / moveDurationSum);
		if (attrslist.isInAttributesSet("avgMouseAccel"))
			reportOutFile.printf(",%.3f", velocityChangesSum / moveDurationSum);
		if (attrslist.isInAttributesSet("maxXVelocity"))
			reportOutFile.printf(",%.3f", maxXVelocity);
		if (attrslist.isInAttributesSet("maxYVelocity"))
			reportOutFile.printf(",%.3f", maxYVelocity);
		if (attrslist.isInAttributesSet("maxVelocity"))
			reportOutFile.printf(",%.3f", maxVelocity);
		if (attrslist.isInAttributesSet("maxXAccel"))
			reportOutFile.printf(",%.3f", maxXAccel);
		if (attrslist.isInAttributesSet("maxYAccel"))
			reportOutFile.printf(",%.3f", maxYAccel);
		if (attrslist.isInAttributesSet("maxAccel"))
			reportOutFile.printf(",%.3f", maxAccel);
		if (attrslist.isInAttributesSet("mouseMovesCount"))
			reportOutFile.printf(",%d", mouseMovesCount);

	}

	public VelocityParams getVelocityParams(ArrayList mouseMoveEpisodes) {
		Iterator itr = mouseMoveEpisodes.iterator();
		int episodesCount = mouseMoveEpisodes.size();
		if (episodesCount == 0) {

			return new VelocityParams();
		}

		float xDistanceTravelledSum = 0;
		float yDistanceTravelledSum = 0;
		float yVelocityChangesSum = 0;
		float xVelocityChangesSum = 0;
		float velocityChangesSum = 0;
		float maxXVelocity = 0;
		float maxYVelocity = 0;
		float maxVelocity = 0;
		float maxXAccel = 0;
		float maxYAccel = 0;
		float maxAccel = 0;
		float distanceTravelledSum = 0;
		float moveDurationSum = 0;
		double totalAngelChangesSum = 0;
		int directionChangesCountSum = 0;
		int mouseMovesCount = 0;
		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();
			distanceTravelledSum += mouseMoveEpisode.getDistanceTravelled();
			xDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));

			velocityChangesSum += Math.abs(mouseMoveEpisode.getAvgMouseAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			xVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			mouseMovesCount += mouseMoveEpisode.getMovesCount();
			if (mouseMoveEpisode.getMaxXVelocity() > maxXVelocity) {
				maxXVelocity = mouseMoveEpisode.getMaxXVelocity();
			}
			if (mouseMoveEpisode.getMaxYVelocity() > maxYVelocity) {
				maxYVelocity = mouseMoveEpisode.getMaxYVelocity();
			}
			if (mouseMoveEpisode.getMaxVelocity() > maxVelocity) {
				maxVelocity = mouseMoveEpisode.getMaxVelocity();
			}
			if (mouseMoveEpisode.getMaxXAccel() > maxXAccel) {
				maxXAccel = mouseMoveEpisode.getMaxXAccel();
			}
			if (mouseMoveEpisode.getMaxYAccel() > maxYAccel) {
				maxYAccel = mouseMoveEpisode.getMaxYAccel();
			}
			if (mouseMoveEpisode.getMaxAccel() > maxAccel) {
				maxAccel = mouseMoveEpisode.getMaxAccel();
			}

			moveDurationSum += mouseMoveEpisode.getMoveDuration();
			totalAngelChangesSum += mouseMoveEpisode.getTotalAngelChanges();
			directionChangesCountSum += mouseMoveEpisode
					.getDirectionChangesCount();

		}

		moveDurationSum = moveDurationSum / 1000f;
		VelocityParams velocityParams = new VelocityParams();
		velocityParams.episodesCount = episodesCount;
		velocityParams.distanceTravelledSum = distanceTravelledSum;
		velocityParams.directionChangesCountSum = directionChangesCountSum;
		velocityParams.totalAngelChangesSum = totalAngelChangesSum;
		velocityParams.moveDurationSum = (long) (moveDurationSum * 1000);
		velocityParams.avgXVelocity = (xDistanceTravelledSum / moveDurationSum);
		velocityParams.avgYVelocity = (yDistanceTravelledSum / moveDurationSum);
		velocityParams.avgVelocity = (distanceTravelledSum / moveDurationSum);
		velocityParams.avgXAccel = (xVelocityChangesSum / moveDurationSum);
		velocityParams.avgYAccel = (yVelocityChangesSum / moveDurationSum);
		velocityParams.avgAccel = (yVelocityChangesSum / moveDurationSum);
		velocityParams.maxXVelocity = maxXVelocity;
		velocityParams.maxYVelocity = maxYVelocity;
		velocityParams.maxVelocity = maxVelocity;
		velocityParams.maxXAccel = maxXAccel;
		velocityParams.maxYAccel = maxYAccel;
		velocityParams.maxAccel = maxAccel;
		velocityParams.mouseMovesCount = mouseMovesCount;

		return velocityParams;

	}

	public VelocityParams printVelocityArffsDif(ArrayList mouseMoveEpisodes,
			String annotaion, PrintWriter reportOutFile,
			VelocityParams prevVelocityParams) {
		Iterator itr = mouseMoveEpisodes.iterator();
		int episodesCount = mouseMoveEpisodes.size();

		float xDistanceTravelledSum = 0;
		float yDistanceTravelledSum = 0;
		float yVelocityChangesSum = 0;
		float xVelocityChangesSum = 0;
		float velocityChangesSum = 0;
		float maxXVelocity = 0;
		float maxYVelocity = 0;
		float maxVelocity = 0;
		float maxXAccel = 0;
		float maxYAccel = 0;
		float maxAccel = 0;
		float distanceTravelledSum = 0;
		float moveDurationSum = 0;
		double totalAngelChangesSum = 0;
		int directionChangesCountSum = 0;
		int mouseMovesCount = 0;
		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();
			distanceTravelledSum += mouseMoveEpisode.getDistanceTravelled();
			xDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));

			velocityChangesSum += Math.abs(mouseMoveEpisode.getAvgMouseAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			xVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			mouseMovesCount += mouseMoveEpisode.getMovesCount();
			if (mouseMoveEpisode.getMaxXVelocity() > maxXVelocity) {
				maxXVelocity = mouseMoveEpisode.getMaxXVelocity();
			}
			if (mouseMoveEpisode.getMaxYVelocity() > maxYVelocity) {
				maxYVelocity = mouseMoveEpisode.getMaxYVelocity();
			}
			if (mouseMoveEpisode.getMaxVelocity() > maxVelocity) {
				maxVelocity = mouseMoveEpisode.getMaxVelocity();
			}
			if (mouseMoveEpisode.getMaxXAccel() > maxXAccel) {
				maxXAccel = mouseMoveEpisode.getMaxXAccel();
			}
			if (mouseMoveEpisode.getMaxYAccel() > maxYAccel) {
				maxYAccel = mouseMoveEpisode.getMaxYAccel();
			}
			if (mouseMoveEpisode.getMaxAccel() > maxAccel) {
				maxAccel = mouseMoveEpisode.getMaxAccel();
			}

			moveDurationSum += mouseMoveEpisode.getMoveDuration();
			totalAngelChangesSum += mouseMoveEpisode.getTotalAngelChanges();
			directionChangesCountSum += mouseMoveEpisode
					.getDirectionChangesCount();

		}
		if (episodesCount == 0) {
			reportOutFile.println(" " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + " "
					+ 0);
			return new VelocityParams();
		}

		moveDurationSum = moveDurationSum / 1000f;

		VelocityParams velocityParams = new VelocityParams();
		velocityParams.episodesCount = episodesCount;
		velocityParams.distanceTravelledSum = distanceTravelledSum;
		velocityParams.directionChangesCountSum = directionChangesCountSum;
		velocityParams.totalAngelChangesSum = totalAngelChangesSum;
		velocityParams.moveDurationSum = (long) (moveDurationSum * 1000);
		velocityParams.avgXVelocity = (xDistanceTravelledSum / moveDurationSum);
		velocityParams.avgYVelocity = (yDistanceTravelledSum / moveDurationSum);
		velocityParams.avgVelocity = (distanceTravelledSum / moveDurationSum);
		velocityParams.avgXAccel = (xVelocityChangesSum / moveDurationSum);
		velocityParams.avgYAccel = (yVelocityChangesSum / moveDurationSum);
		velocityParams.avgAccel = (yVelocityChangesSum / moveDurationSum);
		velocityParams.maxXVelocity = maxXVelocity;
		velocityParams.maxYVelocity = maxYVelocity;
		velocityParams.maxVelocity = maxVelocity;
		velocityParams.maxXAccel = maxXAccel;
		velocityParams.maxYAccel = maxYAccel;
		velocityParams.maxAccel = maxAccel;
		velocityParams.mouseMovesCount = mouseMovesCount;

		if (attrslist.isInAttributesSet("episodesCount"))
			reportOutFile.printf(",%d", episodesCount
					- prevVelocityParams.episodesCount);
		if (attrslist.isInAttributesSet("distanceTravelledSum"))
			reportOutFile.printf(",%.3f", distanceTravelledSum
					- prevVelocityParams.distanceTravelledSum);
		if (attrslist.isInAttributesSet("directionChangesCountSum"))
			reportOutFile.printf(",%d", directionChangesCountSum
					- prevVelocityParams.directionChangesCountSum);
		if (attrslist.isInAttributesSet("totalAngelChangesSum"))
			reportOutFile.printf(",%.3f", totalAngelChangesSum
					- prevVelocityParams.totalAngelChangesSum);
		if (attrslist.isInAttributesSet("moveDurationSum"))
			reportOutFile.printf(",%d", (long) (moveDurationSum * 1000)
					- prevVelocityParams.moveDurationSum);
		if (attrslist.isInAttributesSet("avgMouseXVelocity"))
			reportOutFile.printf(",%.3f",
					(xDistanceTravelledSum / moveDurationSum)
							- prevVelocityParams.avgXVelocity);
		if (attrslist.isInAttributesSet("avgMouseYVelocity"))
			reportOutFile.printf(",%.3f",
					(yDistanceTravelledSum / moveDurationSum)
							- prevVelocityParams.avgYVelocity);
		if (attrslist.isInAttributesSet("avgMouseVelocity"))
			reportOutFile.printf(",%.3f",
					(distanceTravelledSum / moveDurationSum)
							- prevVelocityParams.avgVelocity);
		if (attrslist.isInAttributesSet("avgMouseXAccel"))
			reportOutFile.printf(",%.3f",
					(xVelocityChangesSum / moveDurationSum)
							- prevVelocityParams.avgXAccel);
		if (attrslist.isInAttributesSet("avgMouseYAccel"))
			reportOutFile.printf(",%.3f",
					(yVelocityChangesSum / moveDurationSum)
							- prevVelocityParams.avgYAccel);
		if (attrslist.isInAttributesSet("avgMouseAccel"))
			reportOutFile.printf(",%.3f",
					(velocityChangesSum / moveDurationSum)
							- prevVelocityParams.avgAccel);
		if (attrslist.isInAttributesSet("maxXVelocity"))
			reportOutFile.printf(",%.3f", maxXVelocity
					- prevVelocityParams.maxXVelocity);
		if (attrslist.isInAttributesSet("maxYVelocity"))
			reportOutFile.printf(",%.3f", maxYVelocity
					- prevVelocityParams.maxYVelocity);
		if (attrslist.isInAttributesSet("maxVelocity"))
			reportOutFile.printf(",%.3f", maxVelocity
					- prevVelocityParams.maxVelocity);
		if (attrslist.isInAttributesSet("maxXAccel"))
			reportOutFile.printf(",%.3f", maxXAccel
					- prevVelocityParams.maxXAccel);
		if (attrslist.isInAttributesSet("maxYAccel"))
			reportOutFile.printf(",%.3f", maxYAccel
					- prevVelocityParams.maxYAccel);
		if (attrslist.isInAttributesSet("maxAccel"))
			reportOutFile.printf(",%.3f", maxAccel
					- prevVelocityParams.maxAccel);
		if (attrslist.isInAttributesSet("mouseMovesCount"))
			reportOutFile.printf(",%d", mouseMovesCount
					- prevVelocityParams.mouseMovesCount);

		return velocityParams;

	}

	public float printVelocityArffsNormalized(ArrayList mouseMoveEpisodes,
			String annotaion, PrintWriter reportOutFile, int actionCount) {
		Iterator itr = mouseMoveEpisodes.iterator();
		int episodesCount = mouseMoveEpisodes.size();

		float xDistanceTravelledSum = 0;
		float yDistanceTravelledSum = 0;
		float yVelocityChangesSum = 0;
		float xVelocityChangesSum = 0;
		float velocityChangesSum = 0;
		float maxXVelocity = 0;
		float maxYVelocity = 0;
		float maxVelocity = 0;
		float maxXAccel = 0;
		float maxYAccel = 0;
		float maxAccel = 0;
		float distanceTravelledSum = 0;
		float moveDurationSum = 0;
		double totalAngelChangesSum = 0;
		int directionChangesCountSum = 0;
		int mouseMovesCount = 0;
		while (itr.hasNext()) {
			MouseMoves mouseMoveEpisode = (MouseMoves) itr.next();
			mouseMoveEpisode.calcFeaturesValues();
			distanceTravelledSum += mouseMoveEpisode.getDistanceTravelled();
			xDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yDistanceTravelledSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYVelocity()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));

			velocityChangesSum += Math.abs(mouseMoveEpisode.getAvgMouseAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			xVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseXAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			yVelocityChangesSum += Math.abs(mouseMoveEpisode
					.getAvgMouseYAccel()
					* ((float) mouseMoveEpisode.getMoveDuration() / 1000));
			mouseMovesCount += mouseMoveEpisode.getMovesCount();
			if (mouseMoveEpisode.getMaxXVelocity() > maxXVelocity) {
				maxXVelocity = mouseMoveEpisode.getMaxXVelocity();
			}
			if (mouseMoveEpisode.getMaxYVelocity() > maxYVelocity) {
				maxYVelocity = mouseMoveEpisode.getMaxYVelocity();
			}
			if (mouseMoveEpisode.getMaxVelocity() > maxVelocity) {
				maxVelocity = mouseMoveEpisode.getMaxVelocity();
			}
			if (mouseMoveEpisode.getMaxXAccel() > maxXAccel) {
				maxXAccel = mouseMoveEpisode.getMaxXAccel();
			}
			if (mouseMoveEpisode.getMaxYAccel() > maxYAccel) {
				maxYAccel = mouseMoveEpisode.getMaxYAccel();
			}
			if (mouseMoveEpisode.getMaxAccel() > maxAccel) {
				maxAccel = mouseMoveEpisode.getMaxAccel();
			}

			moveDurationSum += mouseMoveEpisode.getMoveDuration();
			totalAngelChangesSum += mouseMoveEpisode.getTotalAngelChanges();
			directionChangesCountSum += mouseMoveEpisode
					.getDirectionChangesCount();

		}
		moveDurationSum = moveDurationSum / 1000f;
		if (episodesCount == 0) {
			reportOutFile.println(" ,?, ?, ?, ?, ?, ?,");
			return 0;
		}

		if (attrslist.isInAttributesSet("episodesCount"))
			reportOutFile.printf(",%.5f",
					(((float) episodesCount / actionCount)));
		if (attrslist.isInAttributesSet("distanceTravelledSum"))
			reportOutFile.printf(",%.3f", distanceTravelledSum
					/ shellData.minStepsDistance[currentStep]);
		if (attrslist.isInAttributesSet("avgMouseVelocity"))
			reportOutFile.printf(",%.3f", distanceTravelledSum
					/ moveDurationSum);
		if (attrslist.isInAttributesSet("avgMouseAccel"))
			reportOutFile.printf(",%.3f", velocityChangesSum / moveDurationSum);
		if (attrslist.isInAttributesSet("maxVelocity"))
			reportOutFile.printf(",%.3f", maxVelocity);
		if (attrslist.isInAttributesSet("maxAccel"))
			reportOutFile.printf(",%.3f", maxAccel);
		return moveDurationSum;

	}

	public void reportTaskTime(int trialNo, String annotaion,
			PrintWriter reportOutFile) {

		reportOutFile.println(trialNo + " " + taskCompletionTime);

	}

	public void printTrialReport(String reportType, int trialNo,
			String annotaion, PrintWriter reportOutFile) {

		if (reportType.equals("pauses")) {
			reportOutFile.println("trial " + trialNo);
			Collections.sort(timeLine);
			Iterator itr = timeLine.iterator();

			int c = 1;
			while (itr.hasNext()) {
				Object o = itr.next();
				if (o instanceof ActionTime) {
					ActionTime actionTime = (ActionTime) o;
					reportOutFile.println(actionTime.getTime() + ": "
							+ actionTime.getAction());
				} else if (o instanceof Pause) {
					Pause pause = (Pause) o;
					reportOutFile.println(pause.getTime() + " Pause: "
							+ pause.getDuration());
				}
			}

		} else if (reportType.equals("pausesStat")) {
			reportOutFile.println(trialNo + " " + pauseCount + " " + pauseSum
					/ pauseCount);
		} else if (reportType.equals("pausesAfterActions")) {
			Collections.sort(timeLine);
			Iterator itr = timeLine.iterator();
			long actionPauseSum = 0;
			int c = 0;
			Object prevOBj = null;
			while (itr.hasNext()) {
				Object o = itr.next();
				if (prevOBj instanceof ActionTime) {
					ActionTime actionTime = (ActionTime) prevOBj;

					if (o instanceof Pause) {
						Pause pause = (Pause) o;

						if (pause.getTime() == actionTime.getTime()) {
							actionPauseSum += pause.getDuration();
							c++;
						}

					}

				}
				prevOBj = o;
			}
			reportOutFile.println(trialNo + " " + actionPauseSum / c);
		} else if (reportType.equals("pausesBeforeSteps")) {
			reportOutFile.println(trialNo + " " + beforeStepPauseSum
					/ numOfStepsDone);
		} else if (reportType.equals("errorReport")) {
			reportOutFile.println(trialNo + " " + numOfErrorfulSteps);
		}
	}

	/*
	 * public void printTaskTotalResults( PrintWriter out,long
	 * completetionTime,String success,String userCategory){ out.println("");
	 * out.print("@attribute taskCompletionTime numeric ");
	 * out.println(completetionTime + ",");
	 * out.print("@attribute taskCompletion {successed, failed} ");
	 * out.println(success + ","); out.print("@attribute klmDiff numeric ");
	 * out.println((klmTime1000)-completetionTime + ",");
	 * out.print("@attribute klmRatio numeric ");
	 * out.println(completetionTime/(klmTime1000) + ",");
	 * out.print("@attribute class {novice,moderate,experienced} ");
	 * out.println(userCategory+"");
	 * 
	 * 
	 * // out.println("@data");
	 * 
	 * 
	 * 
	 * out.flush(); out.close();
	 * 
	 * }
	 */

	public void splitLowLevelActionsFromSemantic(String inputFile) {

		FileWriter lowLevelOutFile = null;
		PrintWriter semanticOut = null;
		PrintWriter lowLevelOut = null;
		try {
			lowLevelOutFile = new FileWriter("./"
					+ inputFile.substring(0, inputFile.indexOf(".txt"))
					+ "_lowLevel.txt", false);

			lowLevelOut = new PrintWriter(lowLevelOutFile);

			FileWriter outFile = new FileWriter("./"
					+ inputFile.substring(0, inputFile.indexOf(".txt"))
					+ "_semantic_actions.txt", false);
			semanticOut = new PrintWriter(outFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		// String eventType="";

		try {
			while ((line = in.readLine()) != null) {

				// if (tokenizer.hasMoreElements()) eventType =
				// tokenizer.nextToken().trim();
				// if (tokenizer.hasMoreElements())
				// eventSource=tokenizer.nextToken().trim();

				if (line.startsWith("MOUSE_")) {
					lowLevelOut.println(line);
				} else {
					semanticOut.println(line);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		semanticOut.flush();
		semanticOut.close();
		lowLevelOut.flush();
		lowLevelOut.close();

	}

	public static float calcDistance(int y1, int y2, int x1, int x2) {
		return (float) (Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2)));
	}
}
