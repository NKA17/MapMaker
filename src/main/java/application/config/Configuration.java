package application.config;

import UI.app.assets.MapAsset;
import application.io.AssetCache;

import java.awt.*;

import static application.io.AssetCache.*;

public class Configuration {
    public static final int TILE_WIDTH = 50;
    public static final int TILE_HEIGHT = 50;

    public static MapAsset DEFAULT_FLOOR_TILE =
            get("C:\\Users\\Nate\\IdeaProjects\\RPGMapMaker\\src\\main\\resources\\assets\\map\\floor\\0mechanics\\black.png");

    public static Insets GRIDBAG_INSETS = new Insets(4,4,4,10);
    public static Color PLAY_GRID_COLOR = new Color(0,0,0,255);
    public static Color EDIT_GRID_COLOR = new Color(130,200,130,255);
    public static Color PANEL_BG_COLOR = new Color(80,80,80);
    public static Color WINDOW_BG_COLOR = new Color(30,30,30);

    public static String SAVE_MAP_FOLDER = "./src/main/resources/mapSaves/";
    public static String SAVE_LOOT_FOLDER = "./src/main/resources/lootSaves/";
    public static final String FILE_EXTENSION = ".mf";

    //public static final Color COMP_BG_COLOR = new Color(123,182,255);
    public static final Color COMP_BG_COLOR = new Color(52,177,199);
    public static final Color COMP_HOVER_BG_COLOR = new Color(166,233,245);
    public static final Color COMP_BORDER_COLOR = new Color(0,0,0);

    public static final Color HIGHLIGHT_COLOR = new Color(166,233,245);

    public static final Font GAME_FONT = new Font("Yu Gothic UI",Font.BOLD,20);
    public static final Font TEXT_FONT = new Font("Yu Gothic UI",Font.BOLD,12);
}
