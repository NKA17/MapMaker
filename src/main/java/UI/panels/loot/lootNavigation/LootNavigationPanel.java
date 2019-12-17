package UI.panels.loot.lootNavigation;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.loot.LootManage.LootManagePage;
import UI.pages.loot.dropLoot.SetupLootPage;
import UI.pages.mapSelect.MapSelectPage;
import UI.pages.newMapSetup.NewMapSetupPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LootNavigationPanel extends ApplicationPanel {
    @Override
    public void loadPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton create = ButtonFactory.createButton("Manage");
        create.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {

                LootManagePage lootManagePage = new LootManagePage();
                getObserver().openPage(lootManagePage);

            }
        });

        JButton drop = ButtonFactory.createButton("Drop Loot");
        drop.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                SetupLootPage page = new SetupLootPage(new ArrayList<>());
                getObserver().openPage(page);
            }
        });

        JButton back = ButtonFactory.createButton("Back");
        back.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                MapSelectPage page = new MapSelectPage();
                getObserver().openPage(page);
            }
        });


        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(create,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(drop,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(back,gbc);
    }
}
