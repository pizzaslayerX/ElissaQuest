import java.util.Collections;
import java.util.Enumeration;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import javax.swing.tree.DefaultMutableTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
 
/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class Maze {
	public final int x;
	public final int y;
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
		for(DIR d : DIR.values()) if(!d.opposite.equals(dir) && (~maze[a][b] & d.bit) == 0 && !(interactives[a][b] instanceof DungeonArea)) tree.add(getNode(a + d.dx, b + d.dy, d));
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
				if(i == 1) maze[spot[0] + i][spot[1] + j] -= 4;
				if(j == -1) maze[spot[0] + i][spot[1] + j] -= 1;
				if(j == 1) maze[spot[0] + i][spot[1] + j] -= 2;
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
}