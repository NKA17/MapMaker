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

public class FogFactory {
    private static final int vert = 1;
    private static final int hor = 0;
    private static final MapAsset asset = AssetCache.get(
           ".\\src\\main\\resources\\assets\\map\\floor\\0mechanics\\black.png");

    private static void floodFog(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,FogBody fog){

        if(flags[orix][oriy] > 0){
            return ;
        }
        flags[orix][oriy]++;
        MapTile newt = new PatternTile(asset,orix,oriy);
        fog.getTiles().add(newt);

        expandEast(mapTiles,flags,orix,oriy,fog);
        expandNorth(mapTiles,flags,orix,oriy,fog);
        expandWest(mapTiles,flags,orix,oriy,fog);
        expandSouth(mapTiles,flags,orix,oriy,fog);
    }

    public static List<FogBody> floodFog(MapSet mapSet){
        List<FogBody> tiles = new ArrayList<>();

        List<MapTile> origins = new ArrayList<>();
        MapTile[][][] edges = new EdgeTile[mapSet.getGridWidth()][mapSet.getGridHeight()][2];
        int[][] flags = new int[mapSet.getGridWidth()][mapSet.getGridHeight()];
        for(MapTile mapTile: mapSet.getEdgeLayer().getTiles()){
            if(mapTile.getAssetResource().getName().toLowerCase().contains("fringe"))
                continue;
            edges[mapTile.getGridx()]
                    [mapTile.getGridy()]
                    [mapTile.getAssetResource().getName().contains("_vert")?vert:hor] = mapTile;

            if(null != edges[mapTile.getGridx()][mapTile.getGridy()][hor]
                && null != edges[mapTile.getGridx()][mapTile.getGridy()][vert]){
                origins.add(mapTile);
            }
        }

        for(MapTile mapTile: origins){
            FogBody fog = new FogBody();
            try {
                if(flags[mapTile.getGridx()][mapTile.getGridy()]==0) {
                    floodFog(edges, flags, mapTile.getGridx(), mapTile.getGridy(), fog);
                    if (fog.isShow())
                        tiles.add(fog);
                }
            }catch (Exception e){
                System.out.println("Stack Overflow on Flood Fill");
            }
        }
        return tiles;
    }

    private static void expandNorth(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,FogBody fog){
        oriy--;
        if(oriy < 0){
            fog.setShow(false);
            return;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return ;
        }
        if(mapTiles[orix][oriy][hor] != null){
            return ;
        }

        floodFog(mapTiles,flags,orix,oriy,fog);
    }
    private static void expandSouth(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,FogBody fog){
        oriy++;
        if(oriy >= mapTiles[0].length ){
            fog.setShow(false);
            return;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return ;
        }
        if(mapTiles[orix][oriy-1][hor] != null){
            return ;
        }

        floodFog(mapTiles,flags,orix,oriy,fog);
    }
    private static void expandWest(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,FogBody fog){
        orix++;
        if(orix >= mapTiles.length){
            fog.setShow(false);
            return;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return ;
        }
        if(mapTiles[orix-1][oriy][vert] != null){
            return ;
        }

        floodFog(mapTiles,flags,orix,oriy,fog);
    }
    private static void expandEast(MapTile[][][] mapTiles, int[][] flags, int orix, int oriy,FogBody fog){
        orix--;
        if(orix < 0){
            fog.setShow(false);
            return;
        }
        if(flags[orix][oriy] > 0){
            flags[orix][oriy]++;
            return ;
        }
        if(mapTiles[orix][oriy][vert] != null){
            return ;
        }

        floodFog(mapTiles,flags,orix,oriy,fog);

    }

}
