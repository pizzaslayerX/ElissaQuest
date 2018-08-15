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

public class DamageIndicator extends JPanel{
	public int width,height,entityHealth,diff;
	public Entity entity;
	private DamageIndicator text;
	
	private int transCount,yCount;
	private ScheduledFuture<?> future;
	
	public DamageIndicator(Entity e, int w, int h) {
		entity = e;
		width = w;
		diff = 0;
		height = h;
		entityHealth = entity.health;
		text = this;
		transCount = 255;
		yCount = 20;
		
		setPreferredSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setBackground(Color.BLUE);
		setFocusable(false);
	}

	public DamageIndicator update(Entity e) {
		entity = e;
		entityHealth = e.health;
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
		int x = (width - fm.stringWidth(diff+"")) / 2;
		int y = ((height - fm.getHeight()) / 2) + fm.getAscent();
		g.drawString(diff+"", x, y+yCount);
	}
	  
	
	public void displayDamage() {
			transCount = 255;
			yCount = 20;
			diff = entityHealth - entity.health;
			entityHealth = entity.health;
			ScheduledExecutorService display = Executors.newScheduledThreadPool(1);
			
			future = display.scheduleAtFixedRate(() -> {
				SwingUtilities.invokeLater(() -> { transCount-=17; yCount-=2;
				text.repaint();
				if(transCount <= 0) future.cancel(false);});
			}, 0, 100, TimeUnit.MILLISECONDS);	
			
			
	}
	
	
}
