package application.io;

import UI.app.assets.MapAsset;
import UI.pages.editmap.EditMapPage;
import application.config.AppState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class AssetCache {
    public static HashMap<String,MapAsset> cache = new HashMap<>();
    public static BufferedImage COG_ICON;

    static{
        try{
            COG_ICON = ImageIO.read(new File("C:\\Users\\Nate\\IdeaProjects\\RPGMapMaker\\src\\main\\resources\\icons\\cog.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static MapAsset get(String name){
        try {
            name = (new File(name)).getAbsolutePath();
            if (cache.containsKey(name)) {
                return cache.get(name);
            } else {
                MapAsset asset = new MapAsset(new File(name));
                cache.put(name, asset);
                return asset;
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("If using URI with resource folder, remember that the relative path might be " +
                    "starting from the resources folder and not the src folder");
            return null;
        }
    }
}
