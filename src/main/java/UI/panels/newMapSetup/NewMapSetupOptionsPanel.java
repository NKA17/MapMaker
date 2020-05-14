package UI.panels.newMapSetup;

import UI.app.view.ApplicationAction;
import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.pages.editmap.EditMapPage;
import UI.pages.newMapSetup.NewMapSetupPage;
import UI.pages.paletteSelect.PaletteSelectPage;
import UI.pages.start.StartPage;
import UI.windows.BasicWindow;
import application.config.AppState;
import application.config.Configuration;
import application.io.AssetCache;
import application.io.MapIO;
import application.mapEditing.tools.DragTool;
import application.mapEditing.tools.TilePaintTool;
import application.mapEditing.tools.Tools;
import model.map.structure.RPGMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class NewMapSetupOptionsPanel extends ApplicationPanel {
    private RPGMap map = new RPGMap();
    private OpenPaletteWindowListener openPaletteWindowListener = new OpenPaletteWindowListener(map);

    @Override
    public void loadPanel() {
        Dimension small_d = new Dimension(24,14);
        Dimension med_d = new Dimension(50,50);
        Dimension large_d = new Dimension(80,80);
        Dimension huge_d = new Dimension(100,100);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 2;


        map.setGridDimensions((int)small_d.getWidth(),(int)small_d.getHeight());

        JButton small = ButtonFactory.createStaticButton(String.format("Small (%d x %d)",(int)small_d.getWidth(),(int)small_d.getHeight()));
        JButton medium = ButtonFactory.createStaticButton(String.format("Medium (%d x %d)",(int)med_d.getWidth(),(int)med_d.getHeight()));
        JButton large = ButtonFactory.createStaticButton(String.format("Large (%d x %d)",(int)large_d.getWidth(),(int)large_d.getHeight()));
        JButton huge = ButtonFactory.createStaticButton(String.format("Huge (%d x %d)",(int)huge_d.getWidth(),(int)huge_d.getHeight()));
        JButton contButton = ButtonFactory.createStaticButton("Make Map");
        JButton cancel = ButtonFactory.createStaticButton("Cancel");

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridBagLayout());
        imagePanel.setPreferredSize(new Dimension(58,58));
        imagePanel.setBackground(null);
        BufferedImage bimg = Configuration.DEFAULT_FLOOR_TILE.getImage().getSubimage(0,0,50,50);
        JLabel imageLabel = new JLabel(new ImageIcon(bimg));
        gbc.anchor = GridBagConstraints.WEST;
        imagePanel.add(imageLabel,gbc);

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

        imagePanel.addMouseListener(openPaletteWindowListener);

        JTextField mapNameField = TextFactory.createTextField("New_Map");
        mapNameField.setPreferredSize(new Dimension(400,30));

        contButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                map.setName(mapNameField.getText());
                EditMapPage editMapPage = new EditMapPage(map);
                MapIO.saveMap(map,map.getName(),null);
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
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        add(TextFactory.createLabel("Map Size:"),gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(small,gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(medium,gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(large,gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(huge,gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(TextFactory.createLabel("Map Name:"),gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(mapNameField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(TextFactory.createLabel("Initial Tile:"),gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(imagePanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        add(new Panel(),gbc);

        Panel navPanel = new Panel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 10;
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

    private class OpenPaletteWindowListener implements MouseListener{

        private RPGMap map;

        public OpenPaletteWindowListener(RPGMap map){
            this.map = map;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            BasicWindow paletteWindow = new BasicWindow();
            paletteWindow.setTitle("Graphic Palette");
            PaletteSelectPage psp = new PaletteSelectPage("./src/main/resources/assets/map/floor/", Tools.PAINT_TOOL);
            paletteWindow.openPage(psp ,false);
            psp.setOnDisposeAction(new ApplicationAction() {
                @Override
                public void action() {
                    Configuration.DEFAULT_FLOOR_TILE = (((TilePaintTool)Tools.PAINT_TOOL).getRandomAsset());
                    NewMapSetupPage newMapPage = new NewMapSetupPage();
                    getObserver().openPage(newMapPage);
                }
            });
            paletteWindow.makeVisible();

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
