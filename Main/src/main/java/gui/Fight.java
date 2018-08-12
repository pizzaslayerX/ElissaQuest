package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import items.Consumable;
import items.StatusEffect;
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
	public int enemyCount = 0, delay = 1500;
	public String paction = "";
	
	public ActionListener taskPerformer  = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	    	 if(mainFight.enemies.size()>0) {
	    	  if(enemyCount<mainFight.enemies.size()) {
	    		  try {
	    				mainFight.changePic(mainFight.enemyPics.get(enemyCount));
	    			} catch (IOException e1) {	}
	    			mainFight.enemy.setBorder(mainFight.genBorder(enemies.get(enemyCount).first.name,1));
	    			mainFight.healthFocus(enemyCount);
	    		  if(enemyTurnDelay.getDelay()!= delay)
	    			  enemyTurnDelay.setDelay(delay);
	    		  mainFight.enemies.get(enemyCount).first.attack(player, gameplay);
	    		  mainFight.updateHealth();
	    		  System.out.println("Health Updated!");
	    		  try {
						mainFight.changePic(mainFight.enemyPics.get(enemyCount));
						mainFight.enemy.setBorder(mainFight.genBorder(enemies.get(enemyCount).first.name,1));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		  enemyCount++;
	    	  }else {
	    		  	enemyTurnDelay.stop();
	    		  	mainFight.choice = 0;
	    	  		mainFight.target = 0;
	    	  		mainFight.choosing = true;
	    	  		mainFight.enemy.setBorder(mainFight.genBorder(enemies.get(0).first.name,1));
					mainFight.healthFocus(0);
					
						try {
							mainFight.changePic(mainFight.enemyPics.get(0));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    	  		mainFight.showMenu();
	    	  		player.regen();
	    	  		for(Pair<Enemy,JTextPane> e:mainFight.enemies)
	    	  			e.first.regen();
	    	  		mainFight.updateHealth();
	    	  		StatusEffect.checkEffects(player);
	    	  		mainFight.updateHealth();
	    	  }
	    	 }else {
	    		 enemyTurnDelay.stop();
	    		 mainFight.setVisible(false);
	    		 mainFight.gameplay.r.window.remove(mainFight);
	    		 mainFight.gameplay.r.setVisible(true);
	    		 mainFight.gameplay.r.grabFocus();
	    		 mainFight.gameplay.r.enable();
	    	 }
	      }
	  };;
	
	public Fight(Player p,ArrayList<Pair<Enemy,JTextPane>> e,MainFightPanel mf) {
		player = p;
		enemies = e;
		mainFight = mf;
		gameplay = mf.gameplay;
		round = 0;
		enemyTurnDelay = new Timer(delay,taskPerformer);
	}
	
	public void getPlayerTurn(String action) {
		mainFight.choice = 0;
		paction = action;
		round++;
		switch(action) {
		case "attack":
			System.out.println("success!\nTargetted #"+ mainFight.target + ": "+ mainFight.getTarget().name);
			player.attack(mainFight.getTarget(), gameplay);
			for(int i = 0;i<enemies.size();i++)
				enemies.get(i).second.setBackground(Color.BLACK);
			enemies.get(0).second.setBackground(Color.GRAY);
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
		if(paction.equals("attack") && mainFight.getTarget().health <= 0) {
			try {
				mainFight.changePic(mainFight.enemyPics.get(0));
			} catch (IOException e1) {	}
			mainFight.enemy.setBorder(mainFight.genBorder(enemies.get(0).first.name,1));
			mainFight.healthFocus(0);
		}
	
		for(int i = 0;i<mainFight.enemies.size();i++) {
			StatusEffect.checkEffects(mainFight.enemies.get(i).first);
			if(mainFight.enemies.get(i).first.health <= 0) {
				//Play death animation and death sound
				if(mainFight.enemies.size()>1 && i==0) {
					 try {
							mainFight.changePic(mainFight.enemyPics.get(1));
							mainFight.enemy.setBorder(mainFight.genBorder(enemies.get(1).first.name,1));
						} catch (IOException e) {}
					 	mainFight.healthFocus(1);
						
					
				}
				mainFight.enemyPics.remove(i);
				mainFight.enemy.remove(mainFight.ehealth.get(i));
				mainFight.ehealth.remove(i);
				mainFight.enemy.remove(mainFight.emana.get(i));
				mainFight.emana.remove(i);
				mainFight.enemy.remove(mainFight.estamina.get(i));
				mainFight.estamina.remove(i);
				mainFight.enemies.remove(i);
			}
		}
		enemyCount = 0;
		enemyTurnDelay.start();		
	}
	
	public boolean checkGameOver() {
		return false;
	}
	
	
}
