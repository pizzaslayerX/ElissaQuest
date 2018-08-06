package run;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import entities.Enemy;
import entities.Player;
import gui.MainFightPanel;
import maze.Maze;



 
public class GamePlay implements Runnable{
	public int scale = 2;
	  public static boolean openPanel = true;
	public Maze maze;
	public DrawScreen r;
	public String returnText = " ";
	public Player player = new Player(this);
	public int blinkMode = 0;
	
	
	public GamePlay(DrawScreen r) {
		this.r = r;
	}
	
	public void newFight(Enemy e) {
		r.setVisible(false);
		r.window.add(new MainFightPanel(e,this));
	}
	
	public void newFight(ArrayList<Enemy> e) {
		r.setVisible(false);
		r.window.add(new MainFightPanel(e,this));
	}
	
	public void go() {
		System.out.println("go");
		for(int i = 0; i < 6; i++) newMaze();
	}

	public void newMaze() {
		if(maze == null) maze = new Maze(30, 15);
		else maze = new Maze(maze.x + 10, maze.y + 5);
		player.x = maze.startx*r.mazeSize;
		player.y = maze.starty*r.mazeSize;
		r.xtrans = (int)(r.window.GAME_SIZE.getWidth()/4) - player.x;
		r.ytrans = (int)(r.window.GAME_SIZE.getHeight()/4) - player.y;
		r.repaint();
		maze.interact(this);
	}
	
	public void userWait() {
		synchronized(r.returnText) {
			while(r.returnText.isEmpty()) {
	        	try {
	        		r.returnText.wait();
	        	} catch (InterruptedException e) {}
	    	}
			returnText = r.returnText.remove(0);
		}
	}

	@Override
	public void run() {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
		ses.scheduleAtFixedRate(new Runnable() {public void run() {
			final int[] count = new int[]{0};
			ScheduledExecutorService blink = Executors.newSingleThreadScheduledExecutor();
			blink.scheduleAtFixedRate(new Runnable() {public void run() {
				blinkMode++;
				r.repaint();
				count[0]++;
				if(count[0] >= 7) blink.shutdown();
			}}, 0, 250, TimeUnit.MILLISECONDS);
			blinkMode = 0;
		}}, 8, 8, TimeUnit.SECONDS);
		go();
	}
}
