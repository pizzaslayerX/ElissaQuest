import java.util.ArrayList;

public class Dungeon implements Interactive {
	public ArrayList<int[]> area;
	public ArrayList<Enemy> enemies;
	public Chest loot;
	
	public Dungeon(ArrayList<int[]> a, ArrayList<Enemy> e, Chest c) {
		area = a;
		enemies = e;
		loot = c;
	}
	@Override
	public void interact(ElissaQuest r) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		for(int[] i : area) arr[i[0]][i[1]] = null;
		arr[a][b] = loot;
	}
}