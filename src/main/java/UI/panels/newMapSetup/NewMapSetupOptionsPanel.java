package UI.panels.newMapSetup;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.pages.editmap.EditMapPage;
import UI.pages.start.StartPage;
import application.config.Configuration;
import application.mapEditing.tools.DragTool;
import model.map.structure.RPGMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewMapSetupOptionsPanel extends ApplicationPanel {
    @Override
    public void loadPanel() {
        Dimension small_d = new Dimension(24,14);
        Dimension med_d = new Dimension(50,28);
        Dimension large_d = new Dimension(80,80);
        Dimension huge_d = new Dimension(120,120);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 2;


        RPGMap map = new RPGMap();
        map.setGridDimensions((int)small_d.getWidth(),(int)small_d.getHeight());

        JButton small = ButtonFactory.createStaticButton(String.format("Small (%d x %d)",(int)small_d.getWidth(),(int)small_d.getHeight()));
        JButton medium = ButtonFactory.createStaticButton(String.format("Medium (%d x %d)",(int)med_d.getWidth(),(int)med_d.getHeight()));
        JButton large = ButtonFactory.createStaticButton(String.format("Large (%d x %d)",(int)large_d.getWidth(),(int)large_d.getHeight()));
        JButton huge = ButtonFactory.createStaticButton(String.format("Huge (%d x %d)",(int)huge_d.getWidth(),(int)huge_d.getHeight()));
        JButton contButton = ButtonFactory.createStaticButton("Make Map");
        JButton cancel = ButtonFactory.createStaticButton("Cancel");

        small.setPreferredSize(new Dimension(400,30));
        small.setBackground(Configuration.COMP_HOVER_BG_COLOR);
        small.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                large.setBackground(Configuration.COMP_BG_COLOR);
                medium.setBackground(Configuration.COMP_BG_COLOR);
                small.setBackground(Configuration.COMP_HOVER_BG_COLOR);
                huge.setBackground(Configuration.COMP_BG_COLOR);
                map.setGridDimensions((int)small_d.getWidth(),(int)small_d.getHeight());
            }
        });

        medium.setPreferredSize(new Dimension(400,30));
        medium.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                small.setBackground(Configuration.COMP_BG_COLOR);
                large.setBackground(Configuration.COMP_BG_COLOR);
                medium.setBackground(Configuration.COMP_HOVER_BG_COLOR);
                huge.setBackground(Configuration.COMP_BG_COLOR);
                map.setGridDimensions((int)med_d.getWidth(),(int)med_d.getHeight());
            }
        });

        large.setPreferredSize(new Dimension(400,30));
        large.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                small.setBackground(Configuration.COMP_BG_COLOR);
                medium.setBackground(Configuration.COMP_BG_COLOR);
                large.setBackground(Configuration.COMP_HOVER_BG_COLOR);
                huge.setBackground(Configuration.COMP_BG_COLOR);
                map.setGridDimensions((int)large_d.getWidth(),(int)large_d.getHeight());
            }
        });

        huge.setPreferredSize(new Dimension(400,30));
        huge.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                small.setBackground(Configuration.COMP_BG_COLOR);
                medium.setBackground(Configuration.COMP_BG_COLOR);
                large.setBackground(Configuration.COMP_BG_COLOR);
                huge.setBackground(Configuration.COMP_HOVER_BG_COLOR);
                map.setGridDimensions((int)huge_d.getWidth(),(int)huge_d.getHeight());
            }
        });

        JTextField mapNameField = TextFactory.createTextField("New_Map");
        mapNameField.setPreferredSize(new Dimension(400,30));

        contButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                map.setName(mapNameField.getText());
                EditMapPage editMapPage = new EditMapPage(map);
                getObserver().openPage(editMapPage);
            }
        });

        cancel.setPreferredSize(contButton.getPreferredSize());
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getObserver().openPage(new StartPage());
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(TextFactory.createLabel("Map Size:"),gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(small,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(medium,gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(large,gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(huge,gbc);


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(TextFactory.createLabel("Map Name:"),gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(mapNameField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new Panel(),gbc);

        Panel navPanel = new Panel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(navPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = 0;
        gbc.anchor = GridBagConstraints.EAST;
        navPanel.add(cancel,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = 0;
        gbc.anchor = GridBagConstraints.EAST;
        navPanel.add(contButton,gbc);


    }
}
