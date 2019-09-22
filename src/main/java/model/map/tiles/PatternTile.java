package model.map.tiles;

import UI.app.assets.MapAsset;
import application.config.Configuration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PatternTile extends MapTile{
    public PatternTile(File assetFile, int gridx, int gridy) {
        super(assetFile, gridx, gridy);
    }
    public PatternTile(MapAsset assetFile, int gridx, int gridy) {
        super(assetFile, gridx, gridy);
    }


    public BufferedImage buildTile(){
        int segsw = getAssetResource().getImage().getWidth() / Configuration.TILE_WIDTH;
        int segsh = getAssetResource().getImage().getHeight() / Configuration.TILE_HEIGHT;
        int gridx = getGridx()==0?0:getGridx() % segsw;
        int gridy = getGridy()==0?0:getGridy() % segsh;
        return getAssetResource().getImage().getSubimage(gridx*Configuration.TILE_WIDTH,gridy*Configuration.TILE_HEIGHT,Configuration.TILE_WIDTH,Configuration.TILE_HEIGHT);
    }



    @Override
    public void draw(Graphics g) {
        g.drawImage(
                buildTile(),
                getGridx()*Configuration.TILE_WIDTH,
                getGridy()*Configuration.TILE_HEIGHT,
                Configuration.TILE_WIDTH,
                Configuration.TILE_HEIGHT,
                null);

    }


}
