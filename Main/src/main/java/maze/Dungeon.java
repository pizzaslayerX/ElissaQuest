package maze;
import java.util.ArrayList;

import entities.Enemy;
import entities.Enemy.Enemies;
import entities.Interactive;
import run.GamePlay;

public class Dungeon implements Interactive {
	public ArrayList<int[]> area;
	public ArrayList<Enemy> enemies;
	public Chest loot;
	
	public Dungeon(ArrayList<int[]> a, ArrayList<Enemy> e, Chest c) {
		
		area = a;
		//enemies = e;
		enemies = new ArrayList<Enemy>();
		for(int i=0;i<3;i++)
		enemies.add(Enemies.skeleton());
		enemies.add(Enemies.manEatingDeer());
		enemies.add(Enemies.manEatingDeer());
		loot = c;
	}
	@Override
	public void interact(GamePlay r) {
		r.newFight(enemies);
	}

	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		for(int[] i : area) arr[i[0]][i[1]] = null;
		arr[a][b] = loot;
	}
}