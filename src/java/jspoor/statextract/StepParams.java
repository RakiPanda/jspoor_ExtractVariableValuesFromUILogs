package jspoor.statextract;

/**
 * holds the performance attributes values extracted for a specific step 
 * @author arin ghazarian
 *
 */
public class StepParams {
	public boolean isNosiyOrWitjErrors=true;

	public long avgTooltipViewTime;
	public long stepTimeInMillis;
	public int tooltipViewingSessions;
	public float avgTooltipsViewedCountInSession;
	public double avgItemsnavigatediInEachSession;
	public long avgTooltipSessionTime;
	public long avgMenuNavigationTime;
	public long avgMenuItemNavigataionTime;
	public long avgComboSelectionTime;
	public float avgMouseMovesBeforActions;
	public int toolipViewedCount;
	public double avgVelocityBeforeButtonClicks;
	public int menuNavigationsCount;
	public long avgAllPauses;
	public long avgKeyTypedPause;
	public long avgSelectedMenuItemsDwellTime;
	public int mouseMoveEpisodesSize;
	public int mouseDraggEpisodesSize;
	public long avgPurePauseDuringResponseTimes;
	public float avgResponseTimeAdaptiveMouseMovesRank;
	public int pauseCount;
	public long avgActionPause;
	public long beforeStepPause_;
	public float klmDiff;
	public float klmRatio;
	public float avgVibrationsRadius;
	public float vibrationsCount;
	public long avgPauseTime;
	public long beforeStepPause;
	public VelocityParams velocityParams;	

}
