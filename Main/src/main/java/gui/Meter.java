package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Meter extends JPanel{
	public int min,max;
	public Color empty,fill;
	
	
	public Meter(int min,int max,Color e,Color f) {
		this.min = min;
		this.max = max;
		empty = e;
		fill = f;
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawObjects(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics g) {
		//g.fillRect(x, y, width, height);
	}
}
