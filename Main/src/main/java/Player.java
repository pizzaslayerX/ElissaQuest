
public class Player {
	public ElissaQuest runner;
	private String direction;
<<<<<<< HEAD
	public int level;
	public Player() {
		level = 1;
=======
	public Player(ElissaQuest e) {
		runner = e;
>>>>>>> refs/remotes/origin/master
	}
	
	public void getMove() {
		if(!runner.returnText.isEmpty()) {
			direction = runner.returnText.get(0);
			System.out.println(direction);
			switch(direction) {
				case "up":
					ElissaQuest.y-=10;
					runner.returnText.clear();
					break;
				case "down":
					ElissaQuest.y+=10;
					runner.returnText.clear();
					break;
				case "right":
					ElissaQuest.x+=10;
					runner.returnText.clear();
					break;
				case "left":
					ElissaQuest.x-=10;
					runner.returnText.clear();
					break;
				
			}
			runner.gameplay.userWait();
			runner.canvas.repaint();
		}
		
	}
}
