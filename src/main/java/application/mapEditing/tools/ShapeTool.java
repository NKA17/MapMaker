package application.mapEditing.tools;

import application.mapEditing.toolInterfaces.ITool;
import drawing.shapes.Circle;
import drawing.shapes.Shape;
import model.map.structure.RPGMap;

import java.awt.event.MouseEvent;

public class ShapeTool implements ITool {
    private Shape shape = new Circle();

    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        try {
            shape = shape.getClass().newInstance();
            map.getShapes().add(shape);
            shape.setX1(e.getX() - map.getXoffset());
            shape.setY1(e.getY() - map.getYoffset());
            shape.setX2(e.getX() - map.getXoffset());
            shape.setY2(e.getY() - map.getYoffset());
        }catch (Exception f){
            f.printStackTrace();
        }
    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {

    }

    @Override
    public void drag(MouseEvent e, RPGMap map) {
        shape.setX2(e.getX()-map.getXoffset());
        shape.setY2(e.getY()-map.getYoffset());
    }

    public void setShape(Shape shape){
        this.shape = shape;
    }
}
