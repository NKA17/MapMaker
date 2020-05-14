package drawing.shapes;

import UI.pages.editmap.EditMapPage;
import UI.pages.playMap.PlayMapPage;
import application.config.AppState;
import application.config.Configuration;
import application.mapEditing.tools.DragTool;

import java.awt.*;

public class Circle extends Shape {
    @Override
    public void draw(Graphics g){
        super.draw(g);

        int x1 = getX1();
        int y1 = getY1();
        int x2 = getX2();
        int y2 = getY2();

        g.drawOval(x1-(getLineLength()),y1-(getLineLength()),getLineLength()*2,getLineLength()*2);

        g.setColor(new Color(255,90,90,140));
        g.fillOval(x1-(getLineLength()),y1-(getLineLength()),getLineLength()*2,getLineLength()*2);

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

    @Override
    public boolean shouldDrag(int x, int y) {
        int w = Math.abs(getX2()-getX1());
        int h = Math.abs(getY2()-getY1());
        int xx = Math.min(getX1(),getX2());
        int yy = Math.min(getY1(),getY2());
        return x >= xx  && x <= xx+w &&
                y >= yy && y <= yy+h;
    }
}
