package entities;
import run.ElissaRunner;

public class Player extends Entity{
	public ElissaRunner runner;
	private String direction;
	public int level;
		
	public Player(ElissaRunner e) {
		runner = e;
		level = 1;

	}//h
	
	
	public void getMove() {
		if(!runner.returnText.isEmpty()) {
			direction = runner.returnText.get(0);
			System.out.println(direction);
			switch(direction) {
				case "up":
					ElissaRunner.y-=10;
					runner.returnText.clear();
					break;
				case "down":
					ElissaRunner.y+=10;
					runner.returnText.clear();
					break;
				case "right":
					ElissaRunner.x+=10;
					runner.returnText.clear();
					break;
				case "left":
					ElissaRunner.x-=10;
					runner.returnText.clear();
					break;
				
			}
			runner.gameplay.userWait();
			runner.canvas.repaint();
		}
		
	}
}
