package jspoor.uilog.loglisteners;
import java.awt.event.*;

import jspoor.uilog.Logger;

/**
 * This listener is registered with all components which we want to log when focus is gained and lost on them.
 * it is registered with swing components by aspectj at loadtime.
 * 
 * @author arin ghazarian
 * 
 */
public class LoggingFocusListner implements FocusListener {




     public void focusGained(FocusEvent e) {
		Logger.getInstance().logThisFocusGainedEventComponent(e);
	 }

	 public void focusLost(FocusEvent e) {
		Logger.getInstance().logThisFocusLostEventComponent(e);
    }
}
