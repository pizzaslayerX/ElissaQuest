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

}
