package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import items.Consumable;
import misc.Pair;
import misc.Util;
import run.GamePlay;

public class Fight {
	public Player player;
	public ArrayList<Pair<Enemy,JTextPane>> enemies;
	public MainFightPanel mainFight;
	public GamePlay gameplay;
	public boolean playerTurn = true;
	public int round;
	public Timer enemyTurnDelay;
	public int enemyCount = 0;
	
	public ActionListener taskPerformer  = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	    	  if(enemyCount<mainFight.enemies.size()) {
	    		  mainFight.enemies.get(enemyCount).first.attack(player, gameplay);
	    		  mainFight.updateHealth();
	    		  System.out.println("Health Updated!");
	    		  enemyCount++;
	    	  }else {
	    		  enemyTurnDelay.stop();
	    		  mainFight.choice = 0;
	    	  		mainFight.target = 0;
	    	  		mainFight.choosing = true;
	    	  		mainFight.showMenu();
	    	  }
	      }
	  };;
	
	public Fight(Player p,ArrayList<Pair<Enemy,JTextPane>> e,MainFightPanel mf) {
		player = p;
		enemies = e;
		mainFight = mf;
		gameplay = mf.gameplay;
		round = 0;
		 enemyTurnDelay = new Timer(1000,taskPerformer);
	}
	
	public void getPlayerTurn(String action) {
		mainFight.choice = 0;
		round++;
		switch(action) {
		case "attack":
			System.out.println("success!\nTargetted #"+ mainFight.target + ": "+ mainFight.getTarget().name);
			player.attack(mainFight.getTarget(), gameplay);
			break;
		case "item":
			System.out.println("success!\nUsed #"+ mainFight.target + ": "+ player.equippedPots.first.get(mainFight.target).name);
			((Consumable) player.equippedPots.first.get(mainFight.target)).use(player);
			 player.equippedPots.first.remove(mainFight.target);
			 mainFight.updateHealth();
			break;
		}	  
		getEnemyTurn();
	}
	
	public void getEnemyTurn() {
		enemyCount = 0;
		enemyTurnDelay.start();
		
		
	}
	  public static void pause(int t)
	    {
	        try {
	            Thread.sleep(t);
	        } catch(InterruptedException ex) {
	            Thread.currentThread().interrupt();
	        }
	    }
	
}
