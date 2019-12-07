package UI.pages.editmap;

import UI.app.assets.MapAsset;
import UI.app.view.ApplicationPage;
import UI.pages.LoadPage.LoadPage;
import UI.panels.editmap.MapToolPanel;
import UI.panels.mapView.MapViewPanel;
import application.io.LoadModel;
import application.io.MapIO;
import model.map.structure.RPGMap;
import model.map.tiles.AssetTile;

import java.awt.*;
import java.io.File;

public class EditMapPage extends ApplicationPage{

    private String mapFileName;
    private RPGMap privateMap = null;

    public EditMapPage(String mapFileName) {
        this.mapFileName = mapFileName;
    }

    public EditMapPage(RPGMap map){
        privateMap = map;
        System.out.println("Edit page "+map);
    }

    @Override
    public void loadPage() {
        MapViewPanel mapView = new MapViewPanel();
        LoadModel loadModel = new LoadModel();
        RPGMap map = new RPGMap();

        if(privateMap == null) {

            Runnable run = new Runnable() {
                @Override
                public void run() {
                    LoadPage page = new LoadPage(loadModel) {
                        @Override
                        public void onLoad() {
                            mapView.remove(this);
                            mapView.repaint();
                        }
                    };
                    page.setBackground(new Color(0, 0, 0));
                    page.loadPage();
                    mapView.add(page);
                    mapView.invalidate();
                    mapView.revalidate();
                }
            };
            Thread th = new Thread(run);
            th.start();

            Runnable run2 = new Runnable() {
                @Override
                public void run() {

                    MapIO.loadMap(mapFileName, loadModel, map);
                    //loadModel.removeSelf();
                    mapView.removeAll();
                    mapView.repaint();
                }
            };
            Thread th2 = new Thread(run2);
            th2.start();
        }else {
            map.setGridDimensions(privateMap.getGridWidth(),privateMap.getGridHeight());
            map.setName(privateMap.getName());
            map.init();
        }

        mapView.setMap(map);
        MapToolPanel mapToolPanel = new MapToolPanel(mapView);
        mapToolPanel.setMap(mapView.getMap());
        addPanel(mapToolPanel,0,0);

        addPanel(mapView,1,0);
        mapView.setObserver(getObserver());
        mapView.createEditor();
    }

}
