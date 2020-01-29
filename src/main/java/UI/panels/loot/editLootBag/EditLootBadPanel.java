package UI.panels.loot.editLootBag;

import UI.app.view.ApplicationAction;
import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.pages.editmap.EditMapPage;
import UI.pages.loot.LootManage.LootManagePage;
import UI.pages.loot.editItem.EditItemPage;
import UI.pages.loot.editLootBag.EditLootBagPage;
import UI.windows.BasicWindow;
import application.config.AppState;
import application.config.Configuration;
import application.io.LootIODB;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EditLootBadPanel extends ApplicationPanel {

    private DropBag bag;
    private DropRateBagGraphPanel graphPanel;
    private JScrollPane scrollPane = new JScrollPane();

    public EditLootBadPanel(DropBag bag,DropRateBagGraphPanel graphPanel){
        this.bag = bag;
        this.graphPanel = graphPanel;
    }

    @Override
    public void loadPanel() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;


        gbc.gridx = 0;
        gbc.gridy = 1;

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        //scrollPane.setPreferredSize(new Dimension(400,200));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBackground(null);
        scrollPane.setPreferredSize(new Dimension(400,300));
        JPanel viewPanel = new JPanel();
        viewPanel.setBackground(null);
        viewPanel.setLayout(new GridBagLayout());
        scrollPane.setViewportView(viewPanel);
        scrollPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        add(scrollPane);


        for(ItemRow item: bag.getItems()){
            gbc.gridx = 0;
            JButton button = createButton(item);
            button.setPreferredSize(new Dimension(270,30));
            viewPanel.add(button,gbc);

            gbc.gridx = 1;
            JButton dupeButton = createDuplicateButton(item,bag);
            dupeButton.setPreferredSize(new Dimension(30,30));
            viewPanel.add(dupeButton,gbc);

            gbc.gridx = 2;
            JButton deleteButton = createDeleteButton(item,bag);
            deleteButton.setPreferredSize(new Dimension(50,30));
            viewPanel.add(deleteButton,gbc);

            gbc.gridy++;
        }

        viewPanel.invalidate();
        viewPanel.revalidate();
        scrollPane.invalidate();
        scrollPane.revalidate();
    }

    private JButton createButton(ItemRow item){
        String buttonText = String.format("%s x%s rate: %.2f-%.2f",
                item.getName(),
                item.getQuantity(),
                item.getDropRateMin(),
                item.getDropRateMax());

        ApplicationAction rollover = new ApplicationAction() {
            @Override
            public void action() {
                graphPanel.getDropBag().setHighlighted(item);
                graphPanel.repaint();
            }
        };
        ApplicationAction rollout = new ApplicationAction() {
            @Override
            public void action() {
                graphPanel.getDropBag().setHighlighted(null);
                graphPanel.repaint();
            }
        };

        JButton button = ButtonFactory.createButton(buttonText,rollover,rollout);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                bag.setName(((EditLootBagPage) AppState.ACTIVE_PAGE).getBagName());
                EditItemPage page = new EditItemPage(item,bag);
                getObserver().openPage(page);
            }
        });

        return button;
    }

    private JButton createDeleteButton(ItemRow item, DropBag bag){
        JButton button = ButtonFactory.createButton("Delete");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    bag.setName(((EditLootBagPage) AppState.ACTIVE_PAGE).getBagName());
                    bag.getItems().remove(item);
                    LootIODB.deleteItem(item);
                    EditLootBagPage page = new EditLootBagPage(bag);
                    getObserver().openPage(page);
                    page.getLootBagPanel().setScrollPaneValue(scrollPane.getVerticalScrollBar().getValue());
                    getObserver().revalidate();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        return button;
    }

    private JButton createDuplicateButton(ItemRow item, DropBag bag){
        JButton button = ButtonFactory.createButton("+");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                bag.setName(((EditLootBagPage) AppState.ACTIVE_PAGE).getBagName());
                ItemRow dupe = new ItemRow();
                dupe.setName(item.getName());
                dupe.setDescription(item.getDescription());
                dupe.setQuantity(item.getQuantity());
                dupe.setDropRateMin(item.getDropRateMin());
                dupe.setDropRateMax(item.getDropRateMax());
                bag.getItems().add(dupe);
                EditLootBagPage page = new EditLootBagPage(bag);
                getObserver().openPage(page);
                page.getLootBagPanel().setScrollPaneValue(scrollPane.getVerticalScrollBar().getValue());
                getObserver().revalidate();
            }
        });

        return button;
    }

    private JButton createEditButton(ItemRow item, DropBag bag){
        JButton button = ButtonFactory.createButton("Edit");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EditItemPage page = new EditItemPage(item,bag);
                BasicWindow itemWindow = new BasicWindow();
                itemWindow.setTitle("Edit Item");
                itemWindow.openPage(page ,false);
                page.setOnDisposeAction(new ApplicationAction() {
                    @Override
                    public void action() {
                        EditLootBagPage editLootBagPage = new EditLootBagPage(bag);
                        getObserver().openPage(editLootBagPage);
                    }
                });
                itemWindow.makeVisible();
            }
        });

        return button;
    }

    private void setScrollPaneValue(int value){
        scrollPane.getVerticalScrollBar().setValue(value);
    }
}
