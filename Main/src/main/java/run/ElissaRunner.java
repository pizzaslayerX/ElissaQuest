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
	/*
	
	public static void main(String[] args) {
		ElissaRunner main = new ElissaRunner();
		
		frame = new JFrame();
		layout = new FlowLayout(FlowLayout.LEADING, 0, 0);
		canvas = new Canvas();
		
		canvas.setSize(SCREEN_SIZE);
	    layout.setVgap(0);
	    frame.setResizable(false);
	    frame.setLayout(layout);
		frame.setSize(SCREEN_SIZE);
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//wwijw
		canvas.setPreferredSize(GAME_SIZE);
        canvas.setMaximumSize(GAME_SIZE);
        canvas.setMinimumSize(GAME_SIZE);
		frame.add(canvas);
		frame.add(main);
		frame.setVisible(true);
		frame.pack();
		main.start();

	}
	*/

	
}
