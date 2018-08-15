package run;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import entities.Enemy;
import entities.Player;
import gui.MainFightPanel;
import maze.Maze;
import misc.Util;



 
public class GamePlay implements Runnable{
	public int scale = 1;
	public static boolean openPanel = false;  
	public Maze maze;
	public DrawScreen r;
	public Player player = new Player(this);
	public MainFightPanel mfp;
	public int blinkMode = 0;
	public CountDownLatch latch = new CountDownLatch(1);
	ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
	ScheduledFuture<?> blink;
	boolean[] move = new boolean[8];
	
	
	public GamePlay(DrawScreen r) {
		this.r = r;
	}
	
	public void newFight(Enemy e) {
		r.setVisible(false);
		openPanel = true;
		r.window.add(mfp = new MainFightPanel(e,this));
	}
	
	public void newFight(ArrayList<Enemy> e) {
		r.setVisible(false);
		openPanel = true;
		r.window.add(mfp = new MainFightPanel(e,this));
	}
	
	public void go() {
		System.out.println("go");
		for(int i = 0; i < 1 /*6*/; i++) newMaze();
	}
//have bosses call this method when they disappear
	public void newMaze() {
		if(maze == null) maze = new Maze(30, 15);
		else maze = new Maze(maze.x + 10, maze.y + 5);
		player.x = maze.startx*r.mazeSize;
		player.y = maze.starty*r.mazeSize;
		r.xtrans = (int)(r.window.GAME_SIZE.getWidth()/4) - player.x - 8;
		r.ytrans = (int)(r.window.GAME_SIZE.getHeight()/4) - player.y - 8;
		latch.countDown();
		r.repaint();
	}


	@Override
	public void run() {
		ses.scheduleAtFixedRate(() -> {
			if(!openPanel) blink = ses.scheduleAtFixedRate(Util.guiRunnable(() -> {
				if(blinkMode++ >= 7) {
					blinkMode = 0;
					r.repaint();
					maze.enemyMove();
					blink.cancel(false);
				}
				r.repaint();
			}), 0, 100, TimeUnit.MILLISECONDS);
		}, 8, 8, TimeUnit.SECONDS);
		ses.scheduleAtFixedRate(Util.guiRunnable(() -> {
			boolean change = false;
			boolean animate = false;
			if(move[0]&& (((maze.maze[maze.playerx][maze.playery] & 1) != 0  && player.x % r.mazeSize <= r.mazeSize - 16)|| player.y % r.mazeSize != 0) ) {
				player.y-=scale;
				r.ytrans += scale;
				change = true;
				animate = true;
			}
			if(move[1]&& (((maze.maze[maze.playerx][maze.playery] & 4) != 0  && player.x % r.mazeSize <= r.mazeSize - 16)|| player.y % r.mazeSize != r.mazeSize - 16) ) {
				player.y+=scale;
				r.ytrans -= scale;
				change = true;
				animate = true;
			}
			if(move[2]&& (((maze.maze[maze.playerx][maze.playery] & 2) != 0 && player.y % r.mazeSize <= r.mazeSize - 16)|| player.x %r.mazeSize != r.mazeSize - 16) ) {
				player.x+=scale;
				r.xtrans -= scale;
				change = true;
				animate = true;
			}
			if(move[3]&& (((maze.maze[maze.playerx][maze.playery] & 8) != 0 && player.y % r.mazeSize <= r.mazeSize - 16)|| player.x %r.mazeSize != 0) ) {
				player.x-=scale;
				r.xtrans += scale;
				change = true;
				animate = true;
			}
			if(move[4]) {
				r.ytrans += 1;
				change = true;
			}
			if(move[5]) {
				r.ytrans -= 1;
				change = true;
			}
			if(move[6]) {
				r.xtrans -= 1;
				change = true;
			}
			if(move[7]) {
				r.xtrans += 1;
				change = true;
			}
			if(change) {
				if(animate) r.repaintMove();
				else r.repaint();
				maze.playerx=(player.x+r.mazeSize/2)/r.mazeSize;
				maze.playery=(player.y+r.mazeSize/2)/r.mazeSize;
				if(maze.interactives[maze.playerx][maze.playery] != null) {
					r.disable();
					maze.interactives[maze.playerx][maze.playery].interact(GamePlay.this);
					maze.interactives[maze.playerx][maze.playery].disappear(maze.interactives, maze.playerx, maze.playery);
					System.out.println("test");
				}
			}
		}), 0, 20 /*40*/, TimeUnit.MILLISECONDS);
		go();
	}
}
