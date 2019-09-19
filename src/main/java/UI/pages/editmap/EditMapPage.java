package UI.pages.editmap;

import UI.app.view.ApplicationPage;
import UI.panels.editmap.MapToolPanel;
import UI.panels.editmap.MapViewPanel;
import application.io.MapIO;
import model.map.structure.RPGMap;

public class EditMapPage extends ApplicationPage{


    @Override
    public void loadPage() {

        RPGMap map = MapIO.loadMap("newMap.json");

        MapViewPanel mapView = new MapViewPanel();
        mapView.setMap(map);
        MapToolPanel mapToolPanel = new MapToolPanel(mapView);
        mapToolPanel.setMap(mapView.getMap());
        addPanel(mapToolPanel,0,0);

        addPanel(mapView,1,0);
        mapView.createEditor();
    }

}
