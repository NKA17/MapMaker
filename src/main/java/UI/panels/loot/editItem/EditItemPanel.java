package UI.panels.loot.editItem;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonBag;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.pages.loot.editItem.EditItemPage;
import UI.pages.loot.editLootBag.EditLootBagPage;
import UI.pages.loot.lootNavigation.LootNavigationPage;
import UI.pages.mapSelect.MapSelectPage;
import UI.pages.newMapSetup.NewMapSetupPage;
import UI.panels.loot.editLootBag.DropRateBagGraphPanel;
import UI.panels.loot.editLootBag.GraphContainerPanel;
import application.config.Configuration;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;
import model.map.tiles.MapTile;
import sun.font.TextLabelFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditItemPanel extends ApplicationPanel {

    private ItemRow editItem;
    private ItemRow item;
    private DropBag dropBag;
    private boolean isNew = true;

    public EditItemPanel(ItemRow item,DropBag dropBag){
        isNew = !dropBag.getItems().remove(item);
        this.item = item;
        this.editItem = item.copy();
        this.dropBag = dropBag;
    }
    @Override
    public void loadPanel() {

        //DropRateGraphPanel gp = new DropRateGraphPanel(item.getDropRateMin(),item.getDropRateMax());
        //gp.loadPanel();
        dropBag.setHighlighted(editItem);
        GraphContainerPanel gp = new GraphContainerPanel(dropBag);
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
        nameLabel.setPreferredSize(new Dimension(130,20));
        JLabel descLabel = TextFactory.createLabel("Description");
        descLabel.setPreferredSize(new Dimension(130,20));
        JLabel quantityLabel = TextFactory.createLabel("Quantity");
        quantityLabel.setPreferredSize(new Dimension(130,20));
        JLabel dropMinLabel = TextFactory.createLabel(String.format("Min Coefficient: (%.2f)",editItem.getDropRateMin()));
        dropMinLabel.setPreferredSize(new Dimension(130,20));
        JLabel dropMaxLabel = TextFactory.createLabel(String.format("Max Coefficient: (%.2f)",editItem.getDropRateMax()));
        dropMaxLabel.setPreferredSize(new Dimension(130,20));

        JTextField nameField = TextFactory.createTextField(editItem.getName());
        nameField.setPreferredSize(new Dimension(300,20));
        JTextField descField = TextFactory.createTextField(editItem.getDescription());
        descField.setPreferredSize(new Dimension(300,20));
        JTextField quantityField = TextFactory.createTextField(editItem.getQuantity());
        quantityField.setPreferredSize(new Dimension(300,20));

        JSlider minSlider = ButtonFactory.createSlider(-200,200);
        JSlider maxSlider = ButtonFactory.createSlider(-200,200);
        minSlider.setValue((int) Math.round(editItem.getDropRateMin()*100));
        maxSlider.setValue((int) Math.round(editItem.getDropRateMax()*100));
        minSlider.setPreferredSize(new Dimension(250,20));
        maxSlider.setPreferredSize(new Dimension(250,20));
        minSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(minSlider.getValue() > maxSlider.getValue())
                    minSlider.setValue(maxSlider.getValue());
                editItem.setName(nameField.getText());
                editItem.setDescription(descField.getText());
                editItem.setQuantity(quantityField.getText());
                editItem.setDropRateMin((minSlider.getValue()+0.0)/100.0);
                editItem.setDropRateMax((maxSlider.getValue()+0.0)/100.0);
                dropMinLabel.setText(String.format("Min Coefficient: (%.2f)",editItem.getDropRateMin()));
                dropMaxLabel.setText(String.format("Max Coefficient: (%.2f)",editItem.getDropRateMax()));
                gp.repaint();
            }
        });

        maxSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(maxSlider.getValue() < minSlider.getValue())
                    maxSlider.setValue(minSlider.getValue());
                editItem.setName(nameField.getText());
                editItem.setDescription(descField.getText());
                editItem.setQuantity(quantityField.getText());
                editItem.setDropRateMin((minSlider.getValue()+0.0)/100.0);
                editItem.setDropRateMax((maxSlider.getValue()+0.0)/100.0);
                dropMinLabel.setText(String.format("Min Coefficient: (%.2f)",editItem.getDropRateMin()));
                dropMaxLabel.setText(String.format("Max Coefficient: (%.2f)",editItem.getDropRateMax()));
                gp.repaint();
            }
        });

        minSlider.setBackground(null);
        maxSlider.setBackground(null);

        JButton add = ButtonFactory.createButton("Ok");
        add.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                editItem.setName(nameField.getText());
                editItem.setDescription(descField.getText());
                editItem.setQuantity(quantityField.getText());
                editItem.setDropRateMin((minSlider.getValue()+0.0)/100.0);
                editItem.setDropRateMax((maxSlider.getValue()+0.0)/100.0);
                if(!dropBag.getItems().contains(item))
                    dropBag.getItems().add(item);
                dropBag.setHighlighted(null);
                saveItem();
                EditLootBagPage page = new EditLootBagPage(dropBag);
                getObserver().openPage(page);

            }
        });
        JButton cancel = ButtonFactory.createButton("Cancel");
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                if(!isNew)
                dropBag.setHighlighted(null);
                EditLootBagPage page = new EditLootBagPage(dropBag);
                getObserver().openPage(page);

            }
        });

        ButtonBag buttonBag = new ButtonBag();
        JButton common = buttonBag.createButton("Common");
        JButton uncommon = buttonBag.createButton("Uncommon");
        JButton rare = buttonBag.createButton("Rare");
        JButton veryRare = buttonBag.createButton("Very Rare");
        JButton legendary = buttonBag.createButton("Legendary");

        common.addActionListener(new RarityAction(.61,.85,minSlider,maxSlider,editItem));
        uncommon.addActionListener(new RarityAction(.38,.64,minSlider,maxSlider,editItem));
        rare.addActionListener(new RarityAction(.13,.42,minSlider,maxSlider,editItem));
        veryRare.addActionListener(new RarityAction(.03,.14,minSlider,maxSlider,editItem));
        legendary.addActionListener(new RarityAction(0,.04,minSlider,maxSlider,editItem));

        JPanel commonPanel = new JPanel(new GridBagLayout());
        commonPanel.setBackground(null);
        gbc.gridx = 0;
        gbc.insets = new Insets(4,4,4,10);
        commonPanel.add(common,gbc);
        gbc.gridx = 1;
        commonPanel.add(uncommon,gbc);
        gbc.gridx = 2;
        commonPanel.add(rare,gbc);
        gbc.gridx = 3;
        commonPanel.add(veryRare,gbc);
        gbc.gridx = 4;
        commonPanel.add(legendary,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
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
        fieldPanel.add(minSlider,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        fieldPanel.add(dropMaxLabel,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 4;
        fieldPanel.add(maxSlider,gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        buttonPanel.add(add,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        buttonPanel.add(cancel,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        graphPanel.add(gp,gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(fieldPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(commonPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(graphPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(buttonPanel,gbc);
    }

    private void saveItem(){
        item.setName(editItem.getName());
        item.setDescription(editItem.getDescription());
        item.setQuantity(editItem.getQuantity());
        item.setDropRateMin(editItem.getDropRateMin());
        item.setDropRateMax(editItem.getDropRateMax());
    }

    class RarityAction implements ActionListener{

        private double min;
        private double max;
        private JSlider minSlider;
        private JSlider maxSlider;
        private ItemRow item;


        public RarityAction(double min, double max, JSlider minSlider, JSlider maxSlider, ItemRow item) {
            this.min = min;
            this.max = max;
            this.minSlider = minSlider;
            this.maxSlider = maxSlider;
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            minSlider.setValue((int)Math.round(min*100.0));
            maxSlider.setValue((int)Math.round(max*100.0));
            item.setDropRateMin(min);
            item.setDropRateMax(max);
        }
    }
}
