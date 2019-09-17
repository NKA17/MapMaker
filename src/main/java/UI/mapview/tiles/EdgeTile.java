package UI.mapview.tiles;

import UI.app.assets.MapAsset;
import application.config.Configuration;

import java.awt.*;
import java.io.File;

public class EdgeTile extends MapTile {
    public EdgeTile(File assetFile, int gridx, int gridy) {
        super(assetFile, gridx, gridy);
    }

    public EdgeTile(MapAsset mapAsset, int gridx, int gridy) {
        super(mapAsset, gridx, gridy);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(
                getAssetResource().getImage(),
                getGridx()* Configuration.TILE_WIDTH+getAssetResource().getImageOffsetX(),
                getGridy()*Configuration.TILE_HEIGHT+getAssetResource().getImageOffsetY(),
                null);
    }
}
