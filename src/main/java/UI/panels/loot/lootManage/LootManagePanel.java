package UI.panels.loot.lootManage;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.pages.loot.LootManage.LootManagePage;
import UI.pages.loot.editLootBag.EditLootBagPage;
import application.config.Configuration;
import application.io.LootIO;
import application.io.LootIODB;
import application.loot.structure.DropBag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        if(Configuration.WIDTH_CONSTRAINT==-1&&Configuration.HEIGHT_CONSTRAINT==-1) {
            scrollPane.setPreferredSize(new Dimension(400, 300));
        }else {
            scrollPane.setPreferredSize(new Dimension(
                    Configuration.WIDTH_CONSTRAINT-20,
                    Configuration.HEIGHT_CONSTRAINT - 70
            ));
        }
        JPanel viewPanel = new JPanel();
        viewPanel.setBackground(null);
        viewPanel.setLayout(new GridBagLayout());
        scrollPane.setViewportView(viewPanel);
        scrollPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        add(scrollPane);

        try {
            List<DropBag> dropBagList = LootIO.getEmptyDropBags();
            Collections.sort(dropBagList, new Comparator<DropBag>() {
                @Override
                public int compare(DropBag o1, DropBag o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
            for(DropBag bag: dropBagList) {
                gbc.gridx = 0;
                JButton button = createButton(bag);
                viewPanel.add(button, gbc);

                gbc.gridx = 1;
                JButton editButton = createDeleteButton(bag);
                viewPanel.add(editButton, gbc);

                gbc.gridy++;
            }
        }catch (Exception e){
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel error = TextFactory.createLabel("ERROR: Issues Connecting to DB");
            viewPanel.add(error,gbc);
            viewPanel.setBackground(new Color(255,150,150));
            e.printStackTrace();
        }

        viewPanel.invalidate();
        viewPanel.revalidate();
        scrollPane.invalidate();
        scrollPane.revalidate();
    }




    private JButton createButton(DropBag bag){
        String buttonText = bag.getName();
        JButton button = ButtonFactory.createButton(buttonText);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    LootIO.load(bag);
                    EditLootBagPage page = new EditLootBagPage(bag);
                    getObserver().openPage(page);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        if(Configuration.WIDTH_CONSTRAINT!=-1){
            button.setPreferredSize(new Dimension(
                    Configuration.WIDTH_CONSTRAINT - 100,
                    25
            ));
        }else {
            button.setPreferredSize(new Dimension(300,30));
        }

        return button;
    }

    private JButton createDeleteButton(DropBag bag){
        JButton button = ButtonFactory.createButton("Delete");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try{
                    LootIO.deleteBag(bag.getName());
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                getObserver().openPage(new LootManagePage());
            }
        });



        if(Configuration.WIDTH_CONSTRAINT!=-1){
            button.setPreferredSize(new Dimension(
                    40,
                    25
            ));
        }else {
            button.setPreferredSize(new Dimension(50,30));
        }

        return button;
    }
}
