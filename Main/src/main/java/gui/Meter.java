package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import misc.Util;

public class Meter extends JPanel{
	public int current,max;
	public Color empty,fill;
	private int fullWidth,titleSize;
	public ArrayList<DamageDisplay> displays = new ArrayList<DamageDisplay>();
	private String title;
	
	
	public Meter(int current,int max,Color f,Color e) {
		this.current = current;
		this.max = max;
		empty = e;
		fill = f;
		setDoubleBuffered(true);
	}
	public Meter(int current,int max,Color f,Color e,String t,int size) {
		this.current = current;
		this.max = max;
		empty = e;
		fill = f;
		title = t;
		titleSize = size;
		setDoubleBuffered(true);
	}

	public void update(int curr,String t) {
		if(current - curr != 0) displayDamage(current - curr);
		current = curr;
		title = t;
		//repaint();
	}
	
	public void update(int curr) {
		if(current - curr != 0) displayDamage(current - curr);
		current = curr;
		//repaint();
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawObjects(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics g) {
		fullWidth = getWidth();
		//System.out.println("workeddraw");
		g.setColor(fill);
		g.fillRect(0, 0,getFillWidth(),getHeight());
		//System.out.println("fullWidth: "+fullWidth);
		//System.out.println((int)(getWidth()));
		//System.out.println(title+": "+(int)(getHeight()));
		g.setColor(empty);
		g.fillRect(getFillWidth(), 0,getEmptyWidth(),getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, titleSize));
		g.drawString(title, 5, (int)(getHeight()*0.7));
		DrawPanel dp = (DrawPanel)getParent();
		BufferedImage bf = new BufferedImage(dp.width, dp.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = bf.createGraphics();
		gr.setFont(new Font("Monospaced", Font.BOLD, 15));
		FontMetrics fm = ((Graphics2D)gr).getFontMetrics();
		for(DamageDisplay dd : displays) {
			gr.setColor(new Color(dd.dmg > 0 ? 255 : 0, dd.dmg < 0 ? 255 : 0 ,0,dd.transCount)); //dmg should never be 0 but makes sure you deal with that
			Point p = SwingUtilities.convertPoint(this, dd.width - fm.stringWidth((int)Math.abs(dd.dmg)+"")/2, ((getHeight() - fm.getHeight()) / 2) + fm.getAscent()- dd.yCount, dp);
			gr.drawString((int)Math.abs(dd.dmg)+"", (int)p.getX(), (int)p.getY());
		}
		dp.update(bf);
		//System.out.println("repainted");
	}
	
	private int getFillWidth() {
		//System.out.println(ratio);
		return max == 0 ? 0 : fullWidth*current / max;
	}
	
	private int getEmptyWidth() {
		return fullWidth - getFillWidth();
	}
	

	/*
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
			g.drawString((int)Math.abs(dd.dmg)+"", x, y - dd.yCount);
		}
	}*/
	  
	
	public void displayDamage(int d) {
		DamageDisplay dd = new DamageDisplay(d, getFillWidth());
		displays.add(dd);

		ScheduledExecutorService display = Executors.newScheduledThreadPool(1);
			
		dd.future = display.scheduleAtFixedRate(Util.guiRunnable(() -> {
			dd.transCount-=3;
			dd.yCount+=1;
			repaint();
			if(dd.transCount <= 0) {
				displays.remove(dd);
				dd.future.cancel(false);
			}
		}), 0, 25, TimeUnit.MILLISECONDS);	
			
			
	}
	
	private class DamageDisplay {
		public int transCount,yCount,dmg, width;
		private ScheduledFuture<?> future;
		
		public DamageDisplay(int d, int w) {
			dmg = d;
			width = w;
			transCount = 255;
			yCount = 10;
		}
	}
	
	
}

