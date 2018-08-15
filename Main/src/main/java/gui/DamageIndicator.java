package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import entities.Enemy;
import entities.Entity;
import items.StatusEffect;
import misc.Pair;
import misc.Util;

public class DamageIndicator extends JPanel{
	public int width,height,dmg;
	public Entity entity;
	
	private int transCount,yCount;
	private ScheduledFuture<?> future;
	
	public DamageIndicator(Entity e, int w, int h, int d) {
		entity = e;
		width = w;
		height = h;
		dmg = d;
		transCount = 0;
		yCount = 20;
		
		setPreferredSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setBackground(Color.BLUE);
		setFocusable(false);
	}

	public DamageIndicator update(Entity e) {
		entity = e;
		return this;
	}
	  
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawObjects(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void drawObjects(Graphics g) {
		g.setColor(new Color(255,0,0,transCount));
		g.setFont(new Font("Monospaced", Font.BOLD, 30));
		
		Graphics2D g2d = (Graphics2D)g;
		FontMetrics fm = g2d.getFontMetrics();
		int x = (width - fm.stringWidth(dmg+"")) / 2;
		int y = ((height - fm.getHeight()) / 2) + fm.getAscent();
		g.drawString(dmg+"", x, y+yCount);
	}
	  
	
	public void displayDamage() {
			transCount = 255;
			yCount = 20; // set dmg
			ScheduledExecutorService display = Executors.newScheduledThreadPool(1);
			
			future = display.scheduleAtFixedRate(Util.guiRunnable(() -> {
				transCount-=16;
				yCount-=2;
				repaint();
				if(transCount <= 0) future.cancel(false);
			}), 0, 100, TimeUnit.MILLISECONDS);	
			
			
	}
	
	
}
