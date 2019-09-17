package UI.panels.editmap;

import UI.app.ApplicationPage;
import UI.app.ApplicationPanel;
import UI.mapview.RPGMap;
import application.mapEditing.editors.MapEditor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class MapViewPanel extends ApplicationPanel {
    private RPGMap map = new RPGMap();
    private MapEditor mapEditor;

    public void createEditor(){
        mapEditor = new MapEditor(getMap(),this);
        addMouseListener(mapEditor);
        addMouseMotionListener(mapEditor);
    }

    public RPGMap getMap() {
        return map;
    }

    public MapEditor getMapEditor() {
        return mapEditor;
    }

    public void setMapEditor(MapEditor mapEditor) {
        this.mapEditor = mapEditor;
    }

    public void setMap(RPGMap map) {
        this.map = map;
    }

    @Override
    public void loadPanel() {
        setPreferredSize(new Dimension(1200,700));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        map.draw(g);
    }
}
