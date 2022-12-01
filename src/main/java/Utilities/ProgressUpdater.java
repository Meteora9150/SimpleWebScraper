package Utilities;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;

public class ProgressUpdater implements Runnable {

	//boolean end=false;
	static JFrame f= new JFrame("Please wait while it ends");
	static JProgressBar barr;
	static boolean active=false;
	
	@Override
	public void run() {

		f.setUndecorated(true);
	    barr = new JProgressBar();
	    barr.setValue(0);
	    barr.setStringPainted(true);
	    JLabel  l = new JLabel("Please wait for the bar completitoion");
	    JLabel  l2 = new JLabel("If it looks stuck don't worry it is still loading");
	    JPanel p = new JPanel();
	    p.add(l);
	    p.add(barr);
	    p.add(l2);
	    f.add(p);
	    f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
	    f.setSize(300, 100);
	    f.setLocationRelativeTo(null);
	    f.show();
	    active=true;
	}
	
	public static void updateBarr(int val) {
		barr.setValue(val);
		if(barr.getValue()==100) {
			closewindow();
		}
	}
	
	public static void closewindow() {
		barr.setValue(0);
		active=false;
		f.dispose();
	}

	public static boolean isStillOn() {
		return active;
	}

}
