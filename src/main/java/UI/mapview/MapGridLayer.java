package UI.mapview;

import UI.mapview.MapLayer;
import UI.mapview.tiles.MapTile;
import UI.mapview.tiles.PatternTile;

import java.awt.*;

public class MapGridLayer extends MapLayer {
    private int rows;
    private int columns;
    private int width;
    private int height;
    private Color color = new Color(170,170,170,200);

    public MapGridLayer(int rows, int columns, int width, int height) {
        this.rows = rows;
        this.columns = columns;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g,int mapxoffset,int mapyoffset){
        g.setColor(color);
        for(int x = 0; x < rows; x++){
            for(int y = 0; y < columns; y++){
                int xmin = Math.abs(mapxoffset / width);
                int ymin = Math.abs(mapyoffset / height);
                int xmax = xmin+Math.abs((1200)/ width);
                int ymax = ymin+Math.abs((700)/ height);


                boolean validx = x >= xmin && x <= xmax;
                boolean validy = y >= ymin && y <= ymax;
                if(validx && validy) {
                    g.drawRect(x * width, y * height, width - 1, height - 1);
                }
            }
        }
    }
}
