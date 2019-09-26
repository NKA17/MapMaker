package application.mapEditing.tools;

import application.config.AppState;
import application.mapEditing.toolInterfaces.Draggable;
import model.map.structure.MapSet;
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
        boolean hidden = map.getActiveLayer().isHidden(e.getX(),e.getY());

        toDrag = map.getFirstTile(e.getX(),e.getY());
        if(!(toDrag instanceof MapSet)){
            if(hidden){
                toDrag = map.getActiveLayer();
                AppState.ACTIVE_DRAGGABLE = null;
            }else {
                AppState.ACTIVE_DRAGGABLE = toDrag;
            }
        }else{
            AppState.ACTIVE_DRAGGABLE = null;
        }
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
            if(toDrag == map.getLayerSets().get(0)) {
                map.translate(delta_x,delta_y);
            }else {
                toDrag.translate(delta_x, delta_y);
            }
        }
        startx = e.getXOnScreen();
        starty = e.getYOnScreen();
    }
}
