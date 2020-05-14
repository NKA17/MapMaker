package UI.factory;

import UI.app.view.ApplicationAction;
import application.config.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        button.setFocusable(false);
        button.addMouseListener(new EnterListener(button));
        return button;
    }

    public static JButton createButton(String text,ApplicationAction rolloverExtension){
        JButton button = new JButton(text);
        button.setBackground(Configuration.COMP_BG_COLOR);
        button.setBorder(new BubbleBorder(Color.BLACK,2,6));
        button.setRolloverEnabled(false);
        button.setFocusPainted(false);
        button.setFont(TextFactory.LABEL_FONT);
        button.setFocusable(false);
        EnterListener listener = new EnterListener(button);
        listener.setRolloverExtension(rolloverExtension);
        button.addMouseListener(listener);
        return button;
    }

    public static JButton createButton(String text,ApplicationAction rolloverExtension,ApplicationAction rolloutExtension){
        JButton button = new JButton(text);
        button.setBackground(Configuration.COMP_BG_COLOR);
        button.setBorder(new BubbleBorder(Color.BLACK,2,6));
        button.setRolloverEnabled(false);
        button.setFocusPainted(false);
        button.setFont(TextFactory.LABEL_FONT);
        button.setFocusable(false);
        EnterListener listener = new EnterListener(button);
        listener.setRolloverExtension(rolloverExtension);
        listener.setRolloutExtension(rolloutExtension);
        button.addMouseListener(listener);
        return button;
    }

    public static JButton createStaticButton(String text){
        JButton button = new JButton(text);
        button.setBackground(Configuration.COMP_BG_COLOR);
        button.setBorder(new BubbleBorder(Color.BLACK,2,6));
        button.setRolloverEnabled(false);
        button.setFocusPainted(false);
        button.setFont(TextFactory.LABEL_FONT);
        button.setFocusable(false);
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
        jButton.setFocusable(false);
        jButton.addMouseListener(new EnterListener(jButton));
        return jButton;
    }

    public static JSlider createSlider(int min, int max){
        JSlider slider = new JSlider();
        slider.setMaximum(min);
        slider.setMaximum(max);
        slider.setFocusable(false);
        slider.setUI(new SleekSlideUI(slider));
        slider.setBackground(null);

        return slider;
    }

    private static class EnterListener implements MouseListener{
        private JButton button;

        private ApplicationAction rolloverExtension;
        private ApplicationAction rolloutExtension;

        public ApplicationAction getRolloverExtension() {
            return rolloverExtension;
        }

        public void setRolloverExtension(ApplicationAction rolloverExtension) {
            this.rolloverExtension = rolloverExtension;
        }

        public ApplicationAction getRolloutExtension() {
            return rolloutExtension;
        }

        public void setRolloutExtension(ApplicationAction rolloutExtension) {
            this.rolloutExtension = rolloutExtension;
        }

        public EnterListener(JButton button) {
            this.button = button;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(Configuration.COMP_HOVER_BG_COLOR);
            if(rolloverExtension!=null)
                rolloverExtension.action();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(Configuration.COMP_BG_COLOR);
            if(rolloutExtension!=null)
                rolloutExtension.action();
        }
    }

}
