public class DungeonArea implements Interactive {
	public int[] dungeonSpot;
	public Dungeon dungeon;
	
	public DungeonArea(Dungeon d, int... spot) {
		dungeon = d;
		dungeonSpot = spot;
	}
	@Override
	public void interact(ElissaQuest r) {
		dungeon.interact(r);
	}


	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		dungeon.disappear(arr, dungeonSpot[0], dungeonSpot[1]);
	}

}
