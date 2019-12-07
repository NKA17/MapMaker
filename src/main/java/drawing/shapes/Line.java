package drawing.shapes;

import UI.pages.editmap.EditMapPage;
import UI.pages.playMap.PlayMapPage;
import application.config.AppState;
import application.config.Configuration;
import application.mapEditing.tools.DragTool;

import java.awt.*;

public class Line extends Shape {

    public void draw(Graphics g){
        super.draw(g);

        if(AppState.ACTIVE_DRAGGABLE==this
                && AppState.ACTIVE_TOOL instanceof DragTool
                && (AppState.ACTIVE_PAGE instanceof EditMapPage || AppState.ACTIVE_PAGE instanceof PlayMapPage)) {
            g.setColor(Configuration.HIGHLIGHT_COLOR);
            int w = Math.abs(getX2()-getX1());
            int h = Math.abs(getY2()-getY1());
            int x = Math.min(getX1(),getX2());
            int y = Math.min(getY1(),getY2());
            g.drawRect(x,y,w,h);
        }
    }
}
