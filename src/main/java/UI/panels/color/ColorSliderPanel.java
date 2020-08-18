package UI.panels.color;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.panels.playMap.mob.AddMobPanel;
import application.config.Configuration;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ColorSliderPanel extends ApplicationPanel {
    private static HashMap<String, Color> lastColor = new HashMap<>();
    private String key = "";

    public ColorSliderPanel(String key){
        this.key = key;
        if(lastColor.containsKey(key)){
            Color c = lastColor.get(key);
            r = c.getRed();
            g = c.getGreen();
            b = c.getBlue();
            a = c.getAlpha();
        }else{
            lastColor.put(key, new Color(r,g,b));
        }
    }
    public ColorSliderPanel(String key, boolean useAlpha){
        this(key);
        this.useAlpha = useAlpha;
    }

    private int r = 155;
    private int g = 0;
    private int b = 0;
    private int a = 0;
    private boolean useAlpha = false;
    @Override
    public void loadPanel() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = Configuration.GRIDBAG_INSETS;

        Color color = lastColor.get(key);
        JSlider colorSliderR = ButtonFactory.createSlider(0,255);
        JSlider colorSliderG = ButtonFactory.createSlider(0,255);
        JSlider colorSliderB = ButtonFactory.createSlider(0,255);
        JSlider colorSliderA = ButtonFactory.createSlider(0,255);
        colorSliderR.setValue(color.getRed());
        colorSliderG.setValue(color.getGreen());
        colorSliderB.setValue(color.getBlue());
        colorSliderA.setValue(color.getAlpha());
        ApplicationPanel colorPanel = new ApplicationPanel() {
            @Override
            public void loadPanel() {
                setPreferredSize(new Dimension(32,32));
                repaint();
            }

            public void paintComponent(Graphics gr){
                gr.setColor(new Color(r,g,b));
                gr.fillRect(0,0,32,32);
            }
        };
        colorSliderR.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                r = colorSliderR.getValue();
                lastColor.put(key, new Color(r,g,b, useAlpha? a : 255));
                onColorUpdate();
                repaint();
            }
        });
        colorSliderG.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                g = colorSliderG.getValue();
                lastColor.put(key, new Color(r,g,b, useAlpha? a : 255));
                onColorUpdate();
                repaint();
            }
        });
        colorSliderB.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                b = colorSliderB.getValue();
                lastColor.put(key, new Color(r,g,b, useAlpha? a : 255));
                onColorUpdate();
                repaint();
            }
        });
        colorSliderA.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                a = colorSliderA.getValue();
                lastColor.put(key, new Color(r,g,b, useAlpha? a : 255));
                onColorUpdate();
                repaint();
            }
        });

        JButton doneButton = ButtonFactory.createButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onColorFinalize();
                getObserver().dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy=0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(TextFactory.createLabel("Red: "),gbc);
        gbc.gridx = 0;
        gbc.gridy=1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(TextFactory.createLabel("Green: "),gbc);
        gbc.gridx = 0;
        gbc.gridy=2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(TextFactory.createLabel("Blue: "),gbc);
        gbc.gridx = 0;
        gbc.gridy=3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if(useAlpha)
        add(TextFactory.createLabel("Alpha: "),gbc);

        gbc.gridx = 1;
        gbc.gridy=0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(colorSliderR,gbc);
        gbc.gridx = 1;
        gbc.gridy=1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(colorSliderG,gbc);
        gbc.gridx = 1;
        gbc.gridy=2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(colorSliderB,gbc);
        gbc.gridx = 1;
        gbc.gridy=3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if(useAlpha)
        add(colorSliderA,gbc);

        gbc.gridx = 0;
        gbc.gridy=4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(colorPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy=5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(doneButton,gbc);
    }

    public Color getColor(){
        return new Color(r,g,b,useAlpha ? a : 255);
    }

    public abstract void onColorFinalize();

    public void onColorUpdate(){
        //do nothing
    }
}
