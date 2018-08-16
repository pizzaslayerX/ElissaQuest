package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import items.Item;
import misc.Pair;

import static misc.Util.iw;
import static misc.Util.il;

@SuppressWarnings("serial")
public class InventoryPair extends JPanel {
    public ArrayList<Pair<Item, JTextPane>> items;
    public FlowLayout layout;
    public JPanel itemTab;
    public JTextPane descTab;
    public int width, height, itW, itH;
    public Pair<ArrayList<Item>, Integer> in;

    public InventoryPair(Pair<ArrayList<Item>, Integer> i, String str, int w, int h) {
        il(() -> {
            items = new ArrayList<Pair<Item, JTextPane>>();
            in = i;
            setVisible(false);
            setBorder(genBorder(str, 0));
            setMaximumSize(new Dimension(w, h));
            add(Box.createRigidArea(new Dimension(6, 0)));
            setBackground(Color.BLACK);
            setFocusable(false);

            width = w;
            height = h;

            itemTab = new JPanel();
            itW = width / 2 - 20;
            itH = height - 45;
            itemTab.setPreferredSize(new Dimension(itW, itH));
            itemTab.setFocusable(false);
            itemTab.setVisible(true);
            itemTab.setBackground(Color.BLACK);
            //itemTab.setBorder(genBorder("",0));
            add(itemTab);

            //add(Box.createHorizontalStrut(1));
            descTab = new JTextPane();
            descTab.setPreferredSize(new Dimension(itW, itH - 11));
            descTab.setFocusable(false);
            descTab.setEditable(false);
            descTab.setVisible(true);
            descTab.setBackground(Color.BLACK);
            descTab.setBorder(genBorder("", 0));

            add(descTab);
        });
    }

    public void display() {
        il(() -> {
            descTab.setText("");
            itemTab.removeAll();
            itemTab.add(Box.createRigidArea(new Dimension(itW - 10, itH / 16 + 30)));
            for (Pair<Item, JTextPane> j : items) j.second.removeAll();
            items.clear();
            for (int i = 0; i < in.second; i++) {
                if (i < in.first.size() && in.first.get(i) != null) {
                    items.add(new Pair<Item, JTextPane>(in.first.get(i), new JTextPane()));
                    items.get(i).second.setMaximumSize(new Dimension(itW - 10, itH / 8));
                    items.get(i).second.setPreferredSize(new Dimension(itW - 10, itH / 8));
                    if (i == 0) {
                        items.get(i).second.setBackground(Color.GRAY);
                        append(descTab, in.first.get(i).desc, Color.WHITE, 25, false, 2);
                    } else items.get(i).second.setBackground(Color.BLACK);
                    items.get(i).second.setFocusable(false);
                    items.get(i).second.setEditable(false);
                    items.get(i).second.setVisible(true);
                    items.get(i).second.setBorder(genBorder("", 0));
                    //System.out.println(items.get(i).first.getDesc());
                    itemTab.add(items.get(i).second);
                    append(items.get(i).second, items.get(i).first.name, Color.WHITE, 35, true, 1);
                } else
                    itemTab.add(emptyPane());
            }
            revalidate();
        });
    }

    private JTextPane emptyPane() {
        JTextPane dummy = new JTextPane();
        dummy.setMaximumSize(new Dimension(itW - 10, itH / 8));
        dummy.setPreferredSize(new Dimension(itW - 10, itH / 8));
        dummy.setBackground(Color.BLACK);
        dummy.setFocusable(false);
        dummy.setEditable(false);
        dummy.setVisible(true);
        System.out.println("dummyPane");
        dummy.setBorder(genBorder("", 0));
        append(dummy, " ", Color.WHITE, 35, false, 1);
        return dummy;

    }


    private void append(JTextPane p, String n, Color c, int size, boolean bold, int a) {

        StyledDocument doc = p.getStyledDocument();

        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, c);
        StyleConstants.setBold(keyWord, bold);
        switch (a) {
            case 1:
                StyleConstants.setAlignment(keyWord, StyleConstants.ALIGN_CENTER);
                StyleConstants.setFontFamily(keyWord, "Monospace");
                break;
        }
        doc.setParagraphAttributes(0, doc.getLength(), keyWord, false);
        if (size != 0)
            StyleConstants.setFontSize(keyWord, size);
        try {
            doc.insertString(doc.getLength(), n, keyWord);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private TitledBorder genBorder(String n, int orient) {
        TitledBorder border3 = new TitledBorder(n);
        border3.setTitleColor(Color.WHITE);
        border3.setTitleFont(new Font("Monospaced", Font.BOLD, 18));
        border3.setTitleJustification(TitledBorder.CENTER);
        border3.setTitlePosition(TitledBorder.TOP);
        return border3;
    }
}
