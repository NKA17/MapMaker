package model.map.structure;

import model.map.tiles.MapTile;
import application.config.Configuration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MapSet {

    private MapLayer tileLayer = new MapLayer();
    private MapGridLayer gridLayer;
    private List<GraphicLayer> graphicLayers = new ArrayList<>();
    private GraphicLayer activeGraphicLayer;
    private MapLayer edgeLayer = new MapLayer();
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
        return tileLayer.getTile(xOnScreen,yOnScreen,xoffset-parentXoffset,yoffset-parentYoffset);
    }

    public List<MapTile> getEdgeTiles(int xOnScreen, int yOnScreen,int parentXoffset,int parentYoffset){
        return edgeLayer.getTiles(xOnScreen,yOnScreen,xoffset-parentXoffset,yoffset-parentYoffset);
    }


    public void draw(Graphics g,int mapXoffset, int mapYoffset){
        Graphics g2d = mapImage.getGraphics();

        tileLayer.draw(g2d,mapXoffset,mapYoffset);

        if(drawGrid){
            gridLayer.draw(g2d,mapXoffset,mapYoffset);
        }

        for(MapLayer mapLayer: graphicLayers){
            mapLayer.draw(g2d,mapXoffset,mapYoffset);
        }

        edgeLayer.draw(g2d,mapXoffset,mapYoffset);

        g.drawImage(mapImage,xoffset,yoffset,null);
    }
}
