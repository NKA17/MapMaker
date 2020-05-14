package model.map.structure;

import model.map.tiles.AssetTile;
import model.map.tiles.MapTile;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

public class GraphicLayer extends MapLayer {
    public void draw(Graphics g, int mapxoffset, int mapyoffset){

        Collections.sort(getTiles(), new Comparator<MapTile>() {
            @Override
            public int compare(MapTile o1, MapTile o2) {
                boolean o1Floor = o1.getAssetResource().getName().contains("#floor");
                boolean o2Floor = o2.getAssetResource().getName().contains("#floor");
                boolean o1Top = o1.getAssetResource().getName().contains("#top");
                boolean o2Top = o2.getAssetResource().getName().contains("#top");
                if(o1Floor && !o2Floor)
                    return -1;
                if(!o1Floor && o2Floor)
                    return 1;
                if(o1Top && !o2Top)
                    return 1;
                if(!o1Top && o2Top)
                    return -1;
                return (o1.getGridy() ) - (o2.getGridy());
            }
        });

        for(MapTile mapTile: getTiles()){

            if(mapTile.isOnScreen(mapxoffset,mapyoffset)) {

                mapTile.draw(g);
            }
        }
    }
}
