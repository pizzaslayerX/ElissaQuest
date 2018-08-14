package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import entities.Entity;

public class DrawPanel extends JPanel{
	public BufferedImage pic;
	int width, height;
	Entity entity;
	
	public DrawPanel(BufferedImage p,int w,int h, Entity e) {
		pic = p;
		setPreferredSize(new Dimension(w,h));
		setBackground(Color.BLACK);
		setFocusable(false);
		setDoubleBuffered(true);
		width = w;
		entity = e;
		height = h;
	}
	
	public void drawDamage(Graphics g,int amt, Color c) {
		g.setColor(c);
		g.setFont(new Font("Monospaced", Font.BOLD, 30));
		g.drawString(""+amt,0,height/8);
	}
	
	public void update(BufferedImage p) {
		pic = p;
		repaint();
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawObjects(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics g) {
		g.drawImage(pic,(width-pic.getWidth())/2,height/6,this);
		drawDamage(g,20,Color.RED);
	}
	
	public class damagePanel extends JPanel{
		
	}
}
