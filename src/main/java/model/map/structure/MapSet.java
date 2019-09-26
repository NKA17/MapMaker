package model.map.structure;

import application.io.AssetCache;
import application.mapEditing.toolInterfaces.Draggable;
import model.map.mechanics.FogBody;
import model.map.mechanics.FogFactory;
import model.map.tiles.MapTile;
import application.config.Configuration;
import model.map.tiles.PatternTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MapSet implements Draggable{

    private MapLayer tileLayer = new MapLayer();
    private MapGridLayer gridLayer;
    private List<GraphicLayer> graphicLayers = new ArrayList<>();
    private GraphicLayer activeGraphicLayer;
    private MapLayer edgeLayer = new MapLayer();
    private MapLayer fogLayer = new MapLayer();
    private List<FogBody> fogClouds = new ArrayList<>();
    private BufferedImage mapImage;
    private int xoffset = 0;
    private int yoffset = 0;
    private int gridWidth = 40;
    private int gridHeight = 40;

    private boolean drawGrid = false;

    public MapSet(int rows, int columns){
        gridLayer = new MapGridLayer(rows,columns);
        mapImage = new BufferedImage(
                Configuration.TILE_WIDTH * rows,
                Configuration.TILE_HEIGHT*columns,
                BufferedImage.TYPE_4BYTE_ABGR);
        mapImage.createGraphics();
        activeGraphicLayer = new GraphicLayer();
        graphicLayers.add(activeGraphicLayer);
        gridWidth = columns;
        gridHeight = rows;
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

    public GraphicLayer getActiveGraphicLayer() {
        return activeGraphicLayer;
    }

    public void setActiveGraphicLayer(GraphicLayer activeGraphicLayer) {
        this.activeGraphicLayer = activeGraphicLayer;
    }

    public MapLayer getTileLayer() {
        return tileLayer;
    }

    public void setTileLayer(MapLayer tileLayer) {
        this.tileLayer = tileLayer;
    }

    public MapGridLayer getGridLayer() {
        return gridLayer;
    }

    public void setGridLayer(MapGridLayer gridLayer) {
        this.gridLayer = gridLayer;
    }

    public List<GraphicLayer> getGraphicLayers() {
        return graphicLayers;
    }

    public void setGraphicLayers(List<GraphicLayer> graphicLayers) {
        this.graphicLayers = graphicLayers;
        setActiveGraphicLayer(getGraphicLayers().get(0));
    }

    public MapLayer getEdgeLayer() {
        return edgeLayer;
    }

    public void setEdgeLayer(MapLayer edgeLayer) {
        this.edgeLayer = edgeLayer;
    }


    public void toggleGrid(){
        drawGrid = !drawGrid;
    }

    public void showGrid(boolean show){
        drawGrid = show;
    }

    public MapTile getMapTile(int xOnScreen, int yOnScreen,int parentXoffset,int parentYoffset){
        return tileLayer.getTile(xOnScreen,yOnScreen,parentXoffset-xoffset,parentYoffset-yoffset);
    }

    public List<MapTile> getEdgeTiles(int xOnScreen, int yOnScreen,int parentXoffset,int parentYoffset){
        return edgeLayer.getTiles(xOnScreen,yOnScreen,xoffset-parentXoffset,yoffset-parentYoffset);
    }

    public void buildFogLayer(){
        fogLayer = new MapLayer();
        fogClouds = FogFactory.floodFog(this);
        for(FogBody cloud: fogClouds){
            fogLayer.getTiles().addAll(cloud.getTiles());
        }
    }

    public boolean isHidden(int xOnScreen, int yOnScreen){
        int x = xOnScreen - xoffset;
        int y = yOnScreen - yoffset;
        int gridx = x / Configuration.TILE_WIDTH;
        int gridy = y / Configuration.TILE_HEIGHT;

        for(FogBody cloud: fogClouds){
            boolean isBody = false;
            for(MapTile tile: cloud.getTiles()){
                isBody = tile.getGridx() == gridx && tile.getGridy() == gridy;
                if(isBody)
                    break;
            }
            if(isBody){
                return cloud.isShow();
            }
        }
        return false;
    }

//    Fog Of War
//    Fog of war is built on Battle Button press
//    Fog of war is toggle-able
//    Fog of war that is showing disallows the ability to move asset beneath it

    public void destroyFog(){
        fogLayer = new MapLayer();
        fogClouds = new ArrayList<>();
    }

    public void toggleFog(int xOnScreen, int yOnScreen){
        int x = xOnScreen - xoffset;
        int y = yOnScreen - yoffset;
        int gridx = x / Configuration.TILE_WIDTH;
        int gridy = y / Configuration.TILE_HEIGHT;

        for(FogBody cloud: fogClouds){
            boolean isBody = false;
            for(MapTile tile: cloud.getTiles()){
                isBody = tile.getGridx() == gridx && tile.getGridy() == gridy;
                if(isBody)
                    break;
            }
            if(isBody){
                cloud.toggleVisibility();
                break;
            }
        }
    }


    public void draw(Graphics g,int mapXoffset, int mapYoffset){
        Graphics g2d = mapImage.getGraphics();

        tileLayer.draw(g2d,mapXoffset+xoffset,mapYoffset+yoffset);

        if(drawGrid){
            gridLayer.draw(g2d,mapXoffset+xoffset,mapYoffset+yoffset);
        }

        for(MapLayer mapLayer: graphicLayers){
            mapLayer.draw(g2d,mapXoffset+xoffset,mapYoffset+yoffset);
        }

        edgeLayer.draw(g2d,mapXoffset+xoffset,mapYoffset+yoffset);

        fogLayer.draw(g2d, mapXoffset+xoffset,mapYoffset+yoffset);

        g.drawImage(mapImage,xoffset,yoffset,null);
    }

    @Override
    public void translate(int delta_x, int delta_y) {
        xoffset += delta_x;
        yoffset += delta_y;
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

    @Override
    public boolean shouldDrag(int x, int y){
        return true;
    }

}
