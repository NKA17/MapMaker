package application.mapEditing.tools;

import application.mapEditing.toolInterfaces.Draggable;
import model.map.structure.RPGMap;
import application.mapEditing.toolInterfaces.ITool;

import java.awt.event.MouseEvent;

public class DragTool implements ITool {

    private int startx = 0;
    private int starty = 0;
    private Draggable toDrag = null;
    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        startx = e.getXOnScreen();
        starty = e.getYOnScreen();
        toDrag = map.getFirstTile(e.getX(),e.getY());
    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {
        toDrag = null;
    }

    @Override
    public void drag(MouseEvent e, RPGMap map) {
        int delta_x = e.getXOnScreen()-startx;
        int delta_y = e.getYOnScreen()-starty;
        if(toDrag!=null){
            toDrag.translate(delta_x,delta_y);
        }
        //map.translate(delta_x,delta_y);
        System.out.println("Tanslated,"+delta_x+","+delta_y);
        startx = e.getXOnScreen();
        starty = e.getYOnScreen();
    }
}
