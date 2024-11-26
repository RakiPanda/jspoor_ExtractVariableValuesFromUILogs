package jspoor.uilog;

import javax.swing.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.* ;
import java.util.EventObject;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

/**
 * this class is used for creating the log file and write an ui event on it. 
 * 
 * @author arin ghazarian
 *
 */
public class Logger {


    private static Logger instance;
    public PrintWriter uilogout;
    private long mouseMovesCount;
    private long startTime;


    private Logger() {
		mouseMovesCount=0;
        FileWriter outFile = null;

        try {
			 SimpleDateFormat df = new SimpleDateFormat("EEE_d-MMM-yyyy_HH-mm-ss");
			 File f= new File("./ui-log_"+df.format(new Date())+".txt" );
			     outFile = new FileWriter(f,false);
			       // JOptionPane.showMessageDialog(null, f.getAbsolutePath());
			     //   System.out.println("f: "+f.getAbsolutePath());
			        
			    
        } catch (IOException e) {
            e.printStackTrace();
        }
        uilogout = new PrintWriter(outFile);
        startTime=System.currentTimeMillis();
    }

    public void resetLogFile(){
    	mouseMovesCount=0;
        FileWriter outFile = null;
    	loggingTerminated();
    	 try {
			 SimpleDateFormat df = new SimpleDateFormat("EEE_d-MMM-yyyy_HH-mm-ss");
			     outFile = new FileWriter("./ui-log_"+df.format(new Date())+".txt", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        uilogout = new PrintWriter(outFile);
        startTime=System.currentTimeMillis();
    }
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    public void incrementMouseMoves(){
		mouseMovesCount++;
	}


public void resetmouseMovesCount(){
		mouseMovesCount=0;
	}

    public void logThisMenuItemEvent(ActionEvent event) {

        uilogout.println("MENU_ITEM_SELECTED: " +
                ((JMenuItem) event.getSource()).getText() + ": " + (System.currentTimeMillis()-startTime));
        uilogout.flush();
    }

    public void logThisPopupMenuShowEvent(JPopupMenu popupMenu) {
	        uilogout.println("POPUP_MENU_SHOWED: " +
	                popupMenu.getLabel() + ": " + (System.currentTimeMillis()-startTime));
	        uilogout.flush();
    }
     public void logThisMenuNavigationEvent(MouseEvent event) {
	        uilogout.println("MENU_ITEM_NAVIGATED: " +
	                ((JMenuItem) event.getSource()).getText() + ": " + (System.currentTimeMillis()-startTime));
	        uilogout.flush();
    }

     public void logThisButtonActionEvent(ActionEvent event) {
	        uilogout.println("BUTTON_PRESSED: " +
	        		getSourceComponentText(((JButton) event.getSource())) + ": " + (System.currentTimeMillis()-startTime));
	        uilogout.flush();
	    }
	    public void logThisRadioButtonActionEvent(ActionEvent event) {
	        uilogout.println("RADIOBUTTON_SELECTED: " +
	                ((JRadioButton) event.getSource()).getText() +": "+(System.currentTimeMillis()-startTime)+ ": " +((JRadioButton) event.getSource()).isSelected() );
	        uilogout.flush();
	    }

	    public void logThisCheckBoxActionEvent(ItemEvent event) {
	        uilogout.println("CHECKBOX_SELECTED: " +
	                ((JCheckBox) event.getSource()).getText() +": "+(System.currentTimeMillis()-startTime)+ ": " +((JCheckBox) event.getSource()).isSelected() );
	        uilogout.flush();
	    }
	    public void logThisFocusGainedEventComponent(FocusEvent event) {
			uilogout.println("COMPONENT_FOCUS_GAINED: " +((JComponent) event.getSource()).getName() +
			": "+(System.currentTimeMillis()-startTime)+": "+((JComponent) event.getSource()).getClass().getSimpleName());
	        uilogout.flush();
	    }
	    public void logThisFocusLostEventComponent(FocusEvent event) {
			        uilogout.println("COMPONENT_FOCUS_LOST: "+((JComponent) event.getSource()).getName()
			        + ": "+(System.currentTimeMillis()-startTime)+": "+((JComponent) event.getSource()).getClass().getSimpleName());
			        uilogout.flush();
	    }

	     public void logThisJSliderChangeEvent(ChangeEvent  event) {
			        uilogout.println("JSLIDER_CHANGED: " +
			                ((JSlider) event.getSource()).getName() +": "+(System.currentTimeMillis()-startTime)+": "+((JSlider)event.getSource()).getValue() );
			        uilogout.flush();
	    }

public void logThisJComboBoxActionEvent(ActionEvent event) {
	        uilogout.println("COMBOBOX_SELECTED: " +
	                ((JComboBox) event.getSource()).getName() +" selected: "+ (System.currentTimeMillis()-startTime+ ": " +  ((JComboBox) event.getSource()).getSelectedItem()));
	        uilogout.flush();
	    }

public void logThisToolTipShowedEvent(String tip) {
	        uilogout.println("TOOLTIP_SHOWED: " +tip+": " +(System.currentTimeMillis()-startTime));
	        uilogout.flush();
	    }

	   public void logThisToolTipHidededEvent(String tip) {
	        uilogout.println("TOOLTIP_HIDED: " +tip+": " +(System.currentTimeMillis()-startTime));
	        uilogout.flush();
	    }

    public void logThisMouseButtonPressedEvent( MouseEvent event) {
	        uilogout.println("MOUSE_BUTTON_PRESSED: BUTTON" +event.getButton()+ ": "+(System.currentTimeMillis()-startTime));
	        uilogout.flush();
	    }
public void logThisMouseButtonReleasedEvent( MouseEvent event) {
	        uilogout.println("MOUSE_BUTTON_RELEASED: BUTTON" +event.getButton()+ ": "+(System.currentTimeMillis()-startTime));
	        uilogout.flush();
	    }
  public void logThisKeyPressedEvent(KeyEvent event) {
	  String info=returnKeyPressedInfo(event);
				if (info!=null){
			        uilogout.println("KEY_PRESSED: " +info+ ": "+(System.currentTimeMillis()-startTime));
			        uilogout.flush();
				}
 }

   public void logMouseMoves(MouseEvent event) {
	   Point coords=event.getPoint();
	   if (event.getSource() instanceof Component)SwingUtilities.convertPointToScreen(coords,(Component)event.getSource());
 	    uilogout.println("MOUSE_MOVED: "+event.getSource().getClass().getSimpleName()+": " +(System.currentTimeMillis()-startTime)+": (" +(int)coords.getX()+","+(int)coords.getY()+")" );
 	   // resetmouseMovesCount();
	    uilogout.flush();
 }
   public void logThisMouseDragEvent(MouseEvent event) {
	   Point coords=event.getPoint();
	   if (event.getSource() instanceof Component)SwingUtilities.convertPointToScreen(coords,(Component)event.getSource());
	   
	    uilogout.println("MOUSE_DRAGGED: "+event.getSource().getClass().getSimpleName()+": "+ (System.currentTimeMillis()-startTime)+": (" +(int)coords.getX()+","+(int)coords.getY()+")" );
	   // resetmouseMovesCount();
	    uilogout.flush();
}

public static String  returnKeyPressedInfo(KeyEvent e){


                String infoMsg="";
		        int keyCode = e.getKeyCode();
		         int modifiersEx = e.getModifiersEx();
		         String tmpString = KeyEvent.getModifiersExText(modifiersEx);
		         String keyText=KeyEvent.getKeyText(keyCode);
		         if (keyText.equals(tmpString))return null;//modifier only key pressed event		       infoMsg =   KeyEvent.getKeyText(keyCode);


                 infoMsg +=keyText;
		        if (tmpString.length() > 0) {
		            infoMsg += " + " + tmpString;
		        }


		        if (e.isActionKey()) {
		            infoMsg += " ActionKey(ActionKey)";

		        }
				//infoMsg =infoMsg+": "+e.getWhen();
		       // String locationString = "key location: ";
		        //int location = e.getKeyLocation();
		        //if (location == KeyEvent.KEY_LOCATION_STANDARD) {
		        //    locationString += "standard";
		       // } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
		       //     locationString += "left";
		       // } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
		       //     locationString += "right";
		      //  } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
		       //     locationString += "numpad";
		      //  } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
		       //     locationString += "unknown";
		       // }

		      return infoMsg;
    }

 public void logThisWindowEvent(WindowEvent uiEvent) {
       String eventType = "event";
        if (uiEvent.getID() == WindowEvent.WINDOW_CLOSING) {
            eventType = "WINDOW_CLOSED";
        } else if (uiEvent.getID() == WindowEvent.WINDOW_DEICONIFIED) {
            eventType = "WINDOW_DEICONIFIED";
        } else if (uiEvent.getID() == WindowEvent.WINDOW_ICONIFIED) {
            eventType = "WINDOW_ICONIFIED";
        } else {
             return;
        }


        uilogout.println( eventType+ ": " + uiEvent.getSource().getClass().getSimpleName() +
                ": " + getSourceComponentText( uiEvent) + ": " + (System.currentTimeMillis()-startTime));
        uilogout.flush();

    }

     public void logThisAdjustmentEvent(AdjustmentEvent uiEvent) {

	         uilogout.println( "ADJUSTMENT_VALUE_CHANGED"+ ": " + uiEvent.getSource().getClass().getSimpleName() +
                ": " + (System.currentTimeMillis()-startTime));
		    uilogout.flush();

 }


 private String getSourceComponentText(AWTEvent awtEvent) {
         Component component;
        if (awtEvent instanceof ItemEvent )
        component=(Component)((ItemEvent)awtEvent).getItem();
        else
        component=(Component)awtEvent.getSource();

        if (component.getName()!=null) return  component.getName();
        else if ((component instanceof AbstractButton) )
                      return ((AbstractButton)component).getText();
         else if ((component instanceof JInternalFrame)   )
                      return ((JInternalFrame)component).getTitle();
         else if ((component instanceof JFrame)   )
                      return ((JFrame)component).getTitle();
        else return "";
    }


 private String getSourceComponentText(JButton btn) {
     if (btn.getName()!=null) return  btn.getName();
    else if ((btn.getText()!=null ) && (!btn.getText().equals("") ) )
                  return btn.getText();
    else if ((btn.getToolTipText()!=null ) && (!btn.getToolTipText().equals("") ) )
        return btn.getToolTipText();
    else if ((btn.getActionCommand()!=null ) && (!btn.getActionCommand().equals("")) )
        return btn.getActionCommand();
    else return btn.getClass().getSimpleName();
}

public void logMouseMovesCount(){
        uilogout.println("MOUSE_MOVES_COUNT: "+mouseMovesCount);
        uilogout.flush();

    }
    public void loggingTerminated(){
        uilogout.close();
    }



}


