package maze;
import java.util.Collections;
import java.util.Enumeration;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import entities.Enemy;
import entities.Interactive;
import misc.Pair;
import run.ElissaRunner;
import run.GamePlay;

import java.util.ArrayList;
import java.util.Arrays;
 
/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class Maze{
	public final int x;
	public final int y;
	public int playerx;
	public int playery;
	public final int[][] maze;
	public final int startx;
	public final int starty;
	public final int endx;
	public final int endy;
	public final Interactive[][] interactives;
	
	
 
	public Maze(int x, int y) {
		this.x = x;
		this.y = y;
		maze = new int[this.x][this.y];
		interactives = new Interactive[this.x][this.y];
		generateDungeons(4);
		generateMaze(0, 0);
		//display();
		ArrayList<int[]> ar = new ArrayList<int[]>();
		for(int i = 0; i < x; i++) for(int j = 0; j < y; j++)
		if(maze[i][j] == 1 || maze[i][j] == 2 || maze[i][j] == 4 || maze[i][j] == 8) ar.add(new int[]{i, j, maze[i][j]});
		int[] startPos = ar.get((int)(Math.random()*ar.size()));
		startx = startPos[0];
		starty = startPos[1];
		ArrayList<DefaultMutableTreeNode> endNode = getEndGameSpots(startx, starty, 1);
		endx = ((int[]) endNode.get(0).getUserObject())[0];
		endy = ((int[]) endNode.get(0).getUserObject())[1];
		ArrayList<DefaultMutableTreeNode> spots = new ArrayList<DefaultMutableTreeNode>();
		ArrayList<DefaultMutableTreeNode> enemySpots = new ArrayList<DefaultMutableTreeNode>();
		enumEnemyNodes(getTree(startx, starty), spots, enemySpots);
		Collections.shuffle(enemySpots);
		for(int i = 0; i < enemySpots.size(); i++) {
			if(i < enemySpots.size()*.1) {
				modifyNodeValue(enemySpots.get(i), interactives, Enemy.Enemies.skeleton());
			} else {
				modifyNodeValue(enemySpots.get(i), interactives, new Chest(true));
			}
		}
		ArrayList<int[]> dungeons = new ArrayList<int[]>();
		for(int i = 0; i < x; i++) for(int j = 0; j < y; j++) {
			if(interactives[i][j] instanceof Dungeon) dungeons.add(new int[]{i, j});
			//if(interactives[i][j] == null && (i != startx || j != starty)) spots.add(new int[] {i, j});
		}
		for(int[] i : dungeons) {
			ArrayList<Pair<int[], DIR>> exit = new ArrayList<Pair<int[],DIR>>();
			for(DIR d : DIR.values()) if(between(i[0]+2*d.dx, x) && between(i[1]+2*d.dy, y)) exit.add(new Pair<int[],DIR>(new int[] {i[0]+d.dx, i[1]+d.dy}, d));
			Pair<int[], DIR> a = exit.get((int)(Math.random()*exit.size()));
			maze[a.first[0]][a.first[1]] |= a.second.bit;
			maze[a.first[0] + a.second.dx][a.first[1]+a.second.dy] |= a.second.opposite.bit;
		}
		display(); //debug
		//enemyMove();
	}
	
	public void display() {
		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < x; j++) {
				System.out.print(((maze[j][i] & 8) == 0 ? "| " : "  ") + (interactives[j][i] instanceof Interactive ? "x " : "  "));
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}
	
	public ArrayList<DefaultMutableTreeNode> getEndGameSpots(int a, int b, double w) {
		DefaultMutableTreeNode tree = getTree(a, b);
		ArrayList<DefaultMutableTreeNode> spots = new ArrayList<DefaultMutableTreeNode>();
		enumEndNodes(tree, spots, w);
		return spots;
	};
	
	private static void enumEndNodes(DefaultMutableTreeNode tree, ArrayList<DefaultMutableTreeNode> spots, double w) {
		enumNodes(tree, spots, (u, v) -> u.getChildCount() == 0 && u.getLevel() >= w*((DefaultMutableTreeNode) v.getRoot()).getDepth(), false);
	}
	
	public static void enumNodes(DefaultMutableTreeNode tree, ArrayList<DefaultMutableTreeNode> spots, BiPredicate<DefaultMutableTreeNode, DefaultMutableTreeNode> p, boolean b) {
		enumNodes(tree, t -> {spots.add(t);}, p, (u, v) -> u, b);
	}
	
	@SuppressWarnings("unchecked")
	public static void enumNodes(DefaultMutableTreeNode tree, Consumer<DefaultMutableTreeNode> function, BiPredicate<DefaultMutableTreeNode, DefaultMutableTreeNode> p, BiFunction<DefaultMutableTreeNode, DefaultMutableTreeNode, DefaultMutableTreeNode> nextTree, boolean b) {
		for(Enumeration<DefaultMutableTreeNode> e = tree.children(); e.hasMoreElements();) {
			DefaultMutableTreeNode node = e.nextElement();
			boolean bool = p.test(node, tree);
			if(bool) function.accept(node);
			if(b || !bool) enumNodes(nextTree.apply(node, tree), function, p,  nextTree, b);
		}
	}
	
	public void enumNodes1(DefaultMutableTreeNode tree, Consumer<DefaultMutableTreeNode> function, BiPredicate<DefaultMutableTreeNode, DefaultMutableTreeNode> p, BiFunction<DefaultMutableTreeNode, DefaultMutableTreeNode, DefaultMutableTreeNode> nextTree, boolean b, boolean[] end) throws NodeException{
		end[0] = false;
		for(Enumeration<DefaultMutableTreeNode> e = tree.children(); e.hasMoreElements();) {
			DefaultMutableTreeNode node = e.nextElement();
			boolean bool = p.test(node, tree);
			if(bool) function.accept(node);
			if(end[0]) throw new NodeException();
			if(b || !bool) enumNodes1(nextTree.apply(node, tree), function, p,  nextTree, b, end);
		}
	}
	
	public void enumEnemyNodes(DefaultMutableTreeNode tree, ArrayList<DefaultMutableTreeNode> spots, ArrayList<DefaultMutableTreeNode> enemySpots) { //change some stuff here
		spots.add(tree);
		enumNodes(tree, t -> {}, (u,v) -> u.getLevel() > (7+(int)(8*Math.random())), (u,v) -> {spots.add(u); System.out.println(((int[])u.getUserObject())[0] + " " + ((int[])u.getUserObject())[1]); return u;}, false);
		//splatter
		System.out.println("hi");
		boolean[] end = new boolean[] {false};
		try {
			enumNodes1(tree, t -> {
				enemySpots.add(t);
				//modifyNodeValue(t, interactives, Enemy.Enemies.skeleton()); //add enemy (or chest) or add to enemy/chest list
				//enumNodes(getTree(startx, starty), spots, (u,v) -> u.getDepth() <= (7+(int)(7*Math.random())), true);
				//generate splatter, then repeat this
				System.out.println(((int[])t.getUserObject())[0] + "         " + ((int[])t.getUserObject())[1]);
				enumEnemyNodes(getTree(((int[])t.getUserObject())[0],((int[])t.getUserObject())[1]), spots, enemySpots);
				//shouldn't be able to do anything here
				//add stop?
				end[0] = true;
			}, (u,v) -> {
				for(DefaultMutableTreeNode t : spots) if(sameNode(t,u)) return false;
				return true;
			}, (u, v) -> u, false, end);
		} catch (NodeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public static int getNodeValue(DefaultMutableTreeNode tree, int[][] a) {
		return a[((int[]) tree.getUserObject())[0]][((int[]) tree.getUserObject())[1]];
	}
	
	public static <T> T getNodeValue(DefaultMutableTreeNode tree, T[][] a) {
		return a[((int[]) tree.getUserObject())[0]][((int[]) tree.getUserObject())[1]];
	}
	
	public static <T> void modifyNodeValue(DefaultMutableTreeNode tree, T[][] a, T t) {
		a[((int[]) tree.getUserObject())[0]][((int[]) tree.getUserObject())[1]] = t;
	}
	
	public DefaultMutableTreeNode getTree(int a, int b) {
		return getNode(a, b, null);
	}
	
	public DefaultMutableTreeNode getTree(int a, int b, int c) {
		return getNode(a, b, null, c);
	}
	
	public boolean sameNode(DefaultMutableTreeNode a, DefaultMutableTreeNode b) {
		return Arrays.equals((int[])a.getUserObject(), (int[])b.getUserObject());
	}
	
	private DefaultMutableTreeNode getNode(int a, int b, DIR dir) {
		DefaultMutableTreeNode tree = new DefaultMutableTreeNode(new int[]{a, b});
		//System.out.println(a + " " + b);
		for(DIR d : DIR.values()) if(!d.opposite.equals(dir) && (~maze[a][b] & d.bit) == 0 /*&& !(interactives[a][b] instanceof DungeonArea)*/) tree.add(getNode(a + d.dx, b + d.dy, d));
		return tree;
	}
	
	private DefaultMutableTreeNode getNode(int a, int b, DIR dir, int c) {
		DefaultMutableTreeNode tree = new DefaultMutableTreeNode(new int[]{a, b});
		//System.out.println(a + " " + b);
		if(c > 0) for(DIR d : DIR.values()) if(!d.opposite.equals(dir) && (~maze[a][b] & d.bit) == 0 && !(interactives[a][b] instanceof DungeonArea)) tree.add(getNode(a + d.dx, b + d.dy, d, --c));
		return tree;
	}
	
	public void enemyMove() {
		ArrayList<int[]> enemies = new ArrayList<int[]>();
		for(int i = 0; i < x; i++) for(int j = 0; j < y; j++) {
			if(interactives[i][j] instanceof Enemy) enemies.add(new int[]{i, j});
		}
		Collections.shuffle(enemies);
		ArrayList<TreeNode[]> paths = new ArrayList<TreeNode[]>();
		for(int[] i : enemies) {
			DefaultMutableTreeNode e = getTree(i[0], i[1], 12);
			int count1 = 0;
			for(Enumeration<DefaultMutableTreeNode> ee = e.breadthFirstEnumeration();ee.hasMoreElements();) {
				DefaultMutableTreeNode node = ee.nextElement();
				count1++;
			}
			System.out.println(i[0] + " " + i[1] + "               "+count1);
			enumNodes(e, u -> {paths.add(u.getPath());}, (u,v) -> getNodeValue(u, interactives) instanceof Enemy, (u,v) -> u, false);
			for(TreeNode[] p : paths) {
				try {
					((DefaultMutableTreeNode)p[Math.min(6, p.length - 1)]).removeFromParent(); //Math.min(6, p.length - 1)       6
				} catch (ArrayIndexOutOfBoundsException a){
					System.out.println("YA DONE FUCKED UP NOW KID");
					System.out.println(p.length);
					a.printStackTrace();
				}
			}
			int count = 0;
			System.out.println("depth " + e.getDepth());
			int d = distance(Math.min(5, e.getDepth()));
			System.out.println("d " + d);
			ArrayList<DefaultMutableTreeNode> spots = new ArrayList<DefaultMutableTreeNode>();
			for(Enumeration<DefaultMutableTreeNode> ee = e.breadthFirstEnumeration();ee.hasMoreElements();) {
				count++;
				DefaultMutableTreeNode node = ee.nextElement();
				if(node.getLevel() == d) spots.add(node);
			}
			int[] spot = (int[])spots.get((int)(spots.size()*Math.random())).getUserObject();
			System.out.println(count);
			System.out.println("spot " + spot[0] + " " + spot[1]);
			arraySwitch(interactives, i[0], i[1], spot[0], spot[1]);
		}
	}
	
	private int distance(int max) {
		double rand = 1.5*harm(max)*Math.random();
		System.out.println("rand " + rand);
		for(int j = 1; j <= max; j++) {
			System.out.println("harm" + harm(j));
			if(rand < harm(j)) return j;
		}
		return 0;
	}
	
	private double harm(int i) {
		double res = 0;
		for(int j = 1; j <= i; j++) res += 1d/j;
		return res;
	}
 
	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, x) && between(ny, y)
					&& (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny);
			}
		}
	}
 
	public void generateDungeons(int a) {
		ArrayList<int[]> possibleSpots = new ArrayList<int[]>();
		for(int i = 1; i < x - 1; i++) for(int j = 1; j < y - 1; j++) possibleSpots.add(new int[]{i, j});
		for(int i = -2; i < 3; i++) for(int j = -2; j < 3; j++) arrayRemove(possibleSpots, startx + i, starty + j);
		for(int i = 0; i < a; i++) makeDungeon(possibleSpots);
	}
	
	public void makeDungeon(ArrayList<int[]> spots) {
		int[] spot = spots.get((int)(Math.random()*spots.size()));
		Dungeon dungeon = new Dungeon(new ArrayList<int[]>(), null, null); //  change null null
		for(int i = -3; i < 4; i++) for(int j = -3; j < 4; j++) {
			arrayRemove(spots, spot[0] + i, spot[1] + j);
			if(i < 2 && i > -2 && j < 2 && j > -2) {
				if(i != 0 || j != 0) {
					interactives[spot[0] + i][spot[1] + j] = new DungeonArea(dungeon, spot);
					dungeon.area.add(new int[]{spot[0] + i, spot[1] + j});
				}
				maze[spot[0] + i][spot[1] + j] = 15;
				if(i == -1) maze[spot[0] + i][spot[1] + j] -= 8;
				if(i == 1) maze[spot[0] + i][spot[1] + j] -= 2;
				if(j == -1) maze[spot[0] + i][spot[1] + j] -= 1;
				if(j == 1) maze[spot[0] + i][spot[1] + j] -= 4;
			}
		}
		interactives[spot[0]][spot[1]] = dungeon;
	}
	
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}
 
	private enum DIR {
		N(1, 0, -1), E(2, 1, 0), S(4, 0, 1), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;
 
		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}
 
		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};
	
	private static boolean arrayRemove(ArrayList<int[]> ar, int... o) {
		for(int[] i : ar) if(Arrays.equals(i, o)){
			 ar.remove(i);
			 return true;
		}
		return false;
	}
	
	private static <T> void arraySwitch(T[][] arr, int a, int b, int c, int d) {
		T temp = arr[a][b];
		arr[a][b] = arr[c][d];
		arr[c][d] = temp;
	}

	/*public void interact(GamePlay g) {
		while(!g.returnText.isEmpty() /*&& interactives[endx][endy] != null*//*) {
			switch(g.returnText) {
				case "up":
					if((((maze[playerx][playery] & 1) != 0  && g.player.x % g.r.mazeSize <= g.r.mazeSize - 16)|| g.player.y % g.r.mazeSize != 0) ) g.player.y-=g.scale;
					break;
				case "down":
					if((((maze[playerx][playery] & 4) != 0 && g.player.x % g.r.mazeSize <= g.r.mazeSize - 16) || g.player.y % g.r.mazeSize != g.r.mazeSize - 16) ) g.player.y+=g.scale;
					break;
				case "right":
					if((((maze[playerx][playery] & 2) != 0 && g.player.y % g.r.mazeSize <= g.r.mazeSize - 16)|| g.player.x % g.r.mazeSize != g.r.mazeSize - 16) ) g.player.x+=g.scale;
					break;
				case "left":
					if((((maze[playerx][playery] & 8) != 0 && g.player.y % g.r.mazeSize <= g.r.mazeSize - 16)|| g.player.x % g.r.mazeSize != 0) )g.player.x-=g.scale;
					break;
				case "uparrow":
					g.r.ytrans += 4;
					break;
				case "downarrow":
					g.r.ytrans -= 4;
					break;
				case "leftarrow":
					g.r.xtrans += 4;
					break;
				case "rightarrow":
					g.r.xtrans -= 4;
					break;
			}
			g.r.repaint();
			playerx=(g.player.x+g.r.mazeSize/2)/g.r.mazeSize;
			playery=(g.player.y+g.r.mazeSize/2)/g.r.mazeSize;
			if(interactives[playerx][playery] != null) interactives[playerx][playery].interact(g);
			g.userWait();
		}
	}*/

	private class NodeException extends Exception {
		
	}
}