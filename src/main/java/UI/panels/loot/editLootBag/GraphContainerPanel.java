package UI.panels.loot.editLootBag;

import UI.app.view.ApplicationPage;
import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.windows.BasicWindow;
import application.config.Configuration;
import application.loot.structure.DropBag;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphContainerPanel extends ApplicationPanel {

    private DropBag dropBag;
    private DropRateBagGraphPanel graphPanel = null;

    public GraphContainerPanel(DropBag dropBag) {
        this.dropBag = dropBag;
        graphPanel = new DropGraphPanel(dropBag);
        graphPanel.loadPanel();
    }

    @Override
    public void loadPanel() {
        setBackground(null);

        GridBagConstraints gbc = new GridBagConstraints();

        setLayout(new GridBagLayout());

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = Configuration.GRIDBAG_INSETS;
        add(TextFactory.createTitle("Drop Rate"),gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(TextFactory.createLabel("Drop Chance"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(TextFactory.createLabel("Investigation Roll"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(graphPanel,gbc);

        JButton hintButton = ButtonFactory.createButton("?");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        hintButton.setPreferredSize(new Dimension(30,30));
        add(hintButton,gbc);
        hintButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                BasicWindow bw = new BasicWindow();
                bw.setTitle("Hint");
                ApplicationPage page = new ApplicationPage(){

                    @Override
                    public void loadPage() {
                        GridBagConstraints gbc = new GridBagConstraints();
                        setLayout(new GridBagLayout());
                        setPreferredSize(new Dimension(400,200));
                        setBackground(null);

                        JTextArea hint = new JTextArea(
                                "Hint: Mouse over an item to highlight its probability curve. This curve " +
                                        "shows the likelihood of the item being dropped. The left-hand " +
                                        "number is the minimum investigation needed for the item to be " +
                                        "the item to be guaranteed for drop. Everything in between is " +
                                        "probability."
                        );
                        hint.setWrapStyleWord(true);
                        hint.setLineWrap(true);
                        hint.setFont(TextFactory.LABEL_FONT);
                        hint.setForeground(new Color(180,180,180));
                        gbc.fill = 1;
                        gbc.anchor = GridBagConstraints.CENTER;
                        gbc.insets = Configuration.GRIDBAG_INSETS;
                        hint.setBackground(null);
                        hint.setPreferredSize(new Dimension(350,150));
                        JPanel p1 = new JPanel();
                        p1.add(hint);
                        p1.setBackground(null);
                        add(p1,gbc);

                        JButton ok = ButtonFactory.createButton("Ok");
                        ok.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                getObserver().dispose();
                            }
                        });

                        gbc.anchor = GridBagConstraints.CENTER;
                        JPanel p2 = new JPanel(new GridBagLayout());
                        p2.setBackground(null);
                        p2.add(ok,gbc);
                        gbc.gridy = 1;
                        gbc.fill = 0;
                        add(p2,gbc);
                    }
                };
                bw.openPage(page);
                bw.makeVisible();
            }
        });
    }

    public DropRateBagGraphPanel getGraphPanel() {
        return graphPanel;
    }

    public void setGraphPanel(DropRateBagGraphPanel graphPanel) {
        this.graphPanel = graphPanel;
    }
}
