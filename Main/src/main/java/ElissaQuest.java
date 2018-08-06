

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class ElissaQuest extends Applet implements KeyListener, Runnable, MouseListener {
	private static final long serialVersionUID = 1L;
	public static final Dimension SCREEN_SIZE = new Dimension(1270, 662);
	public static final Dimension GAME_SIZE = new Dimension(1270, 662);
	public static final String TITLE = "Elissa's Quest";
	public static final Color BACKGROUND_COLOR = Color.black;
	public static final Color FOREGROUND_COLOR = Color.white;
	public static JFrame frame;
	public static FlowLayout layout;
	public static Canvas canvas;
	public static boolean isRunning = true;
	public Player player = new Player();
	public final List<String> returnText = new LinkedList<String>();
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_A:
					synchronized(returnText) {
						returnText.add("left");
						returnText.notify();
					}
				break;
			case KeyEvent.VK_W:
					synchronized(returnText) {
						returnText.add("up");
						returnText.notify();
					}
				break;
			case KeyEvent.VK_D:
					synchronized(returnText) {
						returnText.add("right");
						returnText.notify();
					}
				break;
			case KeyEvent.VK_S:
					synchronized(returnText) {
						returnText.add("down");
						returnText.notify();
					}
				break;
			/*case KeyEvent.VK_ENTER:
				synchronized(returnText) {
					returnText.add(textIn.getText());
					returnText.notify();
				}*/
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	
	public static void main(String[] args) {
		ElissaQuest main = new ElissaQuest();
	    layout.setVgap(0);
	    frame.setResizable(false);
	    frame.setLayout(layout);
		frame.setSize(SCREEN_SIZE);
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.add(main);
		frame.setVisible(true);
		main.start();
	}
	
	public ElissaQuest() {
		setPreferredSize(SCREEN_SIZE);
		frame = new JFrame();
		layout = new FlowLayout(FlowLayout.LEADING, 0, 0);
		canvas = new Canvas();
	}
	
	public void start() {
		init();
		new Thread(this).start();
	}
	
	public void init() {
		setFocusable(true);
		//textIn.addKeyListener(this);
		//text.addMouseListener(this);
	}
	
	public void run() {
		new Thread(this).start();
	}
	
	public void pause(int i) {
		try {
			Thread.sleep(i);
		} catch(InterruptedException e) {}
	}
	
	public void stop() {
		isRunning = false;
	}
	
}
