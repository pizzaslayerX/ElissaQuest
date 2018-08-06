
public class GamePlay {
	public AdvancedMaze maze;
	public ElissaQuest r;
	public String returnText = "";
	
	public void userWait() {
		synchronized(r.returnText) {
			while(r.returnText.isEmpty()) {
	        	try {
	        		r.returnText.wait();
	        	} catch (InterruptedException e) {}
	    	}
			returnText = r.returnText.remove(0);
		}//
		//DAS
	}
}
