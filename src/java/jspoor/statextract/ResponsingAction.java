package jspoor.statextract;

import java.util.StringTokenizer;

/**
 * represents a lengthy action which has a notable response time
 * 
 * @author arin ghazarian
 * 
 */
public class ResponsingAction extends ActionIndicator {

	/**
	 * duration of the responsing action
	 */
	public long responseTime;

	/**
	 * constructor
	 * 
	 * @param id
	 *            id of the responsing action
	 * @param condition
	 *            this string indicates the condition when this a responsing
	 *            action happens
	 * @param responseTime
	 *            the duration of operation
	 */
	public ResponsingAction(int id, String condition, long responseTime) {
		super(condition, id);
		this.responseTime = responseTime;
	}

}
