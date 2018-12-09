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
	public GamePlay gameplay;
	public boolean playerTurn = true;
	public int round;
	public Timer enemyTurnDelay;
	public int enemyCount = 0, delay = 1500;
	public String paction = "";
	
	public ActionListener taskPerformer  = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	    	 if(gameplay.mfp.enemies.size()>0) {
	    	  if(enemyCount<gameplay.mfp.enemies.size()) {
	    		  try {
	    				gameplay.mfp.changePic(gameplay.mfp.enemyPics.get(enemyCount));
	    			} catch (IOException e1) {	}
	    			gameplay.mfp.enemy.setBorder(gameplay.mfp.genBorder(enemies.get(enemyCount).first.name,1));
	    			gameplay.mfp.healthFocus(enemyCount);
	    		  if(enemyTurnDelay.getDelay()!= delay)
	    			  enemyTurnDelay.setDelay(delay);
	    		  gameplay.mfp.enemies.get(enemyCount).first.attack(player, gameplay);
	    		  gameplay.mfp.updateHealth();
	    		  System.out.println("Health Updated!");
	    		  try {
						gameplay.mfp.changePic(gameplay.mfp.enemyPics.get(enemyCount));
						gameplay.mfp.enemy.setBorder(gameplay.mfp.genBorder(enemies.get(enemyCount).first.name,1));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		  enemyCount++;
	    	  }else {
	    		  	enemyTurnDelay.stop();
	    		  	gameplay.mfp.choice = 0;
	    	  		gameplay.mfp.target = 0;
	    	  		gameplay.mfp.choosing = true;
	    	  		gameplay.mfp.enemy.setBorder(gameplay.mfp.genBorder(enemies.get(0).first.name,1));
					gameplay.mfp.healthFocus(0);
					
						try {
							gameplay.mfp.changePic(gameplay.mfp.enemyPics.get(0));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    	  		gameplay.mfp.showMenu();
	    	  		player.regen();
	    	  		for(Pair<Enemy,JTextPane> e:gameplay.mfp.enemies)
	    	  			e.first.regen();
	    	  		gameplay.mfp.updateHealth();
	    	  		StatusEffect.checkEffects(player);
	    	  		gameplay.mfp.updateHealth();
	    	  		if(player.health <= 0) {
	    	  			//gameover                                                                                                          MAKE SURE TO RENABLE
	    	  			
	    	  		 gameplay.r.gameOver = true;
	    	  		 enemyTurnDelay.stop();
	   	    		 gameplay.mfp.setVisible(false);
	   	    		 gameplay.r.window.remove(gameplay.mfp);
	   	    		 gameplay.r.setVisible(true);
	   	    		 gameplay.r.grabFocus();
	   	    		 gameplay.r.enable();
	    	  		}
	    	  }
	    	 }else {
	    		 enemyTurnDelay.stop();
	    		 gameplay.openPanel = false;
	    		 gameplay.mfp.setVisible(false);
	    		 gameplay.r.window.remove(gameplay.mfp);
	    		 gameplay.r.setVisible(true);
	    		 gameplay.r.grabFocus();
	    		 gameplay.r.enable();
	    	 }
	      }
	  };
	
	public Fight(Player p,ArrayList<Pair<Enemy,JTextPane>> e,GamePlay gp) {
		player = p;
		enemies = e;
		gameplay = gp;
		round = 0;
		enemyTurnDelay = new Timer(delay,taskPerformer);
	}
	
	public void getPlayerTurn(String action) {
		gameplay.mfp.choice = 0;
		paction = action;
		round++;
		switch(action) {
		case "attack":
			System.out.println("success!\nTargetted #"+ gameplay.mfp.target + ": "+ gameplay.mfp.getTarget().name);
			player.attack(gameplay.mfp.getTarget(), gameplay);
			for(int i = 0;i<enemies.size();i++)
				enemies.get(i).second.setBackground(Color.BLACK);
			enemies.get(0).second.setBackground(Color.GRAY);
			break;
		case "item":
			System.out.println("success!\nUsed #"+ gameplay.mfp.target + ": "+ player.equippedPots.first.get(gameplay.mfp.target).name);
			((Consumable) player.equippedPots.first.get(gameplay.mfp.target)).use(player);
			 player.equippedPots.first.remove(gameplay.mfp.target);
			 gameplay.mfp.updateHealth();
			break;
		}	  
		getEnemyTurn();
		
	}
	
	public void getEnemyTurn() {
		if(paction.equals("attack") && gameplay.mfp.getTarget().health <= 0) {
			try {
				gameplay.mfp.changePic(gameplay.mfp.enemyPics.get(0));
			} catch (IOException e1) {	}
			gameplay.mfp.enemy.setBorder(gameplay.mfp.genBorder(enemies.get(0).first.name,1));
			gameplay.mfp.healthFocus(0);
		}
	
		for(int i = 0;i<gameplay.mfp.enemies.size();i++) {
			StatusEffect.checkEffects(gameplay.mfp.enemies.get(i).first);
			if(gameplay.mfp.enemies.get(i).first.health <= 0) {
				//Play death animation and death sound
				if(gameplay.mfp.enemies.size()>1 && i==0) {
					 try {
							gameplay.mfp.changePic(gameplay.mfp.enemyPics.get(1));
							gameplay.mfp.enemy.setBorder(gameplay.mfp.genBorder(enemies.get(1).first.name,1));
						} catch (IOException e) {}
					 	gameplay.mfp.healthFocus(1);
				}
				gameplay.mfp.enemyPics.remove(i);
				gameplay.mfp.enemy.remove(gameplay.mfp.ehealth.get(i));
				gameplay.mfp.ehealth.remove(i);
				gameplay.mfp.enemy.remove(gameplay.mfp.emana.get(i));
				gameplay.mfp.emana.remove(i);
				gameplay.mfp.enemy.remove(gameplay.mfp.estamina.get(i));
				gameplay.mfp.estamina.remove(i);
				gameplay.mfp.menuBox.remove(gameplay.mfp.enemies.get(i).second);
				gameplay.mfp.enemies.remove(i);
			}
		}
		gameplay.mfp.attack.setBackground(Color.GRAY);
		gameplay.mfp.special.setBackground(Color.BLACK);
		gameplay.mfp.item.setBackground(Color.BLACK);
		enemyCount = 0;
		enemyTurnDelay.start();		
	}
	
	public boolean checkGameOver() {
		return false;
	}
	
	
}
