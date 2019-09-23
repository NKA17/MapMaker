package UI.rendering;

import application.config.Configuration;
import application.io.LoadModel;
import model.map.structure.MapLayer;
import model.map.tiles.MapTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MapLayerRenderer {
    private List<MapTile> tiles = new ArrayList<>();
    private int gridWidth = 40;
    private int gridHeight = 40;
    private BufferedImage render = new BufferedImage(
            Configuration.TILE_WIDTH * gridWidth,
            Configuration.TILE_HEIGHT*gridHeight,
            BufferedImage.TYPE_4BYTE_ABGR);


    public MapLayerRenderer(List<MapTile> tiles, int gridWidth, int gridHeight) {
        this.tiles = tiles;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    public List<MapTile> getTiles() {
        return tiles;
    }

    public void setTiles(List<MapTile> tiles) {
        this.tiles = tiles;
    }

    public BufferedImage render(){
        return render(null);
    }

    public BufferedImage render(LoadModel model){
        render = new BufferedImage(Configuration.TILE_WIDTH * gridWidth, Configuration.TILE_HEIGHT*gridHeight,BufferedImage.TYPE_4BYTE_ABGR);
        render.createGraphics();
        Graphics g = render.getGraphics();
        Collections.sort(tiles, new Comparator<MapTile>() {
            @Override
            public int compare(MapTile o1, MapTile o2) {
                return o1.getGridy() - o2.getGridy();
            }
        });
        for(MapTile tile: tiles){
            tile.draw(g);
            if(model!=null){
                model.incrementReadBytes(1);
            }
        }
        return render;
    }

    public BufferedImage getRender() {
        return render;
    }

    public void setRender(BufferedImage render) {
        this.render = render;
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

}
