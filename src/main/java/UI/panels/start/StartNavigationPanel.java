package UI.panels.start;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.editmap.EditMapPage;
import UI.pages.mapSelect.MapSelectPage;
import UI.pages.newMapSetup.NewMapSetupPage;
import UI.panels.loadPanel.LoadPanel;
import application.io.LoadModel;
import application.io.MapIO;
import model.map.structure.RPGMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartNavigationPanel extends ApplicationPanel {
    @Override
    public void loadPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton new_map = ButtonFactory.createButton("New Map");
        new_map.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {

                NewMapSetupPage newMapPage = new NewMapSetupPage();
                getObserver().openPage(newMapPage);

            }
        });

        JButton load_map = ButtonFactory.createButton("Load Map");
        load_map.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                MapSelectPage page = new MapSelectPage();
                getObserver().openPage(page);
            }
        });

        JButton close = ButtonFactory.createButton("Close");
        close.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                getObserver().dispose();
            }
        });

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(new_map,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(load_map,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(4,4,4,10);
        add(close,gbc);
    }
}
