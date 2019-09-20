package model.map.mechanics;

import UI.app.assets.MapAsset;
import application.io.AssetCache;
import model.map.structure.MapLayer;
import model.map.structure.MapSet;
import model.map.tiles.AssetTile;
import model.map.tiles.EdgeTile;
import model.map.tiles.MapTile;
import model.map.tiles.PatternTile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FloodFill {
    private static final int vert = 1;
    private static final int hor = 0;
    private static final MapAsset asset = AssetCache.get(
            ".\\src\\main\\resources\\assets\\map\\floor\\0mechanics\\black.png");

    private static void fill(MapTile[][][] mapTiles, MapTile[][] tiles, int[][] flags, int orix, int oriy,FogBody fog, MapAsset asset){

        if(flags[orix][oriy] > 0){
            return ;
        }
        flags[orix][oriy]++;
        MapTile newt = new PatternTile(asset,orix,oriy);
        fog.getTiles().add(newt);

        expandEast(mapTiles,tiles,flags,orix,oriy,fog,asset);
        expandNorth(mapTiles,tiles,flags,orix,oriy,fog,asset);
        expandWest(mapTiles,tiles,flags,orix,oriy,fog,asset);
        expandSouth(mapTiles,tiles,flags,orix,oriy,fog,asset);
    }

    public static FogBody fill(MapSet mapSet,int x, int y, MapAsset asset){

        FogBody origin = new FogBody();

        MapTile[][][] edges = new EdgeTile[mapSet.getGridWidth()][mapSet.getGridHeight()][2];
        MapTile[][] tiles = new MapTile[mapSet.getGridWidth()][mapSet.getGridHeight()];
        int[][] flags = new int[mapSet.getGridWidth()][mapSet.getGridHeight()];
        for(MapTile mapTile: mapSet.getEdgeLayer().getTiles()){
            edges[mapTile.getGridx()]
                    [mapTile.getGridy()]
                    [mapTile.getAssetResource().getName().contains("_vert")?vert:hor] = mapTile;

        }

        for(MapTile mapTile: mapSet.getTileLayer().getTiles()){
            tiles[mapTile.getGridx()]
                    [mapTile.getGridy()] = mapTile;
        }

        fill(edges,tiles,flags,x,y,origin,tiles[x][y].getAssetResource());
        return origin;
    }

    private static void expandNorth(MapTile[][][] mapTiles, MapTile[][] tiles, int[][] flags, int orix, int oriy,FogBody fog, MapAsset asset){
        oriy--;
        if(oriy < 0){
            return;
        }
        if(!tiles[orix][oriy].getAssetResource().getName().equals(asset.getName())){
            flags[orix][oriy]++;
            return;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return ;
        }
        if(mapTiles[orix][oriy][hor] != null){
            return ;
        }

        fill(mapTiles,tiles,flags,orix,oriy,fog,asset);
    }
    private static void expandSouth(MapTile[][][] mapTiles, MapTile[][] tiles,  int[][] flags, int orix, int oriy,FogBody fog, MapAsset asset){
        oriy++;
        if(oriy >= mapTiles[0].length ){
            return;
        }
        if(!tiles[orix][oriy].getAssetResource().getName().equals(asset.getName())){
            flags[orix][oriy]++;
            return;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return ;
        }
        if(mapTiles[orix][oriy-1][hor] != null){
            return ;
        }

        fill(mapTiles,tiles,flags,orix,oriy,fog,asset);
    }
    private static void expandEast(MapTile[][][] mapTiles, MapTile[][] tiles, int[][] flags, int orix, int oriy,FogBody fog, MapAsset asset){
        orix++;
        if(orix >= mapTiles.length){
            return;
        }
        if(!tiles[orix][oriy].getAssetResource().getName().equals(asset.getName())){
            flags[orix][oriy]++;
            return;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return ;
        }
        if(mapTiles[orix-1][oriy][vert] != null){
            return ;
        }

        fill(mapTiles,tiles,flags,orix,oriy,fog,asset);
    }
    private static void expandWest(MapTile[][][] mapTiles, MapTile[][] tiles, int[][] flags, int orix, int oriy,FogBody fog, MapAsset asset){
        orix--;
        if(orix < 0){
            return;
        }
        if(!tiles[orix][oriy].getAssetResource().getName().equals(asset.getName())){
            flags[orix][oriy]++;
            return;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return ;
        }
        if(mapTiles[orix][oriy][vert] != null){
            return ;
        }

        fill(mapTiles,tiles,flags,orix,oriy,fog,asset);

    }

}
