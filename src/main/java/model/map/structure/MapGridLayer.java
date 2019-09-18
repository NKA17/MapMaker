package model.map.structure;

import application.config.Configuration;

import java.awt.*;

public class MapGridLayer extends MapLayer {
    private int rows;
    private int columns;
    private Color color = Configuration.GRID_COLOR;

    public MapGridLayer(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public void draw(Graphics g,int mapxoffset,int mapyoffset){
        g.setColor(color);
        for(int x = 0; x < rows; x++){
            for(int y = 0; y < columns; y++){
                int xmin = Math.abs(mapxoffset / Configuration.TILE_WIDTH);
                int ymin = Math.abs(mapyoffset / Configuration.TILE_HEIGHT);
                int xmax = xmin+Math.abs((1200)/ Configuration.TILE_WIDTH);
                int ymax = ymin+Math.abs((700)/ Configuration.TILE_HEIGHT);


                boolean validx = x >= xmin && x <= xmax;
                boolean validy = y >= ymin && y <= ymax;
                if(validx && validy) {
                    g.drawRect(
                            x * Configuration.TILE_WIDTH,
                            y * Configuration.TILE_HEIGHT,
                            Configuration.TILE_WIDTH - 1,
                            Configuration.TILE_HEIGHT - 1);
                }
            }
        }
    }
}
