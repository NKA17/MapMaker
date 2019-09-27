package model.map.structure;

import application.config.AppState;
import model.map.tiles.MapTile;
import application.config.Configuration;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapLayer {
    private List<MapTile> tiles = new ArrayList<>();

    public void draw(Graphics g,int mapxoffset,int mapyoffset){

        for(MapTile mapTile: tiles){

            if(mapTile.isOnScreen(mapxoffset,mapyoffset)) {

                mapTile.draw(g);
            }
        }
    }

    public MapTile getTile(int xOnScreen, int yOnScreen,int xoffset, int yoffset){
        int x = (int)(xOnScreen/AppState.ZOOM/AppState.ZOOM) + xoffset;
        int y =(int)( yOnScreen/AppState.ZOOM/AppState.ZOOM) + yoffset;

        int column = (int)((x / Configuration.TILE_WIDTH)* AppState.ZOOM);
        int row = (int)((y / Configuration.TILE_HEIGHT)* AppState.ZOOM);
        for(MapTile mapTile: getTiles()){
            if(mapTile.getGridx() == column && mapTile.getGridy() == row){
                return mapTile;
            }
        }
        return null;
    }

    public List<MapTile> getTiles(int xOnScreen, int yOnScreen,int xoffset, int yoffset){
        List<MapTile> tiles = new ArrayList<>();
        int x = (int)((xOnScreen + xoffset)* AppState.ZOOM);
        int y = (int)((yOnScreen + yoffset)* AppState.ZOOM);

        int column = (int)((x / Configuration.TILE_WIDTH)* AppState.ZOOM);
        int row = (int)((y / Configuration.TILE_HEIGHT)* AppState.ZOOM);
        for(MapTile mapTile: getTiles()){
            if(mapTile.getGridx() == column && mapTile.getGridy() == row){
                tiles.add(mapTile);
            }
        }
        return tiles;
    }

    public List<MapTile> getTilesOverlapping(MapTile tile){
        List<MapTile> tiles = new ArrayList<>();
        for(MapTile mapTile: getTiles()){
            if(mapTile.getGridx() == tile.getGridx() && mapTile.getGridy() == tile.getGridy()){
                tiles.add(mapTile);
            }
        }
        return tiles;
    }

    public List<MapTile> getTiles() {
        return tiles;
    }

    public void setTiles(List<MapTile> tiles) {
        this.tiles = tiles;
    }

}
