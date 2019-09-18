package UI.app.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ApplicationPage extends JPanel {
    private ApplicationWindow observer;
    private List<ApplicationPanel> panels = new ArrayList<>();
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;

    public ApplicationPage(){
        gridBagLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(gridBagLayout);
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

    public abstract void loadPage();
}
