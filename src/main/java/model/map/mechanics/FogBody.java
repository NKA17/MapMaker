package model.map.mechanics;

import model.map.tiles.MapTile;
import model.map.tiles.PatternTile;

import java.util.ArrayList;
import java.util.List;

public class FogBody {
    private boolean show = true;
    private List<MapTile> tiles = new ArrayList<>();

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public List<MapTile> getTiles() {
        return tiles;
    }

    public void setTiles(List<MapTile> tiles) {
        this.tiles = tiles;
    }
}
