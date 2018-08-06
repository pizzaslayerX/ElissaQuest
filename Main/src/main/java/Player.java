
public class Player {
	public ElissaQuest runner;
	private String direction;
	public int level;
	public Player() {
		level = 1;
	}
	
	public void getMove() {
		if(!runner.returnText.isEmpty()) {
			direction = runner.returnText.get(0);
			switch(direction) {
				case "up":
					ElissaQuest.y++;
					break;
				case "down":
					ElissaQuest.y--;
					break;
				case "right":
					ElissaQuest.x++;
					break;
				case "left":
					ElissaQuest.x--;
					break;
			}
		}
	}
}
