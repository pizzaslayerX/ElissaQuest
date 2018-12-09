package run;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JPanel;

import entities.Enemy;
import entities.Interactive;
import gui.LootScreen;
import misc.Util;

import java.awt.Color;
import java.awt.Font;
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
	private static BufferedImage walk1UP,walk1DOWN,walk1RIGHT,walk1LEFT;
	private static BufferedImage walk2UP,walk2DOWN,walk2RIGHT,walk2LEFT;
	private static BufferedImage FOV;
	private static BufferedImage up;
	private static BufferedImage right;
	private static BufferedImage down;
	private static BufferedImage left;
	private static BufferedImage small;
	private static BufferedImage med;
	private static BufferedImage large;
	private static BufferedImage max;
	public static BufferedImage chest;
	public Action[] ac = new Action[16];
	public GamePlay gameplay = new GamePlay(this);
	public LootScreen lootScreen;
	public Window window;
	private static int moveVal = 0;
	public int direction = 0; //0=UP,1=DOWN,2=RIGHT,3=LEFT
	private static boolean state1 = true;
	public int mazeSize = 24;
	public boolean gameOver = false;
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


		
		test = walk1UP = Util.loadImage("state1.png");
		walk2UP = Util.loadImage("state2.png");
		walk1DOWN = Util.loadImage("state1Back.png");
		walk2DOWN = Util.loadImage("state2Back.png");
		walk1RIGHT = Util.loadImage("state1Right.png");
		walk2RIGHT = Util.loadImage("state2Right.png");
		walk1LEFT = Util.loadImage("state1Leftt.png");
		walk2LEFT = Util.loadImage("state2Left.png");
		chest = Util.loadImage("Chest.png");
		
		FOV = Util.loadImage("circle.png");
		up = Util.loadImage("2250x1000.png");
		left = Util.loadImage("1000x250.png");
		down = Util.loadImage("2250x1000.png");
		right = Util.loadImage("1000x250.png");
		small = Util.loadImage("EnemyLight1.png");
		med =Util.loadImage("EnemyLight2.png");
		large = Util.loadImage("EnemyLight3.png");
		max = Util.loadImage("EnemyLight4.png");

		Util.keybind(this, KeyEvent.VK_W, "up", ac[0] = Util.actionMaker(u -> {gameplay.move[0] = true;}));
		Util.keybind(this, KeyEvent.VK_S, "down", ac[1] = Util.actionMaker(u -> {gameplay.move[1] = true;}));
		Util.keybind(this, KeyEvent.VK_D, "right", ac[2] = Util.actionMaker(u -> {gameplay.move[2] = true;}));
		Util.keybind(this, KeyEvent.VK_A, "left", ac[3] = Util.actionMaker(u -> {gameplay.move[3] = true;}));
		Util.keybind(this, KeyEvent.VK_UP, "uparrow", ac[4] = Util.actionMaker(u -> {gameplay.move[4] = true;}));
		Util.keybind(this, KeyEvent.VK_DOWN, "downarrow", ac[5] = Util.actionMaker(u -> {gameplay.move[5] = true;}));
		Util.keybind(this, KeyEvent.VK_RIGHT, "rightarrow", ac[6] =Util.actionMaker(u -> {gameplay.move[6] = true;}));
		Util.keybind(this, KeyEvent.VK_LEFT, "leftarrow", ac[7] = Util.actionMaker(u -> {gameplay.move[7] = true;}));
		Util.keybind(this, KeyEvent.VK_W, "up1", ac[8] = Util.actionMaker(u -> {gameplay.move[0] = false;}), true);
		Util.keybind(this, KeyEvent.VK_S, "down1", ac[9] = Util.actionMaker(u -> {gameplay.move[1] = false;}), true);
		Util.keybind(this, KeyEvent.VK_D, "right1", ac[10] = Util.actionMaker(u -> {gameplay.move[2] = false;}), true);
		Util.keybind(this, KeyEvent.VK_A, "left1", ac[11] = Util.actionMaker(u -> {gameplay.move[3] = false;}), true);
		Util.keybind(this, KeyEvent.VK_UP, "uparrow1", ac[12] = Util.actionMaker(u -> {gameplay.move[4] = false;}),true);
		Util.keybind(this, KeyEvent.VK_DOWN, "downarrow1", ac[13] = Util.actionMaker(u -> {gameplay.move[5] = false;}),true);
		Util.keybind(this, KeyEvent.VK_RIGHT, "rightarrow1", ac[14] = Util.actionMaker(u -> {gameplay.move[6] = false;}),true);
		Util.keybind(this, KeyEvent.VK_LEFT, "leftarrow1", ac[15] = Util.actionMaker(u -> {gameplay.move[7] = false;}),true);
		
		lootScreen = new LootScreen(gameplay,window.getWidth(),window.getHeight());
		lootScreen.setVisible(false);
		window.add(lootScreen);
	}
	
	public void disable() {
		for(Action a : ac) a.setEnabled(false);
		gameplay.move = new boolean[8];
	}

	public void enable() {
		for(Action a : ac) a.setEnabled(true);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//g2.translate(this.getWidth()/2, this.getHeight()/2);
		g2.scale(2, 2);
		//g2.translate(-this.getWidth()/2, this.getHeight()/2);
		super.paintComponent(g2);
		drawObjects(g2);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void repaintMove() {
		moveVal++;
		if((moveVal %= 6 ) == 0) animate();
		repaint();
	}
	
	private void drawObjects(Graphics g) {
		if(!gameOver) {
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
	   }else {
		   	g.setColor(Color.RED);
			g.setFont(new Font("Monospaced", Font.BOLD, 50));
			g.drawString("GAME OVER", 185,160);
	   }
	}
	
	public BufferedImage getMov(int state) {
		if(state == 1)
		switch(direction) {
			case 0:
				return walk1UP;
			case 1:
				return walk1DOWN;
			case 2:
				return walk1RIGHT;
			case 3:
				return walk1LEFT;
		}
		else {
			switch(direction) {
			case 0:
				return walk2UP;
			case 1:
				return walk2DOWN;
			case 2:
				return walk2RIGHT;
			case 3:
				return walk2LEFT;
		 }
		}
		return walk1UP;
	}
	
	private void animate() {
		state1 = !state1;
		if(!state1) {
			test = getMov(2);
			return;
		}
		test = getMov(1);
	}

	
}
