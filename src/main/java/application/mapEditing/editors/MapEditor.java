package application.mapEditing.editors;

import model.map.structure.RPGMap;
import UI.panels.editmap.MapViewPanel;
import application.mapEditing.toolInterfaces.ITool;
import application.mapEditing.tools.NoneTool;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MapEditor implements MouseListener, MouseMotionListener {
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
        mapViewPanel.repaint();
    }
    public void mouseReleased(MouseEvent e) {
        tool.deactivateTool(e,map);
        mapViewPanel.repaint();
    }

    public ITool getTool() {
        return tool;
    }

    public void setTool(ITool tool) {
        this.tool = tool;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        tool.drag(e,map);
        System.out.println("Repainted");
        mapViewPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
