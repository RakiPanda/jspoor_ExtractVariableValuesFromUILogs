package jspoor.uilog;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class Test {

	
	public static void main(String []args){
	Logger l=Logger.getInstance();
	JButton Button1=new JButton();
	l.logMouseMoves(new MouseEvent(Button1,123,123456,0,1,563,1,false));
	long t1=System.currentTimeMillis();
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,1,2563,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12346,0,2,5163,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12345,0,3,5623,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,4,2563,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12346,0,5,5163,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12345,0,7,5623,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,8,2563,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12346,0,9,5163,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12345,0,10,5623,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,1,2563,1,false));

	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,11,2563,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12346,0,12,5163,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12345,0,13,5623,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,14,2563,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12346,0,15,5163,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12345,0,17,5623,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,18,2563,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12346,0,19,5163,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12345,0,110,5623,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,11,2563,1,false));
	
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,111,2563,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12346,0,112,5163,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12345,0,113,5623,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,114,2563,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12346,0,115,5163,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12345,0,117,5623,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,118,2563,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12346,0,119,5163,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12345,0,1110,5623,1,false));
	l.logMouseMoves(new MouseEvent(Button1,123,12356,0,1112,2563,1,false));
	
	long t2=System.currentTimeMillis();
	
	System.out.println("time for 10 calls: "+(t2-t1));
	l.loggingTerminated();
	
	
	
	
	
}
	
}
