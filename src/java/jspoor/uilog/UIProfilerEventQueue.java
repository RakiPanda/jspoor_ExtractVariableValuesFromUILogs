package jspoor.uilog;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuDragMouseEvent;

/**
 * this class is replaced with swing's default eventqueue to be able to log
 * mouse and keyboard events
 * 
 * @author arin ghazarian
 * 
 */
public class UIProfilerEventQueue extends EventQueue {
	private long lastEventTime = 0;

	protected void dispatchEvent(AWTEvent event) {

		if ((event instanceof KeyEvent) && (event.getID() == Event.KEY_PRESS)) {
			lastEventTime = System.currentTimeMillis();
			Logger.getInstance().logThisKeyPressedEvent((KeyEvent) event);
		} else if ((event.getID() == MouseEvent.MOUSE_MOVED)) {
			// Logger.getInstance().incrementMouseMoves();
			Logger.getInstance().logMouseMoves((MouseEvent) event);
		} else if ((event.getID() == MouseEvent.MOUSE_PRESSED)) {

			Logger.getInstance().logThisMouseButtonPressedEvent(
					(MouseEvent) event);
		} else if ((event.getID() == MouseEvent.MOUSE_RELEASED)) {
			Logger.getInstance().logThisMouseButtonReleasedEvent(
					(MouseEvent) event);
		} else if ((event.getID() == MouseEvent.MOUSE_DRAGGED)) {
			Logger.getInstance().logThisMouseDragEvent((MouseEvent) event);
		}
		super.dispatchEvent(event);
	}
}
