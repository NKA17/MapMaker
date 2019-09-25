package application.mapEditing.tools;

import UI.app.assets.MapAsset;
import application.io.AssetCache;
import model.map.structure.MapLayer;
import model.map.structure.RPGMap;
import model.map.tiles.AssetTile;
import application.mapEditing.toolInterfaces.IPaintTool;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AssetPaintTool implements IPaintTool {
    private List<String> palette = new ArrayList<>();
    private String defaultAsset = "./src/main/resources/assets/map/assets/tools/bucket 1.png";

    public AssetPaintTool(){
        addAssetToPaint(defaultAsset);
    }

    public AssetPaintTool(String... assetPath){
        for(String path: assetPath){
            addAssetToPaint(path);
        }
    }

    public void addAssetToPaint(String filePath){
        try{
            filePath = (new File(filePath).getAbsolutePath());
            palette.add(filePath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean hasAssetToPaint(String filePath){
        filePath = (new File(filePath).getAbsolutePath());
        for(String  asset: palette){
            if(asset.equals((new File(filePath)).getAbsolutePath()))
                return true;
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
    public void activateTool(MouseEvent e, RPGMap map) {
        MapLayer mapLayer = map.getActiveLayer().getActiveGraphicLayer();
        try {
            String assetString = getRandomFromPalette();
            MapAsset asset = AssetCache.get(assetString);
            AssetTile tile = new AssetTile(asset);
            tile.setGridx(e.getX() - map.getActiveLayer().getXoffset());
            tile.setGridy(e.getY() - map.getActiveLayer().getYoffset());
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

    protected List<String> getPalette() {
        return palette;
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
}
