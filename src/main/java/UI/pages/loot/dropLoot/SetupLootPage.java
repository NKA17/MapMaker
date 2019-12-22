package UI.pages.loot.dropLoot;

import UI.app.view.ApplicationPage;
import UI.factory.ButtonFactory;
import UI.pages.loot.lootNavigation.LootNavigationPage;
import UI.panels.loot.dropLoot.AddLootPanel;
import UI.panels.loot.dropLoot.DropLootPanel;
import UI.windows.BasicWindow;
import application.io.LootIO;
import application.loot.structure.DropBag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SetupLootPage extends ApplicationPage {

    private List<DropBag> dropList;

    public SetupLootPage(List<DropBag> dropList) {
        this.dropList = dropList;
    }

    @Override
    public void loadPage() {
        AddLootPanel lootPanel = new AddLootPanel(dropList);
        addPanel(lootPanel,0,0,1);
        DropLootPanel lootPanel2 = new DropLootPanel(dropList);
        addPanel(lootPanel2,1,0,1);
        lootPanel.setObserver(getObserver());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(null);
        JButton backButton = ButtonFactory.createButton("Back");
        JButton investButton = ButtonFactory.createButton("Investigate");
        JButton dropButton = ButtonFactory.createButton("Drop");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,10);
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(dropButton,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(investButton,gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(backButton,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(panel,gbc);


        backButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                LootNavigationPage page = new LootNavigationPage();
                getObserver().openPage(page);
            }
        });

        investButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                BasicWindow bw = new BasicWindow();
                bw.setTitle("Loot");
                EnterInvestigationRollPage page = new EnterInvestigationRollPage(dropList);
                bw.openPage(page);
                bw.makeVisible();
            }
        });

        dropButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                BasicWindow bw = new BasicWindow();
                bw.setTitle("Loot");
                ShowLootPage page = new ShowLootPage(dropList,0);
                bw.openPage(page);
                bw.makeVisible();
            }
        });
    }
}
