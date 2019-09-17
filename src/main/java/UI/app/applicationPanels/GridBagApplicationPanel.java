package UI.app.applicationPanels;

import UI.app.ApplicationPanel;

import java.awt.*;

public abstract class GridBagApplicationPanel extends ApplicationPanel {

    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;

    public GridBagApplicationPanel(){
        this.gridBagLayout = new GridBagLayout();
        this.gbc = new GridBagConstraints();
    }

    public void setInsets(int left, int right, int bottom, int top){
        gbc.insets = new Insets(top,left,bottom,right);
    }

    public void add(int x, int y){

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
    }
}
