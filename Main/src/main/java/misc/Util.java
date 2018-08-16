package misc;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class Util {
	public static void keybind(JComponent com, int i, String s, Consumer<ActionEvent> c) {
		com.getInputMap().put(KeyStroke.getKeyStroke(i, 0, false), s);
		com.getActionMap().put(s, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.accept(arg0);
			}
		});
	}
	public static void keybind(JComponent com, int i, String s, Consumer<ActionEvent> c, int j) {
		com.getInputMap(j).put(KeyStroke.getKeyStroke(i, 0, false), s);
		com.getActionMap().put(s, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.accept(arg0);
			}
		});
	}
	public static void keybind(JComponent com, int i, String s, Consumer<ActionEvent> c, boolean b) {
		com.getInputMap().put(KeyStroke.getKeyStroke(i, 0, b), s);
		com.getActionMap().put(s, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.accept(arg0);
			}
		});
	}
	public static void keybind(JComponent com, int i, String s, Consumer<ActionEvent> c, int j, boolean b) {
		com.getInputMap(j).put(KeyStroke.getKeyStroke(i, 0, b), s);
		com.getActionMap().put(s, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.accept(arg0);
			}
		});
	}
	public static void keybind(JComponent com, int i, String s, Action a, boolean b) {
		com.getInputMap().put(KeyStroke.getKeyStroke(i, 0, b), s);
		com.getActionMap().put(s, a);
	}
	public static void keybind(JComponent com, int i, String s, Action a, int j, boolean b) {
		com.getInputMap(j).put(KeyStroke.getKeyStroke(i, 0, b), s);
		com.getActionMap().put(s, a);
	}
	
	public static void keybind(JComponent com, int i, String s, Action a) {
		com.getInputMap().put(KeyStroke.getKeyStroke(i, 0, false), s);
		com.getActionMap().put(s, a);
	}
	public static void keybind(JComponent com, int i, String s, Action a, int j) {
		com.getInputMap(j).put(KeyStroke.getKeyStroke(i, 0, false), s);
		com.getActionMap().put(s, a);
	}
	
	public static Action actionMaker(Consumer<ActionEvent> c) {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.accept(arg0);
			}
		};
	}
	
	public static BufferedImage loadImage(String fileName){

        try {
            return ImageIO.read(new File("src/res/pics/"+fileName)); 

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image could not be read");
            System.exit(1);
            return null;
        }
    }
	public static void il(Runnable r) {
		//SwingUtilities.invokeLater(r);
		iw(r);
	}
	public static void iw(Runnable r) {
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
