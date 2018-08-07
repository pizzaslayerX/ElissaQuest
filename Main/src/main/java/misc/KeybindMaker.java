package misc;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class KeybindMaker {
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
}
