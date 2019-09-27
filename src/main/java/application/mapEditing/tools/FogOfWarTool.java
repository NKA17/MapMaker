package application.mapEditing.tools;

import application.mapEditing.toolInterfaces.ITool;
import model.map.structure.RPGMap;

import java.awt.event.MouseEvent;

public class FogOfWarTool implements ITool {
    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        map.getActiveLayer().toggleFog(e.getX()-map.getXoffset(),e.getY()-map.getYoffset());
    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {

    }

    @Override
    public void drag(MouseEvent e, RPGMap map) {

    }
}
