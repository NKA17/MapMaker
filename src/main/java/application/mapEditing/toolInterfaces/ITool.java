package application.mapEditing.toolInterfaces;

import UI.mapview.RPGMap;

import java.awt.event.MouseEvent;

public interface ITool {
    void activateTool(MouseEvent e, RPGMap map);
    void deactivateTool(MouseEvent e, RPGMap map);
    void drag(MouseEvent e, RPGMap map);
}
