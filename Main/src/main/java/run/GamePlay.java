package run;

import java.util.ArrayList;

import entities.Enemy;
import entities.Player;
import gui.MainFightPanel;
import maze.Maze;

public class GamePlay {
    public static boolean openPanel = true;
    
	public int scale = 10;
	public Maze maze;
	public DrawScreen r;
	public String returnText = "";
	public Player player = new Player(this);
	public GamePlay(DrawScreen r) {
		this.r = r;
	}
	
	public void newFight(Enemy e) {
		r.setVisible(false);
		r.window.add(new MainFightPanel(e));
	}
	
	public void newFight(ArrayList<Enemy> e) {
		r.setVisible(false);
		r.window.add(new MainFightPanel(e));
	}
	
	public void go() {
		maze = new Maze(30, 15);
		maze.interact(this);
		maze = new Maze(40, 20);
		maze.interact(this);
		maze = new Maze(50, 25);
		maze.interact(this);
		maze = new Maze(60, 30);
		maze.interact(this);
		maze = new Maze(70, 35);
		maze.interact(this);
		maze = new Maze(80, 40);
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
}
