package application.io;

import UI.app.assets.MapAsset;
import UI.pages.LoadPage.LoadPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.map.structure.GraphicLayer;
import model.map.structure.MapLayer;
import model.map.structure.MapSet;
import model.map.structure.RPGMap;
import model.map.tiles.MapTile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.util.List;

public class Jsonifier {

    private static LoadModel loadModel;
    public static JSONObject toJSON(RPGMap map,LoadModel model){
        JSONObject jsonObject = new JSONObject();
        loadModel = model;

        jsonObject.put("gridWidth",map.getGridWidth());
        jsonObject.put("gridHeight",map.getGridHeight());
        jsonObject.put("mapSets",mapSetsToJSON(map.getLayerSets()));
        jsonObject.put("mechanicalLayer",mapLayerToJSON(map.getMechanicsLayer()));

        return jsonObject;
    }

    private static JSONArray mapSetsToJSON(List<MapSet> mapSets){
        JSONArray array = new JSONArray();

        for(MapSet mapset: mapSets){
            array.put(mapSetToJSON(mapset));
        }
        return array;
    }

    private static JSONObject mapSetToJSON(MapSet mapset){
        JSONObject json = new JSONObject();

        json.put("mapLayer",mapLayerToJSON(mapset.getTileLayer()));
        json.put("edgeLayer",mapLayerToJSON(mapset.getEdgeLayer()));
        json.put("graphicLayers",graphicLayersToJSON(mapset.getGraphicLayers()));
        json.put("gridWidth",mapset.getGridWidth());
        json.put("gridHeight",mapset.getGridHeight());
        json.put("xoffset",mapset.getXoffset());
        json.put("yoffset",mapset.getYoffset());

        return json;
    }

    private static JSONObject mapLayerToJSON(MapLayer mapLayer){
        JSONObject json = new JSONObject();

        if(loadModel!=null)
        loadModel.incrementTotalBytes(mapLayer.getTiles().size());
        json.put("tiles",tilesToJSON(mapLayer.getTiles()));

        return json;
    }

    private static JSONArray graphicLayersToJSON(List<GraphicLayer> graphicLayer){
        JSONArray array = new JSONArray();

        for(GraphicLayer gl: graphicLayer){
            array.put(mapLayerToJSON(gl));
        }

        return array;
    }

    private static JSONArray tilesToJSON(List<MapTile> tiles){
        JSONArray array = new JSONArray();

        for(MapTile tile: tiles){
            array.put(tileToJSON(tile));
        }
        return array;
    }

    private static JSONObject tileToJSON(MapTile tile){
        JSONObject json = new JSONObject();

        json.put("x",tile.getGridx());
        json.put("y",tile.getGridy());
        json.put("rads",tile.getRadians());
        json.put("resource",assetToJSON(tile.getAssetResource()));

        return json;
    }

    private static JSONObject assetToJSON(MapAsset asset){
        JSONObject json = new JSONObject();

        if(loadModel != null)
        loadModel.incrementReadBytes(1);
        json.put("name",asset.getName());
        json.put("xoffset",asset.getImageOffsetX());
        json.put("yoffset",asset.getImageOffsetY());
        json.put("x",asset.getX());
        json.put("y",asset.getY());

        return json;
    }
}
