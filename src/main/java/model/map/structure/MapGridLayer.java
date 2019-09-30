package model.map.structure;

import application.config.AppState;
import application.config.Configuration;

import java.awt.*;

public class MapGridLayer extends MapLayer {
    private int rows;
    private int columns;
    private Color color = Configuration.GRID_COLOR;

    public MapGridLayer(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
    }

    public void draw(Graphics g,int mapxoffset,int mapyoffset){
        g.setColor(color);
        for(int x = 0; x < columns; x++){
            for(int y = 0; y < rows; y++){
                g.setColor(color);
                int xmin = (int)(Math.abs(mapxoffset / Configuration.TILE_WIDTH)/ AppState.ZOOM);
                int ymin = (int)(Math.abs(mapyoffset / Configuration.TILE_HEIGHT)/ AppState.ZOOM);
                int xmax = xmin+(int)(Math.abs((1200)/ Configuration.TILE_WIDTH)/ AppState.ZOOM);
                int ymax = ymin+(int)(Math.abs((700)/ Configuration.TILE_HEIGHT)/ AppState.ZOOM);


                boolean validx = x >= xmin && x <= xmax;
                boolean validy = y >= ymin && y <= ymax;
                if(validx && validy) {
                    g.drawRect(
                            x * Configuration.TILE_WIDTH,
                            y * Configuration.TILE_HEIGHT,
                            Configuration.TILE_WIDTH - 1,
                            Configuration.TILE_HEIGHT - 1);

//                    g.setColor(new Color(180,250,180,200));
//                    g.drawString(String.format("%d:%d",x ,y),
//                            x * Configuration.TILE_HEIGHT+5,y * Configuration.TILE_HEIGHT+15);
                }
            }
        }
    }
}
