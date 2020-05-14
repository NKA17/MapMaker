package UI.pages.loot.dropLoot;

import UI.app.view.ApplicationPage;
import UI.factory.ButtonFactory;
import UI.panels.loot.dropLoot.ShowLootPanel;
import UI.panels.loot.editItem.EditItemPanel;
import application.config.Configuration;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;
import hyperbot.ExternalCommand;
import hyperbot.ExternalCommandClient;
import hyperbot.HyperBotLootPrompt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowLootPage extends ApplicationPage {

    private List<DropBag> dropBagList;
    private ShowLootPanel lootPanel;
    private int roll;

    public ShowLootPage(List<DropBag> dropBagList, int roll) {
        this.dropBagList = dropBagList;

        this.roll = roll;
    }

    @Override
    public void loadPage() {
        lootPanel = new ShowLootPanel(dropBagList,roll);
        addPanel(lootPanel,0,0,1);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(null);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(panel,gbc);

        JButton send = ButtonFactory.createButton("HyperBot");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendItems();
            }
        });
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = Configuration.GRIDBAG_INSETS;
        panel.add(send,gbc);


        JButton sendTest = ButtonFactory.createButton("Test");
        sendTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendItemsTest();
            }
        });
        gbc.gridy = 0;
        gbc.gridx = 1;
        panel.add(sendTest,gbc);

        lootPanel.setObserver(getObserver());
    }

    private void sendItems(){
        ExternalCommandClient client = new ExternalCommandClient();

        ExternalCommand lootPrompt = new HyperBotLootPrompt();
        client.send(lootPrompt);

        for(DropBag bag: lootPanel.getBags()){
            String title = bag.getName()+";dropped...";
            String items = "";
            for(ItemRow item: bag.getItems()){
                int q = Integer.parseInt(item.getQuantity().trim());
                items += String.format("%s %s\n",
                        item.getName(),
                        (q>1?"x"+q:""));
            }
            ExternalCommand command = new ExternalCommand("embed",title,items);
            client.send(command);
        }
    }

    private void sendItemsTest(){
        ExternalCommandClient client = new ExternalCommandClient();

        ExternalCommand lootPrompt = new HyperBotLootPrompt();
        lootPrompt.channelId = "604400677451726888";
        client.send(lootPrompt);

        for(DropBag bag: lootPanel.getBags()){
            String title = bag.getName()+";dropped...";
            String items = "";
            for(ItemRow item: bag.getItems()){
                int q = Integer.parseInt(item.getQuantity().trim());
                items += String.format("%s %s\n",
                        item.getName(),
                        (q>1?"x"+q:""));
            }
            ExternalCommand command = new ExternalCommand("embed",title,items);
            command.channelId = "604400677451726888";
            client.send(command);
        }
    }
}
