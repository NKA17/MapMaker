package UI.panels.loadPanel;

import UI.app.view.ApplicationPage;
import UI.app.view.ApplicationPanel;
import UI.pages.LoadPage.LoadPage;
import application.config.Configuration;
import application.io.LoadModel;

import javax.swing.*;
import java.awt.*;

public class LoadPanel extends ApplicationPanel {
    private LoadModel model;
    private JLabel prompt = new JLabel();
    private JLabel progress = new JLabel();
    private LoadBarPanel loadBarPanel;
    private LoadPage observer;
    public LoadPanel(LoadModel model){
        this.model = model;
        loadBarPanel = new LoadBarPanel(model);
    }

    public void setObserver(LoadPage observer){
        this.observer = observer;
    }

    @Override
    public void loadPanel() {
        prompt.setText(model.getPrompt());
        progress.setText(model.getProgressString(2));
        loadBarPanel.loadPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        //add(prompt,gbc);

        gbc.gridy = 1;
        //add(progress,gbc);

        gbc.gridy = 2;
        //add(loadBarPanel,gbc);

        model.setObserver(this.observer);

        setPreferredSize(new Dimension(110,16));

    }

    public void doThings(){
        repaint();
        //prompt.setText(model.getPrompt());
        //progress.setText(model.getProgressString(2));
        //invalidate();
        //revalidate();

    }

    @Override
    public void paintComponent(Graphics g){

        g.setColor(new Color(0,0,0));
        //g.fillRect(0,0,getWidth()-1,getHeight()-1);
        g.setColor(Configuration.EDIT_GRID_COLOR);
        g.drawRect(0,0,104,14);
        g.fillRect(2,2,(int)(100 * model.getProgress()),10);
    }

}
