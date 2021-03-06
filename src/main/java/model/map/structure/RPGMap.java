package model.map.structure;

import UI.app.assets.MapAsset;
import application.io.AssetCache;
import application.mapEditing.toolInterfaces.Draggable;
import drawing.shapes.Shape;
import model.map.tiles.AssetTile;
import model.map.tiles.MapTile;
import model.map.tiles.PatternTile;
import application.config.Configuration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RPGMap {
    private int gridWidth = 40;
    private int gridHeight = 40;
    private int xoffset = 0;
    private int yoffset = 0;
    private List<MapSet> layerSets = new ArrayList<>();
    private BufferedImage mapImage =  new BufferedImage(Configuration.TILE_WIDTH * gridWidth, Configuration.TILE_HEIGHT*gridHeight,BufferedImage.TYPE_4BYTE_ABGR);
    private MapSet activeLayer;
    private String name = "New_Map";
    private List<Shape> shapes = new ArrayList<>();
    private MechanicalLayer mechanicsLayer = new MechanicalLayer();


    public void init(){
        mapImage =  new BufferedImage(Configuration.TILE_WIDTH * gridWidth, Configuration.TILE_HEIGHT*gridHeight,BufferedImage.TYPE_4BYTE_ABGR);
        MapSet defaultSet = new MapSet(gridWidth,gridHeight);
        mapImage.createGraphics();
        MapLayer tileLayer = createTileLayer(Configuration.DEFAULT_FLOOR_TILE);
        defaultSet.setTileLayer(tileLayer);

        activeLayer = defaultSet;
        layerSets.add(defaultSet);

    }
    public int getXoffset() {
        return xoffset;
    }

    public void setXoffset(int xoffset) {
        this.xoffset = xoffset;
    }

    public int getYoffset() {
        return yoffset;
    }

    public void resterizeMapImage(){
        mapImage =  new BufferedImage(Configuration.TILE_WIDTH * gridWidth, Configuration.TILE_HEIGHT*gridHeight,BufferedImage.TYPE_4BYTE_ABGR);
    }

    public void setYoffset(int yoffset) {
        this.yoffset = yoffset;
    }

    public Draggable getFirstTile(int x, int y){
        List<MapLayer> layers = new ArrayList<>();
        layers.add(activeLayer.getTileLayer());
        layers.addAll(activeLayer.getGraphicLayers());

        List<Draggable> draggables = new ArrayList<>();
        draggables.add(activeLayer);
        for(GraphicLayer gl : activeLayer.getGraphicLayers()){
            for(MapTile at: gl.getTiles()){
                draggables.add((Draggable)at);
            }
        }
        for(MapTile at :  mechanicsLayer.getTiles()){
                draggables.add((Draggable)at);
        }

        for(int j = draggables.size()-1; j >= 0; j--){
            Draggable draggable = draggables.get(j);
            if( draggable.shouldDrag(
                    x-getXoffset()-activeLayer.getXoffset(),
                    y-getYoffset()-activeLayer.getYoffset()) ){
                return draggable;
            }
        }
        return null;
    }

    public MapLayer createTileLayer(MapAsset asset){
        MapLayer mapLayer = new MapLayer();

        try{
            for(int x = 0; x < gridWidth; x++){
                for(int y = 0; y < gridHeight; y++){
                        PatternTile patternTile = new PatternTile(
                                asset,
                                x, y);
                        mapLayer.getTiles().add(patternTile);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mapLayer;
    }

    public void draw(Graphics g){
        Graphics g2d = mapImage.getGraphics();
        for(MapSet mapLayer: layerSets){
            mapLayer.draw(g2d,xoffset,yoffset);
        }
        for(Shape shape: shapes){
            shape.draw(g2d);
        }

        mechanicsLayer.draw(g2d,xoffset,yoffset);
        //System.out.println(String.format("w=%d, h=%d, x=%d, y=%d, x+1199=%d, y+699=%d",mapImage.getWidth(),mapImage.getHeight(),-getXoffset(),-getYoffset(),-getXoffset()+1199,-getYoffset()+699));
        //System.out.println(String.format("gw=%d, gh=%d",getGridWidth(),getGridHeight()));

        g.drawImage(mapImage.getSubimage(-getXoffset(),-getYoffset(),1199,699),0,0,null);
    }

    public void translate(int delta_x, int delta_y){
        this.xoffset += delta_x;
        this.yoffset += delta_y;
        if(this.xoffset > 0){
            this.xoffset = 0;
        }
        if(this.yoffset > 0){
            this.yoffset = 0;
        }
        if(this.xoffset < -(gridWidth*Configuration.TILE_WIDTH)+1200){
            this.xoffset = -(gridWidth*Configuration.TILE_WIDTH)+1200;
        }
        if(this.yoffset < -(gridHeight*Configuration.TILE_HEIGHT)+700){
            this.yoffset = -(gridHeight*Configuration.TILE_HEIGHT)+700;
        }
    }


    public MapTile getMapTile(int xOnScreen, int yOnScreen){
        return getActiveLayer().getMapTile(xOnScreen,yOnScreen,-xoffset,-yoffset);
    }

    public List<MapTile> getEdgeTiles(int xOnScreen, int yOnScreen){
        return getActiveLayer().getEdgeTiles(xOnScreen,yOnScreen,xoffset,yoffset);
    }

    public MapSet getActiveLayer() {
        return activeLayer;
    }

    public void setActiveLayer(MapSet activeLayer) {
        this.activeLayer = activeLayer;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public List<MapSet> getLayerSets() {
        return layerSets;
    }

    public void setLayerSets(List<MapSet> layerSets) {
        this.layerSets = layerSets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGridDimensions(int width, int height){
        setGridWidth(width);
        setGridHeight(height);
    }

    public BufferedImage getMapImage() {
        return mapImage;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public MechanicalLayer getMechanicsLayer() {
        return mechanicsLayer;
    }

    public void setMechanicsLayer(MechanicalLayer mechanicsLayer) {
        this.mechanicsLayer = mechanicsLayer;
    }
}
