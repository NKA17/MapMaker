package application.mapEditing.tools;

import UI.app.assets.MapAsset;
import UI.mapview.MapLayer;
import UI.mapview.RPGMap;
import UI.mapview.tiles.AssetTile;
import UI.mapview.tiles.MapTile;
import application.mapEditing.toolInterfaces.IPaintTool;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AssetPaintTool implements IPaintTool {
    private List<MapAsset> palette = new ArrayList<>();

    public AssetPaintTool(){
        addAssetToPaint("./src/main/resources/assets/map/assets/Tools/bucket 1.png");
    }

    public AssetPaintTool(String... assetPath){
        for(String path: assetPath){
            addAssetToPaint(path);
        }
    }

    public void addAssetToPaint(String filePath){
        try{
            File f = new File(filePath);
            palette.add(new MapAsset(f));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean hasAssetToPaint(String filePath){
        for(MapAsset asset: palette){
            if(asset.getName().equals((new File(filePath)).getAbsolutePath()))
                return true;
        }
        return false;
    }



    public boolean removeAssetToPaint(String filePath){
        if(palette.size() < 2){
            return false;
        }
        for(int i = 0; i < palette.size(); i++){
            if(palette.get(i).getName().equals(filePath)){
                palette.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        MapLayer mapLayer = map.getActiveLayer();
        try {
            MapAsset asset = getRandomFromPalette();
            AssetTile tile = new AssetTile(asset);
            tile.setGridx(e.getX() - map.getXoffset());
            tile.setGridy(e.getY() - map.getYoffset());
            mapLayer.getTiles().add(tile);
        }catch (Exception e2){
            e2.printStackTrace();
        }
    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {

    }

    @Override
    public void drag(MouseEvent e, RPGMap map) {

    }

    protected List<MapAsset> getPalette() {
        return palette;
    }

    protected void setPalette(List<MapAsset> palette) {
        this.palette = palette;
    }

    private MapAsset getRandomFromPalette(){
        Random rand = new Random();
        return palette.get(rand.nextInt(palette.size()));
    }
}
