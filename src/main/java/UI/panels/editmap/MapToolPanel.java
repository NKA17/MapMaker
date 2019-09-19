package UI.panels.editmap;

import UI.app.assets.MapAsset;
import UI.app.view.ApplicationPanel;
import application.io.MapIO;
import model.map.mechanics.FogBody;
import model.map.mechanics.FogFactory;
import model.map.structure.RPGMap;
import UI.pages.paletteSelect.PaletteSelectPage;
import UI.pages.start.StartPage;
import UI.windows.BasicWindow;
import application.mapEditing.editors.MapEditor;
import application.mapEditing.tools.DragTool;
import application.mapEditing.tools.Tools;
import model.map.tiles.MapTile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MapToolPanel extends ApplicationPanel {

    private RPGMap map;
    private MapViewPanel mapViewPanel;
    private MapEditor mapEditor;

    public MapToolPanel(MapViewPanel mapViewPanel) {
        this.mapViewPanel = mapViewPanel;
    }

    public RPGMap getMap() {
        return map;
    }

    public void setMap(RPGMap map) {
        this.map = map;
    }

    public MapViewPanel getMapViewPanel() {
        return mapViewPanel;
    }

    public void setMapViewPanel(MapViewPanel mapViewPanel) {
        this.mapViewPanel = mapViewPanel;
    }

    public MapEditor getMapEditor() {
        return mapEditor;
    }

    public void setMapEditor(MapEditor mapEditor) {
        this.mapEditor = mapEditor;
    }

    @Override
    public void loadPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton gridTool = new JButton("Grid");
        gridTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMap().getActiveLayer().toggleGrid();
                getMapViewPanel().repaint();
            }
        });

        JButton paintTool = new JButton("Tile Paint");
        paintTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMapViewPanel().getMapEditor().setTool(Tools.PAINT_TOOL);
            }
        });

        JButton edgeTool = new JButton("Edge Paint");
        edgeTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMapViewPanel().getMapEditor().setTool(Tools.EDGE_PAINT_TOOL);
            }
        });

        JButton tileSelect = new JButton("E");
        tileSelect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                BasicWindow paletteWindow = new BasicWindow();
                paletteWindow.setTitle("Tile Palette");
                paletteWindow.openPage(
                        new PaletteSelectPage("./src/main/resources/assets/map/floor/",Tools.PAINT_TOOL));
                paletteWindow.makeVisible();
            }
        });

        JButton edgeSelect = new JButton("E");
        edgeSelect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                BasicWindow paletteWindow = new BasicWindow();
                paletteWindow.setTitle("Edge Palette");
                paletteWindow.openPage(
                        new PaletteSelectPage("./src/main/resources/assets/map/structure/",Tools.EDGE_PAINT_TOOL));
                paletteWindow.makeVisible();
            }
        });

        JButton assetPaint = new JButton("Graphic");
        assetPaint.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMapViewPanel().getMapEditor().setTool(Tools.GRAPHIC_PAINT_TOOL);
            }
        });

        JButton graphicSelect = new JButton("E");
        graphicSelect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                BasicWindow paletteWindow = new BasicWindow();
                paletteWindow.setTitle("Graphic Palette");
                paletteWindow.openPage(
                        new PaletteSelectPage("./src/main/resources/assets/map/assets/",Tools.GRAPHIC_PAINT_TOOL));
                paletteWindow.makeVisible();
            }
        });

        JButton dragTool = new JButton("Drag");
        dragTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMapViewPanel().getMapEditor().setTool(new DragTool());
            }
        });

        JButton fog = new JButton("Show Fog");
        fog.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MapAsset asset = new MapAsset(
                        new File("C:\\Users\\Nate\\IdeaProjects\\RPGMapMaker\\src\\main\\resources\\" +
                                "assets\\map\\floor\\0mechanics\\black.png"));
                for(FogBody fb: FogFactory.floodFog(map.getActiveLayer())){
                    for(MapTile tile: fb.getTiles()) {
                        for (MapTile floor : map.getActiveLayer().getTileLayer().getTiles()) {
                            if (floor.getGridy() == tile.getGridy() && floor.getGridx() == tile.getGridx()) {
                                floor.setAssetResource(tile.getAssetResource());
                            }
                        }
                    }
                }
                getMapViewPanel().repaint();
            }
        });

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    MapIO.saveMap(map,"newMap.json");
                }catch (Exception v){
                    v.printStackTrace();
                }
            }
        });

        JButton close = new JButton("Home");
        close.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                getObserver().openPage(new StartPage());
            }
        });


        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(gridTool,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(paintTool,gbc);


        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(tileSelect,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(edgeTool,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(edgeSelect,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(assetPaint,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(graphicSelect,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(dragTool,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(4,4,4,10);
        add(fog,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(4,4,4,10);
        add(save,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(4,4,4,10);
        add(close,gbc);
    }
}
