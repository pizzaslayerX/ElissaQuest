package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import items.Item;
import misc.Pair;
import misc.Util;
import run.DrawScreen;
import run.GamePlay;

public class LootScreen extends DisplayPanel{
	
	public ArrayList<Pair<Item,Integer>> loot;
	public JPanel itemArea,itemNames,itemCounts;
	public int width,height;
	public JPanel display;
	public BufferedImage image;
	private static final Color CELL_BLUE = new Color(0,17,26);
	
	public LootScreen(GamePlay g,BufferedImage im,ArrayList<Item> items,int w, int h) {
		super(g);
		loot = new ArrayList<Pair<Item,Integer>>();
		width = w;
		height = h;
		for(int i = 0;i<items.size();i++) {
			if(!lootContains(items.get(i)))
				loot.add(new Pair<Item,Integer>(items.get(i),1));
			else 
				for(Pair<Item,Integer> p: loot) 
					if(p.first.equals(items.get(i)))
						p.second++;
		}
		
		gameplay = g;
		image = im;
		init();
		
		
	}
	
	public void init() {
		
		setPreferredSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setBackground(Color.BLACK);
		
		display = new JPanel();
		display.setPreferredSize(new Dimension((int)(width*0.5),(int)(height*0.9)));
		display.setMaximumSize(new Dimension((int)(width*0.5),(int)(height*0.9)));
		display.setFocusable(false);
		display.setBackground(Color.BLACK);
		display.setBorder(Util.genBorder("Loot", 1));
		display.setLayout(new BoxLayout(display,BoxLayout.Y_AXIS));
		
		
		
		DrawPanel dp = new DrawPanel(image,width/2,(int)(height*0.9)/2);
		JPanel picArea = new JPanel();
		picArea.setPreferredSize(new Dimension(width/2+5,(int)(height*0.9)/2+5));
		picArea.setBackground(Color.BLACK);
		
		
		JSeparator line = new JSeparator(SwingConstants.HORIZONTAL);
		line.setPreferredSize(new Dimension(width/2,5));
		
		picArea.add(dp);
		picArea.add(line);
		
		itemArea = new JPanel();
		itemArea.setPreferredSize(new Dimension(width/2+5,(int)(height*0.9)/2-48));
		itemArea.setMaximumSize(new Dimension(width/2+5,(int)(height*0.9)/2-48));
		itemArea.setBackground(Color.BLACK);
		FlowLayout flow = new FlowLayout();
		flow.setHgap(0);
		flow.setVgap(0);
		itemArea.setLayout(flow);
		
		itemNames = new JPanel();
		itemNames.setPreferredSize(new Dimension((int)((width/2+5)*0.75),(int)(height*0.9)/2-48));
		itemNames.setMaximumSize(new Dimension((int)((width/2+5)*0.75),(int)(height*0.9)/2-48));
		itemNames.setBackground(CELL_BLUE);
		itemNames.setLayout(new BoxLayout(itemNames,BoxLayout.Y_AXIS));
		itemNames.setDoubleBuffered(true);
		ArrayList<JTextPane> itemPanes = new ArrayList<JTextPane>();
		for(int i = 0;i<10;i++) {
			itemPanes.add(new JTextPane());
			itemPanes.get(i).setPreferredSize(new Dimension((int)((width/2+5)*0.75),((int)(height*0.9)/2-48)/10));
			itemPanes.get(i).setBorder(Util.genBorder("",1));
			itemPanes.get(i).setFocusable(false);
			itemPanes.get(i).setEditable(false);
			itemPanes.get(i).setBackground(Color.BLACK);
			if(i<loot.size()) {
				itemPanes.get(i).setOpaque(false);
				Util.append(itemPanes.get(i),"  "+loot.get(i).first.name,Color.WHITE, 16, true, 0);
			}
				
			itemNames.add(itemPanes.get(i));
		}
		
		
		
		itemCounts = new JPanel();
		itemCounts.setPreferredSize(new Dimension((int)((width/2+5)*0.25)-16,(int)(height*0.9)/2-48));
		itemCounts.setMaximumSize(new Dimension((int)((width/2+5)*0.25)-16,(int)(height*0.9)/2-48));
		itemCounts.setBackground(CELL_BLUE);
		itemCounts.setLayout(new BoxLayout(itemCounts,BoxLayout.Y_AXIS));
		itemCounts.setDoubleBuffered(true);
		ArrayList<JTextPane> countPanes = new ArrayList<JTextPane>();
		for(int i = 0;i<10;i++) {
			countPanes.add(new JTextPane());
			countPanes.get(i).setPreferredSize(new Dimension((int)((width/2+5)*0.25)-16,((int)(height*0.9)/2-48)/10));
			countPanes.get(i).setBackground(Color.BLACK);
			countPanes.get(i).setBorder(Util.genBorder("",1));
			countPanes.get(i).setFocusable(false);
			countPanes.get(i).setEditable(false);
			if(i<loot.size()) {
				countPanes.get(i).setOpaque(false);
				Util.append(countPanes.get(i),""+loot.get(i).second,Color.WHITE, 15, true, 1);
			}
			itemCounts.add(countPanes.get(i));
		}
		
		itemArea.add(itemNames);
		itemArea.add(itemCounts);
		display.add(picArea);
		display.add(Box.createVerticalStrut(5));
		display.add(itemArea);
		display.setVisible(true);
		
		add(display);
		setVisible(true);
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
		Util.keybind(this, KeyEvent.VK_SHIFT, "up", u -> {
			update(0);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
		Util.keybind(this, KeyEvent.VK_ENTER, "down", u -> {
			update(0);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		Util.keybind(this, KeyEvent.VK_LEFT, "enter", u -> {
			update(0);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		Util.keybind(this, KeyEvent.VK_LEFT, "left", u -> {
			update(0);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		Util.keybind(this, KeyEvent.VK_RIGHT, "right", u -> {
			update(0);
		}, JComponent.WHEN_IN_FOCUSED_WINDOW);
		grabFocus();
	}
	
	
	@SuppressWarnings("unused")
	private ArrayList<Item> getItems(ArrayList<Pair<Item,Integer>> items){
		ArrayList<Item> in = new ArrayList<Item>();
		for(Pair<Item,Integer> i : items)
			in.add(i.first);
		return in;
	}
	
	private boolean lootContains(Item i) {
		for(Pair<Item,Integer> p: loot) 
			if(p.first.equals(i)) return true;
		return false;
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		 gameplay.openPanel = false;
		 setVisible(false);
		 gameplay.r.window.remove(this);
		 gameplay.r.setVisible(true);
		 gameplay.r.grabFocus();
		 gameplay.r.enable();
		 gameplay.openPanel = false;
	}

	@Override
	public void update(int num) {
		if(num==0)
			exit();
		
	}
	
}
