package application.io;

import UI.app.assets.MapAsset;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import model.map.structure.GraphicLayer;
import model.map.structure.MapLayer;
import model.map.structure.MapSet;
import model.map.structure.RPGMap;
import model.map.tiles.AssetTile;
import model.map.tiles.EdgeTile;
import model.map.tiles.MapTile;
import model.map.tiles.PatternTile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Objectifier {

    private static long assetsToLoad = 0;
    private static long assetsLoaded = 0;

    public static RPGMap toRPGMap(JSONObject json){
        RPGMap map = new RPGMap();
        json = json.getJSONObject("map");

        assetsLoaded = 0;
        assetsToLoad = 0;
        map.setGridWidth(json.getInt("gridWidth"));
        map.setGridHeight(json.getInt("gridHeight"));
        map.setLayerSets(toMapSets(json.getJSONObject("mapSets")));
        map.setActiveLayer(map.getLayerSets().get(0));

        return map;
    }

    private static List<MapSet> toMapSets(JSONObject json){
        JSONArray array = json.getJSONArray("myArrayList");
        List<MapSet> mapSets = new ArrayList<>();

        for(int i = 0; i < array.length(); i++){
            mapSets.add(toMapSet(array.getJSONObject(i)));
        }

        return mapSets;
    }

    private static MapSet toMapSet(JSONObject json){
        json = json.getJSONObject("map");
        MapSet mapSet = new MapSet(json.getInt("gridHeight"),json.getInt("gridWidth"));

        mapSet.setTileLayer(toTileLayer(json.getJSONObject("mapLayer")));
        mapSet.setEdgeLayer(toEdgeLayer(json.getJSONObject("edgeLayer")));
        mapSet.setGraphicLayers(toGraphicLayers(json.getJSONObject("graphicLayers")));

        return mapSet;
    }

    private static MapLayer toTileLayer(JSONObject json){
        json = json.getJSONObject("map");
        MapLayer layer = new MapLayer();

        layer.setTiles(toPatternTiles(json.getJSONObject("tiles")));

        return layer;
    }

    private static List<MapTile> toPatternTiles(JSONObject json){
        JSONArray jsonArray = json.getJSONArray("myArrayList");
        List<MapTile> tiles = new ArrayList<>();

        assetsToLoad += jsonArray.length();
        for(int i = 0; i < jsonArray.length(); i++){
            tiles.add(toPatternTile(jsonArray.getJSONObject(i)));
        }

        return tiles;
    }

    private static PatternTile toPatternTile(JSONObject json){
        json = json.getJSONObject("map");
        MapAsset mapAsset = toMapAsset(json.getJSONObject("resource"));

        PatternTile pTile = new PatternTile(mapAsset,json.getInt("x"),json.getInt("y"));

        return pTile;
    }

    private static List<GraphicLayer> toGraphicLayers(JSONObject json){
        JSONArray jsonArray = json.getJSONArray("myArrayList");
        List<GraphicLayer> tiles = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            tiles.add(toGraphicLayer(jsonArray.getJSONObject(i)));
        }

        return tiles;
    }

    private static GraphicLayer toGraphicLayer(JSONObject json){
        json = json.getJSONObject("map");
        GraphicLayer layer = new GraphicLayer();

        for(AssetTile tile: (toGraphicTiles(json.getJSONObject("tiles")))){
            layer.getTiles().add(tile);
        }

        return layer;
    }

    private static List<AssetTile> toGraphicTiles(JSONObject json){
        JSONArray jsonArray = json.getJSONArray("myArrayList");
        List<AssetTile> tiles = new ArrayList<>();

        assetsToLoad += jsonArray.length();
        for(int i = 0; i < jsonArray.length(); i++){
            tiles.add(toGraphicTile(jsonArray.getJSONObject(i)));
        }

        return tiles;
    }

    private static AssetTile toGraphicTile(JSONObject json){
        json = json.getJSONObject("map");
        MapAsset mapAsset = toMapAsset(json.getJSONObject("resource"));

        AssetTile pTile = new AssetTile(mapAsset,json.getInt("x"),json.getInt("y"));

        return pTile;
    }

    private static MapLayer toEdgeLayer(JSONObject json){
        json = json.getJSONObject("map");
        MapLayer layer = new MapLayer();

        layer.setTiles(toEdgeTiles(json.getJSONObject("tiles")));

        return layer;
    }

    private static List<MapTile> toEdgeTiles(JSONObject json){
        JSONArray jsonArray = json.getJSONArray("myArrayList");
        List<MapTile> tiles = new ArrayList<>();

        assetsToLoad += jsonArray.length();
        for(int i = 0; i < jsonArray.length(); i++){
            tiles.add(toEdgeTile(jsonArray.getJSONObject(i)));
        }

        return tiles;
    }

    private static EdgeTile toEdgeTile(JSONObject json){
        json = json.getJSONObject("map");
        MapAsset mapAsset = toMapAsset(json.getJSONObject("resource"));

        EdgeTile pTile = new EdgeTile(mapAsset,json.getInt("x"),json.getInt("y"));

        return pTile;
    }
    private static MapAsset toMapAsset(JSONObject json){
        json = json.getJSONObject("map");
        String name = json.getString("name");
        assetsLoaded++;
        System.out.println(((assetsLoaded + 0.0)/(assetsToLoad + 0.0))* 100);
        return AssetCache.get(name);
    }
}
