package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import entities.Entity;
import misc.Util;

public class DamageIndicator extends JPanel{
	public int width,height;
	public ArrayList<DamageDisplay> displays = new ArrayList<DamageDisplay>();
	
	public DamageIndicator(int w, int h) {
		width = w;
		height = h;
		
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
		FontMetrics fm = ((Graphics2D)g).getFontMetrics();
		for(DamageDisplay dd : displays) {
			g.setColor(new Color(dd.dmg > 0 ? 255 : 0, dd.dmg < 0 ? 255 : 0 ,0,dd.transCount)); //dmg should never be 0 but makes sure you deal with that
			g.setFont(new Font("Monospaced", Font.BOLD, 30));
			int x = (width - fm.stringWidth((int)Math.abs(dd.dmg)+"")) / 2;
			int y = ((height - fm.getHeight()) / 2) + fm.getAscent();
			g.drawString((int)Math.abs(dd.dmg)+"", x, y+dd.yCount);
		}
	}
	  
	
	public void displayDamage(int d) {
		DamageDisplay dd = new DamageDisplay(d);
		displays.add(dd);

		ScheduledExecutorService display = Executors.newScheduledThreadPool(1);
			
		dd.future = display.scheduleAtFixedRate(Util.guiRunnable(() -> {
			dd.transCount-=5;
			dd.yCount-=1;
			repaint();
			if(dd.transCount <= 0) {
				displays.remove(dd);
				dd.future.cancel(false);
			}
		}), 0, 25, TimeUnit.MILLISECONDS);	
			
			
	}
	
	public class DamageDisplay {
		public int transCount,yCount,dmg;
		private ScheduledFuture<?> future;
		
		public DamageDisplay(int d) {
			dmg = d;
			transCount = 255;
			yCount = 20;
		}
	}
	
	
}
