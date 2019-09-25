package application.mapEditing.editors;

import UI.panels.mapView.MapViewPanel;
import application.config.AppState;
import application.mapEditing.toolInterfaces.ITool;
import application.mapEditing.tools.DragTool;
import application.mapEditing.tools.NoneTool;
import model.map.structure.MapLayer;
import model.map.structure.RPGMap;

import java.awt.event.*;

public class MapPlayer extends MapEditor implements MouseListener, MouseMotionListener, KeyListener {

    public MapPlayer(RPGMap map, MapViewPanel mapViewPanel) {
        super(map,mapViewPanel);
    }

    public void mouseClicked(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }



    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==68)//d
            setTool(new DragTool());

        else if(e.getKeyCode()==71)//g
            getMap().getActiveLayer().toggleGrid();

//        else if(e.getKeyCode()==127)//delete
//            deleteActiveDraggable();

        else
            System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
