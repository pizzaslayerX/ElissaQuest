
public class Player {
	public ElissaQuest runner;
	private String direction;
	public Player() {
		
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
