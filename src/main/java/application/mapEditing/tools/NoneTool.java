package application.mapEditing.tools;

import UI.mapview.RPGMap;
import application.mapEditing.toolInterfaces.ITool;

import java.awt.event.MouseEvent;

public class NoneTool implements ITool {
    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        System.out.println("Tool Activated: "+e.getXOnScreen()+", "+e.getYOnScreen());
    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {
        System.out.println("Tool Activated: "+e.getXOnScreen()+", "+e.getYOnScreen());
    }

    @Override
    public void drag(MouseEvent e, RPGMap map) {

    }
}
