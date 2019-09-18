package model.map.structure;

import UI.app.assets.MapAsset;
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

    public RPGMap(){
        MapSet defaultSet = new MapSet(gridWidth,gridHeight);
        mapImage.createGraphics();
        MapLayer tileLayer = createTileLayer("./src/main/resources/assets/map/floor/grass/grass 3.jpg");
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

    public void setYoffset(int yoffset) {
        this.yoffset = yoffset;
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

    public void draw(Graphics g){
        Graphics g2d = mapImage.getGraphics();
        for(MapSet mapLayer: layerSets){
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


    public MapTile getMapTile(int xOnScreen, int yOnScreen){
        return getActiveLayer().getMapTile(xOnScreen,yOnScreen,xoffset,yoffset);
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
}
