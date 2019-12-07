package application.mapEditing.transpose;

import model.map.structure.MapLayer;
import model.map.tiles.MapTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileUtils {
    public static HashMap<String,List<MapTile>> sortByTags(List<MapTile> tiles){
        HashMap<String,List<MapTile>> tagMap = new HashMap<>();

        Pattern tagPattern = Pattern.compile("#([a-zA-Z]+)");

        for(MapTile tile: tiles){
            Matcher matcher = tagPattern.matcher(tile.getAssetResource().getName());
            boolean any = false;
            while(matcher.find()){
                any = true;
                String match = matcher.toMatchResult().group(1);
                if(tagMap.containsKey(match)){
                    tagMap.get(match).add(tile);
                }else{
                    tagMap.put(match,new ArrayList<>());
                    tagMap.get(match).add(tile);
                }
            }
            if(!any){
                if(!tagMap.containsKey("missed"))
                    tagMap.put("missed",new ArrayList<>());
                tagMap.get("missed").add(tile);
            }
        }
        return tagMap;
    }

    public static void loadIntoTaggedBaskets(List<MapTile> tiles, HashMap<String,MapLayer> taggedLayers){
        HashMap<String,List<MapTile>> tagMap = sortByTags(tiles);
        loadIntoTaggedBaskets(tagMap,taggedLayers);
    }
    public static void loadIntoTaggedBaskets(HashMap<String,List<MapTile>> taggedTiles, HashMap<String,MapLayer> taggedLayers){
        HashMap<MapTile,Boolean> flags = new HashMap<>();

        Iterator<String> iter = taggedTiles.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            List<MapTile> tiles = taggedTiles.get(key);
            for(MapTile t: tiles){
                if(!flags.containsKey(t)){
                    taggedLayers.get(key).getTiles().add(t);
                    flags.put(t,true);
                }
            }
        }
    }
}
