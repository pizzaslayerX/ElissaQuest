package run;

import maze.Maze;

public class GamePlay {
	public Maze maze;
	public ElissaRunner r;
	public String returnText = "";
	
	public GamePlay(ElissaRunner r) {
		this.r = r;
	}
	
	public void go() {
		maze = new Maze(30, 15);
		maze.interact(r);
		maze = new Maze(40, 20);
		maze.interact(r);
		maze = new Maze(50, 25);
		maze.interact(r);
		maze = new Maze(60, 30);
		maze.interact(r);
		maze = new Maze(70, 35);
		maze.interact(r);
		maze = new Maze(80, 40);
		maze.interact(r);
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
