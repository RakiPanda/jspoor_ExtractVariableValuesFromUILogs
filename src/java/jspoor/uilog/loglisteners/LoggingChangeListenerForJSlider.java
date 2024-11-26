package jspoor.uilog.loglisteners;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.*;

import jspoor.uilog.Logger;


/**
 * This listener is registered with all JSlider components which we want to log statechanged
 *  events on them. it is registered with swing components by aspectj at loadtime.
 * 
 * @author arin ghazarian
 * 
 */
public class LoggingChangeListenerForJSlider implements ChangeListener {

public void stateChanged(ChangeEvent e) {
	Logger.getInstance().logThisJSliderChangeEvent(e);
    }
}