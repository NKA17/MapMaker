package UI.pages.saveMap;

import UI.app.view.ApplicationPage;
import UI.panels.saveMap.SaveMapPanel;
import model.map.structure.RPGMap;

import java.awt.*;

public class SaveMapPage extends ApplicationPage {

    private RPGMap map;

    public SaveMapPage(RPGMap map) {
        this.map = map;
    }

    @Override
    public void loadPage() {
        SaveMapPanel saveMapPanel = new SaveMapPanel(map);
        addPanel(saveMapPanel,0,0, 1);
    }
}
