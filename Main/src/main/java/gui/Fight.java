package gui;

import java.util.ArrayList;

import javax.swing.JPanel;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import misc.Pair;
import run.GamePlay;

public class Fight {
	public Player player;
	public ArrayList<Pair<Enemy,JPanel>> enemies;
	public MainFightPanel mainFight;
	public GamePlay gameplay;
	public boolean playerTurn = true;
	
	public Fight(Player p,ArrayList<Pair<Enemy,JPanel>> e,MainFightPanel mf) {
		player = p;
		enemies = e;
		mainFight = mf;
		gameplay = mf.gameplay;
	}
	
	public void getPlayerTurn(int action) {
		switch(action) {
		case 1:
			player.attack(mainFight.getTarget(), gameplay);
		}
	}
	
	
}
