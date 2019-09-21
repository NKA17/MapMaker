package application.mapEditing.tools;

import UI.app.assets.MapAsset;
import application.io.AssetCache;
import model.map.structure.RPGMap;
import model.map.tiles.EdgeTile;
import model.map.tiles.MapTile;
import application.config.Configuration;
import application.mapEditing.toolInterfaces.IPaintTool;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EdgePaintTool implements IPaintTool {

    private List<String> vertPalette = new ArrayList<>();
    private List<String> palette = new ArrayList<>();
    private String defaultAsset = "./src/main/resources/assets/map/structure/basic/1.png";

    public EdgePaintTool(){
        addAssetToPaint(defaultAsset);
    }

    public EdgePaintTool(String... assetPath){
        for(String path: assetPath){
            addAssetToPaint(path);
        }
    }


    public void addAssetToPaint(String filePath){
        filePath = (new File(filePath).getAbsolutePath());
        palette.add(filePath);
        String vertFile = filePath.replace(".png","_vert.png");
        vertPalette.add(vertFile);
    }

    @Override
    public boolean hasAssetToPaint(String filePath) {
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
                vertPalette.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public void activateTool(MouseEvent e, RPGMap map) {
        editTile(e,map,false);
    }

    @Override
    public void deactivateTool(MouseEvent e, RPGMap map) {

    }

    @Override
    public void drag(MouseEvent e, RPGMap map) {
        editTile(e,map,true);
    }

    private void editTile(MouseEvent e, RPGMap map, boolean isDrag){
        int index = getRandomPaletteIndex();
        String assetString = palette.get(index);
        MapAsset asset = AssetCache.get(assetString);
        MapTile edgeTile = new EdgeTile(
                asset,
                (e.getX() - map.getXoffset()-10) / Configuration.TILE_WIDTH,
                (e.getY() - map.getYoffset()-10) / Configuration.TILE_HEIGHT);


        List<MapTile> mapTiles = map.getActiveLayer().getEdgeLayer().getTilesOverlapping(edgeTile);

        if(!isBottomEdge(e,edgeTile,map) && !isRightEdge(e,edgeTile,map)) {
            return;
        }else if(asset.getName().endsWith("empty 1.png")) {
            if(isRightEdge(e,edgeTile,map) && !isBottomEdge(e,edgeTile,map)){
                for(MapTile tile: mapTiles){
                    if(tile.getAssetResource().getName().contains("_vert")){
                        map.getActiveLayer().getEdgeLayer().getTiles().remove(tile);
                    }
                }
            }else{
                for(MapTile tile: mapTiles){
                    if(!tile.getAssetResource().getName().contains("_vert")){
                        map.getActiveLayer().getEdgeLayer().getTiles().remove(tile);
                    }
                }
            }
        }else {
            if(isRightEdge(e,edgeTile,map) && isBottomEdge(e,edgeTile,map)){
                return;
            }
            if(isRightEdge(e,edgeTile,map) && !isBottomEdge(e,edgeTile,map)){
                for(MapTile tile: mapTiles){
                    if((!isDrag || !tile.getAssetResource().getName().equals(asset.getName().replace(".png","_vert.png")))
                            && tile.getAssetResource().getName().contains("_vert")){
                        map.getActiveLayer().getEdgeLayer().getTiles().remove(tile);
                    }
                }
                assetString = vertPalette.get(index);
                asset = AssetCache.get(assetString);
                edgeTile.setAssetResource(asset);
                asset.setImageOffsetY(-(asset.getImage().getHeight()-Configuration.TILE_HEIGHT)/4);
            }else{
                for(MapTile tile: mapTiles){
                    if((!isDrag || !tile.getAssetResource().getName().equals(asset.getName()))
                            && !tile.getAssetResource().getName().contains("_vert")){
                        map.getActiveLayer().getEdgeLayer().getTiles().remove(tile);
                    }
                }
                asset.setImageOffsetX(-(asset.getImage().getWidth()-Configuration.TILE_WIDTH)/4);
            }
            for(MapTile mapTile: mapTiles){
                if(mapTile.getAssetResource().getName().equals(asset.getName()))
                    return;
            }
            map.getActiveLayer().getEdgeLayer().getTiles().add(edgeTile);
            System.out.println(map.getActiveLayer().getEdgeLayer().getTiles().size());
        }
    }


    @Override
    public void resetPalette() {
        palette.clear();
        vertPalette.clear();
    }

    @Override
    public boolean isEmpty() {
        return palette.size() == 0;
    }

    private int getRandomPaletteIndex(){
        Random rand = new Random();
        return rand.nextInt(palette.size());
    }

    public boolean isBottomEdge(MouseEvent e, MapTile mapTile,RPGMap map){
        int edge = mapTile.getGridy() * Configuration.TILE_HEIGHT + Configuration.TILE_HEIGHT;
        return (e.getY()-map.getYoffset() > edge - 10
                && e.getY()-map.getYoffset() < edge + 10);
    }

    public boolean isRightEdge(MouseEvent e, MapTile mapTile, RPGMap map){
        if(palette.size()==0){
            addAssetToPaint(defaultAsset);
        }
        int edge = mapTile.getGridx() * Configuration.TILE_WIDTH + Configuration.TILE_WIDTH;
        return (e.getX()-map.getXoffset() > edge - 10
                && e.getX()-map.getXoffset() < edge + 10);
    }
}
