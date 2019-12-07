package model.map.structure;

import UI.app.assets.MapAsset;
import UI.pages.editmap.EditMapPage;
import application.config.AppState;
import model.map.tiles.MapTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;

public class MechanicalLayer extends MapLayer {
    public void draw(Graphics g, int mapxoffset, int mapyoffset){

        Collections.sort(getTiles(), new Comparator<MapTile>() {
            @Override
            public int compare(MapTile o1, MapTile o2) {
                boolean o1Floor = o1.getAssetResource().getName().contains("#floor");
                boolean o2Floor = o2.getAssetResource().getName().contains("#floor");
                if(o1Floor && !o2Floor)
                    return -1;
                if(!o1Floor && o2Floor)
                    return 1;
                return (o1.getGridy() ) - (o2.getGridy());
            }
        });

        for(MapTile mapTile: getTiles()){

            if(AppState.ACTIVE_PAGE instanceof EditMapPage){
                if (mapTile.isOnScreen(mapxoffset, mapyoffset)) {
                    mapTile.draw(g);
                }
            }else {
                BufferedImage bimg = mapTile.getAssetResource().getImage();
                String[] parts = mapTile.getAssetResource().getName().split("\\\\|/");
                String name = parts[parts.length-1];
                int x = mapTile.getGridx() ;
                int y = mapTile.getGridy() ;

                if (name.equals("spawn.png") && !mapTile.isOnScreen(mapxoffset, mapyoffset)) {
                    if(x + mapxoffset < 0){
                        x = 0 - mapxoffset;
                    }
                    if(x + mapxoffset > 1150){//
                        x = 1150 - mapxoffset;
                    }
                    if(y + mapyoffset < 0){
                        y = 0 - mapyoffset;
                    }
                    if(y + mapyoffset > 650){
                        y = 650 - mapyoffset;
                    }
                    g.drawImage(bimg,x,y,null);
                }else
                if (name.equals("compass.png")) {
                    x = 15 - mapxoffset;
                    y = 15 - mapyoffset;
                    g.drawImage(bimg,x,y,null);
                }
            }
        }
    }
}
