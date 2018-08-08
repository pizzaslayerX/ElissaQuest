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
import misc.KeybindMaker;
import misc.Pair;
import run.GamePlay;
import run.Window;

public class MainFightPanel extends JPanel{
	private ArrayList<Pair<Enemy,JTextPane>> enemies;

	public GamePlay gameplay;
    public FlowLayout layout;
    public JLabel label;
    public Fight fight;
    public static JLabel picSpace;
    public JPanel fightPanel,user;
    public Meter health,mana,stamina, ehealth, emana, estamina;
    public static JTextPane attack,item,special;
    public static JPanel picArea,enemy,blankEnemy,menuBox;
    public int choice = -1,  target = 0;
    public boolean choosing = true,targetSelect=false;
    private static final Color HEALTH_GREEN = new Color(22, 150, 10);
    
	public MainFightPanel(Enemy e, GamePlay gp){
		gameplay = gp;
		
		enemies = new ArrayList<Pair<Enemy,JTextPane>>();
		enemies.add(new Pair<Enemy,JTextPane>(e,new JTextPane()));
		health = new Meter(gp.player.health,gp.player.maxHealth,HEALTH_GREEN,Color.BLACK,"HP: " + gp.player.health + "/" + gp.player.maxHealth,19);
		mana = new Meter(gp.player.mana,gp.player.maxMana,Color.BLUE,Color.BLACK,"Mana: " + gp.player.mana + "/" + gp.player.maxMana,19);
		stamina = new Meter((int)(gp.player.stamina),(int)(gp.player.maxStamina),new Color(255, 127, 0),Color.BLACK,"Stamina: " + (int)(gp.player.stamina) + "/" + (int)(gp.player.maxStamina),19);
		ehealth = new Meter(e.health, e.maxHealth, new Color(200,0,0),Color.BLACK, "HP", 19);
		emana = new Meter(e.mana, e.maxMana, new Color(127,0,127),Color.BLACK, "Mana", 19);
		estamina = new Meter((int)e.stamina, (int)e.maxStamina, new Color(0,127,127),Color.BLACK, "HP", 19);
		try {
			init("test.jpg"/*e.getPic()*/);
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
		enemies = new ArrayList<Pair<Enemy,JTextPane>>();
		for(Enemy en : e) enemies.add(new Pair<Enemy,JTextPane>(en,new JTextPane()));
		
		health = new Meter(gp.player.health,gp.player.maxHealth,HEALTH_GREEN,Color.BLACK,"HP: " + gp.player.health + "/" + gp.player.maxHealth,19);
		mana = new Meter(gp.player.mana,gp.player.maxMana,Color.BLUE,Color.BLACK,"Mana: " + gp.player.mana + "/" + gp.player.maxMana,19);
		stamina = new Meter((int)(gp.player.stamina),(int)(gp.player.maxStamina),new Color(255, 127, 0),Color.BLACK,"Stamina: " + (int)(gp.player.stamina) + "/" + (int)(gp.player.maxStamina),19);
		ehealth = new Meter(e.get(0).health, e.get(0).maxHealth, new Color(200,0,0),Color.BLACK, "HP", 19);
		emana = new Meter(e.get(0).mana, e.get(0).maxMana, new Color(127,0,127),Color.BLACK, "Mana", 19);
		estamina = new Meter((int)e.get(0).stamina, (int)e.get(0).maxStamina, new Color(0,127,127),Color.BLACK, "HP", 19);
		try {
			init(e.get(0).getPic());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		update(1);
		fight = new Fight(gameplay.player,enemies,this);
		//interact(gp);
	}
	
	

	public void updateHealth() {
		health.update(gameplay.player.health,"HP: " + gameplay.player.health + "/" + gameplay.player.maxHealth);
		mana.update(gameplay.player.mana,"Mana: " + gameplay.player.mana + "/" + gameplay.player.maxMana);
		stamina.update((int)gameplay.player.stamina,"Stamina: " + (int)gameplay.player.stamina + "/" + (int)gameplay.player.maxStamina);
		ehealth.update(enemies.get(0).first.health,  "HP");
		emana.update(enemies.get(0).first.mana, "Mana");
		estamina.update((int)enemies.get(0).first.stamina, "Stamina");
	}
	
	public static void append(JTextPane p, String n, Color c,int size, boolean bold) {
    	
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
	
public static void append(JTextPane p, String n, Color c,int size, boolean bold,int a) {
    	
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
	
	public static void changePic(String pic) throws IOException {
		enemy.remove(picArea);
		picArea.remove(picSpace);
		 BufferedImage image = ImageIO.read(new File("src/res/pics/" + pic));
		 picSpace = new JLabel(new ImageIcon(image));
		 picArea.add(picSpace);
		 enemy.add(picArea);
	}
	
	public void init(String pic) throws Exception {
		updateHealth();
		setPreferredSize(Window.GAME_SIZE);
		setBackground(Color.BLACK);
		setForeground(Window.FOREGROUND_COLOR);
		setVisible(true);
		setFocusable(true);
		setDoubleBuffered(true);
		
		
		
		health.setMaximumSize(new Dimension(550,50));
		health.setBackground(Color.BLACK);
		health.setVisible(true);
		health.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		mana.setMaximumSize(new Dimension(550,50));
		mana.setBackground(Color.BLACK);
		mana.setVisible(true);
		mana.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		stamina.setMaximumSize(new Dimension(550,50));
		stamina.setBackground(Color.BLACK);
		stamina.setVisible(true);
		stamina.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		ehealth.setPreferredSize(new Dimension(550,25));
		ehealth.setBackground(Color.BLACK);
		ehealth.setVisible(true);
		ehealth.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		///*
		emana.setPreferredSize(new Dimension(550,25));
		emana.setBackground(Color.BLACK);
		emana.setVisible(true);
		emana.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		estamina.setPreferredSize(new Dimension(550,25));
		estamina.setBackground(Color.BLACK);
		estamina.setVisible(true);
		estamina.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));//*/
		
		
		TitledBorder border12 = new TitledBorder("Enemy");
        border12.setTitleColor(Color.WHITE);
        border12.setTitleFont(new Font("Monospaced", Font.BOLD, 18));
        border12.setTitleJustification(TitledBorder.CENTER);
        border12.setTitlePosition(TitledBorder.TOP);
		
		enemy = new JPanel();
		enemy.setPreferredSize(new Dimension(((int)Window.GAME_SIZE.getWidth()/2-10),((int)Window.GAME_SIZE.getHeight()-40)));
		enemy.setBackground(Color.BLACK);
		enemy.setVisible(true);
		//enemy.add(Box.createHorizontalStrut(5));
		enemy.setBorder(border12);
		//enemy.setLayout(new BoxLayout(enemy,BoxLayout.Y_AXIS));
/*
		blankEnemy = new JPanel();
		blankEnemy.setPreferredSize(new Dimension(205,205));
		blankEnemy.setBackground(enemy.getBackground());
		blankEnemy.setVisible(true);*/
		//enemy.add(blankEnemy);
		
		picArea = new JPanel();
		 BufferedImage image = ImageIO.read(new File("src/res/pics/" + "test.jpg"));	
         picSpace = new JLabel(new ImageIcon(image));
        picArea.add(picSpace);

		picArea.setPreferredSize(new Dimension(600,370));
		picArea.setBackground(Color.BLACK);
		picArea.setVisible(true);
		
		enemy.add(ehealth);
		enemy.add(Box.createVerticalStrut(5));
		enemy.add(emana);
		enemy.add(Box.createVerticalStrut(5));
		enemy.add(estamina);
		enemy.add(Box.createVerticalStrut(5));
		enemy.add(picArea);
		
		TitledBorder border2 = new TitledBorder("Battle");
        border2.setTitleColor(Color.WHITE);
        border2.setTitleFont(new Font("Monospaced", Font.BOLD, 18));
        border2.setTitleJustification(TitledBorder.CENTER);
        border2.setTitlePosition(TitledBorder.TOP);
        
		user = new JPanel();
		user.setLayout(new BoxLayout(user,BoxLayout.PAGE_AXIS));
		user.setPreferredSize(new Dimension(((int)Window.GAME_SIZE.getWidth()/2-20),((int)Window.GAME_SIZE.getHeight()-40)));
		user.setBackground(Color.BLACK);
		user.setVisible(true);
		user.setBorder(border2);
		
		Border a = BorderFactory.createLineBorder(Color.WHITE, 3);
		Border s = BorderFactory.createLineBorder(Color.WHITE, 3);
		Border d = BorderFactory.createLineBorder(Color.WHITE, 3);
		
		attack = new JTextPane();
		attack.setEditable(false);
		attack.setMaximumSize(new Dimension(550,140));
		attack.setBackground(Color.BLACK);
		attack.setVisible(true);
		attack.setBorder(d);
		
		item = new JTextPane();
		item.setEditable(false);
		item.setMaximumSize(new Dimension(550,140));
		item.setBackground(Color.BLACK);
		item.setVisible(true);
		item.setBorder(s);
		
		special = new JTextPane();
		special.setEditable(false);
		special.setMaximumSize(new Dimension(550,140));
		special.setBackground(Color.BLACK);
		special.setVisible(true);
		special.setBorder(a);
		
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
		for(int i = 0;i<enemies.size();i++) {
			enemies.get(i).second.setPreferredSize(new Dimension(580,50));
			enemies.get(i).second.setBorder(genBorder("",0));
			enemies.get(i).second.setBackground(Color.BLACK);
			append(enemies.get(i).second,enemies.get(i).first.name+"",Color.WHITE,35,false,1);
			if(i==0) enemies.get(i).second.setBackground(Color.GRAY);
				
			menuBox.add(enemies.get(i).second);
		}
		//boo
		add(enemy);	
		add(user);	
		//System.out.println("Before repaint");
		//System.out.println("Before repaint");
		
		append(attack," ATTACK",Color.WHITE,120,false);
		append(special,"   MAGIC",Color.WHITE,115,false);
		append(item,"   ITEM",Color.WHITE,130,false);
		System.out.println("Before repaint");

		updateHealth();

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
		KeybindMaker.keybind(this, KeyEvent.VK_W, "up", u -> {
			update(1);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
		KeybindMaker.keybind(this, KeyEvent.VK_S, "down", u -> {
			update(-1);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		KeybindMaker.keybind(this, KeyEvent.VK_ENTER, "enter", u -> {
			update(0);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		grabFocus();
		
	}

	
	private TitledBorder genBorder(String n,int orient) {
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
		health.setMaximumSize(new Dimension(550,29));
		mana.setMaximumSize(new Dimension(550,29));
		stamina.setMaximumSize(new Dimension(550,29));
	}
	
	private void showMenu() {
		special.setVisible(true);
		item.setVisible(true);
		attack.setVisible(true);
		health.setMaximumSize(new Dimension(550,50));
		mana.setMaximumSize(new Dimension(550,50));
		stamina.setMaximumSize(new Dimension(550,50));
	}
	
	private void update(int num) {
	if(targetSelect == true) {		
		if(num == 0) {
			menuBox.setVisible(false);
			targetSelect = false;
			target = choice;
			fight.getPlayerTurn("attack");
		}else {
			choice += -num;
			if(choice < 0) choice = enemies.size()-1;
			if(choice > enemies.size()-1) choice = 0;
			for(int i = 0;i<enemies.size();i++) {
				if(i!=choice) 
					enemies.get(i).second.setBackground(Color.BLACK);
				else 
					enemies.get(i).second.setBackground(Color.GRAY);
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
					return;
				}
				menuBox.setVisible(true);
				menuBox.setBorder(genBorder("Select Target",0));
				targetSelect = true;
				choice = 0;
				return;
			}
		}
		choice += num;
		if(choice < 0) choice = 2;
		if(choice > 2) choice = 0;
		switch(choice) {
		case 0:
			attack.setBackground(Color.GRAY);
			item.setBackground(Color.BLACK);
			special.setBackground(Color.BLACK);
			break;
		case 1:
			attack.setBackground(Color.BLACK);
			item.setBackground(Color.GRAY);
			special.setBackground(Color.BLACK);
			break;
		case 2:
			attack.setBackground(Color.BLACK);
			item.setBackground(Color.BLACK);
			special.setBackground(Color.GRAY);
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
