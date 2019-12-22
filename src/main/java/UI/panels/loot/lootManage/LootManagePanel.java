package UI.panels.loot.lootManage;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.editmap.EditMapPage;
import UI.pages.loot.LootManage.LootManagePage;
import UI.pages.loot.editLootBag.EditLootBagPage;
import UI.pages.mapSelect.MapSelectPage;
import application.config.AppState;
import application.config.Configuration;
import application.io.LootIO;
import application.loot.structure.DropBag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LootManagePanel extends ApplicationPanel {
    @Override
    public void loadPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;

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
        for(File lootFile: dir.listFiles()){
            if(lootFile.getName().endsWith(Configuration.FILE_EXTENSION)){
                gbc.gridx = 0;
                JButton button = createButton(lootFile);
                button.setPreferredSize(new Dimension(300,30));
                viewPanel.add(button,gbc);

                gbc.gridx = 1;
                JButton editButton = createDeleteButton(lootFile);
                editButton.setPreferredSize(new Dimension(50,30));
                viewPanel.add(editButton,gbc);

                gbc.gridy++;
            }
        }

        viewPanel.invalidate();
        viewPanel.revalidate();
        scrollPane.invalidate();
        scrollPane.revalidate();
    }

    private JButton createButton(File file){
        String buttonText = file.getName().replace(Configuration.FILE_EXTENSION,"");
        JButton button = ButtonFactory.createButton(buttonText);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DropBag bag = LootIO.load(file.getName());
                EditLootBagPage page = new EditLootBagPage(bag);
                getObserver().openPage(page);
            }
        });

        return button;
    }

    private JButton createDeleteButton(File file){
        JButton button = ButtonFactory.createButton("Delete");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                file.delete();
                getObserver().openPage(new LootManagePage());
            }
        });

        return button;
    }
}
