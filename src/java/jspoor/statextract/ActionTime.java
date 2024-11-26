package jspoor.statextract;

/**
 * This class represents an action done on ui (such as pressing buttons) with
 * its happening time
 * 
 * @author arin ghazarian
 * 
 */
public class ActionTime extends TimeLineObject {

	/**
	 * an string indicating the action
	 */
	String action;

	public ActionTime(String action, long time) {
		super(time);
		this.action = action;

	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
