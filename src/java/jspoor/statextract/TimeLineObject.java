package jspoor.statextract;

/**
 * this object is used to represents events which have time ordering
 * 
 * @author arin ghazarian
 * 
 */

public class TimeLineObject implements Comparable {

	/**
	 * the time which the event has happens
	 */
	private long time;

	public TimeLineObject(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) throws ClassCastException {
		TimeLineObject timeLineObject = (TimeLineObject) o;
		if (time > timeLineObject.getTime())
			return 1;
		else if (time < timeLineObject.getTime())
			return -1;
		else
			return 0;

	}
}
