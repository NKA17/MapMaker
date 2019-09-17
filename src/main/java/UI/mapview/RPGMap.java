package UI.mapview;

import UI.app.assets.MapAsset;
import UI.mapview.tiles.EdgeTile;
import UI.mapview.tiles.MapTile;
import UI.mapview.tiles.PatternTile;
import application.config.Configuration;

import javax.imageio.ImageIO;
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
    private List<MapLayer> layers = new ArrayList<>();
    private MapGridLayer mapGridLayer = new MapGridLayer(gridHeight,gridWidth, Configuration.TILE_WIDTH,Configuration.TILE_HEIGHT);
    private MapLayer tileLayer = new MapLayer();
    private MapLayer edgeLayer = new MapLayer();
    private BufferedImage mapImage =  new BufferedImage(Configuration.TILE_WIDTH * gridWidth, Configuration.TILE_HEIGHT*gridHeight,BufferedImage.TYPE_4BYTE_ABGR);
    private GraphicLayer activeLayer;

    public RPGMap(){
        mapImage.createGraphics();
        tileLayer = createTileLayer("./src/main/resources/assets/map/floor/grass/grass 3.jpg");
        layers.add(tileLayer);

        activeLayer = new GraphicLayer();
        layers.add(activeLayer);

        edgeLayer = new MapLayer();
        layers.add(edgeLayer);
    }

    public void toggleGrid(){
        if(layers.contains(mapGridLayer)){
            hideGrid();
        }else {
            showGrid();
        }
    }


    public MapTile getMapTile(int xOnScreen, int yOnScreen){
        return tileLayer.getTile(xOnScreen,yOnScreen,xoffset,yoffset);
    }

    public List<MapTile> getEdgeTiles(int xOnScreen, int yOnScreen){
        return edgeLayer.getTiles(xOnScreen,yOnScreen,xoffset,yoffset);
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

    public void setYoffset(int yoffset) {
        this.yoffset = yoffset;
    }

    public MapLayer getTileLayer() {
        return tileLayer;
    }

    public void setTileLayer(MapLayer tileLayer) {
        this.tileLayer = tileLayer;
    }

    public MapLayer getEdgeLayer() {
        return edgeLayer;
    }

    public void setEdgeLayer(MapLayer edgeLayer) {
        this.edgeLayer = edgeLayer;
    }

    public void showGrid(){
        layers.add(1,mapGridLayer);
    }

    public void hideGrid(){
        layers.remove(mapGridLayer);
    }


    public MapLayer createTileLayer(String defaultAsset){
        MapLayer mapLayer = new MapLayer();

        try{
            MapAsset asset = new MapAsset(new File(defaultAsset));
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

    public List<MapLayer> getLayers() {
        return layers;
    }

    public void setLayers(List<MapLayer> layers) {
        this.layers = layers;
    }

    public void draw(Graphics g){
        Graphics g2d = mapImage.getGraphics();
        for(MapLayer mapLayer: layers){
            mapLayer.draw(g2d,xoffset,yoffset);
        }
        g.drawImage(mapImage,xoffset,yoffset,null);
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
            //this.xoffset = -(gridWidth*Configuration.TILE_WIDTH+1201);
        }
        if(this.yoffset < -(gridHeight*Configuration.TILE_HEIGHT)+700){
            //this.yoffset = -(gridHeight*Configuration.TILE_HEIGHT)+701;
        }
    }

    public MapLayer getActiveLayer() {
        return activeLayer;
    }

    public void setActiveLayer(GraphicLayer activeLayer) {
        this.activeLayer = activeLayer;
    }
}
