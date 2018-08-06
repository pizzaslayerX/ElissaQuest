package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Meter extends JPanel{
	public int current,max;
	public Color empty,fill;
	private int fullWidth,titleSize;
	private String title;
	
	public Meter(int current,int max,Color f,Color e) {
		this.current = current;
		this.max = max;
		empty = e;
		fill = f;
	}
	public Meter(int current,int max,Color f,Color e,String t,int size) {
		this.current = current;
		this.max = max;
		empty = e;
		fill = f;
		title = t;
		titleSize = size;
	}

	public void update(int current,String t) {
		this.current = current;
		title = t;
		repaint();
	}
	
	public void update(int current) {
		this.current = current;
		repaint();
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawObjects(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics g) {
		fullWidth = (int)(getWidth());
		System.out.println("workeddraw");
		g.setColor(fill);
		g.fillRect(0, 0,getFillWidth(),(int)(getHeight()));
		System.out.println("fullWidth: "+fullWidth);
		System.out.println((int)(getWidth()));
		System.out.println((int)(getHeight()));
		g.setColor(empty);
		g.fillRect(getFillWidth(), 0,getEmptyWidth(),(int)(getHeight()));
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, titleSize));
		g.drawString(title, 5, (int)(getHeight()*0.7));
	}
	
	private int getFillWidth() {
		double ratio = (double)current / max;
		System.out.println(ratio);
		return (int)(ratio * fullWidth);
	}
	
	private int getEmptyWidth() {
		return fullWidth - getFillWidth();
	}
}
