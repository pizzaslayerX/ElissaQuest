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
			/*
			if(autoCoords) g.drawImage(pic,(width-pic.getWidth())/2,height/20,this);
			else g.drawImage(pic,0,0,this);*/
			Toolkit.getDefaultToolkit().sync();
		}
		
	    public void drawText (Graphics g) {
	    	Graphics2D g2d = (Graphics2D) g;
	        FontMetrics fm = g2d.getFontMetrics();
	        Rectangle2D r = fm.getStringBounds(text, g2d);
	        int x = (this.getWidth() - (int) r.getWidth()) / 2;
	        int y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
	        g.drawString(text, x, y);
		}
		
		
		private void drawObjects(Graphics g) {
			
			//g.setFont(new Font("Monospaced", Font.BOLD, 30));
		}

	}


