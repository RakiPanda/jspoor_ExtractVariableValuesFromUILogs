
package jspoor;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.text.*;
import jspoor.uilog.*;
import jspoor.uilog.loglisteners.*;

public aspect ActionListenerAspect {
  

	
	private pointcut setVisiblePopupPopupsPoints(JPopupMenu popupMenu,boolean visible):
		target (popupMenu) && args(visible) && call(* JPopupMenu.setVisible(boolean)); 
	before (JPopupMenu popupMenu,boolean visible) : setVisiblePopupPopupsPoints(popupMenu,visible) {
			if (visible)Logger.getInstance().logThisPopupMenuShowEvent(popupMenu);
				
	}
	
	private pointcut showPopupsPoints(JPopupMenu popupMenu):
		target (popupMenu) && call(* JPopupMenu.show(..));
      
      before (JPopupMenu popupMenu) : showPopupsPoints(popupMenu) {
                        
       		Logger.getInstance().logThisPopupMenuShowEvent(popupMenu);
       			
	}
	
        private pointcut newMenuItem () :
	 			call(JMenuItem+.new(..)) ;
	 after () returning(JMenuItem mnuItem): newMenuItem() {
	             mnuItem.addMouseListener(new LoggingMouseListener());
	            mnuItem.addActionListener(new LoggingActionListner());
    
	}
	
	
	private pointcut addActionListenersForMenuItemsPoints (JMenuItem mnuItem) :
			target (mnuItem) && call(* JMenuItem.addActionListener(*)) && !within(ActionListenerAspect);
	after (JMenuItem mnuItem) : addActionListenersForMenuItemsPoints(mnuItem) {
	ActionListener[] actionListeners=mnuItem.getActionListeners();
		if (actionListeners.length>1){
				ActionListener logActionListener=actionListeners[actionListeners.length-1];
				mnuItem.removeActionListener(logActionListener);
				mnuItem.addActionListener(logActionListener);
			}
	}

	   private pointcut newButtonPoints () :call(JButton.new(..)) ;
	 	after () returning(JButton btn): newButtonPoints() {
	 	          btn.addActionListener(new LoggingActionListnerForButtons());
	   }
	   
	   private pointcut addActionListenersForButtonsPoints (JButton btn) :
	   			target (btn) && call(* JButton.addActionListener(*)) && !within(ActionListenerAspect);
	   	after (JButton btn) : addActionListenersForButtonsPoints(btn) {
	   	ActionListener[] actionListeners=btn.getActionListeners();
	   		if (actionListeners.length>1){
	   				ActionListener logActionListener=actionListeners[actionListeners.length-1];
	   				btn.removeActionListener(logActionListener);
	   				btn.addActionListener(logActionListener);
	   			}
	}
	
	  private pointcut newJRadioButtonPoints():call(JRadioButton.new(..)) ;
		 	after () returning(JRadioButton btn): newJRadioButtonPoints() {
		 	          btn.addActionListener(new LoggingActionListnerForToggleButtons());
		   }
		   
		   private pointcut addActionListenersForRadioButtonsPoints (JRadioButton btn) :
		   			target (btn) && call(* JRadioButton.addActionListener(*)) && !within(ActionListenerAspect);
		   	after (JRadioButton btn) : addActionListenersForRadioButtonsPoints(btn) {
		   	ActionListener[] actionListeners=btn.getActionListeners();
		   		if (actionListeners.length>1){
		   				ActionListener logActionListener=actionListeners[actionListeners.length-1];
		   				btn.removeActionListener(logActionListener);
		   				btn.addActionListener(logActionListener);
		   			}
	}

	private pointcut newJCheckBoxPoints():call(JCheckBox.new(..)) ;
			 	after () returning(JCheckBox btn): newJCheckBoxPoints() {
			 	          btn.addItemListener(new LoggingItemListnerForCheckBoxes());
			   }
			   
			   private pointcut addaddItemListenersForCheckBoxesPoints (JCheckBox btn) :
			   			target (btn) && call(* JCheckBox.addItemListener(*)) && !within(ActionListenerAspect);
			   	after (JCheckBox btn) : addaddItemListenersForCheckBoxesPoints(btn) {
			   	ItemListener[] itemListeners=btn.getItemListeners();
			   		if (itemListeners.length>1){
			   				ItemListener logItemListener=itemListeners[itemListeners.length-1];
			   				btn.removeItemListener(logItemListener);
			   				btn.addItemListener(logItemListener);
			   			}
	}
	
	
private pointcut newJComponentsPoints():call((JComponent+ && !JPanel+ && !JButton && !JFrame+).new(..)) ;
			 	after () returning(JComponent component): newJComponentsPoints() {
			 	          component.addFocusListener(new LoggingFocusListner());
			   }
			   
 private pointcut addFocusListenerForJComponents (JComponent component) :
			   			target (component) && call(* (JComponent+ && !JPanel+ && !JButton && !JFrame+).addFocusListener(*)) && 
			   			!within(ActionListenerAspect);
			   	after (JComponent component) : addFocusListenerForJComponents(component) {
			   	FocusListener[] focusListeners=component.getFocusListeners();
			   		if (focusListeners.length>1){
			   				FocusListener logFocusKeyListener=focusListeners[focusListeners.length-1];
			   				component.removeFocusListener(logFocusKeyListener);
			   				component.addFocusListener(logFocusKeyListener);
			   			}
	}
	
	
	 private pointcut newJComboBoxsPoints () :call(JComboBox.new(..)) ;
		 	after () returning(JComboBox comboBox): newJComboBoxsPoints() {
		 	          comboBox.addActionListener(new LoggingActionListnerForJComboBox());
		   }
		   
		   private pointcut addActionListenersForComboBoxsPoints(JComboBox comboBox) :
		   			target (comboBox) && call(* JComboBox.addActionListener(*)) && !within(ActionListenerAspect);
		   	after (JComboBox comboBox) : addActionListenersForComboBoxsPoints(comboBox) {
		   	ActionListener[] actionListeners=comboBox.getActionListeners();
		   		if (actionListeners.length>1){
		   				ActionListener logActionListener=actionListeners[actionListeners.length-1];
		   				comboBox.removeActionListener(logActionListener);
		   				comboBox.addActionListener(logActionListener);
		   			}
	}
	
	
	
	 private pointcut newSetToolTipTextPoints(JComponent component) :
		target (component) && call(* JComponent.setToolTipText(*)) && !within(ActionListenerAspect);
		   			
	after (JComponent component) : newSetToolTipTextPoints(component) {
	System.out.println("hola");
	    MirrorToolTipManager toolTipManager = MirrorToolTipManager.sharedInstance();
	    toolTipManager.registerComponent(component);
	}
	
	
	
	
	 private pointcut newJSliderPoints () :call(JSlider.new(..)) ;
			 	after () returning(JSlider slider): newJSliderPoints() {
			 	          slider.addChangeListener(new LoggingChangeListenerForJSlider());
			   }
			   
	 private pointcut addChangeListenersForJSlidersPoints(JSlider slider) :
			   			target (slider) && call(* JSlider.addChangeListener(*)) && !within(ActionListenerAspect);
			   	after (JSlider slider) : addChangeListenersForJSlidersPoints(slider) {
			   	ChangeListener[] changeListeners=slider.getChangeListeners();
			   		if (changeListeners.length>1){
			   				ChangeListener logChangeListener=changeListeners[changeListeners.length-1];
			   				slider.removeChangeListener(logChangeListener);
			   				slider.addChangeListener(logChangeListener);
			   			}
		}

	
	
}
