package items;
//deprecated
import java.util.ArrayList;
import java.util.Arrays;

import misc.Pair;

public class Inventory {
	ArrayList<ArrayList<Pair<Item, Integer>>> inv; //create subinv class to replace ArrayList<Pair<Item, Integer>>?
	
	public Inventory() {
		inv = new ArrayList<ArrayList<Pair<Item, Integer>>>();
	}
	
	public void add(Item i) {
		for(ArrayList<Pair<Item, Integer>> ar : inv) if(ar.get(0).first.getClass().equals(i.getClass())) {
			for(Pair<Item, Integer> p : ar) if(p.first.equals(i)) {
				p.second++;
				return;
			}
			ar.add(new Pair<Item, Integer>(i, 1));
			return;
		}
		inv.add(new ArrayList<Pair<Item, Integer>>(Arrays.asList(new Pair<Item, Integer>(i, 1))));
	}
	
	public ArrayList<Pair<Item, Integer>> get(int a) {
		return inv.get(a);
	}
	
	public ArrayList<Pair<Item, Integer>> getSubInv(Class<?> c) {
		for(ArrayList<Pair<Item, Integer>> ar : inv) if(ar.get(0).first.getClass().equals(c)) return ar;
		return null;
	}
	
	public ArrayList<Pair<Item, Integer>> getSubInv(String s) {
		try {
			return getSubInv(Class.forName(s));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<Pair<Item, Integer>> getWeaponInv() {
		return getSubInv("items.Weapon");
	}
	
	public ArrayList<Pair<Item, Integer>> getConsumableInv() {
		return getSubInv("items.Consumable");
	}
	
	public ArrayList<Pair<Item, Integer>> getEquipmentInv() {
		return getSubInv("items.Equipment");
	}
	
	public Item get(int a, int b) {
		return inv.get(a).get(b).first;
	}
	
	public boolean remove(Item i) {
		for(ArrayList<Pair<Item, Integer>> ar : inv) if(ar.get(0).first.getClass().equals(i.getClass()))
		for(Pair<Item, Integer> p : ar) if(p.first.equals(i)) {
			if(--p.second == 0) ar.remove(p);
			if(ar.size() == 0) inv.remove(ar);
			return true;
		}
		return false;
	}
	
	public void clear() {
		inv.clear();
	}
}
