package UI.panels.loot.dropLoot;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.loot.dropLoot.ShowLootPage;
import UI.pages.loot.editLootBag.EditLootBagPage;
import application.config.AppState;
import application.config.Configuration;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ShowLootPanel extends ApplicationPanel {

    private List<DropBag> dropBagList;
    private int roll;

    public ShowLootPanel(List<DropBag> dropBagList,int roll) {
        this.dropBagList = dropBagList;
        this.roll = roll;
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
        if(Configuration.WIDTH_CONSTRAINT!=-1){
            scrollPane.setPreferredSize(new Dimension(
                    Configuration.WIDTH_CONSTRAINT-75,
                    Configuration.HEIGHT_CONSTRAINT-60
            ));
        }else {
            scrollPane.setPreferredSize(new Dimension(400, 550));
        }
        JPanel viewPanel = new JPanel();
        viewPanel.setBackground(null);
        viewPanel.setLayout(new GridBagLayout());
        scrollPane.setViewportView(viewPanel);
        scrollPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(scrollPane,gbc);



        HashMap<String,DropBag> groupedBagsMap = new HashMap<>();
        for(DropBag bag: dropBagList){
            if(groupedBagsMap.containsKey(bag.getName())){
                groupedBagsMap.get(bag.getName()).getItems().addAll(bag.getItems());
            }else {
                groupedBagsMap.put(bag.getName(),bag);
            }
        }
        dropBagList = new ArrayList<>();
        Iterator<String> iter = groupedBagsMap.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();

            dropBagList.add(groupedBagsMap.get(key).drop(roll).pack());
        }

        DropBag finalBag = new DropBag();
        if(dropBagList.size()>1)
            finalBag.setName("Loot");
        else
            finalBag.setName(dropBagList.get(0).getName());

        for(DropBag dropBag: dropBagList){
            dropBag = dropBag.drop(roll);
            finalBag.getItems().addAll(dropBag.getItems());
        }
        finalBag.pack();

        for(DropBag bag: dropBagList) {
            Collections.sort(bag.getItems(), new Comparator<ItemRow>() {
                @Override
                public int compare(ItemRow o1, ItemRow o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
            JLabel label = new JLabel(bag.getName());
            gbc.gridx = 0;
            viewPanel.add(label, gbc);
            gbc.gridy++;

            for (ItemRow item : bag.getItems()) {
                gbc.gridx = 0;
                JButton button = createButton(item);
                if(Configuration.WIDTH_CONSTRAINT!=-1){
                    button.setPreferredSize(new Dimension(Configuration.WIDTH_CONSTRAINT-200,30));
                }else {
                    button.setPreferredSize(new Dimension(300, 30));
                }
                viewPanel.add(button, gbc);

                gbc.gridx = 1;
                JButton deleteButton = createDeleteButton(item, finalBag);
                deleteButton.setPreferredSize(new Dimension(50, 30));
                viewPanel.add(deleteButton, gbc);

                gbc.gridy++;
            }
        }


        viewPanel.invalidate();
        viewPanel.revalidate();
        scrollPane.invalidate();
        scrollPane.revalidate();
    }


    private JButton createDeleteButton(ItemRow item, DropBag bag){
        JButton button = ButtonFactory.createButton("Delete");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                bag.getItems().remove(item);
                List<DropBag> bags = new ArrayList<>();
                bags.add(bag);
                getObserver().openPage(new ShowLootPage(bags,0));
            }
        });

        return button;
    }

    private JButton createButton(ItemRow item){
        String buttonText = item.toString();
        JButton button = ButtonFactory.createButton(buttonText);
        button.setRolloverEnabled(false);

        return button;
    }

    public List<DropBag> getBags(){
        return dropBagList;
    }
}
