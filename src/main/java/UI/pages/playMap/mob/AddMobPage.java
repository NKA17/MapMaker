package UI.pages.playMap.mob;

import UI.app.view.ApplicationPage;
import UI.panels.mapView.MapViewPanel;
import UI.panels.playMap.mob.AddMobPanel;
import UI.panels.saveMap.SaveMapPanel;
import model.map.structure.RPGMap;

public class AddMobPage extends ApplicationPage {

    private RPGMap map;
    private MapViewPanel viewPanel;

    public AddMobPage(RPGMap map, MapViewPanel viewPanel) {
        this.map = map;
        this.viewPanel = viewPanel;
    }

    @Override
    public void loadPage() {
        getObserver().setTitle("Create Mob");
        AddMobPanel addMobPanel = new AddMobPanel(map,viewPanel);
        addPanel(addMobPanel,0,0, 1);
    }
}
