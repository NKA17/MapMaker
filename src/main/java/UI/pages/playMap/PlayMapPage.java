package UI.pages.playMap;

import UI.app.view.ApplicationPage;
import UI.panels.mapView.MapViewPanel;
import UI.panels.playMap.PlayToolPanel;
import application.config.AppState;
import application.mapEditing.tools.DragTool;
import model.map.structure.MapSet;
import model.map.structure.RPGMap;

public class PlayMapPage extends ApplicationPage {
    private RPGMap map;

    public PlayMapPage(RPGMap map) {
        this.map = map;
        for(MapSet set: map.getLayerSets()){
            set.buildFogLayer();
        }
    }

    @Override
    public void loadPage() {
        MapViewPanel viewPanel = new MapViewPanel();
        addPanel(viewPanel,1,0);
        viewPanel.setMap(map);
        viewPanel.createPlayEditor();
        viewPanel.getMapEditor().setTool(new DragTool());
        AppState.ACTIVE_DRAGGABLE =  null;

        PlayToolPanel toolPanel = new PlayToolPanel(viewPanel);
        addPanel(toolPanel,0,0);
    }
}
