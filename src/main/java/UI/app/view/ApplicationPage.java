package UI.app.view;

import application.config.Configuration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ApplicationPage extends JPanel {
    private ApplicationWindow observer;
    private List<ApplicationPanel> panels = new ArrayList<>();
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;
    private ApplicationAction onDisposeAction;

    public ApplicationPage(){
        gridBagLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(gridBagLayout);
        setBackground(Configuration.PANEL_BG_COLOR);
    }

    public ApplicationWindow getObserver() {
        return observer;
    }

    public void setObserver(ApplicationWindow observer) {
        this.observer = observer;
        for(ApplicationPanel p: panels){
            p.setObserver(getObserver());
        }
    }

    public void addPanel(ApplicationPanel panel){
        panel.loadPanel();
        add(panel);
        panel.setObserver(getObserver());
        panels.add(panel);
    }

    public void addPanel(ApplicationPanel panel,int x, int y){
        panel.loadPanel();
        gbc.gridx = x;
        gbc.gridy = y;
        add(panel,gbc);
        panel.setObserver(getObserver());
        panels.add(panel);
    }

    public void addPanel(ApplicationPanel panel,int x, int y, int fill){
        panel.loadPanel();
        gbc.gridx = x;
        gbc.gridy = y;
        int temp = gbc.fill;
        gbc.fill = fill;
        add(panel,gbc);
        gbc.fill = temp;
        panel.setObserver(getObserver());
        panels.add(panel);
    }

    public void dispose(){
        if(onDisposeAction != null){
            onDisposeAction.action();
        }
        for(ApplicationPanel panel: panels){
            panel.dispose();
        }
    }

    public abstract void loadPage();

    public ApplicationAction getOnDisposeAction() {
        return onDisposeAction;
    }

    public void setOnDisposeAction(ApplicationAction onDisposeAction) {
        this.onDisposeAction = onDisposeAction;
    }
}
