package entities;
import run.DrawScreen;
import run.ElissaRunner;

public class Player {
	public DrawScreen runner;
	private String direction;
	public int level;
	public int x;
	public int y;
	public Player(DrawScreen d) {
		runner = d;
		level = 1;

	}//h
	
	
	public void getMove() {
		if(!runner.returnText.isEmpty()) {
			direction = runner.returnText.get(0);
			System.out.println(direction);
			switch(direction) {
				case "up":
					y-=10;
					runner.returnText.clear();
					break;
				case "down":
					y+=10;
					runner.returnText.clear();
					break;
				case "right":
					x+=10;
					runner.returnText.clear();
					break;
				case "left":
					x-=10;
					runner.returnText.clear();
					break;
				
			}
			runner.gameplay.userWait();
			runner.returnText.clear();
		}
		
	}
}
