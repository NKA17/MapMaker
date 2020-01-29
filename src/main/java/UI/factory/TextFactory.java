package UI.factory;

import UI.windows.BasicWindow;
import application.config.Configuration;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextFactory {
    public static final Font LABEL_FONT = new Font("Yu Gothic UI",Font.BOLD,12);
    public static final Font TITLE_FONT = new Font("Yu Gothic UI",Font.BOLD,16);

    public static JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(new Color(180,180,180));
        return label;
    }

    public static JLabel createTitle(String text){
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(new Color(210,210,210));
        return label;
    }

    public static JTextField createTextField(String defaultText){
        JTextField tf = new JTextField(defaultText);
        tf.setBackground(Configuration.WINDOW_BG_COLOR);
        tf.setForeground(new Color(180,180,180));
        tf.setFont(LABEL_FONT);
        tf.setBorder(null);
        return tf;
    }

    public static JTextField createTextField(String defaultText, int charLimit){
        JTextField tf = createTextField(defaultText);
        tf.setDocument(new FieldCharLimit(charLimit));
        try {
            tf.getDocument().insertString(0, defaultText, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        return tf;
    }

    public static JTextArea createTextArea(String text){
        JTextArea area = new JTextArea(text);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setFont(TextFactory.LABEL_FONT);
        area.setForeground(new Color(180,180,180));
        area.setBackground(Configuration.WINDOW_BG_COLOR);

        return area;
    }

    public static JTextArea createTextArea(String text, int charLimit){
        JTextArea area = createTextArea(text);
        area.setDocument(new FieldCharLimit(charLimit));
        try {
            area.getDocument().insertString(0, text, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return area;
    }



    //Myriad Pro
    //Yu Gothic UI
    //Microsoft YaHei UI
    //Nirmala UI
    //Quicksand
    //Segoe UI
    //Segoe UI Historic
    public static void main(String[] args){
        BasicWindow bs = new BasicWindow();
        Panel panel = new Panel();
        String[] strings =new String[]
                {"Myriad Pro",
        "Yu Gothic UI",
        "Microsoft YaHei UI",
        "Nirmala UI",
        "Quicksand",
        "Segoe UI",
        "Segoe UI Historic"};

        for(String s: strings){
            JButton b = ButtonFactory.createButton(s);
            b.setFont(new Font(s,Font.BOLD,12));
            panel.add(b);
        }
        bs.getContentPane().add(panel);
        bs.makeVisible();
    }
}
class FieldCharLimit extends PlainDocument {
    private int limit;

    FieldCharLimit(int limit) {
        super();
        this.limit = limit;
    }

    public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
