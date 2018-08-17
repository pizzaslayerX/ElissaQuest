package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import run.GamePlay;

public abstract class DisplayPanel extends JPanel{
	protected int width,height;
	public GamePlay gameplay;
	
	public DisplayPanel(GamePlay g) {
		gameplay = g;
		
		width = g.r.window.getWidth();
		height = g.r.window.getHeight();
		setPreferredSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setBackground(Color.BLACK);
		
		
		
		gameplay.r.setVisible(false);
		gameplay.r.disable();
		gameplay.openPanel = true;
		gameplay.r.window.add(this);
		
		
	}
	
	public abstract void init();
	
	public abstract void exit();
	
	public abstract void update(int num);
	
}
