package UI.factory;

import application.config.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ButtonBag {
    private List<JButton> buttons = new ArrayList<>();

    public JButton createButton(String text){
        JButton button = ButtonFactory.createStaticButton(text);
        return addButton(button);
    }

    public JButton addButton(JButton button){
        buttons.add(button);
        button.addActionListener(new ButtonBagAction(buttons,button));
        //consolidateSize();
        return button;
    }

    private void consolidateSize(){
        int width = 0;
        int height = 0;
        for(JButton b: buttons){
            if(b.getWidth() > width)
                width = b.getWidth();
            if(b.getHeight() > height)
                height = b.getHeight();
        }

        for(JButton b: buttons){
            b.setPreferredSize(new Dimension(width,height));
        }
    }

    public void removeButton(JButton button){
        for (int i = 0; i < button.getActionListeners().length; i++){
            ActionListener a = button.getActionListeners()[i];
            if(a instanceof ButtonBagAction){
                button.getActionListeners()[i] = null;
                break;
            }
        }
        buttons.remove(button);
    }

    class ButtonBagAction implements ActionListener {

        private JButton button;
        private List<JButton> buttonBag;
        public ButtonBagAction(List<JButton> buttonBag, JButton button){
            this.button = button;
            this.buttonBag = buttonBag;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for(JButton b: buttonBag){
                if(b == button){
                    b.setBackground(Configuration.HIGHLIGHT_COLOR);
                }else {
                    b.setBackground(Configuration.COMP_BG_COLOR);
                }
            }
        }
    }
}
