package application.mapEditing.editors;

import application.mapEditing.tools.DragTool;
import application.mapEditing.tools.PanTool;
import application.mapEditing.tools.Tools;
import model.map.structure.RPGMap;
import UI.panels.editmap.MapViewPanel;
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==81)//q
            setTool(new PanTool());

        if(e.getKeyCode()==68)//d
            setTool(new DragTool());

        if(e.getKeyCode()==71)//g
            map.getActiveLayer().toggleGrid();

        if(e.getKeyCode()==70)//f
            System.out.println("Not really implemented yet");

        if(e.getKeyCode()==66)//b
            setTool(Tools.PAINT_TOOL);

        if(e.getKeyCode()==83)//s
            setTool(Tools.FLOOD_FILL_TOOL);

        if(e.getKeyCode()==69)//e
            setTool(Tools.EDGE_PAINT_TOOL);

        if(e.getKeyCode()==65)//a
            setTool(Tools.GRAPHIC_PAINT_TOOL);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
