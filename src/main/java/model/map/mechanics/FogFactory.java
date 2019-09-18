package model.map.mechanics;

import UI.app.assets.MapAsset;
import model.map.structure.MapLayer;
import model.map.structure.MapSet;
import model.map.tiles.AssetTile;
import model.map.tiles.EdgeTile;
import model.map.tiles.MapTile;
import model.map.tiles.PatternTile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FogFactory {
    private static final int vert = 1;
    private static final int hor = 0;
    private static final MapAsset asset = new MapAsset(
            new File(".\\src\\main\\resources\\assets\\map\\floor\\0mechanics\\black.png"));
    private static enum DirectionExpandedFrom{NONE,NORTH,EAST,SOUTH,WEST;}

    private static List<MapTile> floodFog(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,DirectionExpandedFrom dir){
        List<MapTile> tiles = new ArrayList<>();

        if(flags[orix][oriy] > 0){
            return new ArrayList<>();
        }
        flags[orix][oriy]++;
        MapTile newt = new PatternTile(asset,orix,oriy);
        tiles.add(newt);

        tiles.addAll(expandEast(mapTiles,flags,orix,oriy,DirectionExpandedFrom.NONE));
        tiles.addAll(expandNorth(mapTiles,flags,orix,oriy,DirectionExpandedFrom.NONE));


        return tiles;
    }

    public static List<MapTile> floodFog(MapSet mapSet){
        List<MapTile> tiles = new ArrayList<>();

        List<MapTile> origins = new ArrayList<>();
        MapTile[][][] edges = new EdgeTile[mapSet.getGridWidth()][mapSet.getGridHeight()][2];
        int[][] flags = new int[mapSet.getGridWidth()][mapSet.getGridHeight()];
        for(MapTile mapTile: mapSet.getEdgeLayer().getTiles()){
            edges[mapTile.getGridx()]
                    [mapTile.getGridy()]
                    [mapTile.getAssetResource().getName().contains("_vert")?vert:hor] = mapTile;

            if(null != edges[mapTile.getGridx()][mapTile.getGridy()][hor]
                && null != edges[mapTile.getGridx()][mapTile.getGridy()][vert]){
                origins.add(mapTile);
            }
        }

        for(MapTile mapTile: origins){
            tiles.addAll(floodFog(edges,flags,mapTile.getGridx(),mapTile.getGridy(),DirectionExpandedFrom.NONE));
        }
        return tiles;
    }

    private static List<MapTile> expandNorth(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,DirectionExpandedFrom dir){
        List<MapTile> tiles = new ArrayList<>();
        oriy--;
        if(orix < 0
                ||flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return new ArrayList<>();
        }
        if(mapTiles[orix][oriy][hor] != null){
            return new ArrayList<>();
        }

        tiles.addAll(floodFog(mapTiles,flags,orix,oriy,DirectionExpandedFrom.NONE));

        return tiles;
    }
    private static List<MapTile> expandSouth(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,DirectionExpandedFrom dir){
        List<MapTile> tiles = new ArrayList<>();
        if(flags[orix][oriy] > 0){
            return new ArrayList<>();
        }
        return tiles;
    }
    private static List<MapTile> expandWest(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,DirectionExpandedFrom dir){
        List<MapTile> tiles = new ArrayList<>();
        if(flags[orix][oriy] > 0){
            return new ArrayList<>();
        }
        return tiles;
    }
    private static List<MapTile> expandEast(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,DirectionExpandedFrom dir){
        List<MapTile> tiles = new ArrayList<>();
        orix--;
        if(orix < 0
                ||flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return new ArrayList<>();
        }
        if(mapTiles[orix][oriy][vert] != null){
            return new ArrayList<>();
        }

        tiles.addAll(floodFog(mapTiles,flags,orix,oriy,DirectionExpandedFrom.NONE));

        return tiles;
    }
}
