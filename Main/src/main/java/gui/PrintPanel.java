package gui;


	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

	import javax.swing.JPanel;

	public class PrintPanel extends JPanel{
		public String text;
		int width, height,xPos,yPos;
		boolean autoCoords;

		public PrintPanel(int w,int h,int x,int y,boolean t) {
			setPreferredSize(new Dimension(w,h));
			setBackground(Color.BLACK);
			setFocusable(false);
			setDoubleBuffered(true);
			width = w;
			height = h;
			autoCoords = t;
			xPos = x;
			yPos = y;
		}
		
		public void update(String t, Color c, boolean a) {
			text = t;
			autoCoords = a;
			repaint();
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawText(g);
			Toolkit.getDefaultToolkit().sync();
		}
		
	    private void drawText (Graphics g) {
	    	g.setFont(new Font("Monospaced", Font.BOLD, 30));
	        int x = xPos;
	        int y = yPos;
	    	Graphics2D g2d = (Graphics2D) g;
	    	if(autoCoords) {
	    		FontMetrics fm = g2d.getFontMetrics();
	    		Rectangle2D r = fm.getStringBounds(text, g2d);
	    		x = (width - (int) r.getWidth()) / 2;
	    		y = (height - (int) r.getHeight()) / 2 + fm.getAscent();
	    	}
	        g.drawString(text, x, y);
		}
		

	}


