package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import entities.Entity;
import misc.Util;

public class DamageIndicator extends JPanel{
	public int width,height,dmg;
	
	private int transCount,yCount;
	private ScheduledFuture<?> future;
	
	public DamageIndicator(int w, int h) {
		width = w;
		height = h;
		dmg = 0;
		transCount = 0;
		yCount = 20;
		
		setPreferredSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setBackground(Color.BLACK);
		setFocusable(false);
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
	  
	
	public void displayDamage(int d) {
		dmg = d;
		transCount = 255;
		yCount = 20; // set dmg

		ScheduledExecutorService display = Executors.newScheduledThreadPool(1);
			
		future = display.scheduleAtFixedRate(Util.guiRunnable(() -> {
			transCount-=17;
			yCount-=2;
			repaint();
			if(transCount <= 0) future.cancel(false);
		}), 0, 100, TimeUnit.MILLISECONDS);	
			
			
	}
	
	
}
