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

        float angle = (float) Math.toDegrees(
                Math.atan2(getY2() - getY1(), getX2() - getX1()));

        float tellAngle = angle+90;
        if(tellAngle < 0){
            tellAngle += 360;
        }

        if(angle < 0){
            angle += 360;
        }
        drawAtVelocity(g,String.format("%.1f°",tellAngle,167),new Point(getX1(),getY1()),angle,-40);



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

        double perpslope = -getSlope();
        double angleLeft = Math.atan(1.0 / (5.0*perpslope-4.0*perpslope));
        int w = (int)((50.0/2.0)*Math.sin(angleLeft));
        int h = (int)((50.0/2.0)*Math.cos(angleLeft));

        Polygon p = new Polygon();
        p.addPoint(getX2() - w, getY2() - h);
        p.addPoint(getX1() - w, getY1() - h);
        p.addPoint(getX1() + w, getY1() + h);
        p.addPoint(getX2() + w, getY2() + h);
        drawEffectedSquares(g, p);
    }

    private void drawAtVelocity(Graphics g, String str, Point origin, double degrees, int distance){
        int h = (int)(distance * Math.sin(Math.toRadians(degrees)));
        int w = (int)(distance * Math.cos(Math.toRadians(degrees)));


        g.setFont(Configuration.GAME_FONT);
        g.setColor(new Color(255,30,30,230));
        g.drawString(str,origin.x+w,origin.y+h);
    }
}
