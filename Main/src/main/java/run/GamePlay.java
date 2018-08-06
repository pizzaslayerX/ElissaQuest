package run;

import entities.Player;
import maze.Maze;

public class GamePlay {
	public Maze maze;
	public DrawScreen r;
	public String returnText = "";
	public Player player = new Player(this);
	
	public GamePlay(DrawScreen r) {
		this.r = r;
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
