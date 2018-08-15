package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import items.Item;
import misc.Pair;
import misc.Util;
import run.DrawScreen;
import run.GamePlay;

public class LootScreen extends JPanel{
	
	public ArrayList<Pair<Item,Integer>> loot;
	public GamePlay gameplay;
	public int width,height;
	public JPanel display;
	
	public LootScreen(GamePlay g,ArrayList<Item> items,int w, int h) {
		loot = new ArrayList<Pair<Item,Integer>>();
		width = w;
		height = h;
		for(int i = 0;i<items.size();i++) 
			if(!lootContains(items.get(i)))
				loot.add(new Pair<Item,Integer>(items.get(i),1));
			else 
				for(Pair<Item,Integer> p: loot) 
					if(p.first.equals(items.get(i)))
						p.second++;
		
		gameplay = g;
		init();
		
		
	}
	
	public void init() {
		display = new JPanel();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setBackground(Color.BLUE);
		
		display.setPreferredSize(new Dimension(width/3,(int)(height*0.9)));
		display.setMaximumSize(new Dimension(width/3,(int)(height*0.9)));
		display.setFocusable(false);
		display.setBackground(Color.RED);
		display.setBorder(Util.genBorder("Loot", 1));
		display.setVisible(true);
		
		add(display);
		setVisible(true);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean lootContains(Item i) {
		for(Pair<Item,Integer> p: loot) 
			if(p.first.equals(i)) return true;
		return false;
	}
	
}
