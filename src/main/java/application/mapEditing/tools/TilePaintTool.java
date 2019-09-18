package application.mapEditing.tools;

import UI.app.assets.MapAsset;
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
    private List<MapAsset> palette = new ArrayList<>();

    public TilePaintTool(){
        addAssetToPaint("./src/main/resources/assets/map/floor/wood/wood 1.png");
    }

    public TilePaintTool(String... assetPath){
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
        MapTile mapTile = map.getMapTile(e.getX(),e.getY());
        try {
            mapTile.setAssetResource(getRandomFromPalette());
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
            mapTile.setAssetResource(getRandomFromPalette());
        }else{
            System.out.println("F");
            PatternTile patternTile = new PatternTile(
                    getRandomFromPalette(),
                    (-map.getXoffset() + e.getX())/ Configuration.TILE_WIDTH,
                    (-map.getYoffset() + e.getY())/ Configuration.TILE_HEIGHT);
            map.getActiveLayer().getTileLayer().getTiles().add(patternTile);
        }
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
