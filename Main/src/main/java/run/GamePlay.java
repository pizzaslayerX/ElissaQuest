package run;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import entities.Enemy;
import entities.Player;
import gui.MainFightPanel;
import maze.Maze;



 
public class GamePlay implements Runnable{
	public int scale = 2;
	public static boolean openPanel = false;  //Unnecessary; remove
	public Maze maze;
	public DrawScreen r;
	//public String returnText = " ";
	public Player player = new Player(this);
	public int blinkMode = 0;
	public CountDownLatch latch = new CountDownLatch(1);
	//public Listener listener = new Listener();
	
	
	public GamePlay(DrawScreen r) {
		this.r = r;
	}
	
	public void newFight(Enemy e) {
		r.setVisible(false);
		openPanel = true;
		r.window.add(new MainFightPanel(e,this));
	}
	
	public void newFight(ArrayList<Enemy> e) {
		r.setVisible(false);
		openPanel = true;
		r.window.add(new MainFightPanel(e,this));
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
		r.xtrans = (int)(r.window.GAME_SIZE.getWidth()/4) - player.x;
		r.ytrans = (int)(r.window.GAME_SIZE.getHeight()/4) - player.y;
		latch.countDown();
		r.repaint();
	}


	@Override
	public void run() {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
		ses.scheduleAtFixedRate(new Runnable() {public void run() {
			ScheduledExecutorService blink = Executors.newSingleThreadScheduledExecutor();
			blink.scheduleAtFixedRate(new Runnable() {public void run() {
				if(blinkMode++ >= 7) {
					blinkMode = 0;
					blink.shutdown();
				}
				r.repaint();
			}}, 0, 100, TimeUnit.MILLISECONDS);
		}}, 8, 8, TimeUnit.SECONDS);
		go();
	}
}
