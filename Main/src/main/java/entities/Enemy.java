package entities;

import run.GamePlay;

public class Enemy extends Entity implements Interactive{
	protected String pic;
	
	@Override
	public void interact(GamePlay r) {
		r.newFight(this);
	}

	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		
	}
	
	public String getPic() {
		return pic;
	}
	
	public void changePic(String p) {
		//NYI
	}
	
}
