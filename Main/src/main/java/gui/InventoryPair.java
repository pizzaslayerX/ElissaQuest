package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import items.Item;
import misc.Pair;

@SuppressWarnings("serial")
public class InventoryPair extends JPanel{
	private ArrayList<Pair<Item,JTextPane>> items;
	public FlowLayout layout;
	private JTextPane itemTab,descTab;
	private int width,height;
	public InventoryPair(ArrayList<Pair<Item,Integer>> in,String str,int w, int h) {
		items = new ArrayList<Pair<Item,JTextPane>>();
		
		setVisible(false);
		setBorder(genBorder(str,0));
		setMaximumSize(new Dimension(w,h));
		add(Box.createRigidArea(new Dimension(6,0)));
		setBackground(Color.BLACK);
		setFocusable(false);
		
		width=w;
		height=h;
		
		itemTab = new JTextPane();		
		itemTab.setPreferredSize(new Dimension(width/2-20,height-45));
		itemTab.setFocusable(false);
		itemTab.setVisible(true);
		itemTab.setBackground(Color.BLACK);
		itemTab.setBorder(genBorder("",0));
		add(itemTab);
		//add(Box.createHorizontalStrut(1));
		descTab = new JTextPane();		
		descTab.setPreferredSize(new Dimension(width/2-20,height-45));
		descTab.setFocusable(false);
		descTab.setVisible(true);
		descTab.setBackground(Color.BLACK);
		descTab.setBorder(genBorder("",0));
		add(descTab);
		
		for(int i=0;i<in.size();i++) {
			items.add(new Pair<Item,JTextPane>(in.get(i).first,new JTextPane()));
			itemTab.add(new JTextPane());
				
			
		}
	}
	
		
	
	
	
	private TitledBorder genBorder(String n,int orient) {
		TitledBorder border3 = new TitledBorder(n);
        border3.setTitleColor(Color.WHITE);
        border3.setTitleFont(new Font("Monospaced", Font.BOLD, 18));
        border3.setTitleJustification(TitledBorder.CENTER);
        border3.setTitlePosition(TitledBorder.TOP);
        return border3;
	}
}
