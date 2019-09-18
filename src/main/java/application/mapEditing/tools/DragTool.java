package application.mapEditing.tools;

import model.map.structure.RPGMap;
import application.mapEditing.toolInterfaces.ITool;

import java.awt.event.MouseEvent;

public class DragTool implements ITool {

    private int startx = 0;
    private int starty = 0;
    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        startx = e.getXOnScreen();
        starty = e.getYOnScreen();
    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {

    }

    @Override
    public void drag(MouseEvent e, RPGMap map) {
        int delta_x = e.getXOnScreen()-startx;
        int delta_y = e.getYOnScreen()-starty;
        map.translate(delta_x,delta_y);
        System.out.println("Tanslated,"+delta_x+","+delta_y);
        startx = e.getXOnScreen();
        starty = e.getYOnScreen();
    }
}
