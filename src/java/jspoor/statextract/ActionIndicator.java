package jspoor.statextract;


import java.util.StringTokenizer;


/**
 * This class is used to represent an action like selecting a menu item or pressing a button.
 * It is used to to check when a specific action has happened in a stream of events.
 * 
 * @author arin ghazarian
 *
 */
public class ActionIndicator {
	
	/**
	 * identifer of the action 
	 */
	int id;
	
	/**
	 * a & separated list of constraints which if satisfied, then the action has happened
	 * such as: eventType=MENU_ITEM_SELECTED&eventSource=New Message  
	 */
	String condition;

	/**
	 * constructors a new instance
	 * @param condition a & separated list of constraints 
	 * @param id identifer of the action
	 */
	public ActionIndicator(String condition, int id) {
		this.condition = condition;
		this.id=id;
	}
	

	
	/**
	 * compares the condition assigned to this instance with the values of input parameters 
	 * @param eventType type of the current event
	 * @param eventSource source of the current event
	 * @param prevNonMouseEventType the last non muse event type
	 * @param prevNonMouseEventSource the last non mouse event source
	 * @param lastAction the last action such as menu selections
	 * @param lastActionSource the last action's source
	 * @param prevNonMouseMoveEventType the last non mouse move action type
	 * @return if the condition was satisfied then returns true else false
	 */
	public boolean isConditionSaticfied(String eventType,String eventSource,
			String prevNonMouseEventType,String prevNonMouseEventSource,
			String lastAction,String lastActionSource,String prevNonMouseMoveEventType){
		
		StringTokenizer tokenizer = new StringTokenizer(condition,"&");
		boolean flag=true;
		while(tokenizer.hasMoreElements()){
			String token=tokenizer.nextToken();
			String value=token.substring(token.indexOf('=')+1,token.length());
			if (token.startsWith("eventType=")){
				if (!eventType.equalsIgnoreCase(value)) {
					flag=false;
					break;
				}
			}else if (token.startsWith("eventSource=")){
				if (!eventSource.equalsIgnoreCase(value)) {
					flag=false;
					break;
				}
			}else if (token.startsWith("prevNonMouseEventType=")){
				if (!prevNonMouseEventType.equalsIgnoreCase(value)) {
					flag=false;
					break;
				}
			}else if (token.startsWith("prevNonMouseEventSource=")){
				if (!prevNonMouseEventSource.equalsIgnoreCase(value)) {
					flag=false;
					break;
				}
			}else if (token.startsWith("lastAction=")){
				if (!lastAction.equalsIgnoreCase(value)) {
					flag=false;
					break;
				}
			}else if (token.startsWith("lastActionSource=")){
				if (!lastActionSource.equalsIgnoreCase(value)) {
					flag=false;
					break;
				}
			}else if (token.startsWith("prevNonMouseMoveEventType=")){
				if (!prevNonMouseMoveEventType.equalsIgnoreCase(value)) {
					flag=false;
					break;
				}
			}
			
		}
		return flag;
	}
}
