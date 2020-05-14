package UI.panels.loot.dropLoot;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.loot.dropLoot.SetupLootPage;
import application.config.Configuration;
import application.loot.structure.DropBag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DropLootPanel extends ApplicationPanel {
    private List<DropBag> dropBagList = new ArrayList<>();
    private JScrollPane scrollPane = new JScrollPane();

    public DropLootPanel(List<DropBag> dropBagList) {
        this.dropBagList = dropBagList;
    }

    public void loadPanel(){

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;


        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        //scrollPane.setPreferredSize(new Dimension(400,200));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBackground(Configuration.PANEL_BG_COLOR);
        if(Configuration.WIDTH_CONSTRAINT!=-1){
            scrollPane.setPreferredSize(new Dimension(
                    (Configuration.WIDTH_CONSTRAINT-60)/2,
                    Configuration.HEIGHT_CONSTRAINT-70));
        }else {
            scrollPane.setPreferredSize(new Dimension(400, 300));
        }
        JPanel viewPanel = new JPanel();
        viewPanel.setBackground(null);
        viewPanel.setLayout(new GridBagLayout());
        scrollPane.setViewportView(viewPanel);
        scrollPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        add(scrollPane);

        Collections.sort(dropBagList, new Comparator<DropBag>() {
            @Override
            public int compare(DropBag o1, DropBag o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        for(DropBag bag: dropBagList){
            gbc.gridx = 0;
            JButton button = createButton(bag);
            //button.setPreferredSize(new Dimension(300,30));
            viewPanel.add(button,gbc);

            gbc.gridx = 1;
            JButton deleteButton = createRemoveButton(bag);
            deleteButton.setPreferredSize(new Dimension(50,30));
            //viewPanel.add(deleteButton,gbc);

            gbc.gridy++;
        }

        viewPanel.invalidate();
        viewPanel.revalidate();
        scrollPane.invalidate();
        scrollPane.revalidate();
    }

    private JButton createRemoveButton(DropBag bag){
        JButton button = ButtonFactory.createButton("Delete");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dropBagList.remove(bag);
                SetupLootPage page = new SetupLootPage(dropBagList);
                getObserver().openPage(page);
                page.getDropLootPanel().setScrollPaneValue(scrollPane.getVerticalScrollBar().getValue());
                getObserver().revalidate();
            }
        });

        return button;
    }

    private JButton createButton(DropBag bag){
        String buttonText = bag.getName();
        JButton button = ButtonFactory.createButton(buttonText);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dropBagList.remove(bag);
                SetupLootPage page = new SetupLootPage(dropBagList);
                getObserver().openPage(page);
                page.getDropLootPanel().setScrollPaneValue(scrollPane.getVerticalScrollBar().getValue());
                getObserver().revalidate();
            }
        });

        if(Configuration.WIDTH_CONSTRAINT!=-1){
            button.setPreferredSize(new Dimension(
                    (Configuration.WIDTH_CONSTRAINT-110)/2,
                    25));
        }else{
            button.setPreferredSize(new Dimension(350,30));
        }

        return button;
    }

    public void setScrollPaneValue(int value){
        scrollPane.getVerticalScrollBar().setValue(value);
    }
}
