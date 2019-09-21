package application.mapEditing.tools;

import application.io.AssetCache;
import application.mapEditing.toolInterfaces.IPaintTool;
import model.map.mechanics.FloodFill;
import model.map.mechanics.FogBody;
import model.map.structure.RPGMap;
import model.map.tiles.MapTile;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FloodFillTool implements IPaintTool {
    private List<String> palette = new ArrayList<>();

    @Override
    public void addAssetToPaint(String filePath) {

    }

    @Override
    public boolean hasAssetToPaint(String filePath) {
        return false;
    }

    @Override
    public boolean removeAssetToPaint(String filePath) {
        return false;
    }

    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        MapTile tile = map.getMapTile(e.getX(),e.getY());
        FogBody fogBody = FloodFill.fill(map.getActiveLayer(),tile.getGridx(),tile.getGridy(),tile.getAssetResource());
        for(MapTile ft: fogBody.getTiles()){
            for(MapTile mt: map.getActiveLayer().getTileLayer().getTiles()){
                if(mt.getGridx() == ft.getGridx() && mt.getGridy() == ft.getGridy()){
                    mt.setAssetResource(AssetCache.get(getRandomFromPalette()));
                }
            }
        }
    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {

    }


    @Override
    public boolean isEmpty() {
        return palette.size() == 0;
    }

    @Override
    public void resetPalette() {
        palette.clear();
    }

    @Override
    public void drag(MouseEvent e, RPGMap map) {

    }

    private String getRandomFromPalette(){
        Random rand = new Random();
        List<String> palette = ((TilePaintTool)Tools.PAINT_TOOL).getPalette();
        return palette.get(rand.nextInt(palette.size()));
    }
}
