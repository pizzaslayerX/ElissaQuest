package run;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entities.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DrawScreen extends JPanel implements KeyListener{
	public final List<String> returnText = new LinkedList<String>();
	private static BufferedImage test;
	public GamePlay gameplay = new GamePlay(this);
	private static int moveVal = 0;
	private static boolean state1 = true;
	public DrawScreen() {
		init();
	}
	
	public void init() {
		setPreferredSize(Window.GAME_SIZE);
		setBackground(Window.BACKGROUND_COLOR);
		setForeground(Window.FOREGROUND_COLOR);
		setVisible(true);
		setFocusable(true);
		setDoubleBuffered(true);
		addKeyListener(this);
		
		loadImage("src/res/pics/state1.png");
	}
	
	 private static void loadImage(String fileName){

	            try {
	                test = ImageIO.read(new File(fileName)); 

	            } catch (IOException e) {
	                e.printStackTrace();
	                System.out.println("Image could not be read");
	                System.exit(1);
	            }
	        }
	    
	
	@Override
    public void paintComponent(Graphics g) {
		if(moveVal < 5)
			moveVal++;
		else {
			moveVal = 0;
			animate();
		}
		Graphics2D g2 = (Graphics2D) g;
		//g2.translate(this.getWidth()/2, this.getHeight()/2);
		g2.scale(3, 3);
		//g2.translate(-this.getWidth()/2, this.getHeight()/2);
		super.paintComponent(g2);
		drawObjects(g2);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics g) {
		g.drawImage(test,gameplay.player.x,gameplay.player.y,this);
	}
	
	private void animate() {
		state1 = !state1;
		if(!state1) {
			loadImage("src/res/pics/state2.png");
			return;
		}
		loadImage("src/res/pics/state1.png");
	}

	/*public void getMove() {

		if(!returnText.isEmpty()) {
			String direction = returnText.get(0);
			System.out.println(direction);
			switch(direction) {
				case "up":
					gameplay.player.y-=2;
					returnText.clear();
					break;
				case "down":
					gameplay.player.y+=2;
					returnText.clear();
					break;
				case "right":
					gameplay.player.x+=2;
					returnText.clear();
					break;
				case "left":
					gameplay.player.x-=2;
					returnText.clear();
					break;
				
			}

		}
		
	}*/
	@Override
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
		//getMove();
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		
	}
}
