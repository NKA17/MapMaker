package UI.pages.mapSelect;

import UI.app.view.ApplicationPage;
import UI.panels.mapSelect.MapSelectPanel;

public class MapSelectPage extends ApplicationPage {


    @Override
    public void loadPage() {
        MapSelectPanel panel = new MapSelectPanel();
        addPanel(panel,0,0,1);
    }
}
