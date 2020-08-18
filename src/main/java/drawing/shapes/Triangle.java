package drawing.shapes;


import UI.pages.editmap.EditMapPage;
import UI.pages.playMap.PlayMapPage;
import application.config.AppState;
import application.config.Configuration;
import application.mapEditing.tools.DragTool;

import java.awt.*;

public class Triangle extends Shape {

    Polygon p = new Polygon();
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        int x1 = getX1();
        int y1 = getY1();
        int x2 = getX2();
        int y2 = getY2();
        double perpslope = -getSlope();

        double angleLeft = Math.atan(1.0 / (5.0*perpslope-4.0*perpslope));
        int w = (int)((getLineLength()/2.0)*Math.sin(angleLeft));
        int h = (int)((getLineLength()/2.0)*Math.cos(angleLeft));

        p = new Polygon();
        p.addPoint(x1,y1);
        p.addPoint(x2 + w, y2 + h);
        p.addPoint(x2 - w, y2 - h);

        g.drawPolygon(p);

        g.setColor(new Color(255,90,90,70));
        g.fillPolygon(p);
        drawEffectedSquares(g, p);

        if(AppState.ACTIVE_DRAGGABLE==this
                && AppState.ACTIVE_TOOL instanceof DragTool
                && (AppState.ACTIVE_PAGE instanceof EditMapPage || AppState.ACTIVE_PAGE instanceof PlayMapPage)) {
            g.setColor(Configuration.HIGHLIGHT_COLOR);
            Rectangle r = p.getBounds();
            g.drawRect((int)r.getX(),(int)r.getY(),r.width,r.height);
        }
    }



    @Override
    public boolean shouldDrag(int x, int y) {
        return p.contains(new Point(x,y));
    }
}
