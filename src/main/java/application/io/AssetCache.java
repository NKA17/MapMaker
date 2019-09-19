package application.io;

import UI.app.assets.MapAsset;

import java.io.File;
import java.util.HashMap;

public class AssetCache {
    public static HashMap<String,MapAsset> cache = new HashMap<>();

    public static MapAsset get(String name){
        if(cache.containsKey(name)){
            return cache.get(name);
        }else {
            MapAsset asset = new MapAsset(new File(name));
            cache.put(name,asset);
            return asset;
        }
    }
}
