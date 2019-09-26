package model.map.mechanics;

import application.io.AssetCache;
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

    public void toggleVisibility(){
        show = !show;
        if(show){
            for(MapTile tile: getTiles()){
                tile.setAssetResource(AssetCache.get("./src/main/resources/assets/map/floor/0mechanics/black.png"));
            }
        }else{
            for(MapTile tile: getTiles()){
                tile.setAssetResource(AssetCache.get("./src/main/resources/assets/map/misc/BlankTile.png"));
            }
        }
    }
}
