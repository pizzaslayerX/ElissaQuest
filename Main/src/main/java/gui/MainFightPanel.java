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
import run.Window;

public class MainFightPanel extends JPanel implements KeyListener{
	private ArrayList<Enemy> enemies;
    public FlowLayout layout;
    public JLabel label;
    public static JLabel picSpace;
    public JPanel fightPanel,user;
    public static JTextPane attack,item,special;
    public static JPanel picArea,enemy,blankEnemy;
    
	public MainFightPanel(Enemy e){
		enemies = new ArrayList<Enemy>();
		enemies.add(e);
		try {
			init(e.getPic());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public MainFightPanel(ArrayList<Enemy> e) {
		enemies = new ArrayList<Enemy>();
		enemies.addAll(e);
		try {
			init(e.get(0).getPic());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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

		setPreferredSize(Window.GAME_SIZE);
		setBackground(Color.BLACK);
		setForeground(Window.FOREGROUND_COLOR);
		setVisible(true);
		setFocusable(true);
		setDoubleBuffered(true);
		addKeyListener(this);

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
		attack.setMaximumSize(new Dimension(550,170));
		attack.setBackground(Color.LIGHT_GRAY);
		attack.setVisible(true);
		attack.setBorder(d);
		
		item = new JTextPane();
		item.setEditable(false);
		item.setMaximumSize(new Dimension(550,170));
		item.setBackground(Color.GRAY);
		item.setVisible(true);
		item.setBorder(s);
		
		special = new JTextPane();
		special.setEditable(false);
		special.setMaximumSize(new Dimension(550,170));
		special.setBackground(Color.BLUE);
		special.setVisible(true);
		special.setBorder(a);
		
		user.add(Box.createVerticalStrut(25));
		user.add(attack);
		user.add(Box.createVerticalStrut(10));
		user.add(special);
		user.add(Box.createVerticalStrut(10));
		user.add(item);
		add(enemy);	
		add(user);	
		repaint();
	}

	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawObjects(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
		g.drawString("Attack",0,100);
	}
	
	
	
	
	
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
