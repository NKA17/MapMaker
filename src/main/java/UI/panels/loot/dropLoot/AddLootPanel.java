package UI.panels.loot.dropLoot;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.loot.dropLoot.SetupLootPage;
import UI.pages.loot.dropLoot.ShowLootPage;
import application.config.Configuration;
import application.io.LootIO;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddLootPanel extends ApplicationPanel {

    private List<DropBag> dropBagList = new ArrayList<>();

    public AddLootPanel(List<DropBag> dropBagList) {
        this.dropBagList = dropBagList;
    }

    public void loadPanel(){

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;


        JScrollPane scrollPane = new JScrollPane();
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

        File dir = new File(Configuration.SAVE_LOOT_FOLDER);
        for(File file: dir.listFiles()){
            gbc.gridx = 0;
            JButton button = createButton(file);
            button.setPreferredSize(new Dimension(300,30));
            viewPanel.add(button,gbc);

            gbc.gridx = 1;
            JButton deleteButton = createAddButton(file);
            deleteButton.setPreferredSize(new Dimension(50,30));
            viewPanel.add(deleteButton,gbc);

            gbc.gridy++;
        }

        viewPanel.invalidate();
        viewPanel.revalidate();
        scrollPane.invalidate();
        scrollPane.revalidate();
    }


    private JButton createAddButton(File file){
        JButton button = ButtonFactory.createButton("Add");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DropBag bag = LootIO.load(file.getName());
                dropBagList.add(bag);
                SetupLootPage page = new SetupLootPage(dropBagList);
                getObserver().openPage(page);
            }
        });

        return button;
    }

    private JButton createButton(File file){
        String buttonText = file.getName().replace(Configuration.FILE_EXTENSION,"");
        JButton button = ButtonFactory.createButton(buttonText);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DropBag bag = LootIO.load(file.getName());
                dropBagList.add(bag);
                SetupLootPage page = new SetupLootPage(dropBagList);
                getObserver().openPage(page);
            }
        });
        return button;
    }
}
