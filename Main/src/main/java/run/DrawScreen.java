package run;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entities.Enemy;
import entities.Player;
import gui.MainFightPanel;

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
	private static BufferedImage FOV;
	private static BufferedImage up;
	private static BufferedImage right;
	private static BufferedImage down;
	private static BufferedImage left;
	public GamePlay gameplay = new GamePlay(this);
	public Window window;
	private static int moveVal = 0;
	private static boolean state1 = true;
	public int mazeSize = 24;

	public int xtrans;
	public int ytrans;
	public DrawScreen(Window w) {
		window = w;
		init();
		new Thread(gameplay).start();
	}
	
	public void init() {
		setPreferredSize(Window.GAME_SIZE);
		setBackground(Window.BACKGROUND_COLOR);
		setForeground(Window.FOREGROUND_COLOR);
		setVisible(true);
		setFocusable(true);
		setDoubleBuffered(true);
		addKeyListener(this);
		//gameplay.newFight(new Enemy());
		
		loadImage("state1.png");
		loadImage2("circle.png");
		loadImage3("2250x1000.png");
		loadImage4("1000x250.png");
		loadImage5("2250x1000.png");
		loadImage6("1000x250.png");
	}
	
	 private static void loadImage(String fileName){

	            try {
	                test = ImageIO.read(new File("src/res/pics/"+fileName)); 

	            } catch (IOException e) {
	                e.printStackTrace();
	                System.out.println("Image could not be read");
	                System.exit(1);
	            }
	        }
	 private static void loadImage2(String fileName){

         try {
             FOV = ImageIO.read(new File("src/res/pics/"+fileName)); 

         } catch (IOException e) {
             e.printStackTrace();
             System.out.println("Image could not be read");
             System.exit(1);
         }
     }
	 
	 private static void loadImage3(String fileName){

         try {
             up = ImageIO.read(new File("src/res/pics/"+fileName)); 

         } catch (IOException e) {
             e.printStackTrace();
             System.out.println("Image could not be read");
             System.exit(1);
         }
     }
	 
	 private static void loadImage4(String fileName){

         try {
             right = ImageIO.read(new File("src/res/pics/"+fileName)); 

         } catch (IOException e) {
             e.printStackTrace();
             System.out.println("Image could not be read");
             System.exit(1);
         }
     }
	 private static void loadImage5(String fileName){

         try {
             down = ImageIO.read(new File("src/res/pics/"+fileName)); 

         } catch (IOException e) {
             e.printStackTrace();
             System.out.println("Image could not be read");
             System.exit(1);
         }
     }
	 private static void loadImage6(String fileName){

         try {
             left = ImageIO.read(new File("src/res/pics/"+fileName)); 

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
		g2.scale(2, 2);
		//g2.translate(-this.getWidth()/2, this.getHeight()/2);
		super.paintComponent(g2);
		drawObjects(g2);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics g) {
		g.translate(xtrans, ytrans);
		g.drawImage(test,gameplay.player.x,gameplay.player.y,this);
		g.setColor(Color.WHITE);
		for(int i = 0; i < gameplay.maze.x; i++) for(int j = 0; j < gameplay.maze.y; j++) {
			g.drawRect(-1, -1, mazeSize*gameplay.maze.x, 1);
			g.drawRect(-1, -1, 1, mazeSize*gameplay.maze.y);
			boolean boolc = (~gameplay.maze.maze[i][j] & 4) != 0;
			boolean boolb = (~gameplay.maze.maze[i][j] & 2) != 0;
			//if(boola) g.drawRect(i*mazeSize, j*mazeSize, mazeSize-1, 0);
			if(boolb) g.drawRect((i+1)*mazeSize-1, j*mazeSize-1, 1, mazeSize+1);
			if(boolc) g.drawRect(i*mazeSize-1, (j+1)*mazeSize-1, mazeSize+1, 1);
			//if(boold) g.drawRect(i*mazeSize, j*mazeSize, 0, mazeSize-1);
			/*if(boola && boolb && j > 0) g.drawRect(i*mazeSize+mazeSize,j*mazeSize-1,0,0);
			if(boolb && boolc) g.drawRect(i*mazeSize+mazeSize,j*mazeSize+mazeSize,0,0);
			if(boolc && boold && i > 0) g.drawRect(i*mazeSize-1,j*mazeSize+mazeSize,0,0);
			if(boold && boola && j > 0 && i >0) g.drawRect(i*mazeSize-1,j*mazeSize-1,0,0);*/
		}
		g.drawImage(FOV, gameplay.player.x-117, gameplay.player.y-117, this);
		g.drawImage(left, gameplay.player.x-1117, gameplay.player.y-117, this);
		g.drawImage(right, gameplay.player.x+118, gameplay.player.y-117, this);
		g.drawImage(up,  gameplay.player.x-1117, gameplay.player.y-1117, this);
		g.drawImage(down, gameplay.player.x-1117, gameplay.player.y+118, this);
		//g.drawRect(gameplay.player.x-1118, 0, 2000, 2000);
	}
	
	private void animate() {
		state1 = !state1;
		if(!state1) {
			loadImage("state2.png");
			return;
		}
		loadImage("state1.png");
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
	  //if(!GamePlay.openPanel) {
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
				ytrans += 4;
				repaint();
				break;
			case KeyEvent.VK_DOWN:
				ytrans -=4;
				repaint();
				break;
			case KeyEvent.VK_LEFT:
				xtrans += 4;
				repaint();
				break;
			case KeyEvent.VK_RIGHT:
				xtrans -= 4;
				repaint();
				break;
			/*case KeyEvent.VK_ENTER:
				synchronized(returnText) {
					returnText.add(textIn.getText());
					returnText.notify();
				}*/
		//}
		//repaint();
	  }
	  

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		
	}
}
