package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import entities.Enemy;
import entities.Entity;
import entities.Interactive;
import items.Consumable;
import misc.Util;
import misc.Pair;
import run.GamePlay;
import run.Window;

public class MainFightPanel extends JPanel{
	public ArrayList<Pair<Enemy,JTextPane>> enemies;

	public GamePlay gameplay;
    public FlowLayout layout;
    public JLabel label;
    public Fight fight;
    public JPanel fightPanel,user;
    public Meter health,mana,stamina;
    public ArrayList<Meter> ehealth, emana, estamina;
    public JTextPane attack,item,special;
    public JPanel enemy,blankEnemy,menuBox;
    public InventoryPair pots;
    public int choice = 1,  target = 0;
    public boolean choosing = true,targetSelect=false,itemSelect=false;
    private static final Color HEALTH_GREEN = new Color(22, 150, 10);
    public ArrayList<BufferedImage> enemyPics;
    public DrawPanel enemyPic;
    public DamageIndicator dmgIndicator;
    
	public MainFightPanel(Enemy e, GamePlay gp){
		gameplay = gp;
		ehealth = new ArrayList<Meter>();
		emana = new ArrayList<Meter>();
		estamina = new ArrayList<Meter>();
		enemyPics = new ArrayList<BufferedImage>();
		grabFocus();
		pots = new InventoryPair(gameplay.player.equippedPots, "Item Selection",595,455);
		enemies = new ArrayList<Pair<Enemy,JTextPane>>();
		enemies.add(new Pair<Enemy,JTextPane>(e,new JTextPane()));
		health = new Meter(gp.player.health,gp.player.maxHealth,HEALTH_GREEN,Color.BLACK,"HP: " + gp.player.health + "/" + gp.player.maxHealth,19);
		mana = new Meter(gp.player.mana,gp.player.maxMana,Color.BLUE,Color.BLACK,"Mana: " + gp.player.mana + "/" + gp.player.maxMana,19);
		stamina = new Meter((int)(gp.player.stamina),(int)(gp.player.maxStamina),new Color(255, 127, 0),Color.BLACK,"Stamina: " + (int)(gp.player.stamina) + "/" + (int)(gp.player.maxStamina),19);
		ehealth.add(new Meter(e.health, e.maxHealth, new Color(0,127,127),Color.BLACK, "HP", 19));
		emana.add(new Meter(e.mana, e.maxMana, new Color(127,0,127),Color.BLACK, "Mana", 19));
		estamina.add(new Meter((int)e.stamina, (int)e.maxStamina, new Color(200,0,0),Color.BLACK, "HP", 19));
		
		dmgIndicator = new DamageIndicator(enemies.get(0).first,500,80, 5);//chamnge
		for(int i=0;i<enemies.size();i++) {
			enemyPics.add(Util.loadImage(enemies.get(i).first.getPic()));
		}
		try {
			init();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		update(1);
		fight = new Fight(gameplay.player,enemies,this);
		//interact(gp);
	}
	
	
	public MainFightPanel(ArrayList<Enemy> e, GamePlay gp) { 
		gameplay = gp;
/*
		//gameplay.player.inventory.add(Consumable.Consumables.darkVial());
		System.out.println(gameplay.player.inventory.getConsumableInv().size());
		pots = new InventoryPair(gameplay.player.inventory.getConsumableInv(),"Item Selection",595,455);
=======*/
		grabFocus();
		ehealth = new ArrayList<Meter>();
		emana = new ArrayList<Meter>();
		estamina = new ArrayList<Meter>();
		pots = new InventoryPair(gameplay.player.equippedPots,"Item Selection",595,455);
		enemyPics = new ArrayList<BufferedImage>();
		
		enemies = new ArrayList<Pair<Enemy,JTextPane>>();
		for(Enemy en : e) enemies.add(new Pair<Enemy,JTextPane>(en,new JTextPane()));
		
		health = new Meter(gp.player.health,gp.player.maxHealth,HEALTH_GREEN,Color.BLACK,"HP: " + gp.player.health + "/" + gp.player.maxHealth,19);
		mana = new Meter(gp.player.mana,gp.player.maxMana,Color.BLUE,Color.BLACK,"Mana: " + gp.player.mana + "/" + gp.player.maxMana,19);
		stamina = new Meter((int)(gp.player.stamina),(int)(gp.player.maxStamina),new Color(255, 127, 0),Color.BLACK,"Stamina: " + (int)(gp.player.stamina) + "/" + (int)(gp.player.maxStamina),19);
		
		for(int i=0;i<e.size();i++) {
		ehealth.add(new Meter(e.get(i).health, e.get(i).maxHealth, new Color(0,127,127),Color.BLACK, "HP", 19));
		emana.add(new Meter(e.get(i).mana, e.get(i).maxMana, new Color(127,0,127),Color.BLACK, "Mana", 19));
		estamina.add(new Meter((int)e.get(i).stamina, (int)e.get(i).maxStamina, new Color(200,0,0),Color.BLACK, "HP", 19));
		}
		
		dmgIndicator = new DamageIndicator(enemies.get(0).first,500,80, 5);
		
		for(int i=0;i<enemies.size();i++) {
			enemyPics.add(Util.loadImage(enemies.get(i).first.getPic()));
		}
		try {
			init();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		update(1);
		fight = new Fight(gameplay.player,enemies,this);
		//interact(gp);
	}
	
	public void healthFocus(int e) {
		for(int i=0;i<enemies.size();i++) {
			ehealth.get(i).setVisible(false);
			emana.get(i).setVisible(false);
			estamina.get(i).setVisible(false);
		}
		ehealth.get(e).setVisible(true);
		emana.get(e).setVisible(true);
		estamina.get(e).setVisible(true);
	}
	

	public void updateHealth() {
		health.update(gameplay.player.health,"HP: " + gameplay.player.health + "/" + gameplay.player.maxHealth);
		mana.update(gameplay.player.mana,"Mana: " + gameplay.player.mana + "/" + gameplay.player.maxMana);
		stamina.update((int)gameplay.player.stamina,"Stamina: " + (int)gameplay.player.stamina + "/" + (int)gameplay.player.maxStamina);
		
		for(int i=0;i<enemies.size();i++) {
			ehealth.get(i).update(enemies.get(i).first.health,  "HP");
			emana.get(i).update(enemies.get(i).first.mana, "Mana");
			estamina.get(i).update((int)enemies.get(i).first.stamina, "Stamina");
		}
	}
	
	private static void append(JTextPane p, String n, Color c,int size, boolean bold) {
    	
    	StyledDocument doc = p.getStyledDocument();

    	SimpleAttributeSet keyWord = new SimpleAttributeSet();
    	StyleConstants.setForeground(keyWord,c);
    	StyleConstants.setBold(keyWord, bold);
    	if(size != 0)
    		StyleConstants.setFontSize(keyWord, size);
    		try
    		{
    			doc.insertString(doc.getLength(),n, keyWord);
    		} catch(Exception e) { System.out.println(e);}
    }
	
	private static void append(JTextPane p, String n, Color c,int size, boolean bold,int a) {
    	
    	StyledDocument doc = p.getStyledDocument();

    	SimpleAttributeSet keyWord = new SimpleAttributeSet();
    	StyleConstants.setForeground(keyWord,c);
    	StyleConstants.setBold(keyWord, bold);
    	switch(a){
    		case 1: 
    		StyleConstants.setAlignment(keyWord,StyleConstants.ALIGN_CENTER);
    		StyleConstants.setFontFamily(keyWord, "Monospace");
    		break;
    	}
    	doc.setParagraphAttributes(0, doc.getLength(), keyWord, false);
    	if(size != 0)
    		StyleConstants.setFontSize(keyWord, size);
    		try
    		{
    			doc.insertString(doc.getLength(),n, keyWord);
    		} catch(Exception e) { System.out.println(e);}
    }
	
	public void changePic(BufferedImage pic) throws IOException {
		enemyPic.update(pic);
	}
	
	public void init() throws Exception {
		updateHealth();
		setPreferredSize(Window.GAME_SIZE);
		setBackground(Color.BLACK);
		setForeground(Window.FOREGROUND_COLOR);
		setVisible(true);
		setFocusable(true);
		setDoubleBuffered(true);
		//grabFocus();
		gameplay.r.disable();
		for(int i=0;i<enemies.size();i++)
			System.out.println("ENEMY ID: "+enemies.get(i).first.eid);
		health.setPreferredSize(new Dimension(550,25));
		health.setMaximumSize(new Dimension(550,25));
		health.setBackground(Color.BLACK);
		health.setVisible(true);
		health.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		mana.setPreferredSize(new Dimension(550,25));
		mana.setMaximumSize(new Dimension(550,25));
		mana.setBackground(Color.BLACK);
		mana.setVisible(true);
		mana.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		stamina.setPreferredSize(new Dimension(550,25));
		stamina.setMaximumSize(new Dimension(550,25));
		stamina.setBackground(Color.BLACK);
		stamina.setVisible(true);
		stamina.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		enemy = new JPanel();
		enemy.setPreferredSize(new Dimension(((int)Window.GAME_SIZE.getWidth()/2-10),((int)Window.GAME_SIZE.getHeight()-40)));
		enemy.setBackground(Color.BLACK);
		enemy.setVisible(true);
		//enemy.add(Box.createHorizontalStrut(5));
		enemy.setBorder(genBorder(enemies.get(0).first.name,1));
		enemyPic = new DrawPanel(enemyPics.get(0),500,400);
		//enemy.setLayout(new BoxLayout(enemy,BoxLayout.Y_AXIS));
/*
		blankEnemy = new JPanel();
		blankEnemy.setPreferredSize(new Dimension(205,205));
		blankEnemy.setBackground(enemy.getBackground());
		blankEnemy.setVisible(true);*/
		//enemy.add(blankEnemy);
		
		
		for(int i = 0;i<enemies.size();i++) {
			ehealth.get(i).setPreferredSize(new Dimension(550,25));
			ehealth.get(i).setBackground(Color.BLACK);
			ehealth.get(i).setVisible(true);
			ehealth.get(i).setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
			///*
			emana.get(i).setPreferredSize(new Dimension(550,25));
			emana.get(i).setBackground(Color.BLACK);
			emana.get(i).setVisible(true);
			emana.get(i).setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
			
			estamina.get(i).setPreferredSize(new Dimension(550,25));
			estamina.get(i).setBackground(Color.BLACK);
			estamina.get(i).setVisible(true);
			estamina.get(i).setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));//*/
		enemy.add(ehealth.get(i));
		enemy.add(emana.get(i));
		enemy.add(estamina.get(i));
		}
		healthFocus(0);
		
		enemy.add(dmgIndicator);
		dmgIndicator.setVisible(true);
		
		
		enemy.add(enemyPic);
		
		TitledBorder border2 = new TitledBorder("Battle");
        border2.setTitleColor(Color.WHITE);
        border2.setTitleFont(new Font("Monospaced", Font.BOLD, 18));
        border2.setTitleJustification(TitledBorder.CENTER);
        border2.setTitlePosition(TitledBorder.TOP);
        
		user = new JPanel();
		user.setLayout(new BoxLayout(user,BoxLayout.PAGE_AXIS));
		//user.setLayout(new BoxLayout(null);
		user.setPreferredSize(new Dimension(((int)Window.GAME_SIZE.getWidth()/2-20),((int)Window.GAME_SIZE.getHeight()-40)));
		user.setBackground(Color.BLACK);
		user.setVisible(true);
		user.setBorder(border2);
		
		Border a = BorderFactory.createLineBorder(Color.WHITE, 3);
		Border s = BorderFactory.createLineBorder(Color.WHITE, 3);
		Border d = BorderFactory.createLineBorder(Color.WHITE, 3);
		
		attack = new JTextPane();
		attack.setEditable(false);
		attack.setFocusable(false);
		attack.setPreferredSize(new Dimension(550,150));
		attack.setMaximumSize(new Dimension(550,150));
		attack.setBackground(Color.BLACK);
		attack.setVisible(true);
		attack.setBorder(d);
		
		item = new JTextPane();
		item.setEditable(false);
		item.setPreferredSize(new Dimension(550,150));
		item.setMaximumSize(new Dimension(550,150));
		item.setBackground(Color.BLACK);
		item.setVisible(true);
		item.setBorder(s);
		item.setFocusable(false);
		
		special = new JTextPane();
		special.setEditable(false);
		special.setPreferredSize(new Dimension(550,150));
		special.setMaximumSize(new Dimension(550,150));
		special.setBackground(Color.BLACK);
		special.setVisible(true);
		special.setBorder(a);
		special.setFocusable(false);
		
		user.add(Box.createVerticalStrut(5));
		user.add(health);
		user.add(Box.createVerticalStrut(5));
		user.add(mana);
		user.add(Box.createVerticalStrut(5));
		user.add(stamina);
		user.add(Box.createVerticalStrut(20));
		user.add(attack);
		user.add(Box.createVerticalStrut(10));
		user.add(special);
		user.add(Box.createVerticalStrut(10));
		user.add(item);
		
		
		
		 
		menuBox = new JPanel();
		menuBox.setMaximumSize(new Dimension(595,550));
		menuBox.setBackground(Color.BLACK);
		menuBox.setVisible(false);
		
		user.add(menuBox);
		user.add(pots);
		for(int i = 0;i<enemies.size();i++) {
			enemies.get(i).second.setPreferredSize(new Dimension(580,50));
			enemies.get(i).second.setBorder(genBorder("",0));
			enemies.get(i).second.setBackground(Color.BLACK);
			enemies.get(i).second.setFocusable(false);
			append(enemies.get(i).second,enemies.get(i).first.name+"",Color.WHITE,35,false,1);
			if(i==0) enemies.get(i).second.setBackground(Color.GRAY);
				
			menuBox.add(enemies.get(i).second);
		}
		add(enemy);	
		add(user);	
		
		
		append(attack,"ATTACK",Color.WHITE,115,false,1);
		append(special,"MAGIC",Color.WHITE,115,false,1);
		append(item,"ITEM",Color.WHITE,115,false,1);
		System.out.println("Before repaint");

		updateHealth();

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
		Util.keybind(this, KeyEvent.VK_W, "up", u -> {
			update(1);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
		Util.keybind(this, KeyEvent.VK_S, "down", u -> {
			update(-1);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		Util.keybind(this, KeyEvent.VK_ENTER, "enter", u -> {
			update(0);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		grabFocus();
		
	}

	
	public TitledBorder genBorder(String n,int orient) {
		TitledBorder border3 = new TitledBorder(n);
        border3.setTitleColor(Color.WHITE);
        border3.setTitleFont(new Font("Monospaced", Font.BOLD, 18));
        border3.setTitleJustification(TitledBorder.CENTER);
        border3.setTitlePosition(TitledBorder.TOP);
        return border3;
	}
	
	
	
	/*
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("aaa");
	  if(gameplay.openPanel && choosing == true) {
		  System.out.println("aabba");
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_DOWN:
				synchronized(returnText) {
					returnText.add("down");
					returnText.notify();
				}
			break;
		case KeyEvent.VK_W:
				synchronized(returnText) {
					returnText.add("up");
					returnText.notify();
				}
			break;
		case KeyEvent.VK_UP:
				synchronized(returnText) {
					returnText.add("up");
					returnText.notify();
				}
			break;
		case KeyEvent.VK_S:
				synchronized(returnText) {
					returnText.add("down");
					returnText.notify();
				}
			break;
		case KeyEvent.VK_ENTER:
			synchronized(returnText) {
				returnText.add("enter");
				returnText.notify();
			}
	   }
		selectChoice();
	  }
	}*/
	
	public Entity getTarget() {
		return enemies.get(target).first;
	}
	
	private void hideMenu() {
		special.setVisible(false);
		item.setVisible(false);
		attack.setVisible(false);
	}
	
	public void showMenu() {
		special.setVisible(true);
		item.setVisible(true);
		attack.setVisible(true);
	}
	
	private void update(int num) {
	if(itemSelect == true) {
		if(num == 0) {
			if(gameplay.player.equippedPots.first.size()>0) {
				pots.setVisible(false);
				itemSelect = false;
				target = choice;
				fight.getPlayerTurn("item");
				return;
			}
			pots.display();
			System.out.println(gameplay.player.equippedPots.first.size());
		}else {
			if(gameplay.player.equippedPots.first.size()>0) {
				choice += -num;
				if(choice < 0) choice = gameplay.player.equippedPots.first.size()-1;
				if(choice > gameplay.player.equippedPots.first.size()-1) choice = 0;
				for(int i = 0;i<gameplay.player.equippedPots.first.size();i++) {
					if(i!=choice) 
						pots.items.get(i).second.setBackground(Color.BLACK);
					else {
						pots.items.get(i).second.setBackground(Color.GRAY);
						pots.descTab.setText("");
						append(pots.descTab,gameplay.player.equippedPots.first.get(i).desc,Color.WHITE,25,false);
						
					}
			    }
			}
		}
	}
	if(targetSelect == true) {		
		if(num == 0) {
			menuBox.setVisible(false);
			targetSelect = false;
			target = choice;
			fight.getPlayerTurn("attack");
		 	updateHealth();
		 	return;
		}else {
			choice += -num;
			if(choice < 0) choice = enemies.size()-1;
			if(choice > enemies.size()-1) choice = 0;
			for(int i = 0;i<enemies.size();i++) {
				if(i!=choice) 
					enemies.get(i).second.setBackground(Color.BLACK);
				else {
					enemies.get(i).second.setBackground(Color.GRAY);
					enemy.setBorder(genBorder(enemies.get(i).first.name,1));
					healthFocus(i);
					try {
						changePic(enemyPics.get(i));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("You dun goofed mate");
						e.printStackTrace();
					}
				}
			}
		}
	}
	if(choosing == true) {
		if(num == 0){
			hideMenu();
			choosing = false;
			if(choice==0) {
				System.out.println("Size of enemies"+enemies.size());
				if(enemies.size()==1) {
					target = 0;
					choice = 0;
					fight.getPlayerTurn("attack");
					updateHealth();
					return;
				}
				menuBox.setVisible(true);
				menuBox.setBorder(genBorder("Select Target",0));
				targetSelect = true;
				choice = 0;
				return;
			}else if(choice==1) {
				System.out.println("Magic");
			}else if(choice==2){
				target = 0;
				System.out.println("Items");
				pots.setVisible(true);
				pots.display();
				System.out.println("\n\nAfter choice\n\n");
				itemSelect = true;
				choice = 0;
				return;
			}
		}
		choice += -num;
		if(choice < 0) choice = 2;
		if(choice > 2) choice = 0;
		switch(choice) {
		case 0:
			attack.setBackground(Color.GRAY);
			special.setBackground(Color.BLACK);
			item.setBackground(Color.BLACK);
			break;
		case 1:
			attack.setBackground(Color.BLACK);
			special.setBackground(Color.GRAY);
			item.setBackground(Color.BLACK);
			break;
		case 2:
			attack.setBackground(Color.BLACK);
			special.setBackground(Color.BLACK);
			item.setBackground(Color.GRAY);
			break;
		}
	  }
		
	
	}

/*
	@Override
	public void interact(GamePlay g) {
		// TODO Auto-generated method stub
		while(!g.returnText.isEmpty()) {
			System.out.println("aaaaaa");
			switch(g.returnText) {
				case "up": case "uparrow":
					update(1);
					break;
				case "down": case "downarrow":
					update(-1);
					break;
				case "enter":
					update(0);
					break;
			}
			System.out.println("aaaabbb");
			g.userWait();
			System.out.println("bbb");
		}
	}


	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		
	}*/
	
/*
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}*/
	
}
