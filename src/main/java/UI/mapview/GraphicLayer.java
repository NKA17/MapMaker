package UI.mapview;

import UI.mapview.tiles.MapTile;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

public class GraphicLayer extends MapLayer {
    public void draw(Graphics g, int mapxoffset, int mapyoffset){

        Collections.sort(getTiles(), new Comparator<MapTile>() {
            @Override
            public int compare(MapTile o1, MapTile o2) {
                return o1.getGridy() - o2.getGridy();
            }
        });

        for(MapTile mapTile: getTiles()){

            if(mapTile.isOnScreen(mapxoffset,mapyoffset)) {

                mapTile.draw(g);
            }
        }
    }
}
