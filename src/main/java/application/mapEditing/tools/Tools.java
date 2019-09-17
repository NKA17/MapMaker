package application.mapEditing.tools;

import application.mapEditing.toolInterfaces.IPaintTool;

public class Tools {
    public static IPaintTool PAINT_TOOL = new PaintTool();
    public static IPaintTool EDGE_PAINT_TOOL = new EdgePaintTool("./src/main/resources/assets/map/structure/door/door 1.png");

    public static IPaintTool GRAPHIC_PAINT_TOOL = new AssetPaintTool();
}
