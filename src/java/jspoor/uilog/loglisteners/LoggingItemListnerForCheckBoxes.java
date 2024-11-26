package jspoor.uilog.loglisteners;

import java.awt.event.*;

import jspoor.uilog.Logger;

/**
 * This listener is registered with all checkbox components which we want to log
 * itemStateChanged events on them. it is registered with swing components by
 * aspectj at loadtime.
 * 
 * @author arin ghazarian
 * 
 */
public class LoggingItemListnerForCheckBoxes implements ItemListener {

	public void itemStateChanged(ItemEvent e) {
		Logger.getInstance().logThisCheckBoxActionEvent(e);
	}
}
