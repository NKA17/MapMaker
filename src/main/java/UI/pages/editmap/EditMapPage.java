package UI.pages.editmap;

import UI.app.view.ApplicationPage;
import UI.app.view.ApplicationWindow;
import UI.pages.LoadPage.LoadPage;
import UI.panels.editmap.MapToolPanel;
import UI.panels.editmap.MapViewPanel;
import UI.panels.loadPanel.LoadPanel;
import UI.windows.BasicWindow;
import application.io.LoadModel;
import application.io.MapIO;
import model.map.structure.RPGMap;

import java.awt.*;

public class EditMapPage extends ApplicationPage{

    private String mapFileName;

    public EditMapPage(String mapFileName) {
        this.mapFileName = mapFileName;
    }

    @Override
    public void loadPage() {
        MapViewPanel mapView = new MapViewPanel();
        LoadModel loadModel = new LoadModel();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                LoadPage page =new LoadPage(loadModel) {
                    @Override
                    public void onLoad() {
                        mapView.remove(this);
                        mapView.repaint();
                    }
                };
                page.setBackground(new Color(0,0,0));
                page.loadPage();
                mapView.add(page);
                mapView.invalidate();
                mapView.revalidate();
            }
        };
        Thread th = new Thread(run);
        th.start();

        RPGMap map = new RPGMap();
        Runnable run2 = new Runnable() {
            @Override
            public void run() {

                MapIO.loadMap(mapFileName,loadModel,map);
                loadModel.removeSelf();
                mapView.repaint();
            }
        };
        Thread th2 = new Thread(run2);
        th2.start();

        mapView.setMap(map);
        MapToolPanel mapToolPanel = new MapToolPanel(mapView);
        mapToolPanel.setMap(mapView.getMap());
        addPanel(mapToolPanel,0,0);

        addPanel(mapView,1,0);
        mapView.setObserver(getObserver());
        mapView.createEditor();
    }

}
