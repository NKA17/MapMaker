package model.map.tiles;

import UI.app.assets.MapAsset;
import UI.pages.editmap.EditMapPage;
import application.config.AppState;
import application.config.Configuration;
import application.io.AssetCache;

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

        MapAsset asset = getAssetResource();

        if(asset.getName().endsWith("0mechanics\\black.png") && AppState.ACTIVE_PAGE instanceof EditMapPage)
            asset =  AssetCache.get("./src/main/resources/assets/map/reserve/grey.png");
        return asset.getImage().getSubimage(gridx*Configuration.TILE_WIDTH,gridy*Configuration.TILE_HEIGHT,Configuration.TILE_WIDTH,Configuration.TILE_HEIGHT);
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
