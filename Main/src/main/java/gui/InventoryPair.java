package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import items.Item;
import misc.Pair;

@SuppressWarnings("serial")
public class InventoryPair extends JPanel{
	private ArrayList<Item> items;
	private JPanel itemTab,descTab;
	
	public InventoryPair(ArrayList<Pair<Item,Integer>> in) {
		setBorder(genBorder("Item Selection",0));
		//initPanels();
		
		
		
	}
	
	public void initPanels() {
		itemTab = new JPanel();
		itemTab.setMaximumSize(new Dimension());
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
