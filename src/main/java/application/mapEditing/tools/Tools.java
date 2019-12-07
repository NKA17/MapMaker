package application.mapEditing.tools;

import application.mapEditing.toolInterfaces.IPaintTool;

public class Tools {
    public static IPaintTool PAINT_TOOL = new TilePaintTool();
    public static IPaintTool EDGE_PAINT_TOOL = new EdgePaintTool();
    public static IPaintTool GRAPHIC_PAINT_TOOL = new AssetPaintTool();
    public static IPaintTool MECHANICAL_PAINT_TOOL = new AssetPaintTool(".\\src\\main\\resources\\assets\\map\\mechanics\\markers\\spawn.png");
    public static IPaintTool FLOOD_FILL_TOOL = new FloodFillTool();
}
