package jspoor.uilog.loglisteners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;

import jspoor.uilog.Logger;

/**
 * This listener is registered with all components which we want to log when the
 * mouse cursor is eneterd in their region. it is registered with swing
 * components by aspectj at loadtime.
 * 
 * @author arin ghazarian
 * 
 */
public class LoggingMouseListener extends MouseAdapter {

	public void mouseEntered(MouseEvent e) {
		Logger.getInstance().logThisMenuNavigationEvent(e);
	}
}