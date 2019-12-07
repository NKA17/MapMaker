package model.map.structure;

import UI.pages.editmap.EditMapPage;
import application.config.AppState;
import application.config.Configuration;

import java.awt.*;

public class MapGridLayer extends MapLayer {
    private int rows;
    private int columns;
    private Color editColor = Configuration.EDIT_GRID_COLOR;
    private Color playColor = Configuration.PLAY_GRID_COLOR;

    public MapGridLayer(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
    }

    public void draw(Graphics g,int mapxoffset,int mapyoffset){
        Color color = AppState.ACTIVE_PAGE instanceof EditMapPage? editColor : playColor;
        g.setColor(color);
        for(int x = 0; x < columns; x++){
            for(int y = 0; y < rows; y++){
                g.setColor(color);
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

                    if(AppState.ACTIVE_PAGE instanceof EditMapPage && x % 5 == 0 && y % 5 == 0) {
                        g.setColor(new Color(180, 250, 180, 200));
                        g.drawString(String.format("%d:%d", x, y),
                                x * Configuration.TILE_HEIGHT + 5, y * Configuration.TILE_HEIGHT + 15);
                    }
                }
            }
        }
    }
}
