package UI.pages.LoadPage;

import UI.app.view.ApplicationPage;
import UI.panels.loadPanel.LoadPanel;
import application.io.LoadModel;

import java.awt.*;

public abstract class LoadPage extends ApplicationPage {
    private LoadModel model;

    LoadPanel loadPanel;

    public LoadPage(LoadModel model){
        this.model = model;
        loadPanel = new LoadPanel(model);
    }
    @Override
    public void loadPage() {
        loadPanel.setObserver(this);
        loadPanel.setBackground(getBackground());
        add(loadPanel);
        loadPanel.setObserver(getObserver());
        loadPanel.setPreferredSize(getPreferredSize());
        loadPanel.loadPanel();

        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                while (model.getReadBytes() < model.getTotalBytes() || model.getTotalBytes() == 0){
                    long start = System.currentTimeMillis();
                    while(System.currentTimeMillis() - start < 100);
                    updatePage();
                }
            }
        };
        Thread th = new Thread(refresh);
        th.start();

        repaint();
    }

    public abstract void onLoad();

    public void updatePage(){
        loadPanel.doThings();
        //getObserver().invalidate();
        //getObserver().revalidate();
    }
}
