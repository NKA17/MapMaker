package application.config;

import UI.app.view.ApplicationPage;
import application.mapEditing.toolInterfaces.Draggable;
import application.mapEditing.toolInterfaces.ITool;

public class AppState {
    public static ApplicationPage ACTIVE_PAGE = null;
    public static ITool ACTIVE_TOOL = null;
    public static Draggable ACTIVE_DRAGGABLE = null;
    public static double ZOOM = 1.0;
}
