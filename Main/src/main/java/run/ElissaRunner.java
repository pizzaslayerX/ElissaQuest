package run;

import java.util.concurrent.CountDownLatch;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;

public class ElissaRunner extends JApplet {
	
	
	public ElissaRunner() {
		new Window();
	}
	
	public static void main(String args[]) {
	    java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	 
	        	        new JFXPanel(); // initializes JavaFX environment
	        	        
	            new ElissaRunner().setVisible(true);
	        }
	    });
	}
	

	
}
