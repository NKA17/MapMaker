package UI.pages.playMap.mob;

import UI.app.view.ApplicationPage;
import UI.panels.color.ColorSliderPanel;
import UI.panels.mapView.MapViewPanel;
import UI.panels.playMap.mob.AddMobPanel;
import UI.panels.saveMap.SaveMapPanel;
import model.map.structure.RPGMap;

public class GridColorPickerPage extends ApplicationPage {

    private RPGMap map;
    private MapViewPanel viewPanel;
    private boolean useAlpha = false;

    public GridColorPickerPage(RPGMap map, MapViewPanel viewPanel) {
        this(map, viewPanel, false);
    }

    public GridColorPickerPage(RPGMap map, MapViewPanel viewPanel, boolean useAlpha) {
        this.map = map;
        this.viewPanel = viewPanel;
        this.useAlpha = useAlpha;
    }

    @Override
    public void loadPage() {
        addPanel(new ColorSliderPanel("gridColor", useAlpha) {
            @Override
            public void onColorFinalize() {
                map.getActiveLayer().getGridLayer().setGridColor(getColor());
                viewPanel.repaint();
            }

            public void onColorUpdate(){
                map.getActiveLayer().getGridLayer().setGridColor(getColor());
                viewPanel.repaint();
            }
        });
    }
}
