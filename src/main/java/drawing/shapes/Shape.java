package drawing.shapes;

import UI.pages.editmap.EditMapPage;
import UI.pages.playMap.PlayMapPage;
import application.config.AppState;
import application.config.Configuration;
import application.mapEditing.toolInterfaces.Draggable;
import application.mapEditing.tools.DragTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Shape implements Draggable{
    private int x1 = 0;
    private int y1 = 0;
    private int x2 = 0;
    private int y2 = 0;

    private BufferedImage rotate = null;
    private Polygon rotatePoly = new Polygon();

    public void draw(Graphics g){
        if(rotate==null){
            try{
                rotate = ImageIO.read(new File("C:\\Users\\Nate\\IdeaProjects\\RPGMapMaker\\src\\main\\resources\\icons\\rotate.png"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        g.setColor(new Color(255,30,30,230));
        g.drawLine(x1,y1,x2,y2);

        double ww = (x1-x2);
        double hh = (y1-y2);
        if(hh == 0)hh = .0001;
        double angleLeft = Math.atan(ww/hh);
        int w = (int)((30)*Math.sin(angleLeft));
        int h = (int)((30)*Math.cos(angleLeft));
        if(y2<y1){
            h = -h;
            w = -w;
        }

        g.setFont(Configuration.GAME_FONT);
        g.setColor(new Color(255,30,30,230));
        g.drawString(getScaledLineLength()+" ft.",x2+w,y2+h);

        if(AppState.ACTIVE_DRAGGABLE==this
                && AppState.ACTIVE_TOOL instanceof DragTool
                && (AppState.ACTIVE_PAGE instanceof EditMapPage || AppState.ACTIVE_PAGE instanceof PlayMapPage)) {
            int x = x2-15;
            int y = y2-15;
            rotatePoly = new Polygon();
            rotatePoly.addPoint(x,y);
            rotatePoly.addPoint(x+30,y);
            rotatePoly.addPoint(x+30,y+30);
            rotatePoly.addPoint(x,y+30);
            //g.drawPolygon(rotatePoly);
        }
    }

    public boolean shouldRotate(int x, int y){
        return rotatePoly.contains(new Point(x,y));
    }

    public double getSlope(){
        return (x1-x2-0.0) / (y1-y2-0.0);
    }

    public int getLineLength(){
        int a = x1 - x2;
        int b = y1 - y2;
        return (int)Math.sqrt(a*a + b*b);
    }

    public int getScaledLineLength(){
        return getLineLength() / 10;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    @Override
    public void translate(int delta_x, int delta_y) {
        this.x1 += delta_x;
        this.y1 += delta_y;
        this.x2 += delta_x;
        this.y2 += delta_y;
    }

    @Override
    public boolean shouldDrag(int x, int y) {
        int width = Math.abs(x1 - x2);
        int height = Math.abs(y1 - y2);
        return x >= x1 - width && x <= x1 + width &&
                y >= y1 - height && y <= y2 + height;
    }
}
