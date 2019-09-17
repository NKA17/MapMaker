package UI.mapview;

import application.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MapSet {

    private MapLayer tileLayer = new MapLayer();
    private MapGridLayer gridLayer;
    private List<GraphicLayer> graphicLayers = new ArrayList<>();
    private MapLayer edgeLayer = new MapLayer();

    public MapSet(int rows, int columns){
        gridLayer = new MapGridLayer(rows,columns, Configuration.TILE_WIDTH,Configuration.TILE_HEIGHT);
    }
}
