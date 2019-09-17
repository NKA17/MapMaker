package UI.mapview.tiles;

import UI.app.assets.MapAsset;
import application.config.Configuration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class MapTile {
    private int gridx;
    private int gridy;
    private MapAsset assetResource;

    public MapTile(File assetFile, int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.assetResource = new MapAsset(assetFile);
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
        int xmin = Math.abs(-mapxoffset / Configuration.TILE_WIDTH);
        int ymin = Math.abs(-mapyoffset / Configuration.TILE_HEIGHT);
        int xmax = xmin+Math.abs((1200)/ Configuration.TILE_WIDTH);
        int ymax = ymin+Math.abs((700)/ Configuration.TILE_HEIGHT);

        boolean validx = gridx >= xmin && gridx <= xmax;
        boolean validy = gridy >= ymin && gridy <= ymax;

        return validx && validy;
    }
}
