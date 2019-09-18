package model.map.tiles;

import UI.app.assets.MapAsset;

import java.awt.*;
import java.io.File;

public class AssetTile extends MapTile {

    public AssetTile(File assetFile, int gridx, int gridy) {
        super(assetFile, gridx, gridy);
    }

    public AssetTile(File assetFile) {
        super(assetFile, 0, 0);
    }

    public AssetTile(MapAsset mapAsset, int gridx, int gridy) {
        super(mapAsset, gridx, gridy);
    }

    public AssetTile(MapAsset mapAsset) {
        super(mapAsset, 0, 0);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getAssetResource().getImage(),
                getGridx() - (getAssetResource().getImage().getWidth() / 2),
                getGridy() - (getAssetResource().getImage().getHeight()),
                null);
    }

    public  boolean isOnScreen(int mapxoffset, int mapyoffset){
        int xmin = -mapxoffset - getAssetResource().getImage().getWidth();
        int ymin = -mapyoffset - getAssetResource().getImage().getHeight();
        int xmax = -mapxoffset + (getAssetResource().getImage().getWidth() + 1200);
        int ymax = -mapyoffset + (getAssetResource().getImage().getHeight() + 700);

        boolean validx = getGridx() >= xmin && getGridx() <= xmax;
        boolean validy = getGridy() >= ymin && getGridy() <= ymax;

        return validx && validy;
    }
}
