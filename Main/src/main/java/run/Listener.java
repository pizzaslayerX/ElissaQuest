package run;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

public class Listener implements KeyListener {
	public final List<String> returnText = new LinkedList<String>();
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e);
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
		case KeyEvent.VK_UP:
			synchronized(returnText) {
				returnText.add("uparrow");
				returnText.notify();
			}
			break;
		case KeyEvent.VK_DOWN:
			synchronized(returnText) {
				returnText.add("downarrow");
				returnText.notify();
			}
			break;
		case KeyEvent.VK_LEFT:
			synchronized(returnText) {
				returnText.add("leftarrow");
				returnText.notify();
			}
			
			break;
		case KeyEvent.VK_RIGHT:
			synchronized(returnText) {
				returnText.add("rightarrow");
				returnText.notify();
			}
			break;
		case KeyEvent.VK_ENTER:
			synchronized(returnText) {
				returnText.add("enter");
				returnText.notify();
			}
			break;
		}
		
		
	}
		

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
