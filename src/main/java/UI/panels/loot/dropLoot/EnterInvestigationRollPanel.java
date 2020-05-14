package UI.panels.loot.dropLoot;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.pages.loot.LootManage.LootManagePage;
import UI.pages.loot.dropLoot.ShowLootPage;
import application.loot.structure.DropBag;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EnterInvestigationRollPanel extends ApplicationPanel {

    private List<DropBag> dropBagList;

    public EnterInvestigationRollPanel(List<DropBag> dropBagList) {
        this.dropBagList = dropBagList;
    }

    @Override
    public void loadPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel rollLabel = TextFactory.createLabel("Investigation Roll");

        JTextField rollField = TextFactory.createTextField("12");
        rollField.setPreferredSize(new Dimension(300,30));

        JButton next = ButtonFactory.createButton("Drop Loot");
        next.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {

                ShowLootPage sp = new ShowLootPage(dropBagList,Integer.parseInt(rollField.getText()));
                getObserver().openPage(sp);
            }
        });

        JSlider slider = ButtonFactory.createSlider(0,40);
        slider.setValue(12);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rollField.setText(slider.getValue()+"");
            }
        });

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;
        add(rollLabel,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(rollField,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.gridwidth = 2;
        add(slider,gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        add(next,gbc);
    }
}
