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
	public DrawPanel(BufferedImage p,int w,int h) {
		pic = p;
		setPreferredSize(new Dimension(w,h));
		setBackground(Color.BLACK);
		setFocusable(false);
		setDoubleBuffered(true);
		width = w;
		height = h;
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
		g.drawImage(pic,(width-pic.getWidth())/2,height/20,this);
		g.setFont(new Font("Monospaced", Font.BOLD, 30));
	}

}
