package model.map.tiles;

import UI.app.assets.MapAsset;
import application.config.AppState;
import application.config.Configuration;
import application.io.AssetCache;

import java.awt.*;
import java.io.File;

public abstract class MapTile {
    private int gridx;
    private int gridy;
    private MapAsset assetResource;

    public MapTile(File assetFile, int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.assetResource = AssetCache.get(assetFile.getAbsolutePath());
    }

    public MapTile(MapAsset mapAsset, int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.assetResource = mapAsset;
    }

    public int getGridx() {
        return gridx;
    }

    public void setGridx(int gridx) {
        this.gridx = gridx;
    }

    public int getGridy() {
        return gridy;
    }

    public void setGridy(int gridy) {
        this.gridy = gridy;
    }


    public MapAsset getAssetResource() {
        return assetResource;
    }

    public void setAssetResource(MapAsset assetResource) {
        this.assetResource = assetResource;
    }

    public abstract void draw(Graphics g);

    public  boolean isOnScreen(int mapxoffset, int mapyoffset){
        int xmin = Math.abs(-(int)(mapxoffset* AppState.ZOOM) / (int)(Configuration.TILE_WIDTH* AppState.ZOOM));
        int ymin = Math.abs(-(int)(mapyoffset* AppState.ZOOM) / (int)(Configuration.TILE_HEIGHT*AppState.ZOOM));
        int xmax = xmin+Math.abs((1200)/ (int)(Configuration.TILE_WIDTH*AppState.ZOOM));
        int ymax = ymin+Math.abs((700)/ (int)(Configuration.TILE_HEIGHT*AppState.ZOOM));

        boolean validx = gridx >= xmin && gridx <= xmax;
        boolean validy = gridy >= ymin && gridy <= ymax;

        return validx && validy;
    }

    public String toString(){
        String name = getAssetResource().getName();
        name = name.substring(name.lastIndexOf("\\"));
        return String.format("%s:%d,%d",name,getGridx(),getGridy());
    }
}
