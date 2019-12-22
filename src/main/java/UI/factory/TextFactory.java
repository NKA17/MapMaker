package UI.factory;

import UI.windows.BasicWindow;
import application.config.Configuration;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

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
