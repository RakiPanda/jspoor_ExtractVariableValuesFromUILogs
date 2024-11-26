package jspoor.uilog.loglisteners;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import jspoor.uilog.Logger;

/**
 * This listener is registered with all components which we want to log action
 * performed events on them. it is registered with swing components by aspectj at loadtime.
 * 
 * @author arin ghazarian
 * 
 */
public class LoggingActionListner implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		Logger.getInstance().logThisMenuItemEvent(e);
	}
}
