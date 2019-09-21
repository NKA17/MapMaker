package UI.factory;

import javax.swing.*;
import java.awt.*;

public class ButtonFactory {
    public static JButton createButton(String text){
        JButton button = new JButton(text);
        button.setBackground(new Color(100,100,200));
        button.setRolloverEnabled(false);
        button.setFocusPainted(false);
        return button;
    }
}
