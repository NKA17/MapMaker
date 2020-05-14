package application.mapEditing.editors;

import application.config.AppState;
import application.mapEditing.tools.DragTool;
import application.mapEditing.tools.PanTool;
import application.mapEditing.tools.Tools;
import model.map.structure.MapLayer;
import model.map.structure.RPGMap;
import UI.panels.mapView.MapViewPanel;
import application.mapEditing.toolInterfaces.ITool;
import application.mapEditing.tools.NoneTool;

import java.awt.event.*;

public class MapEditor implements MouseListener, MouseMotionListener, KeyListener {
    private RPGMap map;
    private MapViewPanel mapViewPanel;
    private ITool tool = new NoneTool();

    public MapEditor(RPGMap map, MapViewPanel mapViewPanel) {
        this.map = map;
        this.mapViewPanel = mapViewPanel;
    }

    public void mouseClicked(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
        tool.activateTool(e,map);
        mapViewPanel.getObserver().requestFocus();
        mapViewPanel.repaint();
        System.out.println("MapEditor "+map);
    }
    public void mouseReleased(MouseEvent e) {
        tool.deactivateTool(e,map);
    }

    public RPGMap getMap() {
        return map;
    }

    public void setMap(RPGMap map) {
        this.map = map;
    }

    public ITool getTool() {
        return tool;
    }

    public void setTool(ITool tool) {
        AppState.ACTIVE_TOOL = tool;
        if(!(tool instanceof DragTool))
            AppState.ACTIVE_DRAGGABLE = null;
        mapViewPanel.repaint();
        this.tool = tool;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        tool.drag(e,map);
        mapViewPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==81)//q
            setTool(new PanTool());

        else if(e.getKeyCode()==68)//d
            setTool(new DragTool());

        else if(e.getKeyCode()==71)//g
            map.getActiveLayer().toggleGrid();

        else if(e.getKeyCode()==70)//f
            System.out.println("Not really implemented yet");

        else if(e.getKeyCode()==66)//b
            setTool(Tools.PAINT_TOOL);

        else if(e.getKeyCode()==83)//s
            setTool(Tools.FLOOD_FILL_TOOL);

        else if(e.getKeyCode()==69)//e
            setTool(Tools.EDGE_PAINT_TOOL);

        else if(e.getKeyCode()==65)//a
            setTool(Tools.GRAPHIC_PAINT_TOOL);

        else if(e.getKeyCode()==127)//delete
            deleteActiveDraggable();

        else
            System.out.println(e.getKeyCode());
    }

    private void deleteActiveDraggable(){
        for(MapLayer layer: map.getActiveLayer().getGraphicLayers()){
            if(layer.getTiles().remove(AppState.ACTIVE_DRAGGABLE)) {
                AppState.ACTIVE_DRAGGABLE = null;
                mapViewPanel.repaint();
                break;
            }
        }
        if(AppState.ACTIVE_DRAGGABLE!=null){
            map.getMechanicsLayer().getTiles().remove(AppState.ACTIVE_DRAGGABLE);
            AppState.ACTIVE_DRAGGABLE = null;
            mapViewPanel.repaint();
        }
    }

    public MapViewPanel getMapViewPanel() {
        return mapViewPanel;
    }

    public void setMapViewPanel(MapViewPanel mapViewPanel) {
        this.mapViewPanel = mapViewPanel;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
