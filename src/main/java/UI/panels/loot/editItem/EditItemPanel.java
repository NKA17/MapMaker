package UI.panels.loot.editItem;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.pages.loot.editItem.EditItemPage;
import UI.pages.loot.editLootBag.EditLootBagPage;
import UI.pages.loot.lootNavigation.LootNavigationPage;
import UI.pages.mapSelect.MapSelectPage;
import UI.pages.newMapSetup.NewMapSetupPage;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;
import sun.font.TextLabelFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditItemPanel extends ApplicationPanel {

    private ItemRow item;
    private DropBag dropBag;
    public EditItemPanel(ItemRow item,DropBag dropBag){
        this.item = item;
        this.dropBag = dropBag;
    }
    @Override
    public void loadPanel() {

        DropRateGraphPanel gp = new DropRateGraphPanel(item.getDropRateMin(),item.getDropRateMax());
        gp.loadPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel graphPanel = new JPanel(new GridBagLayout());
        graphPanel.setBackground(null);
        JPanel fieldPanel = new JPanel(new GridBagLayout());
        fieldPanel.setBackground(null);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(null);

        JLabel nameLabel = TextFactory.createLabel("Name");
        JLabel descLabel = TextFactory.createLabel("Description");
        JLabel quantityLabel = TextFactory.createLabel("Quantity");
        JLabel dropMinLabel = TextFactory.createLabel("Drop Rate Min");
        JLabel dropMaxLabel = TextFactory.createLabel("Drop Rate Max");

        JTextField nameField = TextFactory.createTextField(item.getName());
        nameField.setPreferredSize(new Dimension(300,20));
        JTextField descField = TextFactory.createTextField(item.getDescription());
        descField.setPreferredSize(new Dimension(300,20));
        JTextField quantityField = TextFactory.createTextField(item.getQuantity());
        quantityField.setPreferredSize(new Dimension(300,20));
        JTextField dropMinField = TextFactory.createTextField(item.getDropRateMin()+"");
        dropMinField.setPreferredSize(new Dimension(300,20));
        JTextField dropMaxField = TextFactory.createTextField(item.getDropRateMax()+"");
        dropMaxField.setPreferredSize(new Dimension(300,20));
        dropMaxField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item.setName(nameField.getText());
                item.setDescription(descField.getText());
                item.setQuantity(quantityField.getText());
                item.setDropRateMin(Double.parseDouble(dropMinField.getText()));
                item.setDropRateMax(Double.parseDouble(dropMaxField.getText()));
                EditItemPage page = new EditItemPage(item,dropBag);
                getObserver().openPage(page);
            }
        });
        dropMinField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item.setName(nameField.getText());
                item.setDescription(descField.getText());
                item.setQuantity(quantityField.getText());
                item.setDropRateMin(Double.parseDouble(dropMinField.getText()));
                item.setDropRateMax(Double.parseDouble(dropMaxField.getText()));
                EditItemPage page = new EditItemPage(item,dropBag);
                getObserver().openPage(page);
            }
        });

        JButton add = ButtonFactory.createButton("Add");
        add.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                item.setName(nameField.getText());
                item.setDescription(descField.getText());
                item.setQuantity(quantityField.getText());
                item.setDropRateMin(Double.parseDouble(dropMinField.getText()));
                item.setDropRateMax(Double.parseDouble(dropMaxField.getText()));
                EditLootBagPage page = new EditLootBagPage(dropBag);
                getObserver().openPage(page);

            }
        });

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        fieldPanel.add(nameLabel,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        fieldPanel.add(nameField,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldPanel.add(descLabel,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        fieldPanel.add(descField,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldPanel.add(quantityLabel,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 2;
        fieldPanel.add(quantityField,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 3;
        fieldPanel.add(dropMinLabel,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        fieldPanel.add(dropMinField,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        fieldPanel.add(dropMaxLabel,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 4;
        fieldPanel.add(dropMaxField,gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        buttonPanel.add(add,gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        graphPanel.add(gp,gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(fieldPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(graphPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(buttonPanel,gbc);
    }
}
