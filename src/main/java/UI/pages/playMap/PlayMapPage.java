package UI.pages.playMap;

import UI.app.assets.MapAsset;
import UI.app.view.ApplicationPage;
import UI.panels.mapView.MapViewPanel;
import UI.panels.playMap.PlayToolPanel;
import application.config.AppState;
import application.mapEditing.tools.DragTool;
import model.map.structure.MapLayer;
import model.map.structure.MapSet;
import model.map.structure.RPGMap;
import model.map.tiles.MapTile;

public class PlayMapPage extends ApplicationPage {
    private RPGMap map;

    public PlayMapPage(RPGMap map) {
        this.map = map;
        for(MapSet set: map.getLayerSets()){
            set.buildFogLayer();
        }

        for(MapTile tile: map.getMechanicsLayer().getTiles()){
            if(tile.getAssetResource().getName().contains("spawn")){
                map.getActiveLayer().toggleFog(tile.getGridx(),tile.getGridy());
            }
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

    public RPGMap getMap() {
        return map;
    }
}
