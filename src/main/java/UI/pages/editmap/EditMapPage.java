package UI.pages.editmap;

import UI.app.view.ApplicationPage;
import UI.panels.editmap.MapToolPanel;
import UI.panels.editmap.MapViewPanel;

public class EditMapPage extends ApplicationPage{


    @Override
    public void loadPage() {
        MapViewPanel mapView = new MapViewPanel();
        MapToolPanel mapToolPanel = new MapToolPanel(mapView);
        mapToolPanel.setMap(mapView.getMap());
        addPanel(mapToolPanel,0,0);

        addPanel(mapView,1,0);
        mapView.createEditor();
    }

}
