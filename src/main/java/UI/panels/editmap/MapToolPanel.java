package UI.panels.editmap;

import UI.app.assets.MapAsset;
import UI.app.view.ApplicationPage;
import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.playMap.PlayMapPage;
import UI.pages.saveMap.SaveMapPage;
import UI.panels.mapView.MapViewPanel;
import UI.rendering.RPGMapRenderer;
import application.config.AppState;
import application.io.AssetCache;
import application.mapEditing.tools.AssetPaintTool;
import application.mapEditing.transpose.MapTranslator;
import application.mapEditing.tools.PanTool;
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

        JButton gridTool = ButtonFactory.createButtonWithIcon("grid");
        gridTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMap().getActiveLayer().toggleGrid();
                getMapViewPanel().repaint();
            }
        });

        JButton panTool = ButtonFactory.createButtonWithIcon("pan");
        panTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMapViewPanel().getMapEditor().setTool(new PanTool());
            }
        });

        JButton paintTool = ButtonFactory.createButtonWithIcon("brush");
        paintTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMapViewPanel().getMapEditor().setTool(Tools.PAINT_TOOL);
            }
        });

        JButton floodTool = ButtonFactory.createButtonWithIcon("flood");
        floodTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMapViewPanel().getMapEditor().setTool(Tools.FLOOD_FILL_TOOL);
            }
        });

        JButton edgeTool = ButtonFactory.createButtonWithIcon("door");
        edgeTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMapViewPanel().getMapEditor().setTool(Tools.EDGE_PAINT_TOOL);
            }
        });

        JButton tileSelect = ButtonFactory.createButtonWithIcon("palette");
        tileSelect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                BasicWindow paletteWindow = new BasicWindow();
                paletteWindow.setTitle("Tile Palette");
                paletteWindow.openPage(
                        new PaletteSelectPage("./src/main/resources/assets/map/floor/",Tools.PAINT_TOOL),false);
                paletteWindow.makeVisible();
            }
        });

        JButton edgeSelect = ButtonFactory.createButtonWithIcon("palette");
        edgeSelect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                BasicWindow paletteWindow = new BasicWindow();
                paletteWindow.setTitle("Edge Palette");
                paletteWindow.openPage(
                        new PaletteSelectPage("./src/main/resources/assets/map/structure/",Tools.EDGE_PAINT_TOOL),false);
                paletteWindow.makeVisible();
            }
        });

        JButton mechanicsPaint = ButtonFactory.createButtonWithIcon("cog");
        mechanicsPaint.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                ((AssetPaintTool)Tools.MECHANICAL_PAINT_TOOL).setActiveLayer(map.getMechanicsLayer());
                getMapViewPanel().getMapEditor().setTool(Tools.MECHANICAL_PAINT_TOOL);
            }
        });

        JButton mechanicsSelect = ButtonFactory.createButtonWithIcon("palette");
        mechanicsSelect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                BasicWindow paletteWindow = new BasicWindow();
                paletteWindow.setTitle("Mechanics Palette");
                paletteWindow.openPage(
                        new PaletteSelectPage("./src/main/resources/assets/map/mechanics/",Tools.MECHANICAL_PAINT_TOOL),false);
                paletteWindow.makeVisible();
            }
        });

        JButton assetPaint = ButtonFactory.createButtonWithIcon("graphic");
        assetPaint.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                ((AssetPaintTool)Tools.GRAPHIC_PAINT_TOOL).setActiveLayer(map.getActiveLayer().getActiveGraphicLayer());
                getMapViewPanel().getMapEditor().setTool(Tools.GRAPHIC_PAINT_TOOL);
            }
        });

        JButton graphicSelect = ButtonFactory.createButtonWithIcon("palette");
        graphicSelect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                BasicWindow paletteWindow = new BasicWindow();
                paletteWindow.setTitle("Graphic Palette");
                paletteWindow.openPage(
                        new PaletteSelectPage("./src/main/resources/assets/map/assets/",Tools.GRAPHIC_PAINT_TOOL),false);
                paletteWindow.makeVisible();
            }
        });

        JButton dragTool = ButtonFactory.createButtonWithIcon("drag");
        dragTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getMapViewPanel().getMapEditor().setTool(new DragTool());
            }
        });

        JButton fog = ButtonFactory.createButtonWithIcon("fog");
        fog.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MapAsset asset = AssetCache.get(
                        "C:\\Users\\Nate\\IdeaProjects\\RPGMapMaker\\src\\main\\resources\\" +
                                "assets\\map\\floor\\0mechanics\\black.png");
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

        JButton save = ButtonFactory.createButtonWithIcon("save");
        save.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                BasicWindow bs = new BasicWindow();
                SaveMapPage page = new SaveMapPage(map);
                bs.openPage(page,false);
                bs.makeVisible();
            }
        });

        JButton close = ButtonFactory.createButtonWithIcon("home");
        close.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                getObserver().openPage(new StartPage());
            }
        });

        JButton battle = ButtonFactory.createButtonWithIcon("battle");
        battle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                getObserver().openPage(new PlayMapPage(MapTranslator.toFlattenedMap(map)));
            }
        });

        JButton flatten = ButtonFactory.createButtonWithIcon("flatten");
        flatten.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                RPGMapRenderer.saveAsImage(map);
            }
        });

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(battle,gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(panTool,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(gridTool,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 2;
        add(mechanicsPaint,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 2;
        add(mechanicsSelect,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 2;
        add(paintTool,gbc);


        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(tileSelect,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(floodTool,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(edgeTool,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(edgeSelect,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(assetPaint,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(graphicSelect,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(dragTool,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(4,4,4,10);
        //add(fog,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.insets = new Insets(4,4,4,10);
        add(save,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.insets = new Insets(4,4,4,10);
        add(close,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.insets = new Insets(4,4,4,10);
        add(flatten,gbc);
    }
}
