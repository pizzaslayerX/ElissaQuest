

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JFrame;

public class ElissaQuest extends JApplet implements KeyListener, Runnable, MouseListener {
	private static final long serialVersionUID = 1L;
	public static final Dimension SCREEN_SIZE = new Dimension(1270, 662);
	public static final Dimension GAME_SIZE = new Dimension(1270, 662);
	public static final String TITLE = "Elissa's Quest";
	public static final Color BACKGROUND_COLOR = Color.black;
	public static final Color FOREGROUND_COLOR = Color.white;
	public static JFrame frame;
	public static FlowLayout layout;
	public static Canvas canvas;
	public static boolean isRunning = false;
	private Graphics g;
	private BufferStrategy bs;
	private Thread t;
	public GamePlay gameplay = new GamePlay(this);
	public Player player = new Player(this);

	public static int x =0;
	public static int y = 0;
	

    private BufferedImage testImage;
	public final List<String> returnText = new LinkedList<String>();
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		
			case KeyEvent.VK_A:
					synchronized(returnText) {
						returnText.add("left");
						returnText.notify();
					}
				break;
			case KeyEvent.VK_W:
					synchronized(returnText) {
						returnText.add("up");
						returnText.notify();
					}
				break;
			case KeyEvent.VK_D:
					synchronized(returnText) {
						returnText.add("right");
						returnText.notify();
					}
				break;
			case KeyEvent.VK_S:
					synchronized(returnText) {
						returnText.add("down");
						returnText.notify();
					}
				break;
			/*case KeyEvent.VK_ENTER:
				synchronized(returnText) {
					returnText.add(textIn.getText());
					returnText.notify();
				}*/
		}
		System.out.println(returnText.get(0));
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	
	public static void main(String[] args) {
		ElissaQuest main = new ElissaQuest();
		
		frame = new JFrame();
		layout = new FlowLayout(FlowLayout.LEADING, 0, 0);
		canvas = new Canvas();
		
		canvas.setSize(SCREEN_SIZE);
	    layout.setVgap(0);
	    frame.setResizable(false);
	    frame.setLayout(layout);
		frame.setSize(SCREEN_SIZE);
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//wwijw
		canvas.setPreferredSize(GAME_SIZE);
        canvas.setMaximumSize(GAME_SIZE);
        canvas.setMinimumSize(GAME_SIZE);
		frame.add(canvas);
		frame.add(main);
		frame.setVisible(true);
		frame.pack();
		main.start();

	}
	
	private static final class ImageLoader
    {

        static BufferedImage loadImage(String fileName)
        {
            BufferedImage bi = null;
            //System.err.println("....setimg...." + fileName);

            try {
                bi = ImageIO.read(new File(fileName)); 

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Image could not be read");
                System.exit(1);
            }

            return bi;
        }
    }
	
	private void render() {
        bs = getCanvas().getBufferStrategy();

        if (bs == null) {
            System.out.println("bs is null....");
            getCanvas().createBufferStrategy(3);
            return;
        }

        player.getMove();
        g = getCanvas().getGraphics();
        g.drawImage(testImage, x, y, null);
    }
	
	
	public void init() {
		setFocusable(true);
		testImage = ImageLoader.loadImage("src/res/pics/test.jpg");
		addKeyListener(this);
		addMouseListener(this);
	}
	
	@Override
	public void start() {
		if (isRunning) 
			 return;
	       isRunning = true;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		init();
        System.out.println("run..." + isRunning);
        while (isRunning) {
            //System.err.println("run..." + running);
            tick();
            render();
        }
	}
	
	public void tick() {
		
	}
	
	public void pause(int i) {
		try {
			Thread.sleep(i);
		} catch(InterruptedException e) {}
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public Canvas getCanvas() {

        if(canvas == null)
        {
            System.out.println("Canvas is null");
            return null;
        }

        return canvas;
    }
	
}
