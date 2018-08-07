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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import entities.Enemy;
import entities.Interactive;
import run.GamePlay;
import run.Window;

public class MainFightPanel extends JPanel implements Interactive{
	private ArrayList<Enemy> enemies;
	public GamePlay gameplay;
    public FlowLayout layout;
    public JLabel label;
    public static JLabel picSpace;
    public JPanel fightPanel,user;
    public Meter health,mana,stamina;
    public static JTextPane attack,item,special;
    public static JPanel picArea,enemy,blankEnemy;
    private static int choice ,  uhuugugyy= -1;
    public static boolean choosing = true;
    private static final Color HEALTH_GREEN = new Color(22, 150, 10);
    
	public MainFightPanel(Enemy e, GamePlay gp){
		gameplay = gp;
		enemies = new ArrayList<Enemy>();
		enemies.add(e);
		health = new Meter(gp.player.health,gp.player.maxHealth,HEALTH_GREEN,Color.BLACK,"HP: " + gp.player.health + "/" + gp.player.maxHealth,19);
		mana = new Meter(gp.player.mana,gp.player.maxMana,Color.BLUE,Color.BLACK,"Mana: " + gp.player.mana + "/" + gp.player.maxMana,19);
		stamina = new Meter((int)(gp.player.stamina),(int)(gp.player.maxStamina),Color.ORANGE,Color.BLACK,"Stamina: " + (int)(gp.player.stamina) + "/" + (int)(gp.player.maxStamina),19);
		try {
			init(e.getPic());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		update(1);
		interact(gp);
	}
	
	
	public MainFightPanel(ArrayList<Enemy> e, GamePlay gp) {
		gameplay = gp;
		enemies = new ArrayList<Enemy>();
		enemies.addAll(e);
		health = new Meter(gp.player.health,gp.player.maxHealth,Color.GREEN,Color.RED,"HP: " + gp.player.health + "/" + gp.player.maxHealth,15);
		mana = new Meter(gp.player.mana,gp.player.maxMana,Color.BLUE,Color.BLACK,"Mana: " + gp.player.mana + "/" + gp.player.maxMana,15);
		stamina = new Meter((int)(gp.player.stamina),(int)(gp.player.maxStamina),Color.ORANGE,Color.BLACK,"Stamina: " + (int)(gp.player.stamina) + "/" + (int)(gp.player.maxStamina),15);
		try {
			init(e.get(0).getPic());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		update(1);
		interact(gp);
	}
	
	public static void append(JTextPane p,String n) {
    	
    	StyledDocument doc = p.getStyledDocument();

    	SimpleAttributeSet keyWord = new SimpleAttributeSet();
    	StyleConstants.setForeground(keyWord, Color.WHITE);
       try
    	{
    	   doc.insertString(doc.getLength(),n, keyWord);
    	} catch(Exception e) { System.out.println(e);}
    }

	public void updateHealth() {
		health.update(gameplay.player.health,"HP: " + gameplay.player.health + "/" + gameplay.player.maxHealth);;
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
		addKeyListener(gameplay.listener);

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
		
		
		TitledBorder border12 = new TitledBorder("Enemy");
        border12.setTitleColor(Color.WHITE);
        border12.setTitleFont(new Font("Monospaced", Font.BOLD, 18));
        border12.setTitleJustification(TitledBorder.CENTER);
        border12.setTitlePosition(TitledBorder.TOP);
		
		enemy = new JPanel();
		enemy.setPreferredSize(new Dimension(((int)Window.GAME_SIZE.getWidth()/2-10),((int)Window.GAME_SIZE.getHeight()-40)));
		enemy.setBackground(Color.BLACK);
		enemy.setVisible(true);
		enemy.add(Box.createHorizontalStrut(5));
		enemy.setBorder(border12);
		//enemy.setLayout(new BoxLayout(enemy,BoxLayout.Y_AXIS));

		blankEnemy = new JPanel();
		blankEnemy.setPreferredSize(new Dimension(205,205));
		blankEnemy.setBackground(enemy.getBackground());
		blankEnemy.setVisible(true);
		enemy.add(blankEnemy);
		
		picArea = new JPanel();
		 BufferedImage image = ImageIO.read(new File("src/res/pics/" + "test.jpg"));	
         picSpace = new JLabel(new ImageIcon(image));
        picArea.add(picSpace);

		picArea.setPreferredSize(new Dimension(600,370));
		picArea.setBackground(Color.BLACK);
		picArea.setVisible(true);
		
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
		
		user.add(health);
		user.add(Box.createVerticalStrut(5));
		user.add(mana);
		user.add(Box.createVerticalStrut(5));
		user.add(stamina);
		user.add(Box.createVerticalStrut(25));
		user.add(attack);
		user.add(Box.createVerticalStrut(10));
		user.add(special);
		user.add(Box.createVerticalStrut(10));
		user.add(item);
		add(enemy);	
		add(user);	
		//System.out.println("Before repaint");
		//System.out.println("Before repaint");
		
		append(attack," ATTACK",Color.WHITE,120,false);
		append(special,"   MAGIC",Color.WHITE,115,false);
		append(item,"   ITEM",Color.WHITE,130,false);
		System.out.println("Before repaint");
		updateHealth();
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

	private void hideMenu() {
		special.setVisible(false);
		item.setVisible(false);
		attack.setVisible(false);
	}
	
	private void update(int num) {
		if(num == 0){
			hideMenu();
			return;
			//return -1;
		}
		choice += num;
		choice %= 3;
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
		//return choice;
	}


	@Override
	public void interact(GamePlay g) {
		// TODO Auto-generated method stub
		while(!g.returnText.isEmpty()) {
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
			g.userWait();
		}
	}


	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		
	}
	
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
