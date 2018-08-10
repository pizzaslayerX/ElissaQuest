package gui;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawPanel extends JPanel{
	public BufferedImage pic;
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawObjects(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics g) {
		g.drawImage(pic,0,0,this);
	}
}
