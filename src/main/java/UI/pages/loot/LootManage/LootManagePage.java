package UI.pages.loot.LootManage;

import UI.app.view.ApplicationPage;
import UI.factory.ButtonFactory;
import UI.pages.loot.editLootBag.EditLootBagPage;
import UI.pages.loot.lootNavigation.LootNavigationPage;
import UI.pages.start.StartPage;
import UI.panels.loot.lootManage.LootManagePanel;
import UI.panels.mapSelect.MapSelectPanel;
import application.loot.structure.DropBag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LootManagePage extends ApplicationPage {

    @Override
    public void loadPage() {
        LootManagePanel panel = new LootManagePanel();
        GridBagConstraints gbc = new GridBagConstraints();
        addPanel(panel,0,0,1);

        JButton cancel = ButtonFactory.createButton("Back");
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                LootNavigationPage sp = new LootNavigationPage();
                getObserver().openPage(sp);
            }
        });

        JButton newBag = ButtonFactory.createButton("New");
        newBag.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EditLootBagPage sp = new EditLootBagPage(new DropBag());
                getObserver().openPage(sp);
            }
        });

        JPanel bPanel = new JPanel();
        gbc.gridx = 0;
        bPanel.add(newBag,gbc);

        gbc.gridx = 1;
        bPanel.add(cancel,gbc);
        bPanel.setBackground(null);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(bPanel,gbc);
    }
}
