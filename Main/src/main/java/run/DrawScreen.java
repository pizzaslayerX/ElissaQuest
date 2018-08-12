package run;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JPanel;

import entities.Enemy;
import entities.Interactive;
import misc.Util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DrawScreen extends JPanel{
	private static BufferedImage test;
	private static BufferedImage walk1;
	private static BufferedImage walk2;
	private static BufferedImage FOV;
	private static BufferedImage up;
	private static BufferedImage right;
	private static BufferedImage down;
	private static BufferedImage left;
	private static BufferedImage small;
	private static BufferedImage med;
	private static BufferedImage large;
	private static BufferedImage max;
	Action ac1;
	Action ac2;
	Action ac3;
	Action ac4;
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
		//addKeyListener(gameplay.listener);
		//gameplay.newFight(Enemy.Enemies.skeleton());


		   

		 /*
		ArrayList<Enemy> g = new ArrayList<Enemy>();
		g.add(Enemy.Enemies.skeleton());
		g.add(Enemy.Enemies.skeleton());

		gameplay.newFight(g);
		  */


		
		test = walk1 = Util.loadImage("state1.png");
		walk2 = Util.loadImage("state2.png");
		FOV = Util.loadImage("circle.png");
		up = Util.loadImage("2250x1000.png");
		left = Util.loadImage("1000x250.png");
		down = Util.loadImage("2250x1000.png");
		right = Util.loadImage("1000x250.png");
		small = Util.loadImage("EnemyLight1.png");
		med =Util.loadImage("EnemyLight2.png");
		large = Util.loadImage("EnemyLight3.png");
		max = Util.loadImage("EnemyLight4.png");

		Util.keybind(this, KeyEvent.VK_W, "up", ac1 = Util.actionMaker(u -> {
			if((((gameplay.maze.maze[gameplay.maze.playerx][gameplay.maze.playery] & 1) != 0  && gameplay.player.x % mazeSize <= mazeSize - 16)|| gameplay.player.y % mazeSize != 0) ) gameplay.player.y-=gameplay.scale;
			repaint();
			gameplay.maze.playerx=(gameplay.player.x+mazeSize/2)/mazeSize;
			gameplay.maze.playery=(gameplay.player.y+mazeSize/2)/mazeSize;
			if(gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery] != null) {
				disable();
				gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery].interact(gameplay);
				gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery].disappear(gameplay.maze.interactives, gameplay.maze.playerx, gameplay.maze.playery);
				System.out.println("test");
			}
		}));
		Util.keybind(this, KeyEvent.VK_S, "down", ac2 = Util.actionMaker(u -> {
			if((((gameplay.maze.maze[gameplay.maze.playerx][gameplay.maze.playery] & 4) != 0  && gameplay.player.x % mazeSize <= mazeSize - 16)|| gameplay.player.y % mazeSize != mazeSize - 16) ) gameplay.player.y+=gameplay.scale;
			repaint();
			gameplay.maze.playerx=(gameplay.player.x+mazeSize/2)/mazeSize;
			gameplay.maze.playery=(gameplay.player.y+mazeSize/2)/mazeSize;
			if(gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery] != null) {
				disable();
				gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery].interact(gameplay);
				gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery].disappear(gameplay.maze.interactives, gameplay.maze.playerx, gameplay.maze.playery);
				System.out.println("test");
			}
		}));
		Util.keybind(this, KeyEvent.VK_D, "right", ac3 = Util.actionMaker(u -> {
			if((((gameplay.maze.maze[gameplay.maze.playerx][gameplay.maze.playery] & 2) != 0 && gameplay.player.y % mazeSize <= mazeSize - 16)|| gameplay.player.x %mazeSize != mazeSize - 16) ) gameplay.player.x+=gameplay.scale;
			repaint();
			gameplay.maze.playerx=(gameplay.player.x+mazeSize/2)/mazeSize;
			gameplay.maze.playery=(gameplay.player.y+mazeSize/2)/mazeSize;
			if(gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery] != null) {
				disable();
				gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery].interact(gameplay);
				gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery].disappear(gameplay.maze.interactives, gameplay.maze.playerx, gameplay.maze.playery);
				System.out.println("test");
			}
		}));
		Util.keybind(this, KeyEvent.VK_A, "left", ac4 = Util.actionMaker(u -> {
			if((((gameplay.maze.maze[gameplay.maze.playerx][gameplay.maze.playery] & 8) != 0 && gameplay.player.y % mazeSize <= mazeSize - 16)|| gameplay.player.x %mazeSize != 0) )gameplay.player.x-=gameplay.scale;
			repaint();
			gameplay.maze.playerx=(gameplay.player.x+mazeSize/2)/mazeSize;
			gameplay.maze.playery=(gameplay.player.y+mazeSize/2)/mazeSize;
			if(gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery] != null) {
				disable();
				gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery].interact(gameplay);
				gameplay.maze.interactives[gameplay.maze.playerx][gameplay.maze.playery].disappear(gameplay.maze.interactives, gameplay.maze.playerx, gameplay.maze.playery);
				System.out.println("test");
			}
		}));
		Util.keybind(this, KeyEvent.VK_UP, "uparrow", u -> {ytrans += 4;
		repaint();});
		Util.keybind(this, KeyEvent.VK_DOWN, "downarrow", u -> {ytrans -= 4;
		repaint();});
		Util.keybind(this, KeyEvent.VK_RIGHT, "rightarrow", u -> {xtrans -= 4;
		repaint();});
		Util.keybind(this, KeyEvent.VK_LEFT, "leftarrow", u -> {xtrans += 4;
		repaint();});
	}
	
	public void disable() {
		ac1.setEnabled(false);
		ac2.setEnabled(false);
		ac3.setEnabled(false);
		ac4.setEnabled(false);
	}

	public void enable() {
		ac1.setEnabled(true);
		ac2.setEnabled(true);
		ac3.setEnabled(true);
		ac4.setEnabled(true);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		if(moveVal < 4)
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
		try {
			gameplay.latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < gameplay.maze.x; i++) for(int j = 0; j < gameplay.maze.y; j++) {
			g.drawRect(-1, -1, mazeSize*gameplay.maze.x, 1);
			g.drawRect(-1, -1, 1, mazeSize*gameplay.maze.y);
			boolean boolc = (~gameplay.maze.maze[i][j] & 4) != 0;
			boolean boolb = (~gameplay.maze.maze[i][j] & 2) != 0;
			if(boolb) g.drawRect((i+1)*mazeSize-1, j*mazeSize-1, 1, mazeSize+1);
			if(boolc) g.drawRect(i*mazeSize-1, (j+1)*mazeSize-1, mazeSize+1, 1);
			if(gameplay.maze.interactives[i][j] instanceof Interactive) switch(gameplay.blinkMode) {
				case 1:
					g.drawImage(small, i*mazeSize+4, j*mazeSize+4, this);
					break;
				case 2:
					g.drawImage(med, i*mazeSize+4, j*mazeSize+4, this);
					break;
				case 3:
					g.drawImage(large, i*mazeSize+4, j*mazeSize+4, this);
					break;
				case 4:
					g.drawImage(max, i*mazeSize+4, j*mazeSize+4, this);
					break;
				case 5:
					g.drawImage(large, i*mazeSize+4, j*mazeSize+4, this);
					break;
				case 6:
					g.drawImage(med, i*mazeSize+4, j*mazeSize+4, this);
					break;
				case 7:
					g.drawImage(small, i*mazeSize+4, j*mazeSize+4, this);
					break;
			}
		}
		g.drawImage(FOV, gameplay.player.x-117, gameplay.player.y-117, this);
		g.drawImage(left, gameplay.player.x-1117, gameplay.player.y-117, this);
		g.drawImage(right, gameplay.player.x+118, gameplay.player.y-117, this);
		g.drawImage(up,  gameplay.player.x-1117, gameplay.player.y-1117, this);
		g.drawImage(down, gameplay.player.x-1117, gameplay.player.y+118, this);
	}
	
	private void animate() {
		state1 = !state1;
		if(!state1) {
			test = walk2;
			return;
		}
		test = walk1;
	}

	
}
