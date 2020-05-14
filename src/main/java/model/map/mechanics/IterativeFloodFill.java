package model.map.mechanics;

import UI.app.assets.MapAsset;
import application.io.AssetCache;
import model.map.structure.MapSet;
import model.map.tiles.EdgeTile;
import model.map.tiles.MapTile;
import model.map.tiles.PatternTile;

public class IterativeFloodFill {
    private static final int vert = 1;
    private static final int hor = 0;
    private static final MapAsset asset = AssetCache.get(
            ".\\src\\main\\resources\\assets\\map\\floor\\0mechanics\\black.png");

    private static void fill(MapTile[][][] edges, MapTile[][] tiles, int[][] flags, int orix, int oriy, FogBody fog, MapAsset asset){
        MapAsset originAsset = tiles[orix][oriy].getAssetResource();

        MapTile newt = new PatternTile(asset,orix,oriy);
        fog.getTiles().add(newt);

        boolean fill = true;

        while(fill){

        }
    }

    public static FogBody fill(MapSet mapSet, int x, int y, MapAsset asset){

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

    private static boolean expandNorth(MapTile[][][] mapTiles, MapTile[][] tiles, int[][] flags, int orix, int oriy,FogBody fog, MapAsset asset){
        oriy--;
        if(oriy < 0){
            return false;
        }
        if(!tiles[orix][oriy].getAssetResource().getName().equals(asset.getName())){
            flags[orix][oriy]++;
            return false;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return false;
        }
        if(mapTiles[orix][oriy][hor] != null){
            return false;
        }

        return true;
    }
    private static boolean expandSouth(MapTile[][][] mapTiles, MapTile[][] tiles,  int[][] flags, int orix, int oriy,FogBody fog, MapAsset asset){
        oriy++;
        if(oriy >= mapTiles[0].length ){
            return false;
        }
        if(!tiles[orix][oriy].getAssetResource().getName().equals(asset.getName())){
            flags[orix][oriy]++;
            return false;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return false;
        }
        if(mapTiles[orix][oriy-1][hor] != null){
            return false;
        }

        return true;
    }
    private static boolean expandEast(MapTile[][][] mapTiles, MapTile[][] tiles, int[][] flags, int orix, int oriy,FogBody fog, MapAsset asset){
        orix++;
        if(orix >= mapTiles.length){
            return false;
        }
        if(!tiles[orix][oriy].getAssetResource().getName().equals(asset.getName())){
            flags[orix][oriy]++;
            return false;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return false;
        }
        if(mapTiles[orix-1][oriy][vert] != null){
            return false;
        }

        return true;
    }
    private static boolean expandWest(MapTile[][][] mapTiles, MapTile[][] tiles, int[][] flags, int orix, int oriy,FogBody fog, MapAsset asset){
        orix--;
        if(orix < 0){
            return false;
        }
        if(!tiles[orix][oriy].getAssetResource().getName().equals(asset.getName())){
            flags[orix][oriy]++;
            return false;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return false;
        }
        if(mapTiles[orix][oriy][vert] != null){
            return false;
        }

        return true;
    }

}
