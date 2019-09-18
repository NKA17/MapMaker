package UI.pages.paletteSelect;

import UI.app.view.ApplicationPage;
import UI.app.view.ApplicationPanel;
import UI.panels.paletteSelect.AssetSelectScrollPanel;
import application.mapEditing.toolInterfaces.IPaintTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaletteSelectPage extends ApplicationPage {

    private String baseDirectory;
    private IPaintTool paintTool;

    public PaletteSelectPage(String baseDirectory, IPaintTool paintTool){
        this.baseDirectory = baseDirectory;
        this.paintTool = paintTool;
    }

    @Override
    public void loadPage() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        ApplicationPanel assetSelectPanel = new AssetSelectScrollPanel(baseDirectory,paintTool);
        assetSelectPanel.loadPanel();

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                getObserver().dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4,4,4,10);
        add(assetSelectPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(4,4,4,10);
        add(closeBtn,gbc);

    }
}
