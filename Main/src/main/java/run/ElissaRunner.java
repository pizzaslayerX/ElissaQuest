package run;

import javax.swing.JApplet;

public class ElissaRunner extends JApplet {
	
	
	public ElissaRunner() {
		new Window();
	}
	
	public static void main(String args[]) {
	    java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            new ElissaRunner().setVisible(true);
	        }
	    });
	}
	

	
}
