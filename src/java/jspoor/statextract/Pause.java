package jspoor.statextract;

/**
 * This class represents a pause in users actions
 * 
 * @author arin ghazarian
 * 
 */
public class Pause extends TimeLineObject {

	/**
	 * duration of the pause in milliseconds
	 */
	private long duration;

	/**
	 * constructor
	 * 
	 * @param time
	 *            the time n which the pause happened
	 * @param duration
	 *            duration of the pause
	 */
	public Pause(long time, long duration) {
		super(time);
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

}
