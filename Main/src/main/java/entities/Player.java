package entities;
import run.GamePlay;

public class Player extends Entity{
	public GamePlay runner;
	private String direction;
	public int level;
	public int x;
	public int y;
	public Player(GamePlay d) {
		runner = d;
		level = 1;

	}//h
	
	
	/*public void getMove() {
		if(!runner.returnText.isEmpty()) {
			direction = runner.r.returnText.get(0);
			System.out.println(direction);
			switch(direction) {
				case "up":
					y-=10;
					runner.r.returnText.clear();
					break;
				case "down":
					y+=10;
					runner.r.returnText.clear();
					break;
				case "right":
					x+=10;
					runner.r.returnText.clear();
					break;
				case "left":
					x-=10;
					runner.r.returnText.clear();
					break;
				
			}
			runner.userWait();
			runner.r.returnText.clear();
		}
		
	}*/
}
