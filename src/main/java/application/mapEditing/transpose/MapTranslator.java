package application.mapEditing.transpose;

import application.config.Configuration;
import application.io.AssetCache;
import model.map.structure.GraphicLayer;
import model.map.structure.MapLayer;
import model.map.structure.MapSet;
import model.map.structure.RPGMap;
import model.map.tiles.AssetTile;
import model.map.tiles.MapTile;

import java.util.HashMap;
import java.util.List;

public class MapTranslator {
    public static RPGMap toFlattenedMap(RPGMap map){
        RPGMap retMap = new RPGMap();
        MapSet set = new MapSet(map.getGridWidth(),map.getGridHeight());
        retMap.getLayerSets().add(set);
        retMap.setActiveLayer(set);

        MapLayer tileLayer = new MapLayer();
        tileLayer.setTiles(map.getLayerSets().get(0).getTileLayer().getTiles());
        set.setTileLayer(tileLayer);

        GraphicLayer fringeLayer = new GraphicLayer();
        GraphicLayer graphicsLayer = new GraphicLayer();
        GraphicLayer topLayer = new GraphicLayer();
        set.getGraphicLayers().add(fringeLayer);
        set.getGraphicLayers().add(graphicsLayer);
        set.getGraphicLayers().add(topLayer);

        HashMap<String,List<MapTile>> tagMap = TileUtils.sortByTags( map.getActiveLayer().getGraphicLayers().get(0).getTiles());
        HashMap<String,MapLayer> layerMap = new HashMap<>();
        layerMap.put("floor",fringeLayer);
        layerMap.put("missed",graphicsLayer);
        layerMap.put("top",topLayer);

        TileUtils.loadIntoTaggedBaskets(tagMap,layerMap);

        MapLayer edgeLayer = new MapLayer();
        set.setEdgeLayer(edgeLayer);

        tagMap = TileUtils.sortByTags( map.getActiveLayer().getEdgeLayer().getTiles());
        for(MapTile tile: tagMap.get("floor")){
            AssetTile assetTile = new AssetTile(AssetCache.get(tile.getAssetResource().getName()),
                    (1+tile.getGridx())*Configuration.TILE_WIDTH,
                    (1+tile.getGridy())*Configuration.TILE_HEIGHT);
            assetTile.setXoffset(-Configuration.TILE_WIDTH);
            assetTile.setYoffset(-Configuration.TILE_HEIGHT);
            fringeLayer.getTiles().add(assetTile);
        }

        for(MapTile tile: tagMap.get("fence")){
            AssetTile assetTile = new AssetTile(AssetCache.get(tile.getAssetResource().getName()),
                    (1+tile.getGridx())*Configuration.TILE_WIDTH,
                    (1+tile.getGridy())*Configuration.TILE_HEIGHT);
            assetTile.setXoffset(-Configuration.TILE_WIDTH);
            assetTile.setYoffset(-Configuration.TILE_HEIGHT);
            graphicsLayer.getTiles().add(assetTile);
        }

        edgeLayer.getTiles().addAll(tagMap.get("missed"));

        retMap.setName(map.getName());
        return retMap;
    }
}
