
public class AdvancedMaze {
	public Maze maze;
	public boolean[][] traveled;
	
	public AdvancedMaze(Maze m) {
		maze = m;
		traveled = new boolean[m.x][m.y];
	}
}
