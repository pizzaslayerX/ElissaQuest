package run;
import maze.AdvancedMaze;

public class GamePlay {
	public AdvancedMaze maze;
	public ElissaRunner r;
	public String returnText = "";
	
	public GamePlay(ElissaRunner r) {
		this.r = r;
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
