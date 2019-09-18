package application.mapEditing.tools;

import application.mapEditing.toolInterfaces.IPaintTool;

public class Tools {
    public static IPaintTool PAINT_TOOL = new TilePaintTool();
    public static IPaintTool EDGE_PAINT_TOOL = new EdgePaintTool();
    public static IPaintTool GRAPHIC_PAINT_TOOL = new AssetPaintTool();
}
