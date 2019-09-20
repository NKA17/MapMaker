package UI.panels.editmap;

import UI.app.view.ApplicationPanel;
import model.map.structure.RPGMap;
import application.mapEditing.editors.MapEditor;

import java.awt.*;

public class MapViewPanel extends ApplicationPanel {
    private RPGMap map ;
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
        //g.setColor(new Color(0,0,0));
        //g.fillRect(0,0,1200,700);
        map.draw(g);
    }
}
