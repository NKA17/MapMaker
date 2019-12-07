package application.mapEditing.tools;

import UI.app.assets.MapAsset;
import application.config.AppState;
import application.mapEditing.toolInterfaces.Draggable;
import drawing.shapes.Shape;
import model.map.structure.MapSet;
import model.map.structure.RPGMap;
import application.mapEditing.toolInterfaces.ITool;
import model.map.tiles.AssetTile;

import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class DragTool implements ITool {

    public static BufferedImage rotateImage;
    private int startx = 0;
    private int starty = 0;
    private double startRad = 0;
    private Draggable toDrag = null;
    private Draggable toRotate = null;

    static {
        try {
            rotateImage = ImageIO.read(new File("C:\\Users\\Nate\\IdeaProjects\\RPGMapMaker\\src\\main\\resources\\icons\\rotate.png"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        startx = e.getXOnScreen();
        starty = e.getYOnScreen();
        boolean hidden = map.getActiveLayer().isHidden(e.getX()-map.getXoffset(),e.getY()-map.getYoffset());

        int x = e.getX()-map.getXoffset();
        int y = e.getY()-map.getYoffset();

        for(Shape shape: map.getShapes()){
            if(shape.shouldRotate(x,y)){
                toRotate = shape;
                AppState.ACTIVE_DRAGGABLE = shape;
                toDrag = null;
                return;
            }
            if(shape.shouldDrag(x,y)){
                toDrag = shape;
                AppState.ACTIVE_DRAGGABLE = shape;
                toRotate = null;
                return;
            }
        }

        toDrag = map.getFirstTile(e.getX(),e.getY());
        if(!(toDrag instanceof MapSet)){
            if(hidden){
                toDrag = map.getActiveLayer();
                AppState.ACTIVE_DRAGGABLE = null;
                toRotate = null;
            }else {
                AppState.ACTIVE_DRAGGABLE = toDrag;
                if(toDrag.shouldRotate(e.getX()-map.getXoffset(),e.getY()-map.getYoffset())) {
                    toRotate = toDrag;
                    toDrag = null;
                }else {
                    toRotate = null;
                }
            }
        }else{
            AppState.ACTIVE_DRAGGABLE = null;
            toRotate = null;
        }

    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {
        toDrag = null;
        startRad = 0;
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
        if(toRotate!=null){
            if(toRotate instanceof Shape)
                rotateShape(e,map);
            else
                rotateAsset(e,map);
        }
        startx = e.getXOnScreen();
        starty = e.getYOnScreen();
    }

    private void rotateShape(MouseEvent e, RPGMap map){
        int delta_x = e.getXOnScreen()-startx;
        int delta_y = e.getYOnScreen()-starty;
        Shape rshape = (Shape)toRotate;
        rshape.setX2(rshape.getX2()+delta_x);
        rshape.setY2(rshape.getY2()+delta_y);
    }

    private void rotateAsset(MouseEvent e, RPGMap map){
        AssetTile assetTile = (AssetTile)toRotate;
        double delta_x = e.getX() - (assetTile.getGridx() + map.getXoffset());
        double delta_y = e.getY() - (assetTile.getGridy()  + map.getYoffset() - assetTile.getAssetResource().getImage().getHeight() / 2);
        double h = Math.sqrt(delta_x*delta_x + delta_y*delta_y);
        if( delta_y == 0 ) delta_y = .001;

        double rads = Math.acos(delta_x / h);
        if(startRad==0){
            startRad = rads;
        }

        if(delta_y > 0){
            assetTile.setRadians(assetTile.getRadians() + (rads - startRad));
        }else {
            assetTile.setRadians(assetTile.getRadians() + (startRad - rads));
        }
        startRad = rads;
    }
}
