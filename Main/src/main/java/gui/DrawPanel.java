package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawPanel extends JPanel{
	public BufferedImage pic;
	int width, height;
	boolean autoCoords;
	public DrawPanel(BufferedImage p,int w,int h) {
		pic = p;
		setPreferredSize(new Dimension(w,h));
		setBackground(Color.BLACK);
		setFocusable(false);
		setDoubleBuffered(true);
		width = w;
		height = h;
		autoCoords = true;
	}
	public DrawPanel(int w,int h) {
		pic = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		setPreferredSize(new Dimension(w,h));
		setBackground(Color.BLACK);
		setFocusable(false);
		setDoubleBuffered(true);
		width = w;
		height = h;
		autoCoords = false;
	}
	
	public void update(BufferedImage p) {
		pic = p;
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(autoCoords) g.drawImage(pic,(width-pic.getWidth())/2,height/20,this);
		else g.drawImage(pic,0,0,this);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//drawObjects(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	
	private void drawObjects(Graphics g) {
		
		//g.setFont(new Font("Monospaced", Font.BOLD, 30));
	}

}
