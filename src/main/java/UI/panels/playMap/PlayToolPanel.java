package UI.panels.playMap;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.editmap.EditMapPage;
import UI.panels.mapView.MapViewPanel;
import application.config.Configuration;
import application.mapEditing.tools.DragTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayToolPanel extends ApplicationPanel {

    private MapViewPanel viewPanel;

    public PlayToolPanel(MapViewPanel viewPanel) {
        this.viewPanel = viewPanel;
    }

    @Override
    public void loadPanel() {
        JButton dragTool = ButtonFactory.createButtonWithIcon("drag");
        dragTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                viewPanel.getMapEditor().setTool(new DragTool());
            }
        });

        JButton edit = ButtonFactory.createButtonWithIcon("edit");
        edit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getObserver().openPage(new EditMapPage(
                        Configuration.SAVE_FOLDER+viewPanel.getMap().getName()));
            }
        });

        JButton grid = ButtonFactory.createButtonWithIcon("grid");
        grid.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                viewPanel.getMap().getActiveLayer().toggleGrid();
                viewPanel.repaint();
            }
        });

        JButton eye = ButtonFactory.createButtonWithIcon("eye");
        eye.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {

            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,10);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = 1;
        add(edit,gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(dragTool,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(grid, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(eye, gbc);
    }
}
