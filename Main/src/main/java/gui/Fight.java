package gui;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import misc.Pair;
import run.GamePlay;

public class Fight {
	public Player player;
	public ArrayList<Pair<Enemy,JTextPane>> enemies;
	public MainFightPanel mainFight;
	public GamePlay gameplay;
	public boolean playerTurn = true;
	
	public Fight(Player p,ArrayList<Pair<Enemy,JTextPane>> e,MainFightPanel mf) {
		player = p;
		enemies = e;
		mainFight = mf;
		gameplay = mf.gameplay;
	}
	
	public void getPlayerTurn(int action) {
		switch(action) {
		case 0:
			System.out.println("success!\nTargetted #"+ mainFight.target + ": "+ mainFight.getTarget().name);
			player.attack(mainFight.getTarget(), gameplay);
			break;
		}
	}
	
	
}
