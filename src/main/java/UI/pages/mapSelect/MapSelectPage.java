package UI.pages.mapSelect;

import UI.app.view.ApplicationPage;
import UI.factory.ButtonFactory;
import UI.pages.start.StartPage;
import UI.panels.mapSelect.MapSelectPanel;
import application.config.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapSelectPage extends ApplicationPage {


    @Override
    public void loadPage() {
        MapSelectPanel panel = new MapSelectPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        addPanel(panel,0,0,1);

        JButton cancel = ButtonFactory.createButton("Back");
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                StartPage sp = new StartPage();
                getObserver().openPage(sp);
            }
        });

        JPanel bPanel = new JPanel();
        bPanel.add(cancel);
        bPanel.setBackground(null);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(bPanel,gbc);
    }
}
