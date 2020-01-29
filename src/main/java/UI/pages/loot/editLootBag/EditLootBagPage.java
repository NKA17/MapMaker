package UI.pages.loot.editLootBag;

import UI.app.view.ApplicationAction;
import UI.app.view.ApplicationPage;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.pages.loot.LootManage.LootManagePage;
import UI.pages.loot.dropLoot.EnterInvestigationRollPage;
import UI.pages.loot.editItem.EditItemPage;
import UI.pages.loot.lootNavigation.LootNavigationPage;
import UI.pages.newMapSetup.NewMapSetupPage;
import UI.pages.paletteSelect.PaletteSelectPage;
import UI.panels.loot.editLootBag.DropRateBagGraphPanel;
import UI.panels.loot.editLootBag.EditLootBadPanel;
import UI.panels.loot.editLootBag.GraphContainerPanel;
import UI.panels.loot.lootManage.LootManagePanel;
import UI.windows.BasicWindow;
import application.config.Configuration;
import application.io.LootIO;
import application.io.LootIODB;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;
import application.mapEditing.tools.TilePaintTool;
import application.mapEditing.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;

public class EditLootBagPage extends ApplicationPage {

    private DropBag bag;

    JTextField nameField;
    private EditLootBadPanel lootBagPanel;

    public EditLootBagPage(DropBag bag){
        this.bag = bag;
        Collections.sort(bag.getItems(), new Comparator<ItemRow>() {
            @Override
            public int compare(ItemRow o1, ItemRow o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
    }

    @Override
    public void loadPage() {
        JPanel namePanel = new JPanel(new GridBagLayout());
        namePanel.setBackground(null);
        JLabel nameLabel = TextFactory.createLabel("Name");
        nameField = TextFactory.createTextField(bag.getName());
        nameField.setPreferredSize(new Dimension(300,30));
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel main = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        add(namePanel,gbc);

        GraphContainerPanel graphPanel = new GraphContainerPanel(bag);
        lootBagPanel = new EditLootBadPanel(bag,graphPanel.getGraphPanel());
        addPanel(lootBagPanel,0,1,1);
        addPanel(graphPanel,1,1,1);

        gbc.gridx = 0;
        gbc.gridy = 0;
        namePanel.add(nameLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        namePanel.add(nameField,gbc);

        JButton test = ButtonFactory.createButton("Test");
        test.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                BasicWindow bw = new BasicWindow();
                EnterInvestigationRollPage sp = new EnterInvestigationRollPage(bag);
                bw.openPage(sp);
                bw.makeVisible();
            }
        });

        JButton cancel = ButtonFactory.createButton("Cancel");
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                LootManagePage sp = new LootManagePage();
                getObserver().openPage(sp);
            }
        });

        JButton add = ButtonFactory.createButton("Add Item");
        add.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ItemRow item = new ItemRow();
                //bag.getItems().add(item);
                EditItemPage page = new EditItemPage(item,bag);
                getObserver().openPage(page);
            }
        });

        JButton save = ButtonFactory.createButton("Save");
        save.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    bag.setName(nameField.getText());
                    LootIODB.saveDropBag(bag);
                    LootManagePage sp = new LootManagePage();
                    getObserver().openPage(sp);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        JPanel bPanel = new JPanel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        bPanel.add(add,gbc);

        gbc.gridx = 1;
        bPanel.add(save,gbc);

        gbc.gridx = 2;
        bPanel.add(test,gbc);

        gbc.gridx = 3;
        bPanel.add(cancel,gbc);
        bPanel.setBackground(null);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(bPanel,gbc);
    }

    public DropBag getBag() {
        return bag;
    }

    public void setBag(DropBag bag) {
        this.bag = bag;
    }

    public String getBagName(){
        return nameField.getText();
    }

    public EditLootBadPanel getLootBagPanel() {
        return lootBagPanel;
    }
}
