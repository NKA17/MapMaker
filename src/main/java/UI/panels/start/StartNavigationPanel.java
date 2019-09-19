package UI.panels.start;

import UI.app.view.ApplicationPanel;
import UI.pages.editmap.EditMapPage;
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

        JButton load_map = new JButton("Load Map");
        load_map.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                EditMapPage editMapPage = new EditMapPage();
                getObserver().openPage(editMapPage);
            }
        });

        JButton close = new JButton("Close");
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
        add(load_map,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(4,4,4,10);
        add(close,gbc);
    }
}
