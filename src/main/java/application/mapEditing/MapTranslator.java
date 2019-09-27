package application.mapEditing;

import application.config.Configuration;
import application.io.AssetCache;
import model.map.structure.GraphicLayer;
import model.map.structure.MapLayer;
import model.map.structure.MapSet;
import model.map.structure.RPGMap;
import model.map.tiles.AssetTile;
import model.map.tiles.MapTile;

public class MapTranslator {
    public static RPGMap toFlattenedMap(RPGMap map){
        RPGMap retMap = new RPGMap();
        MapSet set = new MapSet(map.getGridWidth(),map.getGridHeight());
        retMap.getLayerSets().add(set);
        retMap.setActiveLayer(set);

        MapLayer tileLayer = new MapLayer();
        tileLayer.setTiles(map.getLayerSets().get(0).getTileLayer().getTiles());
        set.setTileLayer(tileLayer);

        GraphicLayer graphicsLayer = new GraphicLayer();
        set.getGraphicLayers().add(graphicsLayer);

        for(GraphicLayer layer: map.getLayerSets().get(0).getGraphicLayers()){
            graphicsLayer.getTiles().addAll(layer.getTiles());
        }

        MapLayer edgeLayer = new MapLayer();
        set.setEdgeLayer(edgeLayer);

        for(MapTile tile: map.getActiveLayer().getEdgeLayer().getTiles()){
            if(tile.getAssetResource().getName().contains("fringe")){
                AssetTile assetTile = new AssetTile(AssetCache.get(tile.getAssetResource().getName()),
                        (1+tile.getGridx())*Configuration.TILE_WIDTH,
                        (1+tile.getGridy())*Configuration.TILE_HEIGHT);
                assetTile.setXoffset(-Configuration.TILE_WIDTH);
                assetTile.setYoffset(-Configuration.TILE_HEIGHT);
                graphicsLayer.getTiles().add(assetTile);
            }else {
                edgeLayer.getTiles().add(tile);
            }
        }

        retMap.setName(map.getName());
        return retMap;
    }
}
