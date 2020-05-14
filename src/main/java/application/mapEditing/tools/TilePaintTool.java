package application.mapEditing.tools;

import UI.app.assets.MapAsset;
import application.io.AssetCache;
import model.map.structure.RPGMap;
import model.map.tiles.MapTile;
import model.map.tiles.PatternTile;
import application.config.Configuration;
import application.mapEditing.toolInterfaces.IPaintTool;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TilePaintTool implements IPaintTool {
    private List<String> palette = new ArrayList<>();
    private String defaultAsset = "./src/main/resources/assets/map/floor/0mechanics/black.png";

    public TilePaintTool(){
        addAssetToPaint(defaultAsset);
    }


    public void addAssetToPaint(String filePath){
        try{
            palette.add((new File(filePath).getAbsolutePath()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean hasAssetToPaint(String filePath){
        for(String asset: palette){
            if(asset.equals((new File(filePath)).getAbsolutePath()))
                return true;
        }
        return false;
    }



    public boolean removeAssetToPaint(String filePath){
        if(palette.size() < 2){
            return false;
        }
        filePath = (new File(filePath).getAbsolutePath());
        for(int i = 0; i < palette.size(); i++){
            if(palette.get(i).equals(filePath)){
                palette.remove(i);
                return true;
            }
        }

        return false;
    }
    @Override
    public void resetPalette() {
        palette.clear();
    }

    @Override
    public boolean isEmpty() {
        return palette.size() == 0;
    }

    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        MapTile mapTile = map.getMapTile(e.getX(),e.getY());
        try {
            mapTile.setAssetResource(AssetCache.get(getRandomFromPalette()));
        }catch (Exception e2){
            e2.printStackTrace();
        }
    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {

    }

    @Override
    public void drag(MouseEvent e, RPGMap map) {
        MapTile mapTile = map.getMapTile(e.getX(),e.getY());
        if(mapTile != null) {
            mapTile.setAssetResource(AssetCache.get(getRandomFromPalette()));
        }else{
            MapAsset asset = AssetCache.get(getRandomFromPalette());
            PatternTile patternTile = new PatternTile(
                    asset,
                    (-map.getXoffset() + e.getX())/ Configuration.TILE_WIDTH,
                    (-map.getYoffset() + e.getY())/ Configuration.TILE_HEIGHT);
            //map.getActiveLayer().getTileLayer().getTiles().add(patternTile);
        }
    }

    protected void setPalette(List<String> palette) {
        this.palette = palette;
    }

    private String getRandomFromPalette(){
        if(palette.size()==0){
            addAssetToPaint(defaultAsset);
        }
        Random rand = new Random();
        return palette.get(rand.nextInt(palette.size()));
    }

    public List<String> getPalette(){
        return palette;
    }

    public MapAsset getRandomAsset(){
        return AssetCache.get(getRandomFromPalette());
    }
}
