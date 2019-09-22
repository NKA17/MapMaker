package UI.factory;

import application.config.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class ButtonFactory {
    public static JButton createButton(String text){
        JButton button = new JButton(text);
        button.setBackground(Configuration.COMP_BG_COLOR);
        button.setBorder(new BubbleBorder(Color.BLACK,2,6));
        button.setRolloverEnabled(false);
        button.setFocusPainted(false);
        button.setFont(TextFactory.LABEL_FONT);
        return button;
    }

    public static JButton createButtonWithIcon(String text){
        JButton jButton;
        try{
            jButton = new JButton(
                    new ImageIcon(ImageIO.read(new File("./src/main/resources/icons/"+text+".png"))));
        }catch (Exception e){
            jButton = new JButton(text);
        }
        jButton.setBackground(Configuration.COMP_BG_COLOR);
        jButton.setRolloverEnabled(false);
        jButton.setFocusPainted(false);
        jButton.setBorder(new BubbleBorder(Color.BLACK,2,6));
        return jButton;
    }


}
