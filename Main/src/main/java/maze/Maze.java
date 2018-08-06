package maze;
import java.util.Collections;
import java.util.Enumeration;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import javax.swing.tree.DefaultMutableTreeNode;

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
public class Maze implements Interactive{
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
		ArrayList<int[]> ar = new ArrayList<int[]>();
		for(int i = 0; i < x; i++) for(int j = 0; j < y; j++)
		if(maze[i][j] == 1 || maze[i][j] == 2 || maze[i][j] == 4 || maze[i][j] == 8) ar.add(new int[]{i, j, maze[i][j]});
		int[] startPos = ar.get((int)(Math.random()*ar.size()));
		startx = startPos[0];
		starty = startPos[1];
		ArrayList<DefaultMutableTreeNode> endNode = getEndGameSpots(startx, starty, 1);
		endx = ((int[]) endNode.get(0).getUserObject())[0];
		endy = ((int[]) endNode.get(0).getUserObject())[1];
		ArrayList<int[]> dungeons = new ArrayList<int[]>();
		ArrayList<int[]> spots = new ArrayList<int[]>();
		for(int i = 0; i < x; i++) for(int j = 0; j < y; j++) {
			if(interactives[i][j] instanceof Dungeon) dungeons.add(new int[]{i, j});
			if(interactives[i][j] == null && (i != startx || j != starty)) spots.add(new int[] {i, j});
		}
		for(int[] i : dungeons) {
			ArrayList<Pair<int[], DIR>> exit = new ArrayList<Pair<int[],DIR>>();
			for(DIR d : DIR.values()) if(between(i[0]+2*d.dx, x) && between(i[1]+2*d.dy, y)) exit.add(new Pair<int[],DIR>(new int[] {i[0]+d.dx, i[1]+d.dy}, d));
			Pair<int[], DIR> a = exit.get((int)(Math.random()*exit.size()));
			maze[a.first[0]][a.first[1]] |= a.second.bit;
			maze[a.first[0] + a.second.dx][a.first[1]+a.second.dy] |= a.second.opposite.bit;
		}
		Collections.shuffle(spots);
		for(int i = 0; i < spots.size()/20d; i++) {
			if(i < spots.size()*7/200d) {
				interactives[spots.get(i)[0]][spots.get(i)[1]] = Enemy.Enemies.skeleton();
			} else {
				interactives[spots.get(i)[0]][spots.get(i)[1]] = new Chest();
			}
		}
		
		display(); //debug
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
				System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
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
	}
	
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
			if(p.test(node, tree)) function.accept(node);
			if(b || !p.test(node, tree)) enumNodes(nextTree.apply(node, tree), function, p,  nextTree, b);
		}
	}
	
	public static int getNodeValue(DefaultMutableTreeNode tree, int[][] a) {
		return a[((int[]) tree.getUserObject())[0]][((int[]) tree.getUserObject())[1]];
	}
	
	public static <T> T getNodeValue(DefaultMutableTreeNode tree, T[][] a) {
		return a[((int[]) tree.getUserObject())[0]][((int[]) tree.getUserObject())[1]];
	}
	
	public DefaultMutableTreeNode getTree(int a, int b) {
		return getNode(a, b, null);
	}
	
	private DefaultMutableTreeNode getNode(int a, int b, DIR dir) {
		DefaultMutableTreeNode tree = new DefaultMutableTreeNode(new int[]{a, b});
		for(DIR d : DIR.values()) if(!d.opposite.equals(dir) && (~maze[a][b] & d.bit) == 0 /*&& !(interactives[a][b] instanceof DungeonArea)*/) tree.add(getNode(a + d.dx, b + d.dy, d));
		return tree;
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

	@Override
	public void interact(GamePlay g) {
		while(!g.returnText.isEmpty() /*&& interactives[endx][endy] != null*/) {
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
			}
			g.r.repaint();
			playerx=(g.player.x+g.r.mazeSize/2)/g.r.mazeSize;
			playery=(g.player.y+g.r.mazeSize/2)/g.r.mazeSize;
			if(interactives[playerx][playery] != null) interactives[playerx][playery].interact(g);
			g.userWait();
		}
	}

	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		
	}
}